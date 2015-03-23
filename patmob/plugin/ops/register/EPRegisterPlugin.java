package patmob.plugin.ops.register;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import patmob.core.PatmobPlugin;
import static patmob.core.PatmobPlugin.coreAccess;
import patmob.core.TreeBranchEditor_2;
import patmob.data.PatentTreeNode;
import patmob.data.ops.impl.RegisterRequest;
import patmob.data.ops.impl.RegisterRequestParams;

/**
 *
 * @author Piotr
 */
public class EPRegisterPlugin implements PatmobPlugin {
    EPRegisterFrame registerFrame;

    @Override
    public String getName() {
        return "European Patent Register Plugin";
    }

    @Override
    public void doJob() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                registerFrame = new EPRegisterFrame(EPRegisterPlugin.this);
                registerFrame.setVisible(true);
            }
        });
    }
    
    public void submitSearchRequest(RegisterRequestParams searchParams) {
//        RegisterRequestParams searchParams = 
//                new RegisterRequestParams("(pa = sanofi and pn = ep) and pd >= 2009");
        RegisterRequest rr = new RegisterRequest(searchParams);
//        rr.submit();
        
        searchParams = rr.submitCall();
        PatentTreeNode patents = searchParams.getPatents();
        if (patents!=null) {
            switch (searchParams.getResultType()) {
                case RegisterRequestParams.SAMPLE_RESULT:
                    //show editor window
                    new TreeBranchEditor_2(
                        patents, coreAccess.getController()).setVisible(true);                    
                    break;
                case RegisterRequestParams.ALL_RESULT:
                    //write to file
                    System.out.println(patents.getDescription());
            }
        } else {
            System.out.println("submitSearchRequest: NULL response");
        }
    }
    
    public void submitBiblioRequest() {
        ArrayList<String> patentList = new ArrayList<>();
        //read list of PNs from user-selected text file
        JFileChooser fc = new JFileChooser();
        int i = fc.showOpenDialog(null);
        if (i==JFileChooser.APPROVE_OPTION) {
            File patentListFile = fc.getSelectedFile();
            try {
                try (BufferedReader br = new BufferedReader(
                        new FileReader(patentListFile))) {
                    String line;
                    while ((line=br.readLine())!=null) {
                        if (line.contains(" ")) {
                            //skip kind code etc,
                            patentList.add(line.substring(0, line.indexOf(" ")));
                        } else {
                            patentList.add(line);
                        }
                    }
                }
            } catch (Exception x) {
                System.out.println("OpsRequestTestPlugin: " + x);
            }
        }
        String[] pubNums = patentList.toArray(new String[0]);
        RegisterRequestParams searchParams = new RegisterRequestParams(pubNums);
        RegisterRequest rr = new RegisterRequest(searchParams);
        rr.submit();
    }
    
}
