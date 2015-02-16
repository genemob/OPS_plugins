/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package patmob.plugin.ops.fc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import patmob.core.Controller;
import patmob.core.TreeBranchEditor_2;
import patmob.core.TreeNodeInfoDisplayer;
import patmob.data.PatentCollectionList;
import patmob.data.PatentDocument;
import patmob.data.PatentTreeNode;
import patmob.data.ops.impl.BiblioSearchRequest;
import patmob.data.ops.impl.EquivalentsRequest;

/**
 *
 * @author Piotr
 */
public class ForwardCitations implements TreeNodeInfoDisplayer{
    private String citedPN;
    private ArrayList<PatentTreeNode> citedEquivalents = new ArrayList<>();
    PatentCollectionList citiEquivalents = new PatentCollectionList();
    private int allEquivalentsCount = 0, doneEquivalentsCount = 0;
    Controller controller;
    
    /**
     * Construct with a publication number we need forward citations for.
     * Launches request for equivalents.
     * @param pn 
     */
    public ForwardCitations(String pn, Controller con) {
        citedPN = pn;
        controller = con;
        EquivalentsRequest er = new EquivalentsRequest(citedPN, this);
        er.submit();
    }

    /**
     * EquivalentRequest launched in constructor calls displayText with results.
     * This method then submits searches for publications citing each of the
     * equivalents 
     * @param text 
     */
    @Override
    public void displayText(String text) {
        HashSet<String> equivalents = new HashSet<>();
        System.out.println(text);
        String[] textLines = text.split("\n");
        for (int i=0; i<textLines.length; i++) {
            String line = textLines[i];
            if (!line.startsWith("-") && line.contains("\t")) {
                equivalents.add(line.substring(0, line.indexOf("\t")));
            }
        }
        allEquivalentsCount = equivalents.size();
        Iterator<String> it = equivalents.iterator();
        while (it.hasNext()) {
            String equivalentPN = it.next();
            System.out.println(equivalentPN);
            BiblioSearchRequest bsr = new BiblioSearchRequest(
                    "CT=" + equivalentPN, this, null, 
                    BiblioSearchRequest.LIST_RESULT);
            bsr.submit();
        }
    }
    
    /**
     * BiblioSearchRequests launched from displayText() call displayNodeInfo()
     * with results for each equivalent. The results are organized by families.
     * @param citedNode 
     */
    @Override
    public void displayNodeInfo(PatentTreeNode citedNode) {
        System.out.println("\t" + citedNode);
        if (citedNode.size()>0) {
            citedEquivalents.add(citedNode);
            citiEquivalents.addChild(citedNode);
//            Iterator<PatentTreeNode> citingFamilies = 
//                    citedNode.getChildren().iterator();
//            while (citingFamilies.hasNext()) {
//                PatentTreeNode citingFamily = citingFamilies.next();
//                System.out.println("\t\t" + citingFamily);
//                Iterator<PatentTreeNode> citingDocs = 
//                        citingFamily.getChildren().iterator();
//                while (citingDocs.hasNext()) {
//                    PatentDocument citingDoc = (PatentDocument) citingDocs.next();
//                    System.out.println("\t\t\t" + citingDoc + citingDoc.getTitle());
//                }
//            }
        } else {
//            System.out.println("\t\t---");
        }
        if (++doneEquivalentsCount==allEquivalentsCount) {
            System.out.println("DONE: " + citedEquivalents);
            new TreeBranchEditor_2(citiEquivalents, controller).setVisible(true);
        }
    }

}
