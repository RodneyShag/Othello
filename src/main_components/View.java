package main_components;

import javax.swing.*;
import java.awt.Point;
import java.awt.event.MouseListener;

/**
 * \brief
 * The "View" for MVC (Model - View - Controller)
 * @author Rodney Shaghoulian
 */
public class View {
	public Board board;					///< The View has access to the Board
	public Controller controller;		///< The View has access to the Controller
	public JFrame frame;				///< We will put a JPanel (which contains NxN JButtons) on this
	public JPanel panel;				///< Will add NxN Buttons to this panel, and add the JPanel to the JFrame
	public Button[][] button;			///< NxN Buttons on Panel
	
	public int frameHeight;				///< Picked to make a square JFrame of ~600 pixels.
	public int frameWidth;				///< Picked to make a square JFrame of ~600 pixels.
	public int boardWidth; 				///< Approximate number of Pixels on square board
	public int squareLength;			///< length of square's edge that a Disk can be placed on
	
	public JButton undoButton;			///< Allows for undoing a Disk placement
	public JButton redoButton;			///< Allows for redoing a Disk placement

	public JTextField whiteName;		///< Used to display unique name for WHITE Player
	public JTextField blackName;		///< Used to display unique name for BLACK Player
	
	public JTextField whiteNumWins;		///< Used to display number of games WHITE has won
	public JTextField blackNumWins;		///< Used to display number of games BLACK has won
	
	public JTextField whiteNumDisks;	///< Used to display number of Disks WHITE has on Board
	public JTextField blackNumDisks;	///< Used to display number of Disks BLACK has on Board
	
	public JButton whiteForfeit;		///< Used to give WHITE the option of forfeiting a game
	public JButton blackForfeit;		///< Used to give BLACK the option of forfeiting a game

	public JButton newGameEasy;			///< Used to create a new game with difficulty = EASY
	public JButton newGameMedium;		///< Used to create a new game with difficulty = MEDIUM
	public JButton newGameHard;			///< Used to create a new game with difficulty = HARD

	public ImageIcon blackIcon;			///< picture of Black Disk
	public ImageIcon whiteIcon;			///< picture of White Disk
	
	public JTextField difficultyText;	///< Displays difficulty level of A.I.
	
	java.awt.Color green;				///< Color of NxN Board Buttons
	java.awt.Color lightGreen;			///< Color used for highlighting moves
	
	/**
	 * Creates a "View" GUI from a Board
	 * @param board 		The Board to create a GUI from (Using each Disk)
	 * @param controller	The controller in MVC
	 */
	public View(Board board, Controller controller){
		button = new Button[board.rows][board.columns];
		frameHeight = 739;
		frameWidth = 917;
		boardWidth = 600;
		squareLength = boardWidth / board.rows;

		green = new java.awt.Color(0, 100, 0);
		lightGreen = new java.awt.Color(0, 200, 0);

		frame = new JFrame("Othello - tbPony");
		blackIcon = new ImageIcon("images/black_disk.png");
		whiteIcon = new ImageIcon("images/white_disk.png");
		
		initialize(board, controller);
	}
	
	/**
	 * Initializes the View given a Board and Controller
	 * @param board			The "Model" that we are initializing the View from
	 * @param controller	The "Controller" we will use for event listening
	 */
	public void initialize(Board board, Controller controller){
		this.board      = board;
		this.controller = controller;
		
		/* Initializes Swing Variables */
		panel = new JPanel(null);
		for (int row = 0; row < board.rows; row++){
			for (int column = 0; column < board.columns; column++){
				button[row][column] = new Button(column, row);
			}
		}
		/* Set up JFrame */
		frame.setSize(frameWidth, frameHeight);
		frame.setLocationRelativeTo(null);						//null places window in center of screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* Loop through board - Create NxN Buttons. Add them to the JPanel */
		for (int row = 0; row < board.rows; row++){
			for (int column = 0; column < board.columns; column++){
				
				/* Create Buttons */
				Disk currentDisk = board.tile[row][column];
				Button currentButton = button[row][column];
				if (currentDisk.color == Color.BLACK)
					currentButton.setIcon(blackIcon);
				else if (currentDisk.color == Color.WHITE)
					currentButton.setIcon(whiteIcon);
				//currentButton.setIcon(currentDisk.image);
				
				/* Set bounds for the Button */
				int xPositionGUI = getXPositionGUI(column);
				int yPositionGUI = getYPositionGUI(row);
				currentButton.setBounds(xPositionGUI, yPositionGUI, squareLength, squareLength);
				
				setBackground(currentButton);
				panel.add(currentButton);
			}
		}
		createInterfaceButtons(panel);
		updateView();
		
		frame.setContentPane(panel);	//add the JPanel to the JFrame
		frame.setVisible(true);
	}
	
