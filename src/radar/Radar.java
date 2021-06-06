package radar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

public class Radar extends JPanel {

    private LinkedList<Statek> statki;
    private static int iloscStatkow = 0;
    private Image mapa;
    private final ImageIcon symbolPunktu = new ImageIcon("img/punkt.png");
    private Timer timer;
    private ActionListener actionListener;
    private MouseAdapter mouseAdapter;
    private JFrame okno;
    private ObiektyNieporuszajace obiektyNp; // dodanie panelu obiektow nieporuszajacych

    //Animacja, patrz nizej
    /*--------------------------------------------------------------------------------------------------------------*/
    int wx =300, wy =250;
    /*--------------------------------------------------------------------------------------------------------------*/

    int counter = 0;  //Tymczasowy kod

    public Radar() {
        obiektyNp = new ObiektyNieporuszajace("dane/dane_obiekty_nieporuszajace.txt");
        actionListener = new ActionListener() {                  //W odpowiedzi na okreslona akcje (w tym przypadku co 1s) wykonuje zawarte w nim instrukcje
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Statek s : statki) {              //petla for each dla listy statkow powietrznych
                    //s.przesun();
                    //System.out.println(counter + ". " + "S = (" + (int) s.getWspolrzedne().getX() + ", " + (int) s.getWspolrzedne().getY() + ")"); //Tymczasowy kod
                    repaint();                                   //ponowne wyolanie nadpisanej metody paint()
                    counter++;  //Tymczasowy kod
                }
            }
        };

        timer = new Timer(1000, actionListener);            //timer wywolujacy co 1s metode actionPerformed()
        timer.start();

        //Kod jak na razie sluzy tylko do animacji, nie jest zintegrowany z innymi funkcjonalnosciami
        /*--------------------------------------------------------------------------------------------------------------*/

