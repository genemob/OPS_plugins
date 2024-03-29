package patmob.plugin.ops;

import patmob.core.TreeNodeInfoDisplayer;
import patmob.data.PatentTreeNode;
import patmob.data.ops.impl.BiblioSearchRequest;

/**
 *
 * @author Piotr
 */
public class OpsFrame extends javax.swing.JFrame
        implements TreeNodeInfoDisplayer{
    OpsPlugin opsPlugin;
    
    public OpsFrame() {
        initComponents();
    }
    
    public OpsFrame(OpsPlugin op) {
        this();
        opsPlugin = op;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        notificationTextArea = new javax.swing.JTextArea();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        queryTextArea = new javax.swing.JTextArea();
        allButton = new javax.swing.JButton();
        sampleButton = new javax.swing.JButton();
        clearButton_BS = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        cpcTextArea = new javax.swing.JTextArea();
        cpcButton = new javax.swing.JButton();
        clearButton_CS = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("REST@OPS Web Services");

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        notificationTextArea.setColumns(20);
        notificationTextArea.setRows(5);
        notificationTextArea.setWrapStyleWord(true);
        jScrollPane2.setViewportView(notificationTextArea);

        jSplitPane1.setBottomComponent(jScrollPane2);

        queryTextArea.setColumns(20);
        queryTextArea.setLineWrap(true);
        queryTextArea.setRows(5);
        queryTextArea.setText("\"antibody drug conjugate*\"");
        queryTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(queryTextArea);

        allButton.setText("All Results");
        allButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allButtonActionPerformed(evt);
            }
        });

        sampleButton.setText("Sample Results");
        sampleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sampleButtonActionPerformed(evt);
            }
        });

        clearButton_BS.setText("Clear");
        clearButton_BS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButton_BSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(sampleButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(allButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton_BS)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(clearButton_BS)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(sampleButton)
                            .addContainerGap()))
                    .addComponent(allButton)))
        );

        jTabbedPane2.addTab("Biblio Search", jPanel1);

        cpcTextArea.setColumns(20);
        cpcTextArea.setLineWrap(true);
        cpcTextArea.setRows(5);
        cpcTextArea.setText("\"antibody drug conjugate*\"");
        cpcTextArea.setWrapStyleWord(true);
        jScrollPane3.setViewportView(cpcTextArea);

        cpcButton.setText("Get CPC Symbols");
        cpcButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cpcButtonActionPerformed(evt);
            }
        });

        clearButton_CS.setText("Clear");
        clearButton_CS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButton_CSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cpcButton)
                        .addGap(4, 4, 4)
                        .addComponent(clearButton_CS)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cpcButton)
                    .addComponent(clearButton_CS))
                .addContainerGap())
        );

        jTabbedPane2.addTab("CPC Search", jPanel2);

        jSplitPane1.setLeftComponent(jTabbedPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void allButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allButtonActionPerformed
        // TODO add your handling code here:
        opsPlugin.biblioSearch(queryTextArea.getText(),
                BiblioSearchRequest.FULL_RESULT);        
    }//GEN-LAST:event_allButtonActionPerformed

    private void sampleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sampleButtonActionPerformed
        // TODO add your handling code here:
        opsPlugin.biblioSearch(queryTextArea.getText(),
                BiblioSearchRequest.SAMPLE_RESULT);
    }//GEN-LAST:event_sampleButtonActionPerformed

    private void cpcButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cpcButtonActionPerformed
        opsPlugin.cpcSearch(cpcTextArea.getText());
    }//GEN-LAST:event_cpcButtonActionPerformed

    private void clearButton_CSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButton_CSActionPerformed
        cpcTextArea.setText("");
    }//GEN-LAST:event_clearButton_CSActionPerformed

    private void clearButton_BSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButton_BSActionPerformed
        queryTextArea.setText("");
    }//GEN-LAST:event_clearButton_BSActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OpsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OpsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OpsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OpsFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OpsFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton allButton;
    private javax.swing.JButton clearButton_BS;
    private javax.swing.JButton clearButton_CS;
    private javax.swing.JButton cpcButton;
    private javax.swing.JTextArea cpcTextArea;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea notificationTextArea;
    private javax.swing.JTextArea queryTextArea;
    private javax.swing.JButton sampleButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void displayNodeInfo(PatentTreeNode node) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void displayText(String text) {
        notificationTextArea.append(text + "\n");
    }
}
