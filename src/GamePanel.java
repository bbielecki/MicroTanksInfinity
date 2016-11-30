import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import javax.swing.Timer;

/**
 * Klasa ta odpowiada za panel rozgrywki, umieszczony on jest w klasie <code>GameFrame</code>.
 *
 */
public class GamePanel extends JPanel implements KeyListener {
    private Font font1 = new Font("Calibri", Font.BOLD, 20), font2 = new Font("Calibri", Font.BOLD, 12);
    Font big = new Font("Calibri", Font.BOLD,25);
    private Tank[] tank;
    private Tank currentTank;
    Thread[] tankThreads;
    private int level = 1;
    private Player player1 = new Player(NewGame.getColor1(), NewGame.getName1());
    private Player player2 = new Player(NewGame.getColor2(), NewGame.getName2());
    private Timer t;
    private JProgressBar jp;
    private int width, height, defaultWidth, defaultHeight;
    public int numberOfTanks = NewGame.getNumOfTanks();
    private int turnNumber = 1;
    private long startTime;
    public Color firstColor = NewGame.getRealColor1(), secondColor = NewGame.getRealColor2();

    private int direction;
    public int endOfLevel = 1, timerV = 0;
    private boolean collisionDetected = false, levelHasAlreadyChanged = false, drawStopText = false, setDefoultSize=false;
    JButton left, right,shoot, backButton, nextTurn;
    Choice weapons;

    private Image bImage,explosionGif, weaponImageStar, weaponImageStar2;
    JLabel explosion;

    /**
     * Konstruktor klasy <code>GamePanel</code> przyjmuje wartości szerokości i wysokości panelu rozgrywki.
     *
     * @param x Szerokość panelu gry
     * @param y Wysokość panelu gry
     */

