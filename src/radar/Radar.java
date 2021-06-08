package radar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class Radar extends JPanel {

    private LinkedList<Statek> statki;
    private Image mapa;
    private final ImageIcon symbolPunktu = new ImageIcon("img/punkt.png");
    private Timer timer;
    private ActionListener actionListener;
    private MouseAdapter mouseAdapter;
    private JFrame okno;
    private ObiektyNieporuszajace obiektyNp; // dodanie panelu obiektow nieporuszajacych

    public Radar() {
        obiektyNp = new ObiektyNieporuszajace("dane/dane_obiekty_nieporuszajace.txt");

        actionListener = wygenerujActionListener();

        timer = new Timer(500, actionListener);            //timer wywolujacy co 1s metode actionPerformed()
        timer.start();

        mouseAdapter = wygenerujMouseAdapter();

        ustawPrametryPanelu();
        JFrame okno = ustawParametryOkna();

        statki = new LinkedList<Statek>();
   }

    private ActionListener wygenerujActionListener(){
        return new ActionListener() {                  //W odpowiedzi na okreslona akcje (w tym przypadku wzbudzenie timera wystepujace co 1s) wykonuje zawarte w nim instrukcje
            @Override
            public void actionPerformed(ActionEvent e) {

                for (Statek s : statki) {              //petla for each dla listy statkow powietrznych
                    System.out.println("S = (" + (int) s.getWspolrzedne().getX() + ", " + (int) s.getWspolrzedne().getY() + ")"); //Tymczasowy kod
                    s.wspolrzedne = s.getTrasa().obliczAktualneWspolrzedneStatku(s.wspolrzedne);
                    if(s.wspolrzedne==null) { //sprawdza czy statek dolecial do ostatniego punktu
                        usunObiekty(s); //usuwa statkek gdy doleci do ostatniego punktu
                    }

                }

                for (int i = 0;i<statki.size();i++){
                    Punkt wsp = statki.get(i).getWspolrzedne();
                    int wys = statki.get(i).getTrasa().getWysokosc();
                    for(int j=i+1;j<statki.size();j++){
                        if(odleglosc(wsp,statki.get(j).getWspolrzedne())< 50){
                            if(odleglosc(wsp,statki.get(j).getWspolrzedne())< 25){
                                System.out.println("Jebut kolizja");
                                Statek s1 = statki.get(i);
                                Statek s2 = statki.get(j);
                                usunObiekty(s1);
                                usunObiekty(s2);
                                break;
                            }
                            else{
                                System.out.println("Niebezpieczne zblizenie");
                            }
                        }
                    }
                    for(Map.Entry<Punkt,Integer> entry : obiektyNp.getKwadratyMap().entrySet()){
                        if(entry.getValue() <= wys){
                            if(odleglosc(wsp,entry.getKey())< 50){
                                if(odleglosc(wsp,entry.getKey())< 25){
                                    System.out.println("Jebut kolizja");
                                    Statek s1 = statki.get(i);
                                    usunObiekty(s1);
                                    break;
                                }
                                else{
                                    System.out.println("Niebezpieczne zblizenie");
                                }
                            }
                        }
                    }
                    for(Map.Entry<Punkt,Integer> entry : obiektyNp.getKolaMap().entrySet()){
                        if(entry.getValue() <= wys){
                            if(odleglosc(wsp,entry.getKey())< 50){
                                if(odleglosc(wsp,entry.getKey())< 25){
                                    System.out.println("Jebut kolizja");
                                    Statek s1 = statki.get(i);
                                    usunObiekty(s1);
                                    break;
                                }
                                else{
                                    System.out.println("Niebezpieczne zblizenie");
                                }
                            }
                        }
                    }
                }

                repaint();
            }
        };
    }

    private double odleglosc(Punkt p1, Punkt p2){
        double a = p1.getX() - p2.getX();
        double b = p1.getY() - p2.getY();
        return Math.sqrt(a*a + b*b);
    }

    private MouseAdapter wygenerujMouseAdapter() {
        return new MouseAdapter() {
            int xPrzedPrzesunieciem, yPrzedPrzesunieciem, xPoPrzesunieciu, yPoPrzesunieciu, indexStatku, indexPunktuTrasyStatku;

            public void mousePressed(MouseEvent e) {
                xPrzedPrzesunieciem = e.getX();
                yPrzedPrzesunieciem = e.getY();

                indexStatku = Character.getNumericValue(
                        e.getComponent().getName().charAt(0)                         //Pobiera pierwszy znak z nazwy punku JLabel na mapie
                );

                indexPunktuTrasyStatku = Character.getNumericValue(
                        e.getComponent().getName().charAt(2)                         //Pobiera trzeci znak z nazwy punku JLabel na mapie
                );

                System.out.println(indexPunktuTrasyStatku);                          //Kod tymczasowy

                if(indexPunktuTrasyStatku < statki.get(indexStatku).getTrasa().indeksOdcinka) {
                    System.out.println("Nie można zmienić odcinka trasy, ktory zostal pokonany");
                }
                if(indexPunktuTrasyStatku == statki.get(indexStatku).getTrasa().indeksOdcinka || indexPunktuTrasyStatku == (statki.get(indexStatku).getTrasa().indeksOdcinka+1)) {
                    System.out.println("Nie można zmienić odcinka trasy, na którym aktualnie znajduje sie statek");
                }
                /*Zakladam, ze statek zawsze startuje z pierwszego punktu
			      pierwszego odcinka trasy i że nie można zmieniać odcinka trasy,
			      na którym aktualnie się znajduje. Komunikat wyswietlany w konsoli
			      tymczasowo, potem mozna pomyslec o wyswietlaniu na ekranie glownym.*/
            }

            public void mouseDragged(MouseEvent e) {
                if(indexPunktuTrasyStatku > (statki.get(indexStatku).getTrasa().indeksOdcinka+1)) {
                    int x = e.getComponent().getX() + e.getX() - xPrzedPrzesunieciem;  //Pozycja poczatkowa punktu + aktualna pozycja kursora - pozycja kursora w momencie klikniecia
                    int y = e.getComponent().getY() + e.getY() - yPrzedPrzesunieciem;  //Pozycja poczatkowa punktu + aktualna pozycja kursora - pozycja kursora w momencie klikniecia

                    e.getComponent().setLocation(x, y);

                    if(xPrzedPrzesunieciem != xPoPrzesunieciu || yPrzedPrzesunieciem != yPoPrzesunieciu){
                        xPoPrzesunieciu = e.getComponent().getX() + 10;                     //Dodaje 10 zeby srodek graficznego punktu pokryl sie ze wspolrzednymi faktycznego punktu
                        yPoPrzesunieciu = e.getComponent().getY() + 10;                     //Dodaje 10 zeby srodek graficznego punktu pokryl sie ze wspolrzednymi faktycznego punktu

                        statki.get(indexStatku).
                                getTrasa().zmienWspolrzednePunkuTrasy(
                                indexPunktuTrasyStatku, new Punkt(xPoPrzesunieciu, yPoPrzesunieciu)
                        );
                    }

                    repaint();
                }
            }

//            public void mouseReleased(MouseEvent e) {
//
//            }
        };
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

        //narysujOdcinkiPomiedzyPunktami(g2D);

        /*for(Statek s: statki) {
            g2D.drawImage(s.symbol, (int)s.wspolrzedne.getX(), (int)s.wspolrzedne.getY(), null);
        }*/

        //for(Statek s: statkiPowietrzne)
        //s.rysuj(g);
        for(Statek s: statki){
            g.drawImage(s.getObraz(),(int)s.getWspolrzedne().getX()-25,(int)s.getWspolrzedne().getY()-25,null);
            Trasa trasa = s.getTrasa();
            for(Odcinek o: trasa.getOdcinki()){
                g2D.setColor(Color.RED);
                g2D.setStroke(new BasicStroke(2));
                g.drawLine((int)o.getP1().getX(), (int)o.getP1().getY(), (int)o.getP2().getX(), (int)o.getP2().getY());
            }
        }
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
            x = (int) statek.getTrasa().getPunktTrasy(i).getX() - 10;      //Odejmuje 10 zeby srodek graficznego punktu pokryl sie ze wspolrzednymi faktycznego punktu
            y = (int) statek.getTrasa().getPunktTrasy(i).getY() - 10;      //Odejmuje 10 zeby srodek graficznego punktu pokryl sie ze wspolrzednymi faktycznego punktu
            label.setLocation(x, y);
            label.setName(statki.size() - 1 + "." + i);                    /*Ustawiam nazwe graficznego punktu w celu skojarzenia go z faktycznym punktem trasy danego samolotu
                                                                             nazwa = index_statku.numer_punktu (pierwszy punkt ma numer 0)*/
            label.addMouseListener(mouseAdapter);
            label.addMouseMotionListener(mouseAdapter);

            this.add(label);
            statek.lista_label.add(label);
        }
    }

    private void usunObiekty(Statek statek) {
        for (JLabel p : statek.lista_label){
            this.remove(p);
        }
        statki.remove(statek);
    }

    private void narysujOdcinkiPomiedzyPunktami(Graphics2D g2D) {
        Punkt punkt1 = null, punkt2 = null;                                    //Pierwszy i drugi punkt, z ktorych sklada sie odcinek
        int iloscPunktowTrasy;

        g2D.setColor(Color.RED);

        g2D.setStroke(new BasicStroke(3));                                //Grubosc lini
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