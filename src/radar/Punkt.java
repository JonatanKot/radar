package radar;

public class Punkt {
	int x, y;

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Punkt(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Punkt(Punkt punkt) {
		this.x = punkt.x;
		this.y = punkt.y;
	}
}
