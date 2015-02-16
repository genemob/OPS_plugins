package patmob.plugin.alerts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import patmob.core.Controller;
import patmob.core.PatmobPlugin;
import patmob.data.PatentCollectionMap;
import patmob.data.ops.impl.BulkBiblioRequest;

/**
 * Generates reports for a list of publication numbers, 
 * by instantiating a OpsAlertsReport2 object for each number,
 * @author Piotr
 */
public class OpsAlertsPlugin2 implements PatmobPlugin {
    Controller controller = coreAccess.getController();
    OpsAlertsFrame2 opsAlertsFrame;
    PatentCollectionMap patentDocs;
    String cutoffDate, legalAuthorities, resultsFolder,
            updateStartTime, updateEndTime;
    ArrayList<OpsAlertsReport2> opsAlerts;
    int completeOpsAlerts;

    public static ArrayList<String> goodLegalEvents = new ArrayList<>(
            Arrays.asList("INTG", "26N")),
    //INTG  ANNOUNCEMENT OF INTENTION TO GRANT
    //26N   NO OPPOSITION FILED
            badLegalEvents = new ArrayList<>(
                    Arrays.asList("18D", "18W"));
    //PG25  LAPSED IN A CONTRACTING STATE ANNOUNCED VIA POSTGRANT INFORM. FROM NAT. OFFICE TO EPO
    //      "PG25", removed 201502216
    //18D   DEEMED TO BE WITHDRAWN
    //18W   WITHDRAWN
    
    @Override
    public String getName() {
//        return "OPS ALERTS 2";
        return "OPS Patent Monitoring";
    }

    @Override
    public void doJob() {
        //show OpsAlertsFrame2 with params from last run
        opsAlertsFrame = new OpsAlertsFrame2(this,
                controller.getPatmobProperty("ops.alerts.cutoffDate"),
                controller.getPatmobProperty("ops.alerts.legalAuthorities"),
                controller.getPatmobProperty("ops.alerts.resultsFolder"));
        opsAlertsFrame.setVisible(true);
    }
    
    /**
     * Called from OpsAlertsFrame2.
     * @param docs
     * @param pd
     * @param cc
     * @param folder 
     */
    public void runAlerts(PatentCollectionMap docs, String pd, String cc,
            String folder) {
        patentDocs = docs;
        cutoffDate = pd;
        legalAuthorities = cc;
        resultsFolder = folder;
        
        updateStartTime = timestamp(true);
        
        //get biblios
        BulkBiblioRequest bbr = new BulkBiblioRequest(patentDocs);
        bbr.submit();
        
        //submit alert reports for all patent publication numbers
        opsAlerts = new ArrayList<>();
        completeOpsAlerts = 0;
        Iterator<String> it = patentDocs.keySet().iterator();
        while (it.hasNext()) {
            OpsAlertsReport2 alert = new OpsAlertsReport2(it.next(), patentDocs,
                    cutoffDate, legalAuthorities, resultsFolder, this);
            opsAlerts.add(alert);
        }
        
        //save properties
        controller.setPatmobProperty("ops.alerts.cutoffDate", cutoffDate);
        controller.setPatmobProperty("ops.alerts.legalAuthorities", legalAuthorities);
        controller.setPatmobProperty("ops.alerts.resultsFolder", resultsFolder);
        controller.savePatmobProperties();
    }
    
    //called from OpsAlertsReport2 when done
    public void opsAlertCompleted() {
        if (++completeOpsAlerts==opsAlerts.size()) {
            updateEndTime = timestamp(true);
            writeSummary();
        }
    }
    
    private void writeSummary() {
        File reportFile = new File(resultsFolder, 
                "UPDATE_SUMMARY_" + timestamp(false) + ".xls");
        
        //java 7 try-with-resources statement
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile))) {
            writeLegend(bw);

            bw.write("Publication Number\t" + "Publication Title\t" +
                    "New Equivalents\t" + "New US/EP Grants\t" + 
                    "New Legal Events\t" + "Key Legal Events");
            bw.newLine();
            for (int i=0; i<opsAlerts.size(); i++) {
                bw.write(opsAlerts.get(i).getPatentNumber() + "\t" +
                        opsAlerts.get(i).getPatentTitle() + "\t" +
                        opsAlerts.get(i).equivalentsFound() + "\t" +
                        opsAlerts.get(i).getEpUsGrants() + "\t" +
                        opsAlerts.get(i).legalEventsFound() + "\t" +
                        opsAlerts.get(i).getKeyLegalEvents());
                bw.newLine();
            }
        } catch (Exception x) {x.printStackTrace();}
    }
    
    private String timestamp(boolean full) {
        if (full) {
            DateFormat dateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.FULL, DateFormat.FULL);
            return dateFormat.format(Calendar.getInstance().getTime());
        } else {
            Calendar calendar = Calendar.getInstance();
            return Integer.toString(calendar.get(Calendar.YEAR)) +
                    Integer.toString(calendar.get(Calendar.MONTH)+1) +          //Jan is 0
                    Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) +
                    "_" +
                    Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)) +
                    Integer.toString(calendar.get(Calendar.MINUTE)) +
                    Integer.toString(calendar.get(Calendar.SECOND));
        }
    }
    
    private void writeLegend(BufferedWriter bw) throws IOException {
            bw.write("Update started:  " + updateStartTime + ".");
            bw.newLine();
            bw.write("Update finished:  " + updateEndTime + ".");
            bw.newLine();
            bw.write("For all publications listed below, the following "
                    + "information was fetched from EPO OPS Web Services:");
            bw.newLine();
            bw.write("    1. Bibliographic data.");
            bw.newLine();
            bw.write("    2. Espacenet equivalents (patent documents with the "
                    + "same priorities).");
            bw.newLine();
            bw.write("    3. Legal data for equivalents from the following "
                    + "authorities:  " + legalAuthorities + ".");
            bw.newLine();
            bw.write("This information is included in the attached reports, "
                    + "highlighting in red the recent data.");
            bw.newLine();
            bw.write("Equivalents and legal events are highlighted if "
                    + "published after this cut-off date:  " + 
                    cutoffDate.substring(0,4) + "-" + 
                    cutoffDate.substring(4,6) + "-" + cutoffDate.substring(6));
            bw.newLine();
            bw.write("The table below summarizes the highlight count for all "
                    + "monitored patent publications.");
            bw.newLine();
            bw.write("Also listed for the monitored period are the US and EP patent"
                    + " grants and the following legal events:");
            bw.newLine();
            bw.write("    INTG  ANNOUNCEMENT OF INTENTION TO GRANT");
            bw.newLine();
            bw.write("    26N   NO OPPOSITION FILED");
            bw.newLine();
            bw.write("    PG25  LAPSED IN A CONTRACTING STATE ANNOUNCED VIA POSTGRANT INFORM. FROM NAT. OFFICE TO EPO");
            bw.newLine();
            bw.write("    18D   DEEMED TO BE WITHDRAWN");
            bw.newLine();
            bw.write("    18W   WITHDRAWN");
            bw.newLine();
            bw.newLine();
    }
}