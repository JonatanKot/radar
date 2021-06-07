package radar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Random;

public class Trasa implements MouseListener {

	private static final Punkt SYGNAL = new Punkt(Double.MAX_VALUE,Double.MAX_VALUE); //Stala do przekazania Statkowi, kiedy lot sie zakonczy
	
	LinkedList <Odcinek> odcinki = new LinkedList<Odcinek>();
	private int iloscOdcinkow = 0;
	private int wysokosc;
	int indeksOdcinka = 0;
	int trasaSize;
	static Random generator = new Random();

	private static int MIN_LICZBA_ODCINKOW = 1;
	private static int MAX_LICZBA_ODCINKOW = 2;

	public Trasa(LinkedList<Odcinek> odcinki, int wysokosc) {
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
		int predkosc;

		for(int i=0; i<liczbaPunktow; i++) {
			predkosc = random.nextInt(maxPredkosc - minPredkosc) + minPredkosc;

			if(i==0) {
				p1 = new Punkt(Punkt.wygenerujLosowyPunkt());
				p2 = new Punkt(Punkt.wygenerujLosowyPunkt());

			} else {
				p1 = new Punkt(p2);
				p2 = new Punkt(Punkt.wygenerujLosowyPunkt());
			}

			double stosunek = ((p2.getY() - p1.getY())/(p2.getX() - p1.getX()));
			double kierunek = Math.atan(stosunek) * (180/Math.PI);
			if((p2.getY()>p1.getY()) && (p2.getX()<p1.getX())) kierunek-=180;
			if((p2.getY()<p1.getY()) && (p2.getX()<p1.getX())) kierunek-=180;

			//if((p2.getY()>p1.getY()) && (p2.getX()>p1.getX())) kierunek+=180;

			//if((p2.getY()<p1.getY()) && (p2.getX()<p1.getX())) kierunek+=180;
			//double dl_odc = Math.sqrt(((p2.getY() - p1.getY())*((p2.getY() - p1.getY())) + ((p2.getX() - p1.getX()))*(p2.getX() - p1.getX())));
			//double sinusalfa = ((p2.getY() - p1.getY())) / dl_odc;
			//double kierunek = Math.asin(sinusalfa);

			odcinki.add(
					new Odcinek(p1, p2, predkosc, (int) kierunek)
			);
		}

		int wysokosc = random.nextInt(maxWysokosc - minWysokosc) + minWysokosc;

		return new Trasa(odcinki, wysokosc);
	}

	public void zmienWspolrzednePunkuTrasy(int indexPunku, Punkt nowyPunkt) {   //Patrz: Radar -> wygenerujMouseAdapter -> mousePressed
		if (indexPunku > 1) {
			if (indexPunku == (getOdcinki().size())) {
				odcinki.get(getOdcinki().size() - 1).setP2(nowyPunkt);
			} else {
				odcinki.get(indexPunku / 2).setP2(nowyPunkt);
				odcinki.get(indexPunku / 2 + 1).setP1(nowyPunkt);
			}
		}
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
		Punkt p1 = odcinki.get(indeksOdcinka).getP1();
		Punkt p2 = odcinki.get(indeksOdcinka).getP2();

		double x = obliczDeltaX(predkosc, kierunek) + wspolrzedne.getX();
		double y = obliczDeltaY(predkosc, kierunek) + wspolrzedne.getY();

		System.out.println(kierunek + " 	" + obliczDeltaX(predkosc,kierunek) + " 	" + obliczDeltaY(predkosc,kierunek));
		/*while((x > p1.getX() && x > p2.getX()) || (x < p1.getX() && x < p2.getX())){ //Sprawdzamy wyjscie poza obecny odcinek
			//System.out.println(iloscOdcinkow);
			/*if(indeksOdcinka++ == (iloscOdcinkow)){
				return SYGNAL;
			}
			double dx = wspolrzedne.getX()-x;
			double dy = wspolrzedne.getY()-y;
			double len = Math.sqrt(dx * dx + dy * dy);
			dx = p2.getX()-x;
			dy = p2.getY()-y;
			kierunek = odcinki.get(indeksOdcinka).getKierunek();
			predkosc = odcinki.get(indeksOdcinka).getPredkosc();
			double dlen = Math.sqrt(dx * dx + dy * dy);
			x = p2.getX() + dlen / len * obliczDeltaX(predkosc, kierunek);
			y = p2.getY() + dlen / len * obliczDeltaY(predkosc, kierunek);

			p1 = odcinki.get(indeksOdcinka).getP1();
			p2 = odcinki.get(indeksOdcinka).getP2();
		}*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		return new Punkt(x, y);
	}

	// dx i dy są liczone przy zalozeniu, ze ruch satatku na danym odcinku jest jednostanjny i prostoliniowy
	// (dlatego zmiana skladowych przemieszczenia w kazdej sekundzie jest taka sama)

	/**
	 * Zwraca zmiane x-owej skladowej przemieszczenia w ciagu 1s
	 * dx = V/3600 * cos(α) * 1s
	 */
	private double obliczDeltaX(int predkosc, int kierunek) {
		return ((predkosc * Math.cos(zamienStopnieNaRadiany(kierunek)) * 1) / 360);
	}

	/**
	 * Zwraca zmiane y-owej skladowej przemieszczenia w ciagu 1s
	 * dy = V/3600 * sin(α) * 1s
	 */
	private double obliczDeltaY(int predkosc, int kierunek) {
		return ((predkosc * Math.sin(zamienStopnieNaRadiany(kierunek)) * 1) / 360);
	}

	private double zamienStopnieNaRadiany(int kierunek) {
		return kierunek * (Math.PI / 180);
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

	public int getWysokosc() {
		return wysokosc;
	}

//	public void setPunktTrasy(int numerPunktu, Punkt nowyPunkt) {
//		Odcinek odcinek = odcinki.get(numerPunktu / 2);
//
//		if(numerPunktu / 2 == 0) {
//			if(numerPunktu % 2 == 0)
//				odcinek.setP1(nowyPunkt);
//			else
//				odcinek.setP2(nowyPunkt);
//		} else {
//			odcinek = odcinki.get(numerPunktu - 1);
//			odcinek.setP2(nowyPunkt);
//		}
//	}

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
