import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Klasa <code>Authors</code> zawiera informacje o
 * autorach gry, betatesterach i ludziach, którym
 * należą się podziękowania za cierpliwość.
 * Klasa dziedziczy po klasie abstrakcyjnej <code>Frame</code>
 *
 * @author      Bartłomiej Bielecki <bartlomiejbielecki1@gmail.com>
 * @author      Jacek Polak <polakjacek@gmail.com>
 * @version     1.1
 * @since       2016-03-26
 */

public class Authors extends Frame
{
    private Font title = new Font("Sans Serif", Font.BOLD, 15);

    /**
     * Konstruktor klasy <code>Authors</code>, który inicjalizuje
     * wielkość ramki w zależności od rozmiaru ekranu urządzenia wykonującego
     * program.
     */
    public Authors()
    {
        this.width=width/2;
        this.height=height*3/4;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }

    /**
     * Metoda ta odpowiedzialna jest za utworzenie ramki <code>Authors</code>.
     * @param authorsFrame Ramka, w której tworzone są niezbędne komponenty do przechowywania pól tekstowych
     * @param mainMenuF Ramka menu głównego, do którego powracamy wychodząc z zakładki <code>Authors</code>
     * @return Brak
     */
    public void createAnAuthorsFrame(JFrame authorsFrame, JFrame mainMenuF)
    {
        JButton backButton = createAButton(width, height, 5, "BACK");
        JTextField titleAuthors = new JTextField();
        JTextArea authorsArea = new JTextArea();
        titleAuthors.setBounds(width/2-width/10,height/12,width/5,height/9);
        authorsArea.setBounds(width/20,2*height/12+height/9,width*9/10,height/2);
        authorsFrame.setResizable(false);

        titleAuthors.setText("ZASADY GRY");
        titleAuthors.setFont(title);
        titleAuthors.setHorizontalAlignment(JTextField.CENTER);
        authorsArea.setVisible(true);
        authorsFrame.add(authorsArea);
        authorsFrame.add(titleAuthors);
        titleAuthors.setVisible(true);
        titleAuthors.setEditable(false);
        titleAuthors.setBorder(null);

        backButton.setBounds(width/10,height/12+height/9+height/12+height/2+height/24,width/5,height/12);
        backButton.setVisible(true);
        authorsFrame.add(backButton);

        authorsFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authorsFrame.setVisible(false);
                mainMenuF.setVisible(true);
            }
        });
    }

}