	/**
	 * Colors the backgrounds of valid moves for a player
	 */
	public void highlightMoves(){
		Player currentPlayer = board.getCurrentPlayer();
		for (int row = 0; row < board.rows; row++){
			for (int col = 0; col < board.columns; col++){
				Point currentPoint = new Point(col, row);
				Button currentButton = button[row][col];
				if (currentPlayer.validMoves.contains(currentPoint))
					currentButton.setBackground(lightGreen);
				else
					currentButton.setBackground(green);
			}
		}
	}
	
	/**
	 * Create the JButtons, Buttons, and JTextFields for the View
	 * @param panel		The panel we are attaching the buttons to
	 */
	public void createInterfaceButtons(JPanel panel){
		createNames(panel);
		createWins(panel);
		createNumDisks(panel);
		createUndoButton(panel);
		createRedoButton(panel);
		createForfeitButtons(panel);
		createNewGameButtons(panel);
		createDifficultyText(panel);
	}
	
	/**
	 * Creates unique names (JTextfields) for BLACK and WHITE
	 * @param panel		The panel to attach the JTextFields to
	 */
	public void createNames(JPanel panel){
		blackName = new JTextField("Black Player");
		blackName.setBounds(650, 20, 200, 40);
		blackName.setBackground(null);
		blackName.setBorder(null);
		blackName.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(blackName);
		whiteName = new JTextField("White Player");
		whiteName.setBounds(650, 540, 200, 40);
		whiteName.setBackground(green);
		whiteName.setBorder(null);
		whiteName.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(whiteName);
	}
	
	/**
	 * Creates number of wins for BLACK and WHITE
	 * @param panel		The panel to attach the JTextFields to
	 */
	public void createWins(JPanel panel){
		whiteNumWins = new JTextField("White # of Wins: " + controller.whiteGamesWon);
		whiteNumWins.setBounds(610, 500, 100, 40);
		whiteNumWins.setEditable(false);
		whiteNumWins.setBorder(null);
		panel.add(whiteNumWins);
		blackNumWins = new JTextField("Black # of Wins: " + controller.blackGamesWon);
		blackNumWins.setBounds(610, 60, 100, 40);
		blackNumWins.setBorder(null);
		blackNumWins.setEditable(false);
		panel.add(blackNumWins);
	}
	
	/**
	 * Creates number of points for BLACK and WHITE
	 * @param panel		The panel to attach the JTextFields to
	 */
	public void createNumDisks(JPanel panel){
		whiteNumDisks = new JTextField("White Disks: " + board.whitePlayer.score);
		whiteNumDisks.setBounds(610, 470, 100, 40);
		whiteNumDisks.setEditable(false);
		whiteNumDisks.setBorder(null);
		panel.add(whiteNumDisks);
		blackNumDisks = new JTextField("Black Disks: " + board.blackPlayer.score);
		blackNumDisks.setBounds(610, 90, 100, 40);
		blackNumDisks.setBorder(null);
		blackNumDisks.setEditable(false);
		panel.add(blackNumDisks);
	}
	
	/**
	 * Creates an "Undo" JButton that lets us undo our previous move
	 * @param panel		The panel to attach the JTextFields to
	 */
	public void createUndoButton(JPanel panel){
		undoButton = new JButton("Undo");
		undoButton.setBounds(630, 250, 100, 40);
		panel.add(undoButton);
	}
	
	/**
	 * Creates a "Redo" JButton that lets us redo our previous move
	 * @param panel		The panel to attach the JTextFields to
	 */
	public void createRedoButton(JPanel panel){
		redoButton = new JButton("Redo");
		redoButton.setBounds(630, 310, 100, 40);
		panel.add(redoButton);
	}
	
	/**
	 * Creates a "Forfeit" JButton that lets a Player resign from a game and take a loss
	 * @param panel		The panel to attach the JTextFields to
	 */
	public void createForfeitButtons(JPanel panel){
		whiteForfeit = new JButton("White Forfeit");
		whiteForfeit.setBounds(720, 485, 150, 30);
		panel.add(whiteForfeit);
		blackForfeit = new JButton("Black Forfeit");
		blackForfeit.setBounds(720, 85, 150, 30);
		panel.add(blackForfeit);	
	}
	
	/**
	 * Creates JButton that lets a Player start a new game
	 * @param panel		The panel to attach the JButton to
	 */
	public void createNewGameButtons(JPanel panel){
		newGameEasy = new JButton("New Game - EASY");
		newGameEasy.setBounds(10, 660, 180, 30);
		panel.add(newGameEasy);
		newGameMedium = new JButton("New Game - MEDIUM");
		newGameMedium.setBounds(210, 660, 180, 30);
		panel.add(newGameMedium);
		newGameHard = new JButton("New Game - HARD");
		newGameHard.setBounds(410, 660, 180, 30);
		panel.add(newGameHard);
	}
	
