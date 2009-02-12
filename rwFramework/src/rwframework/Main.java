/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rwframework;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author destes
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        
        catch (Exception e) {
        }
        
        //frmLogin LoginForm = new frmLogin(DataHandler);
        //LoginForm.setVisible(true);
        try {
            
            if(UIManager.getSystemLookAndFeelClassName().equals("com.sun.java.swing.plaf.gtk.GTKLookAndFeel")) {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } else {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
               
               // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
             UIManager.put("ComboBox.disabledForeground",Color.BLACK);
             UIManager.put("TextField.disabledForeground",Color.BLACK);
             
        } catch (Exception e) { }
        
        
        String vers = System.getProperty("os.name").toLowerCase();
       
           
        if(vers.indexOf("mac") != -1) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            
                  
            
        }
        
        
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                rwframework.controls.cErrorHandler ErrorHandler = new rwframework.controls.cErrorHandler();
                rwframework.controls.cDataHandler DataHandler;
                DataHandler = new rwframework.controls.cDataHandler(ErrorHandler);
                //Load from config.xml
                try{
                    DataHandler.loadSettings("config.xml");
                }catch(Exception e){
                    //System.out.println(e.toString());
                  
                    
                    e.printStackTrace();
                    DataHandler.error = true;
                }
                
                
                
                frmSplash SplashForm = new frmSplash();
                SplashForm.setModal(true); //Causes Splash Form to Pause Parent Call Function when set to visible
                SplashForm.setVisible(true); //Open Splash Form and Halt Function Progression until Form 
                new frmLogin(DataHandler).setVisible(true);

                //Testing User Model
                    
    }});
    }

}
