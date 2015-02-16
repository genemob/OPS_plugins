package patmob.plugin.ops.fc;

import javax.swing.JOptionPane;
import patmob.core.PatmobPlugin;

/**
 *
 * @author Piotr
 */
public class OpsFcPlugin implements PatmobPlugin  {

    @Override
    public String getName() {
        return "OPS Forward Citations";
    }

    @Override
    public void doJob() {
        String userPN = JOptionPane.showInputDialog("Please enter PN");
        System.out.println(userPN);
        
        ForwardCitations fc = new ForwardCitations(userPN, coreAccess.getController());
        
        // 1. Get equivalents for userPN
        // 2. For each equivalent, get forward citations
        // 3. For each citation, get biblio and info about 
    }
    
}
