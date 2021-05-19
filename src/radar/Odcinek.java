package radar;

public class Odcinek {
	private Punkt p1,p2;
	private int predkosc;
	private int kierunek;
	
	public Odcinek(Punkt p1, Punkt p2, int predkosc, int kierunek) {
		this.p1 = p1;
		this.p2 = p2;
		this.predkosc = predkosc;
		this.kierunek = kierunek;
	}
}
