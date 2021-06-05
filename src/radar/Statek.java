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

        return(false);
    }

    public void zmienWspolrzednePunkuTrasy(int indexPunku, Punkt nowyPunkt) {
        trasa.getOdcinki().get(indexPunku/2).setP2(
                nowyPunkt
        );

        trasa.getOdcinki().get(indexPunku/2 + 1).setP1(
                nowyPunkt
        );

        //Kod tymczasowy
        /*----------------------------------------------------------------------------------------------------*/
//        LinkedList<Odcinek> t1 = trasa.getOdcinki();
//
//        for(int i=0; i<3; i++) {
//            System.out.println("P1 = (" + t1.get(i).getP1().getX() + ", " + t1.get(i).getP1().getY() + ")" +
//                    " " + "P2 = (" + t1.get(i).getP2().getX() + ", " + t1.get(i).getP2().getY() + ")");
//        }
        /*----------------------------------------------------------------------------------------------------*/
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
