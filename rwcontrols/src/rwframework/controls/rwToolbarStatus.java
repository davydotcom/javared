/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rwframework.controls;

/**
 *
 * @author destes
 */
public class rwToolbarStatus {
    private boolean Save = false;
    private boolean New = false;
    private boolean Clear = false;
    private boolean SaveClear = false;
    private boolean Delete = false;
    public boolean isSave() {
        return Save;
    }

    public void setSave(boolean Save) {
        this.Save = Save;
    }

    public boolean isNew() {
        return New;
    }

    public void setNew(boolean New) {
        this.New = New;
    }

    public boolean isClear() {
        return Clear;
    }

    public void setClear(boolean Clear) {
        this.Clear = Clear;
    }

    public boolean isSaveClear() {
        return SaveClear;
    }

    public void setSaveClear(boolean SaveClear) {
        this.SaveClear = SaveClear;
    }

    public boolean isDelete() {
        return Delete;
    }

    public void setDelete(boolean Delete) {
        this.Delete = Delete;
    }
    
}
