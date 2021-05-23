package radar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class Radar extends JFrame {

    private LinkedList<Statek> statkiPowietrzne;
    private Image mapa;
    private Timer timer;
    private ActionListener actionListener;
    private int czas = 0;

    public Radar() {

        actionListener = new ActionListener() {                            //W odpowiedzi na okreslona akcje (w tym przypadku co 1s) wykonuje zawarte w nim instrukcje
            @Override
            public void actionPerformed(ActionEvent e) {
                czas++;
                for (Statek s : statkiPowietrzne) {              //petla for each dla listy statkow powietrznych
                    s.przesun();
                    System.out.println(czas + ". " + "S = (" + s.getWspolrzedne().getX() + ", " + s.getWspolrzedne().getY() + ")"); //
                    repaint();                                             //ponowne wyolanie nadpisanej metody paint()
                }
            }
        };

        timer = new Timer(1000, actionListener);                       //timer wywolujacy co 1s metode actionPerformed()
        timer.start();

        JPanel panel = ustawPrametryPanelu();                                //glowny panel, w ktorym znajduje sie mapa i do ktorego beda dodawane statki powietrzne
        ustawParametryOkna(panel);

        statkiPowietrzne  = new LinkedList<Statek>();
    }

    private JPanel ustawPrametryPanelu() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(850, 850));
        mapa = new ImageIcon("img/tlo.jpg").getImage();
        return panel;
    }

    private void ustawParametryOkna(JPanel panel) {
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();                                                        //dopasowanie rozmiaru okna do romiaru elementow jakie zawiera
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(null);
    }

    /**
     * Nadpisana metoda klasy Component.
     * Metoda ta przyjmuje jako argument obiekt typu Graphics (klasa Graphics jest klasa abstrakcyjna),
     * ktory jest w rzeczywistosci obiektem typu Graphics2D zrzutowanym w góre (Graphics2D dziedziczy z Graphics).
     * Metoda jest wywoływana automatycznie kiedy tworzymy instacje obiketu typu graficznego np. JFrame
     */
    public void paint(Graphics g) {
       // super.paint(g); //?????
        Graphics2D g2D = (Graphics2D) g;                                   //Rrzutowanie w doł obiektu typu graphics na obiekt typu graphics2D, poniewaz ma wiecej funkcjonalnosci
        g2D.drawImage(mapa, 0 , 0 , null);
        //for(Statek s: statkiPowietrzne)
           //s.rysuj(g);
    }

    public void dodajStatek(Statek statek) {
        statkiPowietrzne.add(statek);
    }


}
