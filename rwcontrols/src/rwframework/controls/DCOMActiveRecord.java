/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rwframework.controls;

import java.util.ArrayList;
import java.sql.*;
import java.io.*;

/**
 *
 * @author destes
 */
public class DCOMActiveRecord {

    public cDataHandler DataHandler;
    private String TableName = "";
    public boolean Loaded = false;
    public boolean Modified = false;
    private ArrayList FieldSet = null;

    public DCOMActiveRecord() {
    }

    public DCOMActiveRecord(cDataHandler DataHandler) {
        String TableName;
        String ClassName = this.getClass().getSimpleName();
        System.out.println("Finding Table Name");
        for(int x=1;x<ClassName.length();x++)
        {
            if(ClassName.toCharArray()[x] >= (int)'A' && ClassName.toCharArray()[x] <= (int)'Z')
            {
                ClassName = ClassName.substring(0,x) + "_" + ClassName.substring(x);
                x++;
            }
        }
        System.out.println("Done FInding Capital Letters");
        if (ClassName.endsWith("y")) {
            TableName = ClassName.toLowerCase().substring(0, ClassName.length() - 1) + "ies";
        } else {
            TableName = ClassName.toLowerCase() + "s";
        }
        DataHandler.ErrorHandler.printLog("DCOMActiveRecord", "Constructor", "TableName detected from classname as " + TableName);
        this.DataHandler = DataHandler;
        this.TableName = TableName;
        reInitialize();
    }

    public DCOMActiveRecord(cDataHandler DataHandler, String TableName) {
        this.DataHandler = DataHandler;
        this.TableName = TableName;
        reInitialize();

    }

    public static String escape(String Text) {
        if (Text == null || Text.equals("NULL")) {
            return "NULL";
        }
        String RetVal = Text.replaceAll("'", "''");
        try {
            int test = Integer.parseInt(RetVal);
        } catch (NumberFormatException e) {
            RetVal = "'" + RetVal + "'";
        }
        return RetVal;
    }

    public DCOMActiveRecord find_by_id(cDataHandler DataHandler, String Value) {
        String TableName;
        String ClassName = this.getClass().getSimpleName();
        System.out.println("Finding Table Name");
        for(int x=1;x<ClassName.length();x++)
        {
            if(ClassName.toCharArray()[x] >= (int)'A' && ClassName.toCharArray()[x] <= (int)'Z')
            {
                ClassName = ClassName.substring(0,x) + "_" + ClassName.substring(x);
                x++;
            }
        }
        System.out.println("Done FInding Capital Letters");
        if (ClassName.endsWith("y")) {
            TableName = ClassName.toLowerCase().substring(0, ClassName.length() - 1) + "ies";
        } else {
            TableName = ClassName.toLowerCase() + "s";
        }
        return find_by_id(DataHandler, TableName, Value);
    }

    public DCOMActiveRecord find_by_id(cDataHandler DataHandler, String TableName, String Value) {
        DCOMActiveRecord newActiveRecord = new DCOMActiveRecord(DataHandler, TableName);
        newActiveRecord.setValue("id", Value);
        if (newActiveRecord.load()) {
            return newActiveRecord;
        } else {
            return null;
        }
    }

    public static DCOMActiveRecord find_first(cDataHandler DataHandler, String TableName, String Conditions) {
        DCOMActiveRecord newActiveRecord = new DCOMActiveRecord(DataHandler, TableName);
        if (newActiveRecord.load(Conditions, null)) {
            return newActiveRecord;
        } else {
            return null;
        }
    }

    public DCOMActiveRecord find_first(cDataHandler DataHandler, String TableName, String Conditions, String Order) {
        DCOMActiveRecord newActiveRecord = new DCOMActiveRecord(DataHandler, TableName);
        if (newActiveRecord.load(Conditions, Order)) {
            return newActiveRecord;
        } else {
            return null;
        }
    }

