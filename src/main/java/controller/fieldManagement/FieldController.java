package controller.fieldManagement;

import controller.GuiController;
import controller.fieldManagement.fieldActions.BoatAction;
import controller.fieldManagement.fieldActions.TaxAction;
import model.board.Field;
import model.board.fields.BoatField;
import model.board.fields.PropertyField;
import model.player.Player;

import java.util.HashMap;

/**
 * @author Alfred Röttger Rydahl
 */
public class FieldController {

	/*
    ---------------------------------- Fields ----------------------------------
     */
	private Field currentField;
	private TaxAction taxAction;
	private BoatAction boatAction;
    
    /*
    ------------------------------ Constructors --------------------------------
     */

    public FieldController (Field currentField,GuiController guiController, Player player, HashMap<String, String> messageMap) {
        this.currentField = currentField;

        taxAction = new TaxAction(player,messageMap,guiController,currentField);
        boatAction = new BoatAction(player,messageMap,guiController,currentField);

    }

    /*
    ------------------------------ Properties ----------------------------------
     */

    public void doFieldActionByFieldType(Field field) {

        //region Find Field Type

        switch (field.getFieldType()) {
            case Property:
                break;
            case Start:
                break;
            case ChanceCard:
                break;
            case Tax:
                taxAction.action();
                break;
            case Prison:
                break;
            case Boat:
                boatAction.action();
                break;
            case Parking:
                break;
            case Brewery:
                break;
        }
    }


    /*
    ---------------------------- Public Methods --------------------------------
     */
    
    /*
    ----------------------------- Support Methods ------------------------------
     */

}
