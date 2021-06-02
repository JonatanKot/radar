package radar;

import javax.swing.*;
import java.awt.*;

public class Samolot extends Statek {
	public Samolot() {
		symbol = new ImageIcon("img/samolot.png").getImage();
	}

    public Samolot(Punkt wspolrzedne, Trasa trasa) {
    	symbol = new ImageIcon("img/samolot.png").getImage();
        this.wspolrzedne = new Punkt(wspolrzedne);
        this.trasa = new Trasa(trasa);
    }
    
    
}
