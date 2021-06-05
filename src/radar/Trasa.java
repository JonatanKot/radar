package radar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Random;

public class Trasa implements MouseListener {
	
	LinkedList <Odcinek> odcinki = new LinkedList<Odcinek>();
	private int iloscOdcinkow = 0;
	private int wysokosc;
	int indeksOdcinka = 0;
	int trasaSize;
	static Random generator = new Random();

	private static int MIN_LICZBA_ODCINKOW = 2;
	private static int MAX_LICZBA_ODCINKOW = 6;

//	public Trasa(int wysokosc, int xmax, int ymax){       //Konstruktor do generowania losowych tras (work in progress)
//		this.wysokosc = wysokosc;
//		trasaSize = generator.nextInt(9)+1;
//		Punkt pprev = new Punkt(generator.nextInt(2*xmax)-xmax,generator.nextInt(2*ymax)-ymax);
//		for(int i =0;i<trasaSize;i++){
//<<<<<<< HEAD
//			odcinki.add(new Odcinek(pprev,
//					new Punkt(generator.nextInt(2*xmax)-xmax,generator.nextInt(2*ymax)-ymax),
//					generator.nextInt(9)+1));
//=======
//			odcinki.add(new Odcinek(new Punkt(generator.nextInt(2*xmax)-xmax,generator.nextInt(2*ymax)-ymax),
//					odcinki.add(new Odcinek(pprev,
//							new Punkt(generator.nextInt(2*xmax)-xmax,generator.nextInt(2*ymax)-ymax),
//							generator.nextInt(9)+1));
//>>>>>>> 71b9df6 (Losowanie tras, statkow, rozne zmiany (kod prawdopodobie jeszcze do poprawy))
//		}
//	public Trasa(int wysokosc, int xmax, int ymax){       //Konstruktor do generowania losowych tras (work in progress)
//		this.wysokosc = wysokosc;
//		trasaSize = generator.nextInt(9)+1;
//		Punkt pprev = new Punkt(generator.nextInt(2*xmax)-xmax,generator.nextInt(2*ymax)-ymax);
//		for(int i =0;i<trasaSize;i++){
//			odcinki.add(new Odcinek(new Punkt(generator.nextInt(2*xmax)-xmax,generator.nextInt(2*ymax)-ymax),
//					odcinki.add(new Odcinek(pprev,
//							new Punkt(generator.nextInt(2*xmax)-xmax,generator.nextInt(2*ymax)-ymax),
//							generator.nextInt(9)+1));
//		}

	public Trasa(int minPredkoscKmh, int maxPredkoscKmh, int minWysokoscM, int maxWysokoscM) {}

	public Trasa(LinkedList<Odcinek> odcinki, int wysokosc) {
		this.odcinki = odcinki;
	}

	public Trasa(LinkedList<Odcinek> odcinki) {            //Tymczasowy, probny konstruktor
		this.odcinki = odcinki;
	}

	public Trasa(Trasa trasa) {               //Tymczasowy, probny konstruktor kopiujacy
		this.odcinki = trasa.odcinki;
		this.wysokosc = trasa.wysokosc;
	}

	public static Trasa wygenerujLosowaTrase(int minPredkosc, int maxPredkosc, int minWysokosc, int maxWysokosc) {
		LinkedList <Odcinek> odcinki = new LinkedList<Odcinek>();
		Punkt p1 = null, p2 = null;
		Random random = new Random();

		int liczbaOdcninkow = random.nextInt(MAX_LICZBA_ODCINKOW - MIN_LICZBA_ODCINKOW) + MIN_LICZBA_ODCINKOW;
		int liczbaPunktow = liczbaOdcninkow + 1;
		int predkosc, kierunek;

		for(int i=0; i<liczbaPunktow; i++) {
			predkosc = random.nextInt(maxPredkosc - minPredkosc) + minPredkosc;
			kierunek = random.nextInt(360);

			if(i==0) {
				p1 = new Punkt(Punkt.wygenerujLosowyPunkt());
				p2 = new Punkt(Punkt.wygenerujLosowyPunkt());

			} else {
				p1 = new Punkt(p2);
				p2 = new Punkt(Punkt.wygenerujLosowyPunkt());
			}

			odcinki.add(
					new Odcinek(p1, p2, predkosc, kierunek)
			);

			System.out.println("(" + p1.getX() + ", " + p1.getY() + ")" + " " + "(" + p2.getX() + ", " + p2.getY() + ")");
		}

		int wysokosc = random.nextInt(maxWysokosc - minWysokosc) + minWysokosc;

		return new Trasa(odcinki, wysokosc);
	}

	/**
	 * Zwraca aktulane wspolrzedne statku
	 * x = dx + x0
	 * y = dy + y0,
	 * gdzie x0 i y0 to poprzednie wspolrzedne statku (sprzed sekundy)
	 */
	public Punkt obliczAktualneWspolrzedneStatku(Punkt wspolrzedne) {
		int predkosc = odcinki.get(indeksOdcinka).getPredkosc();
		int kierunek = odcinki.get(indeksOdcinka).getKierunek();
		double x = obliczDeltaX(predkosc, kierunek) + wspolrzedne.getX();
		double y = obliczDeltaY(predkosc, kierunek) + wspolrzedne.getY();

		return new Punkt(x, y);
	}

	// dx i dy są liczone przy zalozeniu, ze ruch satatku na danym odcinku jest jednostanjny i prostoliniowy
	// (dlatego zmiana skladowych przemieszczenia w kazdej sekundzie jest taka sama)

	/**
	 * Zwraca zmiane x-owej skladowej przemieszczenia w ciagu 1s
	 * dx = V/3600 * cos(α) * 1s
	 */
	private double obliczDeltaX(int predkosc, int kierunek) {
		return (predkosc *
				Math.cos(zamienStopnieNaRadiany(kierunek)) *
				1) / 3600;
	}

	/**
	 * Zwraca zmiane y-owej skladowej przemieszczenia w ciagu 1s
	 * dy = V/3600 * sin(α) * 1s
	 */
	private double obliczDeltaY(int predkosc, int kierunek) {
		return (predkosc *
				Math.sin(zamienStopnieNaRadiany(kierunek)) *
				1) / 3600;
	}

	private double zamienStopnieNaRadiany(int kierunek) {
		return kierunek * Math.PI / 180;
	}

	public LinkedList<Odcinek> getOdcinki() {
		return odcinki;
	}

	/**
	 * Zwraca n-ty punkt trasy, pierwszy punkt ma numer 0
	 */
	public Punkt getPunktTrasy(int numerPunktu) {
		Odcinek odcinek = odcinki.get(numerPunktu / 2);

		if(numerPunktu / 2 == 0) {
			if(numerPunktu % 2 == 0)
				return odcinek.getP1();
			else
				return odcinek.getP2();
		} else {
			odcinek = odcinki.get(numerPunktu - 1);
			return odcinek.getP2();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
