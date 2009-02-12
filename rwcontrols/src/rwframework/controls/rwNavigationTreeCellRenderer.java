/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rwframework.controls;
import javax.swing.*;
import java.awt.*;
import java.io.*;
/**
 *
 * @author destes
 */
public class rwNavigationTreeCellRenderer extends javax.swing.tree.DefaultTreeCellRenderer {
    public rwNavigationTreeCellRenderer()
    {
        
    }
    
    public Component getTreeCellRendererComponent(JTree tree,Object value,boolean sel,boolean expanded,boolean leaf,int row,boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
     //   System.out.println("Found Node of type: " + value.getClass().getSimpleName());
        if(value.getClass().getSimpleName().equals("DefaultMutableTreeNode"))
        {
            javax.swing.tree.DefaultMutableTreeNode CurrentNode = (javax.swing.tree.DefaultMutableTreeNode)value;
            if(CurrentNode.getUserObject().getClass().getSimpleName().equals("rwNavNode"))
            {
                java.net.URL imgURL = this.getClass().getResource(((rwframework.controls.rwNavNode)(CurrentNode.getUserObject())).icon);
                ImageIcon currentIcon = new ImageIcon(imgURL);
                setIcon(currentIcon);
            }
        }
        return this;
    }
}
