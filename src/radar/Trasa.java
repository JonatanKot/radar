package radar;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Random;

public class Trasa implements MouseListener {
	
	LinkedList <Odcinek> odcinki = new LinkedList<Odcinek>();
	int wysokosc;
	int indeksOdcinka = 0;
	int trasaSize;
	static Random generator = new Random();

	public Trasa(int wysokosc, int xmax, int ymax){       //Konstruktor do generowania losowych tras (work in progress)
		this.wysokosc = wysokosc;
		trasaSize = generator.nextInt(9)+1;
		Punkt pprev = new Punkt(generator.nextInt(2*xmax)-xmax,generator.nextInt(2*ymax)-ymax);
		for(int i =0;i<trasaSize;i++){
			odcinki.add(new Odcinek(pprev,
					new Punkt(generator.nextInt(2*xmax)-xmax,generator.nextInt(2*ymax)-ymax),
					generator.nextInt(9)+1));
		}
	}

	public Trasa(Odcinek odcinek) {            //Tymczasowy, probny konstruktor
		this.odcinki.add(odcinek);
	}

	public Trasa(Trasa trasa) {               //Tymczasowy, probny konstruktor kopiujacy
		this.wysokosc = trasa.wysokosc;
		this.indeksOdcinka = trasa.indeksOdcinka;
		this.trasaSize = trasa.trasaSize;
		this.odcinki = trasa.odcinki;
	}

	/**
	 * Zwraca aktulane wspolrzedne statku
	 * x = dx + x0
	 * y = dy + y0,
	 * gdzie x0 i y0 to poprzednie wspolrzedne statku (sprzed sekundy)
	 */
	public Punkt obliczAktualneWspolrzedne(Punkt wspolrzedne) {
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
