package controller.extraActionManagment;

import controller.GeneralActionController;
import controller.GuiController;
import model.player.Player;


import java.util.HashMap;

/**
 * @author Rasmus Sander Larsen
 * @date 17-01-2019
 */
public abstract class ExtraAction {

    /*
    -------------------------- Fields --------------------------
     */

    protected ExtraActionType_Enum extraActionType;
    protected GuiController guiController;
    protected HashMap<String,String> messageMap;
    protected GeneralActionController generalActionController;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public ExtraAction ( GuiController guiController, HashMap<String,String> messageMap,
                        GeneralActionController generalActionController) {
        this.guiController = guiController;
        this.messageMap = messageMap;
        this.generalActionController = generalActionController;
    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    protected abstract void doExtraAction (Player player);

    
    /*
    ---------------------- Support Methods ----------------------
     */


}
