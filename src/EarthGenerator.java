import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.Properties;

/**
 * Klasa <code>EarthGenerator</code> odpowiada za utworzenie planszy w zależności
 * od podanej funkcji matematycznej.
 * Dziedziczy po klasie abstrakcyjnej <code>Frame</code>
 *
 * @author      Bartłomiej Bielecki <bartlomiejbielecki1@gmail.com>
 * @author      Jacek Polak <polakjacek@gmail.com>
 * @version     1.1
 * @since       2016-03-26
 */

public class EarthGenerator extends Frame{

    /**
     * Metoda ta tworzy planszę w zależności od funkcji matematycznej.
     * @param gamePanel Panel, na którym prowadzona jest rozgrywka
     * @param g Kontekst graficzny
     * @return Panel z narysowaną planszą
     */
    public JPanel earthBuilder(GamePanel gamePanel, Graphics g){

        int x,x2,y,y2;

        Line2D.Double[] line = new Line2D.Double[100];
        Graphics2D g2 = (Graphics2D)g;

        for(int i=1;i<100;i++)
        {
            x=gamePanel.getWidth()/100*i;
            x2=gamePanel.getWidth()/100*(i+1);
            y=(int)Math.sin(x)/x;
            y2=(int)Math.sin(x2)/(x2);
            line[i]=new Line2D.Double(x,y,x2,y2);
            g2.drawLine(x,y,x2,y2);
            g2.setColor(Color.black);
        }

        return gamePanel;
    }

    /**
     * Metoda parsująca informacje o planszy do utworzenia z plików
     * konfiguracyjnych.
     * @return Wzór funkcji
     * @throws IOException
     */
    public String loadMap() throws IOException
    {
        File mapFile = new File("maplvl1.properties");
        Properties pro = new Properties();
        FileInputStream fis = new FileInputStream(mapFile);
        pro.load(fis);
        String jaco = pro.getProperty("Janusz");
        fis.close();
        return jaco;
    }
}