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
    	return(false);
    	
    }
    
    public Image getObraz() {
    	return symbol;
    }
}
