package main_components;

public class Functions {
	/**
	 * Gets opposite color. BLACK and WHITE are opposites
	 * @param color	the color to get the opposite of.
	 * @return		BLACK if parameter is WHITE. WHITE if parameter is BLACK.
	 */
	public static Color getOppositeColor(Color color){
		if (color == Color.BLACK)
			return Color.WHITE;
		else if (color == Color.WHITE)
			return Color.BLACK;
		return null;
	}
}
