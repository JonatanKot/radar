package radar;

import javax.swing.*;
import java.awt.*;

public class Balon extends Statek {

	public Balon() {
		symbol = new ImageIcon("img/balon.png").getImage();
	}

    public Balon(int wysokosc, Punkt wspolrzedne) {
    	symbol = new ImageIcon("img/balon.png").getImage();
        this.wspolrzedne = wspolrzedne;
        //this.trasa = new Trasa(wysokosc);
    }
}
