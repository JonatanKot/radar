package radar;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Radar radar = new Radar();
        radar.dodajStatek(
                Statek.wygenerujLosowyStatek()
        );
        radar.dodajStatek(
                Statek.wygenerujLosowyStatek()
        );
//        radar.dodajStatek(
//                Statek.wygenerujLosowyStatek()
//        );
    }
}
