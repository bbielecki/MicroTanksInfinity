import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.*;

/**
 * Klasa <code>Exit</code> odpowiada za obsługę wyjścia z programu,
 * dziedziczy ona po klasie abstrakcyjnej <code>Frame</code>
 *
 * @author      Bartłomiej Bielecki <address @ example.com>
 * @author      Jacek Polak <polakjacek@gmail.com>
 * @version     1.1
 * @since       2016-03-26
 */

public class Exit extends Frame
{

    Image image;

    /**
     * Konstruktor klasy <code>Exit</code> inicjalizuje  ramki w zależności od
     * rozmiaru ekranu urządzenia wykonującego
     * program.
     */
    public Exit()
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
     * Metoda służąca do tworzenia ramki wyjścia z programu, zawiera obsługę zdarzeniową
     * przycisków, a także pobranie obrazka z internetu
     * @param exitFrame Ramka <code>Exit</code>, na której pojawiają się komponenty
     *                  graficzne m.in. służące do wyjścia z programu lub w nim pozostania
     *
     * @exception IOException
     * @return Brak
     */
    public void createAnExitFrame (JFrame exitFrame)
    {
        JButton exitProgramButton = createAButton(width, height, 1, "YES");
        JButton dontExitButton = createAButton(width, height, 1, "NO");
        JTextField exitQuestion = new JTextField("Do you want to quit the game?",JTextField.CENTER);

        exitProgramButton.setBounds(buttonWidth/8, height - 2*buttonHeight+30, buttonWidth/2, buttonHeight/2);
        exitProgramButton.setVisible(true);
        exitFrame.add(exitProgramButton);

        dontExitButton.setBounds(width-buttonWidth/2-buttonWidth/8, height - 2*buttonHeight+30, buttonWidth/2, buttonHeight/2);
        dontExitButton.setVisible(true);
        exitFrame.add(dontExitButton);

        exitQuestion.setBounds(width / 2 - buttonWidth/2 , height / 2+30 ,buttonWidth,buttonHeight);
        exitQuestion.setVisible(true);
        exitQuestion.setEditable(false);
        exitQuestion.setBorder(null);
        exitQuestion.setFont(new Font("Serif", Font.ITALIC, 22));
        exitFrame.add(exitQuestion);
        exitQuestion.setVisible(true);
        try {
            URL url = new URL("http://betanews.com/wp-content/uploads/2012/03/close-button-300x300.jpg");
            image = ImageIO.read(url);
            image = image.getScaledInstance(width/4,height/5,150);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        ImageIcon im = new ImageIcon(image);

        JLabel imageLabel = new JLabel(im);
        imageLabel.setVisible(true);
        imageLabel.setBounds(width/2-150,10,300,310);
        exitFrame.add(imageLabel);

        exitProgramButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(1);
            }
        });

        dontExitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                exitFrame.setVisible(false);
            }
        });


    }


}