    public ArrayList find_all(String Conditions, String Order) {
        return find_all(this.DataHandler, Conditions, Order);
    }
    public ArrayList hasMany(DCOMActiveRecord subitem)
    {
        String idField = findHasManyName();
        ArrayList results = subitem.find_all("idField = " + this.getValue("id"), null);
        return results;
        
    }
    public DCOMActiveRecord belongsTo(DCOMActiveRecord parentitem)
    {
        String idField = parentitem.TableName;
        if(idField.endsWith("ies"))
        {
            idField = idField.substring(0,idField.length()-4) + "y";
        }
        else if (idField.endsWith("s"))
        {
            idField = idField.substring(0,idField.length() - 2);
        }
        idField = idField + "_id";
        
        return parentitem.find_by_id(this.DataHandler, this.getValue(idField));
    }
    public ArrayList find_all(cDataHandler DataHandler, String Conditions, String Order) {
        String TableName;
        String ClassName = this.getClass().getSimpleName();
        System.out.println("Finding Table Name");
        for(int x=1;x<ClassName.length();x++)
        {
            if(ClassName.toCharArray()[x] >= (int)'A' && ClassName.toCharArray()[x] <= (int)'Z')
            {
                ClassName = ClassName.substring(0,x) + "_" + ClassName.substring(x);
                x++;
            }
        }
        System.out.println("Done FInding Capital Letters");
        if (ClassName.endsWith("y")) {
            TableName = ClassName.toLowerCase().substring(0, ClassName.length() - 1) + "ies";
        } else {
            TableName = ClassName.toLowerCase() + "s";
        }
        return find_all(DataHandler, TableName, Conditions, Order);
    }

    public ArrayList find_all(cDataHandler DataHandler, String TableName, String Conditions, String Order) {
        DCOMActiveRecord baseRecord = new DCOMActiveRecord(DataHandler, TableName);
        String SQLString = "SELECT " + baseRecord.generateFieldList() + " FROM " + TableName;
        if (Conditions != null) {
            SQLString = SQLString + " WHERE (" + Conditions + ")";

        }
        if (Order != null) {
            SQLString = SQLString + " ORDER BY " + Order;
        }

        return find_by_sql(DataHandler, TableName, SQLString);
    }

