package radar;

public class Wspolrzedne {
    private int poczatkowyX;
    private int poczatkowyY;
    private double x;
    private double y;

    public Wspolrzedne(int poczatkowyX, int poczatkowyY) {
        this.poczatkowyX = poczatkowyX;
        this.poczatkowyY = poczatkowyY;
    }

//    public Wspolrzedne(Wspolrzedne wspolrzedne) {
//        poczatkowyX = wspolrzedne.poczatkowyX;
//        poczatkowyY = wspolrzedne.poczatkowyY;
//    }

    /**
     * Zwraca zmiane x-owej skladowej przemieszczenia w ciagu 1s
     */
    private double obliczDeltaX(int predkosc, double kierunek, int czas) {
        return (predkosc *
                Math.cos(zamienStopnieNaRadiany(kierunek)) *
                czas) * 4 / 3600;
    }

    /**
     * Zwraca zmiane y-owej skladowej przemieszczenia w ciagu 1s
     */
    private double obliczDeltaY(int predkosc, double kierunek, int czas) {
        return (predkosc *
                Math.sin(zamienStopnieNaRadiany(kierunek)) *
                czas) * 4 / 3600;
    }

    private double zamienStopnieNaRadiany(double kierunek) {
        return kierunek * Math.PI / 180;
    }

    public Wspolrzedne obliczAktualneWspolrzedne(int predkosc, int kierunek, int czas) {
        x = obliczDeltaX(predkosc, (double) kierunek, czas) + poczatkowyX;                           //skaldowa przemieszczenia x = V/3600 * cos(α) * t + x0 - otrzymujemy wspolrzedna x statku w kilometrach po t sekundach
        y = obliczDeltaY(predkosc, (double) kierunek, czas) + poczatkowyY;                           //skaldowa przemieszczenia y = V/3600 * sin(α) * t + y0 - otrzymujemy wspolrzedna y statku w kilometrach po t sekundach

        return new Wspolrzedne((int) x, (int) y);
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }
}
