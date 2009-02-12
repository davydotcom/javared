/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rwframework.controls;

/**
 *
 * @author destes
 */
public class DCOMActiveField {
    public String FieldName;
    public String DataType;
    public java.awt.Component LinkedObject = null;
    public String Value = null;
    public String isNull = "";
    public String DefaultValue = "";
    public String Key = "";
    public String Extra = "";
    public String OriginalValue = "";
    public DCOMActiveField(String FieldName,String DataType,String isNull,String Key,String DefaultValue,String Extra)
    {
        this.FieldName = FieldName;
        this.DataType = DataType;
        this.Extra = Extra;
        this.isNull = isNull;
        this.DefaultValue = DefaultValue;
        this.Value = DefaultValue;
        this.Key = Key;
    }
    public void updateObject()
    {
        //Need to setup update procedures based on Object class Name as well as DataType
    }
    public void setValue(String Value)
    {
        if(Value == null)
            this.Value = "NULL";
        else
            this.Value = Value;
        
        if(LinkedObject != null)
        {
            if(LinkedObject.getClass().getSimpleName().equals("rwTextField"))
            {
                rwTextField thisTextField = (rwTextField)LinkedObject;
                if(this.Value.equals("NULL"))
                {
                    thisTextField.PreviousValue = "";
                    thisTextField.setText("");
                }
                else{
                    thisTextField.PreviousValue = this.Value;
                    thisTextField.setText(this.Value);
                }
                thisTextField.setEnabled(thisTextField.isDefaultNonKeyEnabled());
            }
            else if(LinkedObject.getClass().getSimpleName().equals("rwCheckBox"))
            {
                rwCheckBox thisCheckBox = (rwCheckBox)LinkedObject;
                if(this.Value.equals("1"))
                    thisCheckBox.setSelected(true);
                else
                    thisCheckBox.setSelected(false);
                thisCheckBox.setEnabled(thisCheckBox.isDefaultNonKeyEnabled());
            }
            else if(LinkedObject.getClass().getSimpleName().equals("rwComboBox"))
            {
                rwComboBox thisComboBox = (rwComboBox)LinkedObject;
                thisComboBox.setSelectedFromID(this.Value);
                thisComboBox.setEnabled(thisComboBox.isDefaultNonKeyEnabled());
            }
            else
            {
                LinkedObject.setEnabled(true);
            }
        }
    }
    public boolean verifyObject()
    {
        return true;
    }
    public void updateValue()
    {
        if(LinkedObject != null)
        {
            if(!verifyObject())
                return;
                    
            if(LinkedObject.getClass().getSimpleName().equals("rwTextField"))
            {
                rwTextField thisTextField = (rwTextField)LinkedObject;
                Value = thisTextField.getText();
            }
            else if(LinkedObject.getClass().getSimpleName().equals("rwCheckBox"))
            {
                rwCheckBox thisCheckBox = (rwCheckBox)LinkedObject;
                if(thisCheckBox.isSelected())
                    Value = "1";
                else
                    Value = "0";
            }
            else if(LinkedObject.getClass().getSimpleName().equals("rwComboBox"))
            {
                rwComboBox thisComboBox = (rwComboBox)LinkedObject;
                Value = thisComboBox.getIDFromSelected();
            }
           
        }
    }
}
