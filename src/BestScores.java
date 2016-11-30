




import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.event.WindowAdapter;
        import java.awt.event.WindowEvent;
        import java.io.*;
        import java.lang.reflect.Array;
        import java.util.*;

/**
 * Klasa <code>BestScores</code> jest odpowiedzialna za listę najlepszych wyników,
 * dziedziczy ona po klasie abstrakcyjnej <code>Frame</code>
 *
 * @author      Bartłomiej Bielecki <address @ example.com>
 * @author      Jacek Polak <polakjacek@gmail.com>
 * @version     1.1
 * @since       2016-03-26
 */

public class BestScores extends Frame {

    /**
     * Konstruktor klasy <code>BestScores</code> ustawia lokalne wymiary ramki w zależności
     * od wymiarów ekranu urządzenia uruchamiającego program
     */
    private static JTextField[] tanksScores1 = new JTextField[11];
    private static JTextField[] tanksScores2 = new JTextField[11];
    private static JTextField[] tanksScores3 = new JTextField[11];
    static String[][] containersName = new String[3][10];
    static String[][] containersPoints = new String[3][10];
    private static String[] places1 = {"place11", "place12", "place13", "place14", "place15",
            "place16", "place17", "place18", "place19", "place110"};
    private static String[] places2 = {"place21", "place22", "place23", "place24", "place25",
            "place26", "place27", "place28", "place29", "place210"};
    private static String[] places3 = {"place31", "place32", "place33", "place34", "place35",
            "place36", "place37", "place38", "place39", "place310"};

    private static String[] points1 = {"points11", "points12", "points13", "points14", "points15", "points16", "points17", "points18",
            "points19", "points110"};
    private static String[] points2 = {"points21", "points22", "points23", "points24", "points25", "points26", "points27", "points28",
            "points29", "points210"};
    private static String[] points3 = {"points31", "points32", "points33", "points34", "points35", "points36", "points37", "points38",
            "points39", "points310"};

    public BestScores() {
        this.width = width * 3 / 4;
        this.height = height * 3 / 4;

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }

    /**
     * Metoda ta tworzy ramkę najlepszych wyników gry MicroTanks.
     *
     * @param BestScoresFrame Ramka, na której umieszczone są pola tekstowe z najlepszymi wynikami
     * @param mainMenuFrame   Ramka menu głównego, do którego powrócimy przyciskiem "Back"
     * @return Brak
     */

