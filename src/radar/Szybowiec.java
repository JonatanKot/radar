package radar;

import javax.swing.ImageIcon;

public class Szybowiec extends Statek{
	public Szybowiec() {
		symbol = new ImageIcon("img/szybowiec.png").getImage();
	}

    public Szybowiec(int wysokosc, Punkt wspolrzedne) {
    	symbol = new ImageIcon("img/szybowiec.png").getImage();
        this.wspolrzedne = wspolrzedne;
        //this.trasa = new Trasa(wysokosc);
    }
}
