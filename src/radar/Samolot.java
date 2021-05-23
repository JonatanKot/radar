package radar;

import javax.swing.*;
import java.awt.*;

<<<<<<< HEAD
public class Samolot extends Statek {
	public Samolot() {
		symbol = new ImageIcon("img/samolot.png").getImage();
	}

    public Samolot(int wysokosc, Punkt wspolrzedne) {
    	symbol = new ImageIcon("img/samolot.png").getImage();
        this.wspolrzedne = wspolrzedne;
        //this.trasa = new Trasa(wysokosc);
    }
    
    
=======
public class Samolot extends StatekPowietrzny {

    private final Image samolot = new ImageIcon("img/samolot.png").getImage();

    public Samolot(int predkosc, int kierunek, int wysokosc, Wspolrzedne wspolrzedne) {
        this.predkosc = predkosc;
        this.kieruenk = kierunek;
        this.wysokosc = wysokosc;
        this.wspolrzedne = wspolrzedne;
        zrzutujKatZeroStopniNaOsY();
    }

    public void rysuj(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(samolot, wspolrzedne.getX(), wspolrzedne.getY(), null);
    }
>>>>>>> 746bed1 (h)
}