    public ArrayList find_by_sql(cDataHandler DataHandler, String TableName, String Query) {
        DCOMActiveRecord baseRecord = new DCOMActiveRecord(DataHandler, TableName);
        ArrayList returnedList = new ArrayList();
        try {
            Connection con = DataHandler.GetConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(Query);
            while (rst.next()) {
                DCOMActiveRecord newRecord = new DCOMActiveRecord();
                newRecord.TableName = baseRecord.TableName;
                newRecord.DataHandler = baseRecord.DataHandler;
                newRecord.FieldSet = new ArrayList();
                for (int x = 0; x < baseRecord.FieldSet.size(); x++) {
                    DCOMActiveField currentField = (DCOMActiveField) (baseRecord.FieldSet.get(x));
                    DCOMActiveField clonethis = new DCOMActiveField(currentField.FieldName, currentField.DataType, currentField.isNull, currentField.Key, currentField.DefaultValue, currentField.Extra);
                    clonethis.Value = rst.getString(clonethis.FieldName);
                    newRecord.FieldSet.add(clonethis);
                }
                newRecord.Loaded = true;
                newRecord.Modified = false;
                returnedList.add(newRecord);
            }
            return returnedList;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void reInitialize() {



        FieldSet = new ArrayList();
        try {
            String SQLString = "SHOW FIELDS FROM " + TableName;
            Connection DataLink = this.DataHandler.GetConnection();
            Statement stmt = DataLink.createStatement();
            ResultSet FieldResults = stmt.executeQuery(SQLString);
            while (FieldResults.next()) {
                DCOMActiveField NewField = new DCOMActiveField(FieldResults.getString("field"), FieldResults.getString("type"), FieldResults.getString("null"), FieldResults.getString("key"), FieldResults.getString("default"), FieldResults.getString("extra"));
                FieldSet.add(NewField);
                DataHandler.ErrorHandler.printLog("DCOMActiveRecord", "reInitialize", "Found Field from Table [" + TableName + "] Name:" + FieldResults.getString("field") + " Type: " + FieldResults.getString("type"));
            }
            stmt.close();
        } catch (SQLException ex) {
            //IMPLEMENT LOGGER
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setValue(String FieldName, String Value) {
        for (int x = 0; x < FieldSet.size(); x++) {
            DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
            if (currentField.FieldName.equals(FieldName)) {
                currentField.Value = Value;
                DataHandler.ErrorHandler.printLog("DCOMActiveRecord", "reInitialize","Applying Value: " + Value + " To " + FieldName);
                
                return;
            }
        }
    }

    public void setLink(String FieldName, java.awt.Component link) {
        for (int x = 0; x < FieldSet.size(); x++) {
            DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
            if (currentField.FieldName.equals(FieldName)) {
                currentField.LinkedObject = link;
                if(currentField.LinkedObject.getClass().getSimpleName().equals("rwTextField"))
                {
                    rwTextField currentTextField = (rwTextField)(currentField.LinkedObject);
                    currentTextField.setEnabled(currentTextField.isDefaultKeyEnabled());
                    
                }
                else if(currentField.LinkedObject.getClass().getSimpleName().equals("rwCheckBox"))
                {
                    rwCheckBox currentCheckBox = (rwCheckBox)(currentField.LinkedObject);
                    currentCheckBox.setEnabled(currentCheckBox.isDefaultKeyEnabled());
                }
                 else if(currentField.LinkedObject.getClass().getSimpleName().equals("rwComboBox"))
                {
                    rwComboBox currentComboBox = (rwComboBox)(currentField.LinkedObject);
                    currentComboBox.setEnabled(currentComboBox.isDefaultKeyEnabled());
                }
                else
                {
                    currentField.LinkedObject.setEnabled(false);
                }
                return;
            }
        }
    }

    public String getValue(String FieldName) {

        for (int x = 0; x < FieldSet.size(); x++) {
            DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
            if (currentField.FieldName.equals(FieldName)) {
             //   System.out.println("[" + FieldName + "]Value Found :" + currentField.Value);
                return currentField.Value;
            }
        }
        return null;
    }

    public void outputValues() {
        for (int x = 0; x < FieldSet.size(); x++) {
            DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
            System.out.print(currentField.Value + "|");


        }
        System.out.println("");
    }

    private String generateFieldList() {
        String fieldList = "";
        for (int x = 0; x < FieldSet.size(); x++) {
            DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
            fieldList = fieldList + currentField.FieldName;
            if (x < FieldSet.size() - 1) {
                fieldList = fieldList + ",";
            }
        }
        return fieldList;
    }

    private String generateFieldValues() {
        String fieldValues = "";
        for (int x = 0; x < FieldSet.size(); x++) {
            DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
            if(currentField.FieldName.equals("created_at"))
            {
             fieldValues = fieldValues + "NOW()";   
            }
            else
            {
                fieldValues = fieldValues + DCOMActiveRecord.escape(currentField.Value);
            }
            if (x < FieldSet.size() - 1) {
                fieldValues = fieldValues + ",";
            }
        }
        return fieldValues;
    }
    
    
    private String generateUpdateList() {
        String fieldValues = "";
        for (int x = 0; x < FieldSet.size(); x++) {
            DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
            if(currentField.FieldName.equals("created_at"))
                fieldValues = fieldValues + currentField.FieldName + " = NOW()";
            else
                fieldValues = fieldValues + currentField.FieldName  +  " = " + DCOMActiveRecord.escape(currentField.Value);
            if (x < FieldSet.size() - 1) {
                fieldValues = fieldValues + ",";
            }
        }
        
        return fieldValues;
    }

    public boolean load_by_unique()
    {
        this.update();
        String SQLQuery = "SELECT " + generateFieldList() + " FROM " + TableName + " WHERE ";
        String Conditions = "";
        boolean first=true;
        for (int x = 0; x < FieldSet.size(); x++) {
            DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
            if(currentField.Key.equals("UNI"))
            {
                if(!first)
                    Conditions = Conditions + " AND ";
                first = false;
                Conditions = Conditions + currentField.FieldName + " = " + DCOMActiveRecord.escape(currentField.Value);
            }
            
        }
        if(Conditions.equals(""))
            return false;
        
        SQLQuery = SQLQuery + Conditions;
        try {
            Connection con = DataHandler.GetConnection();
            Statement stmt = con.createStatement();
            DataHandler.ErrorHandler.printLog("DCOMActiveRecord", "load_by_unique", "MySQL String: " + SQLQuery);
            ResultSet rst = stmt.executeQuery(SQLQuery);
            if (rst.next()) {
                for (int x = 0; x < FieldSet.size(); x++) {
                    DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
                    currentField.setValue(rst.getString(currentField.FieldName));
                    if (currentField.LinkedObject != null) {
                        currentField.LinkedObject.setEnabled(true);
                    }
                }
                Loaded = true;
                Modified = false;
                stmt.close();
                return true;
            }
            stmt.close();
            Loaded = false;

            return true;
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        return false;
        
    }
    public void requestNonKeyFocus()
    {
         for (int x = 0; x < FieldSet.size(); x++) {
            DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
            if(currentField.Key.equals(""))
            {
                if(currentField.LinkedObject != null)
                {
                    currentField.LinkedObject.requestFocus();
                    return;
                }
                
            }
            
        }
    }
    public boolean load() {
        String id = this.getValue("id");
        if (id == null || id.equals("NULL") || id.equals("")) {
            
            return load_by_unique();
        }
        String SQLQuery = "SELECT " + generateFieldList() + " FROM " + TableName + " WHERE id =" + id + ";";
        try {
            Connection con = DataHandler.GetConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(SQLQuery);
            if (rst.next()) {
                for (int x = 0; x < FieldSet.size(); x++) {
                    DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
                    currentField.Value = rst.getString(currentField.FieldName);
                }
            }
            stmt.close();
            Loaded = true;
            Modified = false;
            return true;
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        return false;

    }

    public boolean bindForm(java.awt.Container theContent) {
        if (theContent == null) {
            return false;
        }
        for (int i = 0; i < theContent.getComponentCount(); i++) {
            DataHandler.ErrorHandler.printLog("DCOMActiveRecord", "bindForm", "Found Component of Name: " + theContent.getComponent(i).getClass().getSimpleName());
            if (theContent.getComponent(i).getClass().getSimpleName().equals("rwTextField")) {
                rwTextField thisComponent = (rwTextField) (theContent.getComponent(i));
                if (thisComponent.getTableName().equals(this.TableName)) {
                    this.setLink(thisComponent.getFieldName(), theContent.getComponent(i));
                }
            }
            else if (theContent.getComponent(i).getClass().getSimpleName().equals("rwCheckBox")) {
                rwCheckBox thisComponent = (rwCheckBox) (theContent.getComponent(i));
                if (thisComponent.getTableName().equals(this.TableName)) {
                    this.setLink(thisComponent.getFieldName(), theContent.getComponent(i));
                }
            }
            else if (theContent.getComponent(i).getClass().getSimpleName().equals("rwComboBox")) {
                rwComboBox thisComponent = (rwComboBox) (theContent.getComponent(i));
                if (thisComponent.getTableName().equals(this.TableName)) {
                    this.setLink(thisComponent.getFieldName(), theContent.getComponent(i));
                }
            }
        }
        return false;
    }

    public boolean load(String Conditions, String Order) {

        String SQLQuery = "SELECT " + generateFieldList() + " FROM " + TableName + " WHERE (" + Conditions + ");";
        if (Conditions == null && Order == null) {
            SQLQuery = "SELECT " + generateFieldList() + " FROM " + TableName + ";";
        } else if (Conditions == null && Order != null) {
            SQLQuery = "SELECT " + generateFieldList() + " FROM " + TableName + " ORDER BY " + Order + ";";

        } else if (Order != null && !Order.equals("")) {
            SQLQuery = "SELECT " + generateFieldList() + " FROM " + TableName + " WHERE (" + Conditions + ") ORDER BY " + Order + ";";
        }


        try {
            Connection con = DataHandler.GetConnection();
            Statement stmt = con.createStatement();
            DataHandler.ErrorHandler.printLog("DCOMActiveRecord", "load", "MySQL String: " + SQLQuery);
            ResultSet rst = stmt.executeQuery(SQLQuery);
            if (rst.next()) {
                for (int x = 0; x < FieldSet.size(); x++) {
                    DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
                    currentField.setValue(rst.getString(currentField.FieldName));
                    if (currentField.LinkedObject != null) {
                        currentField.LinkedObject.setEnabled(true);
                    }
                }
                Loaded = true;
                Modified = false;
                stmt.close();
                return true;
            }
            stmt.close();
            Loaded = false;

            return true;
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        return false;

    }
    public void update()
    {
        for (int x = 0; x < FieldSet.size(); x++) {
            DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
            currentField.updateValue();
            
        }
        
    }
    public void reset()
    {
        boolean  focusfirst = false;
        for (int x = 0; x < FieldSet.size(); x++) {
            DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
            currentField.setValue("");
            
                if(currentField.LinkedObject != null)
                {
                    if(currentField.LinkedObject.getClass().getSimpleName().equals("rwTextField"))
                    {
                        rwTextField currentTextField = (rwTextField)(currentField.LinkedObject);
                        currentTextField.setEnabled(currentTextField.isDefaultKeyEnabled());
                    }
                    else if(currentField.LinkedObject.getClass().getSimpleName().equals("rwCheckBox"))
                    {
                        rwCheckBox currentCheckBox = (rwCheckBox)(currentField.LinkedObject);
                        currentCheckBox.setEnabled(currentCheckBox.isDefaultKeyEnabled());
                    }
                    else if(currentField.LinkedObject.getClass().getSimpleName().equals("rwComboBox"))
                    {
                        rwComboBox currentComboBox = (rwComboBox)(currentField.LinkedObject);
                        currentComboBox.setEnabled(currentComboBox.isDefaultKeyEnabled());
                    }
                    else
                    {
                        currentField.LinkedObject.setEnabled(true);
                    }
                    
                }
            
        } 
        Modified = false;
        Loaded = false;
    }
    public boolean update_attributes()
    {
        update();
        return save();
    }
    public boolean isException(String Exceptions,String fieldname)
    {
        String[] Params = Exceptions.split(",");
        for(int y=0;y<Params.length;y++)
        {
            if(Params[y].equals(fieldname))
                return true;
        }
        return false;
    }
    public void new_record(String Exceptions)
    {
        for (int x = 0; x < FieldSet.size(); x++) {
            DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
            if(!isException(Exceptions,currentField.FieldName))
            {
                currentField.setValue(currentField.DefaultValue);
            }
            
            
        }
    }
    
    public void new_record()
    {
        for (int x = 0; x < FieldSet.size(); x++) {
            DCOMActiveField currentField = (DCOMActiveField) (FieldSet.get(x));
            if(!currentField.Key.equals("UNI"))
            {
                currentField.setValue(currentField.DefaultValue);
            }
            
            
        }
    }
    public boolean save() {
        if (Loaded == true) {
            String SQLQuery = "UPDATE " + TableName + " SET " + generateUpdateList() + " WHERE id = " + this.getValue("id");  
            try {
                Connection con = DataHandler.GetConnection();
                Statement stmt = con.createStatement();
                DataHandler.ErrorHandler.printLog("DCOMActiveRecord", "save", "MySQL String: " + SQLQuery);
                stmt.execute(SQLQuery);
                Loaded = true;
                Modified = false;
                stmt.close();
                return true;
            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }
            
        } else {
            String SQLQuery = "INSERT INTO " + TableName + "(" + generateFieldList() + ")" + "Values(" + generateFieldValues() + ")";
            String SecondaryQuery = "SELECT last_insert_id() as id;";
            try {
                Connection con = DataHandler.GetConnection();
                Statement stmt = con.createStatement();
                DataHandler.ErrorHandler.printLog("DCOMActiveRecord", "save", "MySQL String: " + SQLQuery);
                stmt.execute(SQLQuery);
                ResultSet rst = stmt.executeQuery(SecondaryQuery);
                if (rst.next()) {
                    this.setValue("id", rst.getString("id"));
                    
                    
                    stmt.close();
                    this.load();
                    return true;
                }
            } catch (SQLException ex) {
                
                DataHandler.ErrorHandler.GenerateSQLError(ex);
            }
        }
    return false;
    }
    public boolean destroy()
    {
        if(!Loaded)
            return false;
        try
        {
            Connection con = DataHandler.GetConnection();
            Statement stmt = con.createStatement();
            String SQLQuery = "DELETE FROM " + TableName + " WHERE id = " + DCOMActiveRecord.escape(this.getValue("id"));
            DataHandler.ErrorHandler.printLog("DCOMActiveRecord", "destroy()", "Executing Destruction Statement: " + SQLQuery);
            stmt.execute(SQLQuery);
            stmt.close();
            return true;
        }
        catch(SQLException ex)
        {
            DataHandler.ErrorHandler.GenerateSQLError(ex);
        }
        return false;
    }
    private String findHasManyName()
    {
        String ClassName = this.getClass().getSimpleName();
        System.out.println("Finding Table Name");
        for(int x=1;x<ClassName.length();x++)
        {
            if(ClassName.toCharArray()[x] >= (int)'A' && ClassName.toCharArray()[x] <= (int)'Z')
            {
                ClassName = ClassName.substring(0,x) + "_" + ClassName.substring(x);
                x++;
            }
        }
        return ClassName + "_id";
    }
}
