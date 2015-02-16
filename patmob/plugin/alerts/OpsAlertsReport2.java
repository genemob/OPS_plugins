package patmob.plugin.alerts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import patmob.core.TreeNodeInfoDisplayer;
import patmob.data.PatentCollectionMap;
import patmob.data.PatentTreeNode;
import patmob.data.ops.impl.EquivalentsRequest;
import patmob.data.ops.impl.LegalRequest;

/**
 * Submits EquivalentsRequest and, after parsing results, appropriate
 * LegalRequests. After last one completes, gets bibliographic info from
 * patentDocs and writes the report file.
 * @author Piotr
 */
public class OpsAlertsReport2 implements TreeNodeInfoDisplayer {
    OpsAlertsPlugin2 alertsPlugin;
    String patentNumber, redDate, legalCountries, resultFolderPath,
            equivalentsText, patentTitle = "",
            
            epUsGrants = "", keyLegalEvents = "";
    PatentCollectionMap patentDocs;
    ArrayList<OpsLegalReport2> legalReports = new ArrayList<>();
    int completeLegalReports = 0,
            newEquivalents = 0,
            newLegalEvents = 0;
    
    //called when writting summary
    public String getPatentNumber() {return patentNumber;}
    public String getPatentTitle() {return patentTitle;}
    public int equivalentsFound() {return newEquivalents;}
    public int legalEventsFound() {return newLegalEvents;}
    
    public String getEpUsGrants() {return epUsGrants;}
    public String getKeyLegalEvents() {return keyLegalEvents;}
    public void appendKeyLegalEvents (String newEvent) {
        keyLegalEvents = keyLegalEvents.concat(newEvent).concat("  ");
    }

    //submit an EquivalentsRequest in the constructor
    public OpsAlertsReport2(String patNumber, PatentCollectionMap patDocs,
            String cutoffDate, String countries, String folder, 
            OpsAlertsPlugin2 plugin) {
        patentNumber = patNumber;
        patentDocs = patDocs;
        redDate = cutoffDate;
        legalCountries = countries;
        alertsPlugin = plugin;
        resultFolderPath = folder;
        
        EquivalentsRequest er = new EquivalentsRequest(
                patentNumber, this, false);
        er.submit();
    }
    
    //save and parse text from EquivalentsRequest, submit LegalRequests
    @Override
    public void displayText(String text) {
        equivalentsText = parseEquivalents(text);
        if (legalReports.isEmpty()) {
            writeReport();
        }
    }
    
    /**
     * Called when EquivalentsRequest is done. Parses the output to highlight
     * publications with pub date after cutoff and submits LegalRequests.
     * @param text
     * @return 
     */
    String parseEquivalents(String text) {
        String[] lines = text.split("\n");
        boolean sameCycle = false;
        
try {
        for (int i=0; i<lines.length; i++) {
            String myLine = lines[i];
            if (!myLine.startsWith("-")) {
                String[] pubData = myLine.split("\t");
                //RED if after cutoff
                if (pubData[2].compareTo(redDate)>0) {
                    newEquivalents++;
                    lines[i] = "<font color=red><b>" + myLine + "</b></font>";
//>>>>>>>>>>>>>>>>> save pn if US or EP grant <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<                    
                    if (pubData[0].startsWith("US") && pubData[1].startsWith("B")) {
                        epUsGrants = epUsGrants.concat(pubData[0].concat(".")
                                .concat(pubData[1]).concat(" "));
                    } else if (pubData[0].startsWith("EP") && pubData[1].startsWith("B")) {
                        epUsGrants = epUsGrants.concat(pubData[0].concat(".")
                                .concat(pubData[1]).concat(" "));
                    }  
                }
                
                //get legal status for user-defined authorities
                if (legalCountries.contains(pubData[0].substring(0,2)) &&
                        !sameCycle) {
                    sameCycle = true;
                    
                    //submit and save legal request
                    String docdbPN = pubData[0].substring(0,2) + "." +
                            pubData[0].substring(2) + "." + pubData[1];
                    OpsLegalReport2 legal = new OpsLegalReport2(
                            docdbPN, redDate, this);
                    legalReports.add(legal);
                    LegalRequest lr = new LegalRequest(docdbPN, legal);
                    lr.submit();
                }
            } else {
                sameCycle = false;
            }
        }
} catch (Exception x) {
    System.out.println(x);
    System.out.println("parseEquivalents: " + text);
}

        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append("\n");
        }

        return sb.toString();
    }
    
    //called by OpsLegalReport2 when done
    public void legalReportCompleted() {
        if (++completeLegalReports==legalReports.size()) {
            writeReport();
        }
    }
    
    //called by OpsLegalReport2 when new event found
    public void newLegalEvent() {
        newLegalEvents++;
    }
    
    //write report to file when last LegalRequest returns
    public void writeReport() {
        try {
            String biblio = patentDocs.get(patentNumber).getInfo();
            patentTitle = biblio.substring(
                    biblio.indexOf("TI: ")+4, 
                    biblio.indexOf("PA: ")-1).trim();
            
            File reportFile = new File(resultFolderPath, 
                    patentNumber + "_" + patentTitle + ".doc");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile))) {
                bw.write("<html><head><title>" + patentNumber + "</title></head>");
                bw.newLine();
                
                //biblio
                bw.write("<body><h1><font color=blue>" + patentNumber + "</font></h1>");
                bw.newLine();
                bw.write("<pre><b>");
                biblio = biblio.replace("AB: ", "</b>AB: ");
//                bw.write(biblio.substring(0, biblio.indexOf("--------------------")));
                bw.write(biblio);
                bw.newLine();
                bw.write("</pre>");
                
                //equivalent list
                bw.write("<h2><font color=blue>" + patentNumber + " Equivalents</font></h2>");
                bw.newLine();
                bw.write("<pre>" + equivalentsText);
                bw.newLine();
                bw.write("</pre>");
                
                //legal status info
                for (int i=0; i<legalReports.size(); i++) {
                    bw.write("<h2><font color=blue>" + legalReports.get(i).getPublicationNumber()
                            + " Legal Status</font></h2>");
                    bw.newLine();
                    bw.write("<pre>" + legalReports.get(i).getLegalText());
                    bw.newLine();
                    bw.write("</pre>");
                }
                bw.newLine();
                bw.write("</body></html>");
            }
            
            alertsPlugin.opsAlertCompleted();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
        
    @Override
    public void displayNodeInfo(PatentTreeNode node) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