    public GamePanel(int x, int y) {
        width = x;
        height = y;
        weaponImageStar2= null;

        try {
            URL url = new URL("http://cdn.mysitemyway.com/etc-mysitemyway/icons/legacy-previews/icons-256/rounded-glossy-black-icons-signs/095457-rounded-glossy-black-icon-signs-warning-biohazard.png");
            weaponImageStar2 = ImageIO.read(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            URL url = new URL("http://rs651.pbsrc.com/albums/uu236/416o/explosion.gif~c200");
            explosionGif=new ImageIcon(url).getImage();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(width, height));
        tank = new Tank[numberOfTanks];
        tankThreads = new Thread[numberOfTanks];
        createTanks(tank);
        setDoubleBuffered(true);
        setFocusable(true);

        try {
            URL url2 = new URL("http://www.cs.lafayette.edu/~gexia/cs104/labs/lab3/bigexplosion.gif");
            bImage = ImageIO.read(url2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        setDefoultSize=true;
    }

    public int checkTurnNumber() {
        if (turnNumber % 2 == 1)
            return 0;
        else
            return 1;
    }

    public void weaponUpdate(){
        for(int i=0; i<numberOfTanks;i++) {
            tank[i].weaponCoordinatesUpdate();
            repaint();
        }
    }

    public void changeNumberOfMoves(int firstOrSecond) {
        if (firstOrSecond == 0)
            player1.oneMove();
        else
            player2.oneMove();
    }

    public int checkNumberOfMoves(int firstOrSecond) {
        if (firstOrSecond == 0)
            return player1.checkNumberOfMoves();
        else
            return player2.checkNumberOfMoves();
    }

    public Tank[] createTanks(Tank[] tank) {
        for (int i = 0; i < numberOfTanks; i++) {
            tankThreads[i] = new Thread(tank[i]);

            tank[i] = new Tank(width, 200, tankThreads[i]);
            if (i % 2 == 0)                                             //parzyste numery czołgów należa do gracza nr 1, nieparzyste do gracza 2
                tank[i].colorOfTank = firstColor;
            else
                tank[i].colorOfTank = secondColor;

            tank[i].setWeaponImageStar(weaponImageStar2);
        }

        selectATank(tank[0]);
        return tank;
    }

    public void doCountdown()
    {
        timerV = 0;
        t.start();

    }
    public void selectATank(Tank tank) {

        currentTank = tank;
        //addKeyListener(this);
        System.out.println("teraz czolg" + currentTank.getX());
        //currentTank.addNotify();
        currentTank.setReadyToShot(true);
        currentTank.setCollisionsDetected(false);
        repaint();
    }

    public void moveOfTank(int dir) throws InterruptedException {
        currentTank.setCurrentTankStartPosition(currentTank.getX());
        currentTank.setEndOfMove(false);
        direction = dir;
    }

    public void shot() {
        currentTank.setReadyToShot(true);
        currentTank.releaseTheBullet();
        repaint();
        shootDisable();
    }

    public Player getPlayer1(){return player1;}
    public Player getPlayer2(){return player2;}

    public void setAngle(int angle) {
        currentTank.setAngleOfShot(angle);
    }

    public void setStrength(int strength) {
        currentTank.setStrengthOfShot(strength);
    }

    public int getTurnNumber() {return turnNumber;}

    public void nextTurn() {
        System.out.println("zmiana" + turnNumber + "    " + numberOfTanks);
        if (turnNumber < numberOfTanks) {
            turnNumber += 1;

        } else
            turnNumber = 1;

        System.out.println("zmiana" + turnNumber);
        selectATank(tank[turnNumber - 1]);
        shootEnable();
    }

    /**
     * Metoda rysująca planszę w zależności od podanej jej funkcji matematycznej,
     * a także czołgi.
     *
     * @param g Kontekst graficzny
     * @return Brak
     */
    @Override
    public void paintComponent(Graphics g)
    {
        if(setDefoultSize){
            defaultWidth=this.getWidth();
            defaultHeight=this.getHeight();
            setDefoultSize=false;
        }
        super.paintComponent(g);
        double x, x2, y, y2;


        int[] formax = new int[2];

        int[] collisioCoordinates = new int[2];
        if (endOfLevel == 5)
        {

            levelHasAlreadyChanged = true;
            if(level<=4) {
                t = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (timerV == 4) {
                            t.stop();
                            jp.setVisible(false);
                            timerV = 0;
                            drawStopText = true;
                        } else {
                            g.drawString("Zmiana poziomu...", 300, 200);
                            timerV++;
                            jp.setValue(timerV);
                        }
                    }
                });
                t.start();
                jp = new JProgressBar(0, 4);
                jp.setValue(0);
                jp.setStringPainted(true);
                this.add(jp);
                jp.setVisible(true);
                jp.setBounds(defaultWidth / 2 - defaultWidth / 8, defaultHeight / 2 - defaultHeight / 8, defaultWidth / 4, defaultHeight / 4);
            }

            player1.resetAll();
            player2.resetAll();
            endOfLevel = 1;
            level++;


        }

        loadMap(formax);

        int[] groundCoordinates = new int[this.getWidth() + 2];
        groundCoordinates = countGroundCoordinates(formax);
        Polygon ground = new Polygon();

        ground.addPoint(0, this.getHeight());
        for (int i = 1; i < this.getWidth() + 1; i++) {
            ground.addPoint(i, groundCoordinates[i]);
        }
        ground.addPoint(this.getWidth() - 1, this.getHeight());
        ground.addPoint(0, this.getHeight());

        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < numberOfTanks; j++) {
                tank[j].setyCoordinates(groundCoordinates[i + 1], i);
            }
            // line[i]=new Line2D.Double(x,y,x2,y2);
        }
        // normowanie punktów
        // float xScale = (float) getWidth() / 640;
        //  float yScale = (float) getHeight() / 380;

        float xScale = (float) this.getWidth() / defaultWidth;
        float yScale = (float) this.getHeight() / defaultHeight;

        // skalowanie komponentów
        ((Graphics2D) g).scale(xScale, yScale);
        g.setColor(Color.green);
        g.fillPolygon(ground);
        g.drawPolygon(ground);
        // System.out.println(currentTank.getX()+"current Tank");
        try {
            currentTank.move(direction);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int j = 0; j < numberOfTanks; j++) {
            tank[j].setRandomY();
            tank[j].draw(g);
        }

        collisioCoordinates = detectCollision(ground);//wykrycie kolizji z ziemia i innymi czolgami
        if (collisionDetected) {
            Collisions collision = new Collisions(getWidth(), getHeight(), g, collisioCoordinates[0], collisioCoordinates[1], bImage);
            //System.out.println("trafienie");
            collisionDetected = false;
        }

        if(drawStopText){
            String text="To start new Level, please press space";
            g.setColor(Color.red);
            g.setFont(font1);
            g.drawString(text,defaultWidth/5,defaultHeight/10);
        }

        double b= 0.8, a=0.05;
        if (turnNumber % 2 == 1) {
            g.setFont(font1);
            g.setColor(firstColor);
            g.drawString(player1.getName(), (int)(defaultWidth*a), (int)(defaultHeight*a));
            g.drawString(Integer.toString(player1.getPoints()), (int)(defaultWidth*a), (int)(defaultHeight*a+20));
            g.drawString(Integer.toString(player1.getHit()), (int)(defaultWidth*a), (int)(defaultHeight*a+40));
            g.drawString(Integer.toString(player1.getAllShots()),(int)(defaultWidth*a), (int)(defaultHeight*a+60));
            g.setFont(font2);
            g.setColor(secondColor);
            g.drawString(player2.getName(),(int)(defaultWidth*b), (int)(defaultHeight*a));
            g.drawString(Integer.toString(player2.getPoints()), (int)(defaultWidth*b), (int)(defaultHeight*a+20));
            g.drawString(Integer.toString(player2.getHit()), (int)(defaultWidth*b), (int)(defaultHeight*a+40));
            g.drawString(Integer.toString(player2.getAllShots()), (int)(defaultWidth*b), (int)(defaultHeight*a+60));
        } else {
            g.setFont(font2);
            g.setColor(firstColor);
            g.drawString(player1.getName(), (int)(defaultWidth*a), (int)(defaultHeight*a));
            g.drawString(Integer.toString(player1.getPoints()), (int)(defaultWidth*a), (int)(defaultHeight*a+20));
            g.drawString(Integer.toString(player1.getHit()), (int)(defaultWidth*a), (int)(defaultHeight*a+40));
            g.drawString(Integer.toString(player1.getAllShots()),(int)(defaultWidth*a), (int)(defaultHeight*a+60));
            g.setFont(font1);
            g.setColor(secondColor);
            g.drawString(player2.getName(),(int)(defaultWidth*b), (int)(defaultHeight*a));
            g.drawString(Integer.toString(player2.getPoints()), (int)(defaultWidth*b), (int)(defaultHeight*a+20));
            g.drawString(Integer.toString(player2.getHit()), (int)(defaultWidth*b), (int)(defaultHeight*a+40));
            g.drawString(Integer.toString(player2.getAllShots()), (int)(defaultWidth*b), (int)(defaultHeight*a+60));
        }

        if(levelHasAlreadyChanged) {
            weaponUpdate();
            levelHasAlreadyChanged = false;
        }

        if (level > 5)
        {
            //g.setColor(Color.WHITE);
            //g.clearRect(0,0,getWidth(),getHeight());
            g.setColor(Color.BLACK);
            g.setFont(big);
            g.drawString("KONIEC GRY!", getWidth()/4,getHeight()/4);
            if (player1.getPoints() > player2.getPoints())
            {
                g.drawString("Zwycięzca: " + player1.getName(),getWidth()/4,getHeight()/4 +100);
                g.drawString("Ilość punktów: " + player1.getPoints(),getWidth()/4,getHeight()/4 +150);
            }
            else if (player2.getPoints() > player1.getPoints())
            {
                g.drawString("Zwycięzca: " + player2.getName(),getWidth()/4,getHeight()/4+100);
                g.drawString("Ilość punktów: " + player2.getPoints(),getWidth()/4,getHeight()/43 +150);
            }

            else
            {
                g.drawString("Mamy remis proszę państwa!",getWidth()/4,getHeight()/4+100);
            }
        }

        repaint();
    }


    public int[] countGroundCoordinates(int[] coefficient) {
        int[] groundCoordinates = new int[this.getWidth() + 2];

        if (level < 3){
            for (int i = 1; i < this.getWidth() + 2; i++) {
                groundCoordinates[i] = (int) (200 + coefficient[0] * Math.sin((double) (i) / 50) + coefficient[1]);
            }
        }
        else if (level == 3)
        {
            for (int i = 1; i < this.getWidth() + 2; i++) {
                groundCoordinates[i] = (int) (200 + coefficient[0] * (Math.sin(((double) i - (this.getWidth()/2))/5)/(((double)i-(this.getWidth()/2)/5) + coefficient[1])));
            }
        }

        else if (level == 4)
        {
            for (int i = 1; i < this.getWidth() + 2; i++) {
                groundCoordinates[i] = (int) (200 + coefficient[0] * Math.sin((double) (i) / 50) + coefficient[1]);
            }
        }

        else if (level >= 5)
        {
            for (int i = 1; i < this.getWidth() + 2; i++) {
                groundCoordinates[i] = (int) (200 + coefficient[0] * Math.sin((double) (i) / 50) + coefficient[1]);
            }
        }
        return groundCoordinates;
    }


    private int[] detectCollision(Polygon ground) {
        int numberOfShootingTank = 0;
        int[] coordinatesOfCollison = new int[2];
        int numberOfAttacedTank = 0;

        for (int i = 0; i < numberOfTanks; i++) {
            if (tank[i].isShooting())
                numberOfShootingTank = i;
        }

        for (int i = 0; i < numberOfTanks; i++) {
            if (tank[numberOfShootingTank].getBulletFigure().intersects(tank[i].getTankFigure())) {
                //  System.out.println("czolg trafiony");
                numberOfAttacedTank = i;
                //collisionDetected = true;
                if (numberOfShootingTank % 2 == 0) {
                    tank[numberOfShootingTank].setCollisionsDetected(true);
                    player1.addPoints(countingTheNumberOfScoredPoints(numberOfShootingTank, numberOfAttacedTank));
                    player1.addHit();
                }
                if (numberOfShootingTank % 2 == 1) {
                    tank[numberOfShootingTank].setCollisionsDetected(true);
                    player2.addPoints(countingTheNumberOfScoredPoints(numberOfShootingTank, numberOfAttacedTank));
                    player2.addHit();
                }
            }

            if (ground.intersects(tank[numberOfShootingTank].getBulletFigure())) {
                // System.out.println("ziemia trafiona");
                tank[numberOfShootingTank].setCollisionsDetected(true);

            }

            if ((tank[numberOfShootingTank].getBulletFigure().intersects(tank[i].getTankFigure())) || (ground.intersects(tank[numberOfShootingTank].getBulletFigure()))) {
                collisionDetected = true;
                tank[numberOfShootingTank].setCollisionsDetected(true);
                numberOfAttacedTank = i;
                coordinatesOfCollison[0] = tank[numberOfShootingTank].getXBullet();
                coordinatesOfCollison[1] = tank[numberOfShootingTank].getyBullet();
            }

        }
        return coordinatesOfCollison;
    }



    public int countingTheNumberOfScoredPoints(int numberOfShootingTank, int numberOfAttacedTank){
        double points=0.0;
        double p=((double)100/(Math.abs((tank[numberOfShootingTank].getXBullet()-tank[numberOfAttacedTank].getX())*(tank[numberOfShootingTank].getXBullet()-tank[numberOfAttacedTank].getX()))))*10;
        if((p<100 )&&(p>-100)){

            if ((numberOfAttacedTank == numberOfShootingTank) || (numberOfShootingTank % 2 == numberOfAttacedTank % 2))
                points = -p;
            else
                points = p;
        }
        else {
            if ((numberOfAttacedTank == numberOfShootingTank) || (numberOfShootingTank % 2 == numberOfAttacedTank % 2))
                points = -100;
            else
                points = 100;
        }
        System.out.println(points + " czolg " + numberOfShootingTank);
        return (int)points;
    }

    public void setTanksCoordinates(int x) {

    }

    /**
     * Metoda parsująca informacje o planszy do utworzenia z plików
     * konfiguracyjnych.
     *
     * @return Współczynniki funkcji
     * @throws IOException
     */


    private int[] loadMap(int[] form) {
        String a = "0", b = "0", nameOfFile = "";
        switch (level) {
            case 1:
            {
                nameOfFile = "maplvl1.properties";
                break;
            }

            case 2:
            {
                nameOfFile = "maplvl2.properties";
                break;
            }

            case 3:
            {
                nameOfFile = "maplvl3.properties";
                break;
            }

            case 4:
            {
                nameOfFile = "maplvl4.properties";
                break;
            }

            default:
            {
                nameOfFile = "maplvl5.properties";
                break;
            }
        }
        try {
            File mapFile = new File(nameOfFile);
            Properties pro = new Properties();
            FileInputStream fis = new FileInputStream(mapFile);
            pro.load(fis);
            a = pro.getProperty("a");
            b = pro.getProperty("b");
        } catch (IOException e) {
            e.printStackTrace();
        }
        form[0] = Integer.parseInt(a);
        form[1] = Integer.parseInt(b);

        return form;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Metoda ustawiająca kierunek czołgu.
     *
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == e.VK_LEFT) {
            currentTank.setXDirection(-1);
            // System.out.println("dziala");
        }
        if (keyCode == e.VK_RIGHT) {
            currentTank.setXDirection(1);
        }
        if (keyCode == e.VK_SPACE) {
            right.setEnabled(true);
            left.setEnabled(true);
            backButton.setEnabled(true);
            nextTurn.setEnabled(true);
            shoot.setEnabled(true);
            weapons.setEnabled(true);
            drawStopText=false;
        }
    }

    /**
     * Metoda zatrzymująca czołg w momencie puszczenia klawisza kierunkowego(srzałki).
     *
     * @param e KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == e.VK_LEFT) {
            currentTank.setXDirection(0);
        }
        if (keyCode == e.VK_RIGHT) {
            currentTank.setXDirection(0);
        }
    }

    public void addSettingsButtons(JButton l, JButton r, JButton sh, JButton bb, JButton nt, Choice w){
        left=l;
        right=r;
        shoot=sh;
        backButton=bb;
        nextTurn=nt;
        weapons=w;
    }
    public void shootDisable(){
        shoot.setEnabled(false);
    }
    public void shootEnable(){
        shoot.setEnabled(true);
    }
    public Tank getCurrentTank(){return currentTank;}
    private void disableButtons(){
        right.setEnabled(false);
        left.setEnabled(false);
        backButton.setEnabled(true);
        nextTurn.setEnabled(false);
        shoot.setEnabled(false);
        weapons.setEnabled(false);

    }
}