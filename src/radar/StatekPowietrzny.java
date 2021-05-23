package radar;

import java.awt.*;

public abstract class StatekPowietrzny {

    protected int predkosc; //W km/h
    protected int kieruenk; //W stopniach (0-360) 0 to polnoc, 90 to wschod...
    protected int wysokosc;
    protected Wspolrzedne wspolrzedne;

    protected void zrzutujKatZeroStopniNaOsY() {
        kieruenk += 270;
    }

    public abstract void rysuj(Graphics g);

    public void ivanPosunSie(int czas) {
        wspolrzedne.obliczAktualneWspolrzedne(predkosc, kieruenk, czas);
    }

    public Wspolrzedne getWspolrzedne() {
        return wspolrzedne;
    }
}
