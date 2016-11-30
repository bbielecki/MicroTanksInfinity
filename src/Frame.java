import javax.swing.*;
import java.awt.*;

/**
 * Klasa <code>Frame</code> jest abstrakcyjna i dostarcza szereg danych i metod klasom pochodnym.
 * @author      Bartłomiej Bielecki <bartlomiejbielecki1@gmail.com>
 * @author      Jacek Polak <polakjacek@gmail.com>
 * @version     1.1
 * @since       2016-03-26
 */
public abstract class Frame extends JFrame{

    //region Dane ekranowe
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension dim = kit.getScreenSize();
    protected int width = (int) dim.getWidth();
    protected int height = (int) dim.getHeight();
    protected int buttonHeight = height/8;
    protected int buttonWidth = width/4;
    Font ft2 = new Font("Comic", Font.BOLD, 25);
    //endregion

    /**
     * Metoda tworząca ramkę w zależności od podanych parametrów.
     * @param width Szerokość ramki
     * @param height Wysokość ramki
     * @param name Tytuł ramki
     * @return Zbudowana ramka
     */
    protected JFrame createAFrame(int width, int height, String name){

        JFrame frame = new JFrame(name);


        frame.setSize(width, height);
        frame.setResizable(true);
        frame.setLocation(150, 150);
        frame.setLayout(null);
        frame.setBackground( new Color(21,185,125));

        return frame;
    }

    /**
     * Metoda tworząca przycisk w zależności od podanych parametrów.
     * @param width Szerokość przycisku
     * @param height Wysokość przycisku
     * @param numberOfButton Numer przycisku
     * @param name Napis na przycisku
     * @return Utworzony przycisk
     */
    protected JButton createAButton(int width, int height, int numberOfButton, String name) {
        int buttonHeight = height/8;
        int buttonWidth = width/4;
        JButton button = new JButton(name);

        button.setFont(ft2);
        button.setBackground(Color.LIGHT_GRAY);
        button.setBounds(width / 2 - (buttonWidth / 2), height / 8 +(numberOfButton*buttonHeight+numberOfButton*20), buttonWidth, buttonHeight);
        button.setBorder(null);

        return button;

    }

    /**
     * Metoda zwracająca szerokość ekranu.
     * @return Szerokość ekranu
     */
    public int getWidth(){return width;}

    /**
     * Metoda zwracająca wysokość ekranu.
     * @return Wysokość ekranu
     */
    public int getHeight(){return height;}
}