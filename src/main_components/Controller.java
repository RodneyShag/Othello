package main_components;

import listeners.ForfeitListener;
import listeners.NewGameListener;
import listeners.PlaceDiskListener;
import listeners.RedoListener;
import listeners.UndoListener;
import strategies.MinimaxStrategy;
import strategies.AlphaBetaStrategy;
import strategies.Difficulty;
import strategies.RandomStrategy;

/**
 * \brief
 * The Controller in the MVC structure
 * @author Rodney Shaghoulian
 */
public class Controller {
	public Board board; 					///< The Model in MVC structure
	public CommandManager commandManager;	///< Saves Commands so we can "undo" and "redo" them

	public View view;						///< The "View" in MVC structure.
	public int whiteGamesWon = 0;			///< Number of games WHITE has won
	public int blackGamesWon = 0;			///< Number of games BLACK has won
	public int rows;						///< Number of rows on Board
	public int columns;						///< Number of columns on Board

	public Difficulty difficulty;			///< The difficulty (intelligence) of the computer A.I.
	
	/**
	 * Constructor: Initialize the Controller with a new Board and new View. Add mouseListeners to the View
	 */
	public Controller(int rows, int columns, Difficulty difficulty) {
		this.rows = rows;
		this.columns = columns;
		this.difficulty = difficulty;
		initializeVariables();
		view = new View(board, this); // purposely NOT put into intializeVariables() since that function is called often
		addMouseListeners();
	}
	
	public void reset(){
		initializeVariables();
		view.initialize(board, this);
		addMouseListeners();
	}
	
	/**
	 * Reset the Controller with the new Board and updated View. Add mouseListeners to the View
	 */
	public void reset(Difficulty difficulty){
		this.difficulty = difficulty;
		initializeVariables();
		view.initialize(board, this);
		addMouseListeners();
	}
	
	/**
	 * Create Board and CommandManager.
	 */
	public void initializeVariables(){
		board = new Board(rows, columns);	
		commandManager = new CommandManager();
	}
	
	/**
	 * Add mouseListeners to the View
	 */
	public void addMouseListeners(){
		for (int i = 0; i < board.rows; i++){
			for (int j = 0; j < board.columns; j++){
				view.addMouseListener(new PlaceDiskListener(this), view.button[i][j]);
			}
		}
		view.addMouseListener(new UndoListener(this), view.undoButton);
		view.addMouseListener(new RedoListener(this), view.redoButton);
		view.addMouseListener(new ForfeitListener(this), view.whiteForfeit);
		view.addMouseListener(new ForfeitListener(this), view.blackForfeit);
		view.addMouseListener(new NewGameListener(this, Difficulty.EASY),   view.newGameEasy);
		view.addMouseListener(new NewGameListener(this, Difficulty.MEDIUM), view.newGameMedium);
		view.addMouseListener(new NewGameListener(this, Difficulty.HARD),   view.newGameHard);

	}
	
	/**
	 * Makes a move for the computer A.I.
	 */
	public void computerTurn(){
		if (difficulty == Difficulty.EASY)
			computerEasy();
		else if (difficulty == Difficulty.MEDIUM)
			computerMedium();
		else if (difficulty == Difficulty.HARD)
			computerHard();
	}
	
	/**
	 * Makes a move for the computer A.I. on EASY difficulty
	 */
	public void computerEasy(){		
		while (board.playerTurn == Color.WHITE && !board.gameEnded){
			RandomStrategy randomStrategy = new RandomStrategy(this);
			try{
				Thread.sleep(300);
			}
			catch (InterruptedException interruptedException) {
				System.out.println("Sleep exception occurred");
			}
			board = randomStrategy.move(board);
		}
	}

	/**
	 * Makes a move for the computer A.I. on MEDIUM difficulty
	 */
	public void computerMedium(){
		while (board.playerTurn == Color.WHITE && !board.gameEnded){
			MinimaxStrategy minimaxStrategy = new MinimaxStrategy(this);
			try{
				Thread.sleep(300);
			}
			catch (InterruptedException interruptedException) {
				System.out.println("Sleep exception occurred");
			}
			board = minimaxStrategy.move(board);
		}
	}
	
	/**
	 * Makes a move for the computer A.I. on HARD difficulty
	 */
	public void computerHard(){
		while (board.playerTurn == Color.WHITE && !board.gameEnded){
			AlphaBetaStrategy alphaBetaStrategy = new AlphaBetaStrategy(this);
			board = alphaBetaStrategy.move(board);
		}
	}
}
