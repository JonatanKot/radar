package radar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Random;

public class Trasa implements MouseListener {
	
	LinkedList <Odcinek> odcinki;
	int wysokosc;
	int indeksOdcinka = 0;
	int trasaSize;
	static Random generator = new Random();

	public Trasa(int wysokosc, int xmax, int ymax){       //Konstruktor do generowania losowych tras (work in progress)
		this.wysokosc = wysokosc;
		trasaSize = generator.nextInt(9)+1;
		for(int i =0;i<trasaSize;i++){
			odcinki.add(new Odcinek(new Punkt(generator.nextInt(2*xmax)-xmax,generator.nextInt(2*ymax)-ymax),
					new Punkt(generator.nextInt(2*xmax)-xmax,generator.nextInt(2*ymax)-ymax),
					generator.nextInt(9)+1));
		}
	}
	
	public Punkt przesun(Punkt punkt) {
		return punkt;
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
