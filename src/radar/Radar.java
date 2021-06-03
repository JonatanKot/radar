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
    private MouseAdapter mouseAdapter;
    JFrame okno;

    //Animacja, patrz nizej
    /*--------------------------------------------------------------------------------------------------------------*/
    int x=300, y=250;
    ImageIcon pointIcon = new ImageIcon("img/punkt.png");
    /*--------------------------------------------------------------------------------------------------------------*/

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

        //Kod jak na razie sluzy tylko do animacji, nie jest zintegrowany z innymi funkcjonalnosciami
        /*--------------------------------------------------------------------------------------------------------------*/
        JLabel label = new JLabel();
        label.setIcon(pointIcon);
        label.setSize(new Dimension(20, 20));
        label.setLocation(290, 240);
        this.setLayout(null);
        this.add(label);

        JLabel l1 = new JLabel(), l2 = new JLabel();
        l1.setIcon(pointIcon); l2.setIcon(pointIcon);
        l1.setSize(new Dimension(20, 20)); l2.setSize(new Dimension(20, 20));
        l1.setLocation(60, 60); l2.setLocation(740, 190);
        this.add(l1); this.add(l2);

        mouseAdapter = new MouseAdapter() {
            int startX, startY;

            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                System.out.println(startX + " " + startY);
            }

            public void mouseDragged(MouseEvent e) {
                e.getComponent().setLocation(
                        e.getComponent().getX() + e.getX() - startX, //pozycja poczatkowa punktu + aktualna pozycja kursora - pozycja kursora w momencie klikniecia
                        e.getComponent().getY() + e.getY() - startY  //pozycja poczatkowa punktu + aktualna pozycja kursora - pozycja kursora w momencie klikniecia
                );
                x = e.getComponent().getX() + 10;
                y = e.getComponent().getY() + 10;
                repaint();
            }
        };

        label.addMouseListener(mouseAdapter);
        label.addMouseMotionListener(mouseAdapter);

//        for(Component c : this.getComponents()) {
//            c.addMouseListener(mouseAdapter);
//            c.addMouseMotionListener(mouseAdapter);
//        }
        /*--------------------------------------------------------------------------------------------------------------*/

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
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);          /*Odwoluje sie do metody paintComponent klasy wyzszej, niezbedne do
                                           narysowania innych komponentow graficznych na zdjeciu tla np. JLabel*/
        Graphics2D g2D = (Graphics2D) g;  //Rrzutowanie w doł obiektu typu graphics na obiekt typu graphics2D, poniewaz ma wiecej funkcjonalnosci

        g.drawImage(mapa, 0,0,null);

        g2D.setStroke(new BasicStroke(3));
        g2D.setColor(Color.RED);

        g.drawLine(70, 70, x ,y);
        g.drawLine(x, y, 750 ,200);

       // g.drawImage(mapa, 0,0,this.getWidth(),this.getHeight(),this);  //????

        //for(Statek s: statkiPowietrzne)
           //s.rysuj(g);
   }

    public void dodajStatek(Statek statek) {
        statkiPowietrzne.add(statek);
    }


}