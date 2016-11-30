import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * <h1>Witaj w MicroTanks!</h1>
 *  MicroTanks to gra zręcznościowa, która od chwili pierwszego
 *  uruchomienia będzie towarzyszyła Ci przez długi czas!
 *
 * @author      Bartłomiej Bielecki <address @ example.com>
 * @author      Jacek Polak <polakjacek@gmail.com>
 * @version     1.1
 * @since       2016-03-26
 */

public class Main {
    /**
     * Funkcja main inicjalizuje rozgrywkę.
     * @param args Nieużywane
     * @throws IOException
     * @return Brak
     */
    public static void main(String[] args) throws IOException {

        Client client=new Client();
        for(int i=1;i<=3;i++)
            client.getLevelMap(i);

        MainMenu mainMenu = new MainMenu();
        JFrame mainMenuFrame= mainMenu.createAFrame(mainMenu.getWidth(),mainMenu.getHeight(),"Main Menu");
        mainMenuFrame.setForeground(new Color(21,185,125));
        mainMenu.createFrameButtons(mainMenuFrame);
        mainMenuFrame.setVisible(true);



    }
}