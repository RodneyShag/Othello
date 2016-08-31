package main_components;

/**
 * \brief
 * A representation of the standard Disk, which is BLACK or WHITE
 * @author Rodney Shaghoulian
 *
 */
public class Disk {
	/* Data */
	public Color color;		///< An enumerated type. "WHITE, BLACK, or NONE"

	/**
	 * Constructor. Initializes color and position
	 * @param color		The Color (WHITE, BLACK or NONE) of the Disk
	 * @param position	The Euclidean (x,y) position of the Disk
	 */
	public Disk(Color color){
		this.color = color;
	}
	
	/**
	 * Copy Constructor - Deep Copy
	 * @param otherDisk		The other Disk to create a deep copy of
	 */
	public Disk(Disk otherDisk){
		color = otherDisk.color;
	}
	
	
	/**
	 * Changes color (and icon image) of current Disk
	 * @param color		The new color of the Disk
	 */
	public void changeColor(Color color){
		this.color = color;
	}
}
