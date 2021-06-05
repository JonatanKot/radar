package radar;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ObiektyNieporuszajace extends JPanel{

    private HashMap<Punkt, Integer> kolaMap = new HashMap<Punkt, Integer>();
    private HashMap<Punkt, Integer> kwadratyMap = new HashMap<Punkt, Integer>();

    public static final int ILOSC = 5; // ustalona liczba obiektow
    public static final int PROMIEN = 25; // staly promien
    public static final int MAX = 100; // maksymalna wysokosc obiektu nieporuszajacego

    ObiektyNieporuszajace() {
        this.setPreferredSize(new Dimension(850,850));
        int wartoscX,wartoscY, wysokosc;
        for(int i=0; i<ILOSC; i++) {        //generator obiektu nieporuszajacego -> kolo
            wartoscX = (int) Math.floor(Math.random() * (825 - PROMIEN + 1) + PROMIEN);
            wartoscY = (int) Math.floor(Math.random() * (825 - PROMIEN + 1) + PROMIEN);
            wysokosc = (int) Math.floor(Math.random() * MAX);
            kolaMap.put(new Punkt(wartoscX, wartoscY), wysokosc);
        }
        for(int i=0; i<ILOSC; i++) {        //generator obiektu nieporuszajacego -> kwadrat
            wartoscX = (int) Math.floor(Math.random() * (825 - PROMIEN + 1) + PROMIEN);
            wartoscY = (int) Math.floor(Math.random() * (825 - PROMIEN + 1) + PROMIEN);
            wysokosc = (int) Math.floor(Math.random() * MAX);
            kwadratyMap.put(new Punkt(wartoscX, wartoscY), wysokosc);
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        for (Punkt punkt : kolaMap.keySet()) {
            g2D.setColor(Color.orange);
             g2D.fillOval((int)punkt.getX(), (int)punkt.getY(), PROMIEN,PROMIEN);
        }
        for (Punkt punkt : kwadratyMap.keySet()) {
            g2D.setColor(Color.cyan);
            g2D.fillRect((int)punkt.getX(), (int)punkt.getY(), PROMIEN,PROMIEN);
        }
    }

    public HashMap<Punkt, Integer> getKolaMap() {
        return kolaMap;
    }

    public HashMap<Punkt, Integer> getKwadratyMap() {
        return kwadratyMap;
    }
}

