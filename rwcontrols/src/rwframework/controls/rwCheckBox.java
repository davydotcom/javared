/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rwframework.controls;

/**
 *
 * @author destes
 */
public class rwCheckBox extends javax.swing.JCheckBox {
    private String TableName;
    private String FieldName;
    private boolean DefaultKeyEnabled = false;
    private boolean DefaultNonKeyEnabled = true;
    public rwCheckBox(){
            super();
            this.setBackground(java.awt.Color.WHITE);
            this.setOpaque(true);
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
    
}
