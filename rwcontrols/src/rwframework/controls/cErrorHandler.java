/*
 * cErrorHandler.java
 *
 * Created on August 1, 2006, 4:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package rwframework.controls;
import javax.swing.*;
import java.io.*;
import java.sql.*;
/**
 *
 * @author destes
 */
public class cErrorHandler {
    
    /** Creates a new instance of cErrorHandler */
    public cErrorHandler() {
    }
    public void printLog(String className,String Function,String Message)
    {
        System.out.println("Log in " + className + ":" + Function + " -- " + Message);
    }
    public void printLog(String className,String Function,StackTraceElement[] elements)
    {
        for(int x=0;x<elements.length;x++)
        {
           System.out.println("Log in " + className + ":" + Function + " -- " + elements[x].toString());

        }
    }
    public void GenerateSQLError(SQLException ex)
    {
        String ErrorMessage = "SQLException: " + ex.getMessage() + "\nSQLState: " + ex.getSQLState() + "\nVendorError: " + ex.getErrorCode();
        JOptionPane.showMessageDialog(null,ErrorMessage);
    }
    public void GenerateErrorMessage(String Message)
    {
     JOptionPane.showMessageDialog(null,Message);   
    }
}
