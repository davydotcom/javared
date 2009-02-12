/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rwframework.controls;

/**
 *
 * @author destes
 */
public class rwComboBox extends javax.swing.JComboBox {
    private String TableName;
    private String FieldName;
    private String SourceTableName=null;
    private String SourceReturnField=null;
    private String Conditions=null;
    private String SourceDisplayField=null;
    private cDataHandler DataHandler=null;
    private boolean DefaultNonKeyEnabled=true;
    private boolean DefaultKeyEnabled=false;
    private boolean insertBlankItem = true;
    private java.util.ArrayList ResultSet;
    public rwComboBox()
    {
        super();
        this.setBackground(java.awt.Color.WHITE);
        this.setForeground(java.awt.Color.BLUE);
    }
    public void populateCollection()
    {
        DCOMActiveRecord startRecord = new DCOMActiveRecord(DataHandler,SourceTableName);
        ResultSet = startRecord.find_all(DataHandler, SourceTableName, null, null);
        if(ResultSet == null)
            return;
        if(insertBlankItem)
            this.addItem("");
        for(int x=0;x<ResultSet.size();x++)
        {
            this.addItem(((DCOMActiveRecord)(ResultSet.get(x))).getValue(SourceDisplayField));
        }
        
    }
    
    public String getIDFromSelected() {
        if(ResultSet == null) {
            return Integer.toString(this.getSelectedIndex());
        }
        DCOMActiveRecord FieldData = null;
        if(insertBlankItem)
        {
            if(this.getSelectedIndex() == 0)
                return null;
            FieldData = (DCOMActiveRecord)(ResultSet.get(this.getSelectedIndex()-1));
        }
        else
            FieldData = (DCOMActiveRecord)(ResultSet.get(this.getSelectedIndex()));
          
        return FieldData.getValue(SourceReturnField).toString();
    }
    public String getTableName() {
        return TableName;
    }
    public void setSelectedFromID(String Value)
    {
       if(ResultSet == null)
       {
           if(Value.equals("NULL") || Value.equals("") || Value == null)
               this.setSelectedIndex(0);
           else
            this.setSelectedIndex(Integer.parseInt(Value));
           
           return;
       }
        if(insertBlankItem && (Value == null || Value.equals("NULL") || Value.equals("")))
        {
            this.setSelectedIndex(0);
            return;
        }
        for(int x=0;x<ResultSet.size();x++)
        {
            if(((DCOMActiveRecord)(ResultSet.get(x))).getValue(SourceReturnField).equals(Value))
            {
                if(insertBlankItem)
                    this.setSelectedIndex(x+1);
                else
                    this.setSelectedIndex(x);
                break;
            }
        } 
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

    public String getSourceTableName() {
        return SourceTableName;
    }

    public void setSourceTableName(String SourceTableName) {
        this.SourceTableName = SourceTableName;
        if(this.SourceTableName != null && this.SourceDisplayField != null && this.SourceReturnField != null && this.DataHandler != null)
        {
            populateCollection();
        }
    }

    public String getSourceReturnField() {
        return SourceReturnField;
    }

    public void setSourceReturnField(String SourceReturnField) {
        this.SourceReturnField = SourceReturnField;
         if(this.SourceTableName != null && this.SourceDisplayField != null && this.SourceReturnField != null && this.DataHandler != null)
        {
            populateCollection();
        }
    }

    public String getSourceDisplayField() {
        return SourceDisplayField;
    }

    public void setSourceDisplayField(String SourceDisplayField) {
        this.SourceDisplayField = SourceDisplayField;
         if(this.SourceTableName != null && this.SourceDisplayField != null && this.SourceReturnField != null && this.DataHandler != null)
        {
            populateCollection();
        }
    }

    public boolean isDefaultNonKeyEnabled() {
        return DefaultNonKeyEnabled;
    }

    public void setDefaultNonKeyEnabled(boolean DefaultNonKeyEnabled) {
        this.DefaultNonKeyEnabled = DefaultNonKeyEnabled;
    }

    public boolean isDefaultKeyEnabled() {
        return DefaultKeyEnabled;
    }

    public void setDefaultKeyEnabled(boolean DefaultKeyEnabled) {
        this.DefaultKeyEnabled = DefaultKeyEnabled;
    }

    public cDataHandler getDataHandler() {
        return DataHandler;
    }

    public void setDataHandler(cDataHandler DataHandler) {
        this.DataHandler = DataHandler;
         if(this.SourceTableName != null && this.SourceDisplayField != null && this.SourceReturnField != null && this.DataHandler != null)
        {
            populateCollection();
        }
    }

    public String getConditions() {
        return Conditions;
    }

    public void setConditions(String Conditions) {
        this.Conditions = Conditions;
         if(this.SourceTableName != null && this.SourceDisplayField != null && this.SourceReturnField != null && this.DataHandler != null)
        {
            populateCollection();
        }
    }
}
