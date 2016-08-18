package main_components;
/** \brief The main classes for our Othello Program */

import javax.swing.JButton;
import java.awt.Point;

/**
 * \brief
 * An extension of JButton. Button knows its Position on the GUI.
 * @author Rodney Shaghoulian
 */
@SuppressWarnings("serial")
public class Button extends JButton{
	public int xPos;	///< x Position of Button
	public int yPos;	///< y Position of Button
	
	/**
	 * Creates a Button given (x,y) coordinates
	 * @param xPos	x Position of Button
	 * @param yPos	y Position of Button
	 */
	public Button(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	/**
	 * Creates a Point to represent current Button
	 * @return
	 */
	public Point createPoint(){
		return new Point(xPos, yPos);
	}
}