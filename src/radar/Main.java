package radar;

public class Main {

    public static void main(String[] args) {

		Radar radar = new Radar();
		Samolot samolot = new Samolot(10, new Punkt(200, 200));
		Balon balon = new Balon(5, new Punkt(500, 300));

		radar.dodajStatek(samolot); radar.dodajStatek(balon);

//		Wspolrzedne w = new Wspolrzedne(2000, 1000);
//		w = w.obliczWspolrzedne(100, 0,1000,5);
//		System.out.println("S = (" + w.getX() + ", " + w.getY() + ")");
    }
}
