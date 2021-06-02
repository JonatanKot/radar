package radar;

import java.awt.*;

public abstract class Statek {

    protected Punkt wspolrzedne;
    protected Trasa trasa;
    protected Image symbol;

    public Punkt getWspolrzedne() {
        return wspolrzedne;
    }
    
    public boolean przesun() {
    	wspolrzedne = new Punkt(trasa.obliczAktualneWspolrzedne(wspolrzedne));

        return(false);
    }
    
    public Image getObraz() {
    	return symbol;
    }
}
