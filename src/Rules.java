import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Klasa <code>Rules</code> odpowiada za objaśnienie zasad gry w MicroTanks.
 * Dziedziczy ona po klasie abstrakcyjnej <code>Frame</code>
 *
 * @author      Bartłomiej Bielecki <address @ example.com>
 * @author      Jacek Polak <polakjacek@gmail.com>
 * @version     1.1
 * @since       2016-03-26
 */
public class Rules extends Frame
{
    private Font title = new Font("Sans Serif", Font.BOLD, 20);

    /**
     * * Konstruktor klasy <code>Rules</code> inicjalizuje  ramki w zależności od
     * rozmiaru ekranu urządzenia wykonującego
     * program.
     */
    public Rules()
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
     * Metoda tworząca ramkę z polami tekstowymi, w tym jedno zawierające
     * zasady.
     * @param rulesFrame Ramka zawierająca zasady
     * @param mainMenuF Ramka menu głównego, do którego można powrócić za pomocą przycisku
     * @return Brak
     */
    public void createARulesFrame (JFrame rulesFrame, JFrame mainMenuF)
    {
        JButton backButton = createAButton(width, height, 5, "BACK");
        JTextField titleRules = new JTextField();
        JTextArea rulesArea = new JTextArea();
        titleRules.setBounds(width/2-width/10,height/12,width/5,height/9);
        rulesArea.setBounds(width/20,2*height/12+height/9,width*9/10,height/2);
        rulesFrame.setResizable(false);

        titleRules.setText("ZASADY GRY");
        titleRules.setFont(title);
        titleRules.setHorizontalAlignment(JTextField.CENTER);
        rulesArea.setVisible(true);
        rulesFrame.add(rulesArea);
        rulesFrame.add(titleRules);
        titleRules.setVisible(true);
        titleRules.setEditable(false);
        titleRules.setBorder(null);

        backButton.setBounds(width/10,height/12+height/9+height/12+height/2+height/24,width/5,height/12);
        backButton.setVisible(true);
        rulesFrame.add(backButton);

        rulesFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rulesFrame.setVisible(false);
                mainMenuF.setVisible(true);
            }
        });

    }
}