	/**
	 * Creates text field showing difficulty of A.I.
	 * @param panel
	 */
	public void createDifficultyText(JPanel panel){
		difficultyText = new JTextField("Difficulty: " + controller.difficulty);
		difficultyText.setBounds(257, 600, 100, 40);
		difficultyText.setEditable(false);
		difficultyText.setBorder(null);
		panel.add(difficultyText);
	}
	
	/**
	 * Updates the status of JButtons, Buttons, and JTextFields on current View
	 */
	public void updateView(){
		updateTurnVisuals();
		updateGameEndView();
		updateIcons();
		updateNumDisks();
		highlightMoves();
	}
	
	/**
	 * Visually shows whose turn it is by highlighting that Player's name.
	 */
	public void updateTurnVisuals(){
		if (board.playerTurn == Color.WHITE){
			blackName.setBackground(null);
			whiteName.setBackground(lightGreen);
			whiteForfeit.setEnabled(true);
			blackForfeit.setEnabled(false);
		}
		else{
			blackName.setBackground(lightGreen);
			whiteName.setBackground(null);
			whiteForfeit.setEnabled(false);
			blackForfeit.setEnabled(true);
		}
	}
	
	/**
	 * Displays a message if game is over
	 */
	public void updateGameEndView(){
		if (board.gameEnded == true){
			if (board.winner == Color.WHITE){
				javax.swing.JOptionPane.showMessageDialog(null, "White Wins!");
				controller.whiteGamesWon++;
			}
			else if (board.winner == Color.BLACK){	
				javax.swing.JOptionPane.showMessageDialog(null, "Black Wins!");
				controller.blackGamesWon++;
			}
			else
				javax.swing.JOptionPane.showMessageDialog(null, "Tie Game!");
			enableButtons(false);	
		}
	}
	
	/**
	 * Updates all icons on the Board
	 */
	public void updateIcons(){
		for (int row = 0; row < board.rows; row++){
			for (int column = 0; column < board.columns; column++){
				Disk currentDisk = board.tile[row][column];
				Button currentButton = button[row][column];
				if (currentDisk.color == Color.BLACK)
					currentButton.setIcon(blackIcon);
				else if (currentDisk.color == Color.WHITE)
					currentButton.setIcon(whiteIcon);
				else
					currentButton.setIcon(null);
			}
		}
	}
	
	/**
	 * Updates the JButtons for number of disks for each player
	 */
	public void updateNumDisks(){
		whiteNumDisks.setText("White Disks: " + board.whitePlayer.score);
		blackNumDisks.setText("Black Disks: " + board.blackPlayer.score);
	}
	
	/**
	 * Disables clicking of buttons (except New Game Button)
	 */
	public void enableButtons(Boolean enable){
		redoButton.setEnabled(enable);
		undoButton.setEnabled(enable);
		blackForfeit.setEnabled(enable);
		whiteForfeit.setEnabled(enable);
	}
	
	/** 
	 * Create a green background by painting Button green
	 * @param currentButton		The Button we want to set the background color for
	 */
	public void setBackground(Button button){
		button.setBackground(green);
	}
	
	/**
	 * Calculates "x" Position on GUI for Disk
	 * @param x 	the "x" position on Board
	 * @return 		the "x" coordinate on the GUI from 0 to "viewablePixels"
	 */	
	public int getXPositionGUI(int x){
		return x * squareLength;
	}
	
	/**
	 * Calculates "y" Position on GUI for Disk
	 * @param y 	the "y" Position on Board
	 * @return 		the "y" coordinate on the GUI from 0 to "viewablePixels"
	 */	
	public int getYPositionGUI(int y){
		int yOffset = squareLength;
		return boardWidth - (y * squareLength) - yOffset;
	}
	
	/** 
	 * Returns the Button at desired Point
	 * @param button	Point of desired Button
	 * @return			Button corresponding to Disk at Point
	 */
	public Button getButton(Point point){
		return button[point.y][point.x];
	}
	
	/**
	 * Updates the ImageIcon for a Button
	 * @param image		Our new desired ImageIcon
	 * @param button	The Button to update with a new Image
	 */
	public void setIcon(ImageIcon image, Button button){
		button.setIcon(image);
	}
	
	/**
	 * Adds a MouseListener to a JButton
	 * @param mouseListener		MouseListener to add (to a Button)
	 * @param button			JButton to add a MouseListener to
	 */
	public void addMouseListener(MouseListener mouseListener, JButton button){
		button.addMouseListener(mouseListener);
	}
}
