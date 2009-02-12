/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rwframework.controls;

import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author destes
 */
public class rwTextField extends javax.swing.JFormattedTextField {

    private String TableName;
    private String FieldName;
    private String[] BrowseString;
    rwBrowseDialog BrowseDialog = null;
    private boolean DefaultKeyEnabled = false;
    private boolean DefaultNonKeyEnabled = true;
    private cDataHandler DataHandler;
    public String PreviousValue = "";
    public boolean hideDialog = false;
    private String BrowseFieldName = null;
    public rwTextField() {
        super();
        this.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        this.setForeground(java.awt.Color.BLUE);
        this.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKeyReleased(evt);
            }
        });
        this.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
                System.out.println("FIELD CHANGED VALUE!");
                showBrowseDialog();
            }

            public void removeUpdate(DocumentEvent e) {
                System.out.println("FIELD REMOVED VALUE!");
                updateBrowseDialog();

            }

            public void insertUpdate(DocumentEvent e) {
                System.out.println("FIELD INSERT VALUE!");
                showBrowseDialog();
                
            }
        });
        

        this.addFocusListener(new java.awt.event.FocusAdapter() {

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (!evt.isTemporary()) {
                    System.out.println("Focus Lost!");
                    hideBrowseDialog();
                } else {
                    // System.out.println("temp focus lost");
                }
            }

            public void focusGained(java.awt.event.FocusEvent evt) {
                if (!evt.isTemporary()) {
                    hideDialog=false;
                } else {
                    // System.out.println("temp focus gain");
                }
            }
        });

    }

    public void hideBrowseDialog() {

        if (BrowseDialog != null) {
            hideDialog = true;


            int delay = 500; //milliseconds

            ActionListener taskPerformer = new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    if(hideDialog)
                        BrowseDialog.setVisible(false);
                }
            };
            new Timer(delay, taskPerformer).start();
            

        // BrowseDialog.setVisible(false);
        }
    }
    public void updateBrowseDialog()
    {
        if(BrowseDialog != null)
        {
            this.BrowseDialog.SearchString = this.getText();
            this.BrowseDialog.PopulateTable();
        }
    }
    public void showBrowseDialog() {
        if (BrowseDialog != null) {
        System.out.println("Value of Previous: " + PreviousValue + ", Current: " + this.getText());
        if (!PreviousValue.equals(this.getText())) {
            PreviousValue = this.getText();
            
                this.BrowseDialog.SearchString = this.getText();
                this.BrowseDialog.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y + this.getHeight());
                if (!this.BrowseDialog.isVisible()) {
                    this.BrowseDialog.setVisible(true);
                }
                this.requestFocus();
                this.BrowseDialog.PopulateTable();
            }
        }
    }

    public void txtKeyReleased(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == evt.VK_ESCAPE) {
            if (this.BrowseDialog != null && this.BrowseDialog.isVisible()) {
                this.BrowseDialog.setVisible(false);
            }
        }
        if(evt.getKeyCode() == evt.VK_F2)
        {
            this.showBrowseDialog();
        }

    }

    public void invertBorderColor() {
        this.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(1, 1, 1)));
        this.setForeground(java.awt.Color.WHITE);
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String TableName) {
        this.TableName = TableName;
    }

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String FieldName) {
        this.FieldName = FieldName;
    }

    public boolean isDefaultKeyEnabled() {
        return DefaultKeyEnabled;
    }

    public void setDefaultKeyEnabled(boolean DefaultKeyEnabled) {
        this.DefaultKeyEnabled = DefaultKeyEnabled;
    }

    public boolean isDefaultNonKeyEnabled() {
        return DefaultNonKeyEnabled;
    }

    public void setDefaultNonKeyEnabled(boolean DefaultNonKeyEnabled) {
        this.DefaultNonKeyEnabled = DefaultNonKeyEnabled;
    }

    public String[] getBrowseString() {
        return BrowseString;
    }

    public void setBrowseString(String[] BrowseString) {
        this.BrowseString = BrowseString;
        if(BrowseFieldName == null)
          BrowseDialog = new rwBrowseDialog(this.TableName, this.FieldName, DataHandler, BrowseString, this);
        else
            BrowseDialog = new rwBrowseDialog(this.TableName, this.BrowseFieldName, DataHandler, BrowseString, this);
    }

    public cDataHandler getDataHandler() {
        return DataHandler;
    }

    public void setDataHandler(cDataHandler DataHandler) {
        this.DataHandler = DataHandler;
    }

    public void selectValue(String Value) {
        this.setText(Value);
        if (BrowseDialog != null) {
            this.BrowseDialog.setVisible(false);
        }
        this.requestFocus();
    }

    public String getBrowseFieldName() {
        return BrowseFieldName;
    }

    public void setBrowseFieldName(String BrowseFieldName) {
        this.BrowseFieldName = BrowseFieldName;
    }
}
