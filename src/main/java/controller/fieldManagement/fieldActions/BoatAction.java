package controller.fieldManagement.fieldActions;

import controller.GeneralActionController;
import controller.GuiController;
import controller.fieldManagement.FieldAction;
import model.board.Field;
import model.player.Player;

import java.util.HashMap;

/**
 * @author Alfred Röttger Rydahl
 */
public class BoatAction extends FieldAction {

	/*
    ---------------------------------- Fields ----------------------------------
     */

	private Field currentField;
	private int rentFromNoOfBoats = 0;
	private String keyForBoatsOwned = null;
	private GeneralActionController generalActionController;
	private Player[] players;


    /*
    ------------------------------ Constructors --------------------------------
     */

	public BoatAction(Player player, Player[] players,HashMap<String, String> messageMap, GuiController guiController,
					  GeneralActionController generalActionController, Field currentField) {
		super(player,messageMap,guiController);
		this.players = players;
		this.currentField = currentField;
		this.generalActionController = generalActionController;
	}

	/*
    ------------------------------ Properties ----------------------------------
     */


    /*
    ---------------------------- Public Methods --------------------------------
     */

	/**
	 * This method lets you purchase a boatField or pays the rent if the field is already owned by a player.
	 * The rent is payed to the player, that owns the field.
	 * Or the Field is bought and added to Player ownedFields.
	 */

	@Override
	public void action() {

		// Checks that the player doesn't own the field.
		if (currentField.getFieldOwner() != player) {

			//region Buying Sequence.
			if (currentField.getFieldOwner() == null) {
				// Buys the if Player wants.
				buyingSequence();
			}

			//endregion

			//region If there is a owner to the boat field, runs Ray Rent Sequence.
			else {

				// Pay Rent and show message depending on owner number of boats.
				payBoatRent();
			}
			//endregion
		}
		// Otherwise he owns the property
		else {
			guiController.showMessage(messageMap.get("LandedOnOwnField"));
		}
	}

	/*
    ----------------------------- Support Methods ------------------------------
     */

	/**
	 * Player is asked if he wants to buy the field. If yes, player buys the field.
	 */
	private void buyingSequence() {

		// Checks if Player have enough money to buy field.
		if (player.getAccount().getBalance()>=currentField.getFieldCost()) {
			StringBuilder actionBuilder = new StringBuilder();
			actionBuilder.append(messageMap.get("BoatMessage").replace("%boat", currentField.getFieldName()));
			actionBuilder.append(" "+ messageMap.get("WantToBuy?").replace("%cost", String.valueOf(currentField.getFieldCost())));

			// Askes if the player wants to purchase a boatField
			if (guiController.getLeftButtonPressed(actionBuilder.toString(), messageMap.get("Yes"), messageMap.get("No"))) {

				// Player Buys the Field.
				generalActionController.buyField(player, currentField, guiController);
			}

			//region Auction
			else {

				auctionField(currentField,players,generalActionController);

			}
			//endregion
		}
		// Player don't have enough money to buy the field.
		else {
			guiController.showMessage(messageMap.get("NoMoneyToBuyField").
					replace("%fieldName", currentField.getFieldName()));

			//region auction
			auctionField(currentField,players, generalActionController);
			//endregion
		}
	}

	/**
	 * Uses calRentFromNoOfBoats and keyForBoatsOwned to update Player and GUI_Player for both paying and
	 * receiving Player and show the right message on GUI.
	 */
	private void payBoatRent() {

		calRentFromNoOfBoats();

		// Updates Players Balance and matching GUI_Player Balance
		player.updateBalance(-rentFromNoOfBoats);
		guiController.updateBalance(player, player.getAccount().getBalance());

		// ShowMessage on GUI
		guiController.showMessage(messageMap.get(keyForBoatsOwned).replace("%ownerName", currentField.getFieldOwner().getName()));

		// Updates FieldOwner's Balance and matching GUI_Players Balance.
		currentField.getFieldOwner().updateBalance(+rentFromNoOfBoats);
		guiController.updateBalance(currentField.getFieldOwner(), currentField.getFieldOwner().getAccount().getBalance());
	}

	/**
	 * Sets calRentFromNoOfBoats:int & keyForBoatsOwned:String with the correct rent matching number of boats.
	 */
	private void calRentFromNoOfBoats() {
		switch (currentField.getFieldOwner().getNoOfBoatsOwned()) {
			case 1:
				rentFromNoOfBoats = 500;
				keyForBoatsOwned = "BoatsOwned1";
				break;
			case 2:
				rentFromNoOfBoats = 1000;
				keyForBoatsOwned = "BoatsOwned2";
				break;
			case 3:
				rentFromNoOfBoats = 2000;
				keyForBoatsOwned = "BoatsOwned3";
				break;
			case 4:
				rentFromNoOfBoats = 4000;
				keyForBoatsOwned = "BoatsOwned4";
				break;
			default:
				rentFromNoOfBoats = 0;
				keyForBoatsOwned = "BoatsOwnedError";
				break;
		}
	}
}
