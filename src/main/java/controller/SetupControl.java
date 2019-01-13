package controller;

import model.board.Board;
import model.board.BoardManager;
import model.player.Player;
import model.reader.Reader;
import view.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Alfred Röttger Rydahl
 */

public class SetupControl {

    /*
    --------------------------- Fields ---------------------------
     */
    private HashMap<String, String> boardInfo;
    private final int STARTINGMONEY = 30000;
    private final int STARTINGPOSITION = 0;


    /*
    ------------------------ Constructors ------------------------
     */
    public SetupControl()
	{
		boardInfo = new HashMap<>();
	}
    /*
    ------------------------- Properties -------------------------
     */
    /*
    ----------------------- Public Methods -----------------------
     */
    
    public void boardCreator(Board board)
	{
		loadBoardInfo();
		
		BoardManager boardManager = new BoardManager();
		boardManager.setupBoard(boardInfo, board);
		
	}
	
	public void messageMapCreator (HashMap<String, String> messageMap)
	{
		Reader reader = new Reader("messages1.0.csv", ";");
		reader.readFileIntoHashMap(messageMap);
	}
	
	public Player[] playerSetup(GuiController guiController, HashMap<String, String> messageMap)
	{
		guiController.showMessage(messageMap.get("Welcome"));
		
		// Ask how many players who wants to play and Initialize Player[]
		Player[] players = new Player[guiController.getUserInteger(messageMap.get("GetPlayers"), 3, 6)];

		ArrayList<String> tokenColorsString = new ArrayList<>();
		tokenColorsString.add(messageMap.get("CarColor1")); tokenColorsString.add(messageMap.get("CarColor2"));
		tokenColorsString.add(messageMap.get("CarColor3")); tokenColorsString.add(messageMap.get("CarColor4"));
		tokenColorsString.add(messageMap.get("CarColor5")); tokenColorsString.add(messageMap.get("CarColor6"));

		for (int i = 0; i < players.length; i++) {
		String name = guiController.getUserString(messageMap.get("NamePlayer") + " " + Integer.toString(i+1));

			String chosenTokenColor = guiController.getUserChoice(messageMap.get("PickColorForToken") + " ", tokenColorsString);
			for (int j = 0; j < tokenColorsString.size(); j++) {
				if (tokenColorsString.get(j).equals(chosenTokenColor)) {
					tokenColorsString.remove(j);
				}
			}

			Color tokenColor = stringToColorSwitch(chosenTokenColor);

			Player player = new Player(name, tokenColor, STARTINGMONEY, STARTINGPOSITION);
			players[i]=(player);
			guiController.showMessage(messageMap.get("TokenSelected") + " " + chosenTokenColor.toLowerCase() + ".");
		}

		return players;
	}

	public void createGUIPlayers (GuiController guiController, Player[] Players) {
        for (Player p : Players) {
            guiController.addPlayer(p);
        }
    }
    
    /*
    ----------------------- Support Methods ----------------------
     */
    private void loadBoardInfo()
	{
		Reader reader = new Reader("boardV1.csv", ";");
		reader.readFileIntoHashMap(boardInfo);
	}

	private Color stringToColorSwitch (String colorString) {
    	Color colorFromString;

    	switch (colorString) {
			case "Sort":
				colorFromString = Color.BLACK;
				break;
			case "Hvid":
				colorFromString = Color.WHITE;
				break;
			case "Rød":
				colorFromString = Color.RED;
				break;
			case "Grøn":
				colorFromString = Color.GREEN;
				break;
			case "Gul":
				colorFromString = Color.YELLOW;
				break;
			case "Blå":
				colorFromString = Color.BLUE;
				break;
			default:
				colorFromString = Color.PINK;
				break;
		}
    	return colorFromString;
	}
}
