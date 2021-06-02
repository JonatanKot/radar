package radar;

public class Main {

    public static void main(String[] args) {

		Radar radar = new Radar();
		Samolot samolot = new Samolot(
				new Punkt(200, 300),
				new Trasa(
						new Odcinek(
							new Punkt(200, 0),
							new Punkt(200, 700),
							1000
						)
				)
		);

		radar.dodajStatek(samolot);

    }
}
