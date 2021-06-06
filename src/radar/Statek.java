package radar;

import java.awt.*;
import java.util.Random;

public abstract class Statek {

    protected Trasa trasa;
    protected Punkt wspolrzedne;
    protected Image symbol;

    public Punkt getWspolrzedne() {
        return wspolrzedne;
    }
    
    public boolean przesun() {
    	wspolrzedne = new Punkt(
    	        trasa.obliczAktualneWspolrzedneStatku(wspolrzedne)
        );
        if(wspolrzedne == new Punkt(Double.MAX_VALUE,Double.MAX_VALUE)){
            return(true);
        }
        return(false);
    }

    public static Statek wygenerujLosowyStatek() {
        Statek statek = null;
        Trasa trasa = null;
        Random random = new Random();

        int wylosowanyStatek = random.nextInt(3);

        switch (wylosowanyStatek) {
            case 0 : statek = Samolot.wygenerujLosowySamolot();
            case 1 : statek = Smiglowiec.wygenerujLosowySmiglowiec();
            case 2 : statek = Balon.wygenerujLosowyBalon();
            case 3 : statek = Szybowiec.wygenerujLosowySzybowiec();
        }

        return statek;
    }

    public Trasa getTrasa() {
        return trasa;
    }

    public Image getObraz() {
    	return symbol;
    }
}
