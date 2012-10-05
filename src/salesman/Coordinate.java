package salesman;

public class Coordinate {
	private double x, y;

	Coordinate(double leX, double leY) {
		x = leX;
		y = leY;
	}

	public double distance(Coordinate b) {
		return Math.sqrt((this.x - b.x) * (this.x - b.x) + (this.y - b.y)
				* (this.y - b.y));
	}

	public int convertX(int _width, int _absMax) {
		return (int) Math.floor(((this.x + _absMax) * _width) / (2 * _absMax));
	}

	public int convertY(int _height, int _ordMax) {
		return (int) Math.floor(((this.y + _ordMax) * _height) / (2 * _ordMax));
	}
}
