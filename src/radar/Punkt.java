package radar;

public class Punkt {
	double x, y; // zmienilem int-y na double zeby niecalkowita zmiana przemieszczenia nie byla zapominana

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public Punkt(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Punkt(Punkt punkt) {
		this.x = punkt.x;
		this.y = punkt.y;
	}
}