    public void createABestScoresFrame(JFrame BestScoresFrame, JFrame mainMenuFrame) {

        JButton backToMenuButton = createAButton(width, height, 1, "Back");
        backToMenuButton.setBounds(buttonWidth / 8, height - 2 * buttonHeight + 30, buttonWidth / 2, buttonHeight / 2);
        backToMenuButton.setVisible(true);
        BestScoresFrame.add(backToMenuButton);
        BestScoresFrame.setResizable(false);

        tanksScores1[0] = new JTextField("     1 TANK");
        tanksScores2[0] = new JTextField("     2 TANKS");
        tanksScores3[0] = new JTextField("     3 TANKS");
        tanksScores1[0].setEditable(false);
        tanksScores2[0].setEditable(false);
        tanksScores3[0].setEditable(false);
        tanksScores1[0].setBounds(width / 25, (height / 12), 7 * width / 25, height / 30);
        tanksScores2[0].setBounds(width / 25 + 8 * width / 25, height / 12, 7 * width / 25, height / 30);
        tanksScores3[0].setBounds(width / 25 + 2 * width / 25 + 2 * 7 * width / 25, height / 12, 7 * width / 25, height / 30);
        tanksScores1[0].setBorder(BorderFactory.createDashedBorder(Color.RED));
        tanksScores2[0].setBorder(BorderFactory.createDashedBorder(Color.RED));
        tanksScores3[0].setBorder(BorderFactory.createDashedBorder(Color.RED));
        tanksScores1[0].setHorizontalAlignment(JTextField.CENTER);
        tanksScores2[0].setHorizontalAlignment(JTextField.CENTER);
        tanksScores3[0].setHorizontalAlignment(JTextField.CENTER);
        tanksScores1[0].setVisible(true);
        tanksScores2[0].setVisible(true);
        tanksScores3[0].setVisible(true);
        BestScoresFrame.add(tanksScores1[0]);
        BestScoresFrame.add(tanksScores2[0]);
        BestScoresFrame.add(tanksScores3[0]);

        for (int i = 1; i < 11; i++) {
            tanksScores1[i] = new JTextField();
            tanksScores2[i] = new JTextField();
            tanksScores3[i] = new JTextField();
            tanksScores1[i].setEditable(false);
            tanksScores2[i].setEditable(false);
            tanksScores3[i].setEditable(false);
            tanksScores1[i].setBounds(width / 25, (height / 12) + i * height / 30 + i * height / 60, 7 * width / 25, height / 30);
            tanksScores2[i].setBounds(width / 25 + 8 * width / 25, height / 12 + i * height / 30 + i * height / 60, 7 * width / 25, height / 30);
            tanksScores3[i].setBounds(width / 25 + 2 * width / 25 + 2 * 7 * width / 25, height / 12 + i * height / 30 + i * height / 60, 7 * width / 25, height / 30);
            tanksScores1[i].setBorder(BorderFactory.createLoweredBevelBorder());
            tanksScores2[i].setBorder(BorderFactory.createLoweredBevelBorder());
            tanksScores3[i].setBorder(BorderFactory.createLoweredBevelBorder());


            BestScoresFrame.add(tanksScores1[i]);
            BestScoresFrame.add(tanksScores2[i]);
            BestScoresFrame.add(tanksScores3[i]);


            tanksScores1[i].setVisible(true);
            tanksScores2[i].setVisible(true);
            tanksScores3[i].setVisible(true);

        }
        readBestScores();

        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BestScoresFrame.setVisible(false);
                mainMenuFrame.setVisible(true);
            }
        });
    }

    /**
     * Metoda ta wczytuje do pól tekstowych klasy <code>BestScores</code> najlepsze wyniki z poszczególnych typów
     * rozgrywek. Wykorzystuje klasę <code>Properties</code>.
     *
     * @return Brak
     * @throws IOException
     * @throws FileNotFoundException
     * @see IOException
     * @see FileNotFoundException
     */

    public static void readBestScores() {


        try {
            File cfgfile = new File("bestScores.properties");
            FileInputStream fis = new FileInputStream(cfgfile);
            Properties prop = new Properties();
            prop.load(fis);
            for (int b = 0; b < 10; b++) {
                containersName[0][b] = prop.getProperty(places1[b]);
                containersName[1][b] = prop.getProperty(places2[b]);
                containersName[2][b] = prop.getProperty(places3[b]);
                containersPoints[0][b] = prop.getProperty(points1[b]);
                containersPoints[1][b] = prop.getProperty(points2[b]);
                containersPoints[2][b] = prop.getProperty(points3[b]);

                //tanksScores1[b + 1]=new JTextField();
                tanksScores1[b + 1].setText(containersName[0][b] + "  " + containersPoints[0][b]);
                tanksScores1[b + 1].setHorizontalAlignment(JTextField.CENTER);
                //tanksScores2[b + 1]=new JTextField();
                tanksScores2[b + 1].setText(containersName[1][b] + "  " + containersPoints[1][b]);
                tanksScores2[b + 1].setHorizontalAlignment(JTextField.CENTER);
                //tanksScores3[b + 1]=new JTextField();
                tanksScores3[b + 1].setText(containersName[2][b] + "  " + containersPoints[2][b]);
                tanksScores3[b + 1].setHorizontalAlignment(JTextField.CENTER);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            System.err.println("FNFException!");
            e.printStackTrace();
        } catch (IOException f) {
            System.err.println("IOException!");
            f.printStackTrace();
        }
    }


    public static void saveScores(Player winner, Player second, int amountOfTanks) {
           // readBestScores();
            try {
                File cfgfile = new File("bestScores.properties");
                FileInputStream fis = new FileInputStream(cfgfile);
                Properties prop = new Properties();
                prop.load(fis);
                for (int b = 0; b < 10; b++) {
                    containersName[0][b] = prop.getProperty(places1[b]);
                    containersName[1][b] = prop.getProperty(places2[b]);
                    containersName[2][b] = prop.getProperty(places3[b]);
                    containersPoints[0][b] = prop.getProperty(points1[b]);
                    containersPoints[1][b] = prop.getProperty(points2[b]);
                    containersPoints[2][b] = prop.getProperty(points3[b]);

                }
                fis.close();
            } catch (FileNotFoundException e) {
                System.err.println("FNFException!");
                e.printStackTrace();
            } catch (IOException f) {
                System.err.println("IOException!");
                f.printStackTrace();
            }
                try{

            int newWinnerPlace = checkPlace(amountOfTanks,winner.getPoints());
            int newSecondPlace = checkPlace(amountOfTanks,second.getPoints());

            ArrayList<String> namesList = new ArrayList<>();
            ArrayList<String> pointsList = new ArrayList<>();


            for (int i=0;i<=9;i++)
            {
                namesList.add(containersName[amountOfTanks-1][i]);
            }
            for (int j=0;j<=9;j++)
            {
                pointsList.add(containersPoints[amountOfTanks-1][j]);
            }

            namesList.add(newWinnerPlace-1,winner.getName());
            namesList.add(newSecondPlace-1,second.getName());
            pointsList.add(newWinnerPlace-1,Integer.toString(winner.getPoints()));
            pointsList.add(newSecondPlace-1,Integer.toString(second.getPoints()));


            namesList.remove(11);
            namesList.remove(10);
            pointsList.remove(11);
            pointsList.remove(10);

            for (int i=0;i<=9;i++)
            {
                containersName[amountOfTanks-1][i] = namesList.get(i);
                containersPoints[amountOfTanks-1][i] = pointsList.get(i);
            }

                    File cfg = new File("bestScores.properties");
                    FileOutputStream fos = new FileOutputStream(cfg);
                    Properties pro = new Properties();

                    int o=0,p=0;
                    if(amountOfTanks==1){
                        o=2;
                        p=3;
                    }
                    if(amountOfTanks==2){
                        o=1;
                        p=3;
                    }
                    if(amountOfTanks==3){
                        o=1;
                        p=2;
                    }

            for (int c = 1; c<=10;c++)
            {
                String pro1="place" + Integer.toString(amountOfTanks) + c, pro2="points" + Integer.toString(amountOfTanks) + c;

                pro.setProperty(pro1, containersName[amountOfTanks-1][c-1]);
                pro.setProperty(pro2, containersPoints[amountOfTanks-1][c-1]);
                pro.setProperty("place" + Integer.toString(o) + c, containersName[o-1][c-1]);
                pro.setProperty("points" + Integer.toString(o) + c, containersPoints[o-1][c-1]);
                pro.setProperty("place" + Integer.toString(p) + c, containersName[p-1][c-1]);
                pro.setProperty("points" + Integer.toString(p) + c, containersPoints[p-1][c-1]);
            }
                    pro.store(fos,"Hall of Fame");

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int checkPlace(int amount, int points)
    {
        Integer[] numbers = new Integer[11];
        for (int i = 0;i<10;i++)
        {
            numbers[i] = new Integer(0);
            numbers[i] = Integer.parseInt(containersPoints[amount-1][i]);
        }
        numbers[10]=points;
        Arrays.sort(numbers, Collections.reverseOrder());
        int place = Arrays.asList(numbers).indexOf(points);
        return place+1;
    }
}