package radar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class Radar extends JPanel {

    private LinkedList<Statek> statkiPowietrzne;
    private Image mapa;
    private Timer timer;
    private ActionListener actionListener;

    int counter = 0;  //Tymczasowy kod

    public Radar() {

        actionListener = new ActionListener() {                  //W odpowiedzi na okreslona akcje (w tym przypadku co 1s) wykonuje zawarte w nim instrukcje
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Statek s : statkiPowietrzne) {              //petla for each dla listy statkow powietrznych
                    s.przesun();
                    //System.out.println(counter + ". " + "S = (" + (int) s.getWspolrzedne().getX() + ", " + (int) s.getWspolrzedne().getY() + ")"); //Tymczasowy kod
                    repaint();                                   //ponowne wyolanie nadpisanej metody paint()
                    counter++;  //Tymczasowy kod
                }
            }
        };

        timer = new Timer(1000, actionListener);            //timer wywolujacy co 1s metode actionPerformed()
        timer.start();

        ustawPrametryPanelu();
        JFrame okno = ustawParametryOkna();

        statkiPowietrzne  = new LinkedList<Statek>();
    }

    private void ustawPrametryPanelu() {
        this.setPreferredSize(new Dimension(850, 850));
        mapa = new ImageIcon("img/tlo.jpg").getImage();
    }

    private JFrame ustawParametryOkna() {
        JFrame okno = new JFrame();
        okno.add(this);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.pack();                                            //dopasowanie rozmiaru okna do romiaru elementow jakie zawiera
        okno.setLocationRelativeTo(null);                         //po starcie programu ustawia okno na srodku ekranu
        okno.setResizable(true);
        okno.setVisible(true);
        okno.setLayout(null);
        return okno;
    }

    /**
     * Nadpisana metoda klasy Component.
     * Metoda ta przyjmuje jako argument obiekt typu Graphics (klasa Graphics jest klasa abstrakcyjna),
     * ktory jest w rzeczywistosci obiektem typu Graphics2D zrzutowanym w góre (Graphics2D dziedziczy z Graphics).
     * Metoda jest wywoływana automatycznie kiedy tworzymy instacje obiketu typu graficznego np. JFrame
     */
    public void paint(Graphics g) {
       // super.paint(g); //?????
        Graphics2D g2D = (Graphics2D) g;                        //Rrzutowanie w doł obiektu typu graphics na obiekt typu graphics2D, poniewaz ma wiecej funkcjonalnosci
        g2D.drawImage(mapa, 0 , 0 , null);
        //for(Statek s: statkiPowietrzne)
           //s.rysuj(g);
    }

    public void dodajStatek(Statek statek) {
        statkiPowietrzne.add(statek);
    }


}
