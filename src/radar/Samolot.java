package radar;

import javax.swing.*;
import java.awt.*;

public class Samolot extends Statek {
	public Samolot() {
		symbol = new ImageIcon("img/samolot.png").getImage();
	}

    public Samolot(int wysokosc, Punkt wspolrzedne) {
    	symbol = new ImageIcon("img/samolot.png").getImage();
        this.wspolrzedne = wspolrzedne;
        //this.trasa = new Trasa(wysokosc);
    }
    
    
}