//        Random random = new Random();
//        int px, py;
//
//        for(int i=0; i<3; i++) {
//            px = random.nextInt(400) + i*100 + 90;
//            py = random.nextInt(400) + i*100 + 90;
//
//            JLabel label = new JLabel();
//            label.setIcon(pointIcon);
//            label.setSize(new Dimension(20, 20));
//            label.setLocation(px, py);
//            label.setName("0." + i);
//
//            this.add(label);
//        }

        mouseAdapter = new MouseAdapter() {
            int startX, startY, PlaneIndex, WaypointIndex;

            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();

                PlaneIndex = Character.getNumericValue(
                        e.getComponent().getName().charAt(0) //Pobiera pierwszy znak z nazwy punku JLabel na mapie
                );

                WaypointIndex = Character.getNumericValue(
                        e.getComponent().getName().charAt(2) //Pobiera trzeci znak z nazwy punku JLabel na mapie
                );
            }

            public void mouseDragged(MouseEvent e) {
                e.getComponent().setLocation(
                        e.getComponent().getX() + e.getX() - startX, //pozycja poczatkowa punktu + aktualna pozycja kursora - pozycja kursora w momencie klikniecia
                        e.getComponent().getY() + e.getY() - startY  //pozycja poczatkowa punktu + aktualna pozycja kursora - pozycja kursora w momencie klikniecia
                );
                wx = e.getComponent().getX() + 10;
                wy = e.getComponent().getY() + 10;

                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                statki.get(PlaneIndex).
                        zmienWspolrzednePunkuTrasy(
                                WaypointIndex, new Punkt(wx, wy)
                        );
                System.out.println(wx + " " + wy);
            }
        };

        for(Component c : this.getComponents()) {
            c.addMouseListener(mouseAdapter);
            c.addMouseMotionListener(mouseAdapter);
        }
        /*--------------------------------------------------------------------------------------------------------------*/

        ustawPrametryPanelu();
        JFrame okno = ustawParametryOkna();

        statki = new LinkedList<Statek>();
   }

    private void ustawPrametryPanelu() {
        this.setPreferredSize(new Dimension(850, 850));
        this.setLayout(null);
        mapa = new ImageIcon("img/tlo.jpg").getImage();
    }

    private JFrame ustawParametryOkna() {
        JFrame okno = new JFrame();
        okno.add(this);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.pack();                                            //dopasowanie rozmiaru okna do romiaru elementow jakie zawiera
        okno.setLocationRelativeTo(null);                       //po starcie programu ustawia okno na srodku ekranu
        okno.setResizable(true);
        okno.setVisible(true);
        okno.setLayout(null);
        return okno;
    }

    public void dodajStatek(Statek statek) {
        statki.add(statek);
        umiescPunktyTrasyStatkuNaMapie(statek);
    }

    /**
     * Nadpisana metoda klasy Component.
     * Metoda ta przyjmuje jako argument obiekt typu Graphics (klasa Graphics jest klasa abstrakcyjna),
     * ktory jest w rzeczywistosci obiektem typu Graphics2D zrzutowanym w góre (Graphics2D dziedziczy z Graphics).
     * Metoda jest wywoływana automatycznie kiedy tworzymy instacje obiketu typu graficznego np. JFrame
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);          /*Odwoluje sie do metody paintComponent klasy wyzszej, niezbedne do
                                           narysowania innych komponentow graficznych na zdjeciu tla np. JLabel*/
        Graphics2D g2D = (Graphics2D) g;  //Rrzutowanie w doł obiektu typu graphics na obiekt typu graphics2D, poniewaz g2D ma wiecej funkcjonalnosci
        g.drawImage(mapa, 0,0,null);

        // g.drawImage(mapa, 0,0,this.getWidth(),this.getHeight(),this);  //????

        obiektyNp.paint(g);

        narysujOdcinkiPomiedzyPunktami(g2D);

        //for(Statek s: statkiPowietrzne)
        //s.rysuj(g);
    }

    private void umiescPunktyTrasyStatkuNaMapie(Statek statek) {
        int iloscPunktowTrasy = statek.getTrasa().getOdcinki().size() + 1;  //Punktow jest zawsze o 1 wiecej niz odcinkow
        int x, y;

        for(int i=0; i<iloscPunktowTrasy; i++) {
            JLabel label = new JLabel();
            label.setIcon(symbolPunktu);
            label.setSize(
                    new Dimension(20, 20)
            );
            x = (int) statek.getTrasa().getPunktTrasy(i).getX()-10;
            y = (int) statek.getTrasa().getPunktTrasy(i).getY()-10;
            label.setLocation(x, y);
            label.setName(statki.size() - 1 + "." + i);

            this.add(label);
        }
    }

    private void narysujOdcinkiPomiedzyPunktami(Graphics2D g2D) {
        Punkt punkt1 = null, punkt2 = null;
        int iloscPunktowTrasy;

        g2D.setColor(Color.RED);

        g2D.setStroke(new BasicStroke(3));
        for(Statek statek : statki){
            iloscPunktowTrasy = statek.getTrasa().getOdcinki().size() + 1;

            for (int i = 0; i < iloscPunktowTrasy; i++) {
                if (i == 0) {
                    punkt1 = statek.getTrasa().getPunktTrasy(i);
                } else if (i == 1) {
                    punkt2 = statek.getTrasa().getPunktTrasy(i);
                    g2D.drawLine(
                            (int) punkt1.getX(),
                            (int) punkt1.getY(),
                            (int) punkt2.getX(),
                            (int) punkt2.getY()
                    );
                } else {
                    punkt1 = new Punkt(punkt2);
                    punkt2 = statek.getTrasa().getPunktTrasy(i);
                    g2D.drawLine(
                            (int) punkt1.getX(),
                            (int) punkt1.getY(),
                            (int) punkt2.getX(),
                            (int) punkt2.getY()
                    );
                }
            }
        }
    }


}