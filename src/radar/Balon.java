package radar;

import javax.swing.*;
import java.awt.*;

<<<<<<< HEAD
public class Balon extends Statek {

	public Balon() {
		symbol = new ImageIcon("img/balon.png").getImage();
	}

    public Balon(int wysokosc, Punkt wspolrzedne) {
    	symbol = new ImageIcon("img/balon.png").getImage();
        this.wspolrzedne = wspolrzedne;
        //this.trasa = new Trasa(wysokosc);
=======
public class Balon extends StatekPowietrzny {

    private final Image balon = new ImageIcon("img/balon.png").getImage();

    public Balon(int predkosc, int kierunek, int wysokosc, Wspolrzedne wspolrzedne) {
        this.predkosc = predkosc;
        this.kieruenk = kierunek;
        this.wysokosc = wysokosc;
        this.wspolrzedne = wspolrzedne;
        zrzutujKatZeroStopniNaOsY();
    }

    public void rysuj(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(balon, wspolrzedne.getX(), wspolrzedne.getY(), null);
>>>>>>> 746bed1 (h)
    }
}
