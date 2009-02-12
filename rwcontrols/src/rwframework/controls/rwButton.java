/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rwframework.controls;

/**
 *
 * @author destes
 */
public class rwButton extends javax.swing.JButton {
    private boolean DefaultNonKeyEnabled = true;
    private boolean DefaultKeyEnabled = false;

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
}
