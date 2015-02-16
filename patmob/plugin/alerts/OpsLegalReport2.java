package patmob.plugin.alerts;

import java.util.ArrayList;
import java.util.Arrays;
import patmob.core.TreeNodeInfoDisplayer;
import patmob.data.PatentTreeNode;

/**
 *
 * @author Piotr
 */
public class OpsLegalReport2 implements TreeNodeInfoDisplayer {
    String docdbPN, legalText, legalDate;
    OpsAlertsReport2 opsReport;
    
    public OpsLegalReport2 (String pn, String cutoffDate, OpsAlertsReport2 oar) {
        docdbPN = pn;
        opsReport = oar;
        legalDate = cutoffDate.substring(0,4) + "-" + 
                cutoffDate.substring(4,6) + "-" + cutoffDate.substring(6);
    }
    
    public String getLegalText() {
        return legalText;
    }
    
    public String getPublicationNumber() {
            return docdbPN;
    }
    
    @Override
    public void displayText(String text) {
        legalText = parseLegalStatus(text);
        opsReport.legalReportCompleted();
    }
    
    private String parseLegalStatus(String text) {
        String[] lines = text.split("\n");
try {
        for (int i=0; i<lines.length; i++) {
            String myLine = lines[i];
            String[] pubData = myLine.split("\t");
            //RED if after cutoff
            if (pubData[0].compareTo(legalDate)>0) {
                opsReport.newLegalEvent();
                lines[i] = "<font color=red><b>" + myLine + "</b></font>";
//>>>>>>>>>>>>>>>>> save pn and event code if significant <<<<<<<<<<<<<<<<<<<<<<
                String eventCode = pubData[1].trim();
                if (OpsAlertsPlugin2.goodLegalEvents.contains(eventCode)) {
                    opsReport.appendKeyLegalEvents("[+]" + eventCode +":"+ docdbPN);
                } else if (OpsAlertsPlugin2.badLegalEvents.contains(eventCode)) {
                    opsReport.appendKeyLegalEvents("[-]" + eventCode +":"+ docdbPN);
                }
            }
        }
} catch (Exception x) {
    System.out.println(x);
    System.out.println("parseLegalStatus: " + text);
}
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public void displayNodeInfo(PatentTreeNode node) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
