/*
 * cDataHandler.java
 *
 * Created on July 26, 2006, 9:49 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package rwframework.controls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.jdom.*;
import org.jdom.input.*;

import java.io.File;
import java.util.List;

/**
 *
 * @author destes
 */
public class cDataHandler {
    
    /** Creates a new instance of cDataHandler */
    public String hostname;
    public String port;
    public String database;
    public String UserName;
    public String Password;
    public String ToolbarImage = "/rwframework/images/blackbar.png";
    public Connection con = null;
    public boolean error;
    public cErrorHandler ErrorHandler;
    public cDataHandler(cErrorHandler ErrorHandler) {
        error = false;
        this.ErrorHandler = ErrorHandler;
    }
    
    public Connection GetConnection()
    {
        try
        {       
        if(con == null)
        {
            con = DriverManager.getConnection(GetConURL());
            return con;
        }
        if(con.isClosed() == true)
        {
            System.out.println("Connection Was Reset: Reconnecting...");
            con = DriverManager.getConnection(GetConURL());
        }
        return con;
        }
        catch(SQLException ex)
        {
            ErrorHandler.GenerateSQLError(ex);
        }
        return null;
        
    }
    public String GetConURL()
    {
        String ConURL = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?user=" + UserName + "&password=" + Password;
        return ConURL;
    }
    
    public boolean loadSettings(String strFile) throws Exception{
        try
        {
            SAXBuilder b = new SAXBuilder();
            Document doc = b.build(new File(strFile));
            Element root = doc.getRootElement();
            List InfoList = root.getChildren();
            byte check = 0;
            for(int i = 0; i<InfoList.size();i++)
            {
                Element info = (Element)InfoList.get(i);
                
                if(info.getName().compareTo("hostname") == 0)
                {
                  hostname = info.getText();
                  check++;
                }
                if(info.getName().compareTo("UserName") == 0)
                {
                  UserName = info.getText();
                  check++;
                }
                if(info.getName().compareTo("Password") == 0)
                {
                  Password = info.getText();
                  check++;
                }
                if(info.getName().compareTo("port") == 0)
                {
                  port = info.getText();
                  check++;
                }
                if(info.getName().compareTo("database") == 0)
                {
                  database = info.getText();
                  check++;
                }
                if(info.getName().compareTo("toolbar") == 0)
                {
                    ToolbarImage = info.getText();
                }
            }
            if(check != 5)  //making seperate exceptions probably won't be needed
                throw new Exception("Invalid Configuration File.");

        }
        catch(Exception ex)
        {
            throw new Exception("Unable to load Configuration File");
        }
        
        return true;
    }
    
}
