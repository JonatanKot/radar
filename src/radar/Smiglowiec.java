package radar;

import javax.swing.ImageIcon;

public class Smiglowiec extends Statek{
	public Smiglowiec() {
		symbol = new ImageIcon("img/smiglowiec.png").getImage();
	}

    public Smiglowiec(int wysokosc, Punkt wspolrzedne) {
    	symbol = new ImageIcon("img/smiglowiec.png").getImage();
        this.wspolrzedne = wspolrzedne;
        //this.trasa = new Trasa(wysokosc);
    }

}
