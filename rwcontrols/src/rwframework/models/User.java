/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rwframework.models;
import java.sql.*;
/**
 *
 * @author destes
 */
public class User extends rwframework.controls.DCOMActiveRecord{
    public User(rwframework.controls.cDataHandler DataHandler)
    {
        super(DataHandler);
    }
    public boolean updatePassword(String Password)
    {
        if(!Loaded)
            return false;
        
        String SQLQuery = "UPDATE users SET password = PASSWORD(" + rwframework.controls.DCOMActiveRecord.escape(Password) + ") WHERE id = " + this.getValue("id");
        try
        {
            Connection con = DataHandler.GetConnection();
            Statement stmt = con.createStatement();
            stmt.execute(SQLQuery);
            Loaded=false;
            stmt.close();
            this.load();
            return true;
        }
        catch(SQLException ex)
        {
         DataHandler.ErrorHandler.GenerateSQLError(ex);   
        }
        return false;
    }
    public boolean Validate(String Username,String Password)
    {
        this.load("username = '" + Username.replaceAll("'", "''") + "' AND password = PASSWORD('" + Password.replaceAll("'", "''") + "')",null);
        if (this.Loaded)
            if(this.getValue("enabled").equals("1"))
                return true;
            else
            {
               DataHandler.ErrorHandler.GenerateErrorMessage("This User Account Has Been Disabled!");
               return false;
            }
        
        
        else
            return false;
    }
    
}
