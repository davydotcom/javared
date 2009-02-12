/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rwframework.controls;

/**
 *
 * @author destes
 */
public class rwNavNode {
    public String id;
    public String shortcut;
    public String name;
    public String icon;
    public String Classname = null;
    public javax.swing.tree.TreePath objectPath;
    public rwNavNode(String id,String shortcut,String name,String icon,String Classname)
    {
        this.id = id;
        this.shortcut = shortcut;
        this.name = name;
        this.icon = icon;
        this.Classname = Classname;
        
    }
    public String toString()
    {
        return this.id + ". " + this.name;
    }
    
    
}
