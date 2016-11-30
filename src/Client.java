/**
 * Created by Bartłomiej on 2016-06-15.
 */
import java.io.*; import java.net.*;
import java.util.Properties;

public class Client {
    Socket socket;
    OutputStream outputStream;
    PrintWriter printWriter;
    String[][] containersName = new String[3][10];
    String[][] containersPoints = new String[3][10];
    String ip = "localhost";
    String[] places1 = {"place11", "place12", "place13", "place14", "place15",
            "place16", "place17", "place18", "place19", "place110"};
    String[] places2 = {"place21", "place22", "place23", "place24", "place25",
            "place26", "place27", "place28", "place29", "place210"};
    String[] places3 = {"place31", "place32", "place33", "place34", "place35",
            "place36", "place37", "place38", "place39", "place310"};

    String[] points1 = {"points11", "points12", "points13", "points14", "points15", "points16", "points17", "points18",
            "points19", "points110"};
    String[] points2 = {"points21", "points22", "points23", "points24", "points25", "points26", "points27", "points28",
            "points29", "points210"};
    String[] points3 = {"points31", "points32", "points33", "points34", "points35", "points36", "points37", "points38",
            "points39", "points310"};



    public int[] getLevelMap(int numberOfLevel) {
        String aS = "", bS = ""; /// dzieki temu bedzie mozlwiosc zwrotu bezposrednio z funkcji wartosci a i b do kreacji planszy i jednoczesnie zapisanie tych wartosci do pliku
        String nameOfFile, nameOfRequest;
        int[] coefficient = new int[2];

        switch (numberOfLevel){
            case 1: {
                nameOfFile="maplvl1.properties";
                nameOfRequest="get maplvl1";
                break;
            }
            case 2: {
                nameOfFile="maplvl2.properties";
                nameOfRequest="get maplvl2";
                break;
            }
            case 3: {
                nameOfFile="maplvl3.properties";
                nameOfRequest="get maplvl3";
                break;
            }
            case 4: {
                nameOfFile="maplvl4.properties";
                nameOfRequest="get maplvl4";
                break;
            }
            case 5: {
                nameOfFile="maplv5.properties";
                nameOfRequest="get maplvl5";
                break;
            }
            default:{
                nameOfFile="maplvl1.properties";
                nameOfRequest="get maplvl1";
                break;
            }
        }
        try {
            socket = new Socket(ip, 54321);
            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream, true);

            printWriter.println(nameOfRequest);

            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            aS = bufferedReader.readLine();
            bS = bufferedReader.readLine();

            coefficient[0] = 0;//Integer.parseInt(bufferedReader.readLine());
            coefficient[1] = 0;//Integer.parseInt(bufferedReader.readLine());

            File maplvl1 = new File(nameOfFile);
            FileOutputStream fis = new FileOutputStream(maplvl1);
            Properties propConfig = new Properties();
            propConfig.setProperty("a", aS);
            propConfig.setProperty("b", bS);
            propConfig.store(fis, "maplvl");
            fis.close();

            socket.close();

            System.out.println(aS + "   " + bS);
            System.out.println("a = " + coefficient[0] + " i b = " + coefficient[1]);
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
        }

        return coefficient;
    }

    /**
     * Metoda służąca do pobrania z serwera wszystkich najlepszych wyników i zapisania ich do pliku
     */
    public void getBestScores(){
        String nameOfRequest="get bestScores";
        try {
            socket = new Socket(ip, 54321);
            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream, true);
            printWriter.println(nameOfRequest);
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            for(int i=0;i<3;i++){
                for (int j=0;j<10;j++){
                    containersName[i][j]=new String();
                    containersPoints[i][j]=new String();
                }
            }

            for (int b = 0; b < 10; b++) {
                containersName[0][b] = bufferedReader.readLine();
                containersName[1][b] = bufferedReader.readLine();
                containersName[2][b] = bufferedReader.readLine();
                containersPoints[0][b] = bufferedReader.readLine();
                containersPoints[1][b] = bufferedReader.readLine();
                containersPoints[2][b] = bufferedReader.readLine();
            }

            File cfg = new File("bestScores.properties");
            FileOutputStream fos = new FileOutputStream(cfg);
            Properties pro = new Properties();
            for (int c = 1; c<=10;c++)
            {
                pro.setProperty("place1"+ c, containersName[0][c-1]);
                pro.setProperty("points1"+ c, containersPoints[0][c-1]);
                pro.setProperty("place2" + c, containersName[1][c-1]);
                pro.setProperty("points2" + c, containersPoints[1][c-1]);
                pro.setProperty("place3" + c, containersName[2][c-1]);
                pro.setProperty("points3" + c, containersPoints[2][c-1]);
            }
            pro.store(fos,"Hall of Fame");
            fos.close();

            socket.close();

        } catch (Exception e) {
        System.err.println("Client exception: " + e);
         }

    }

    /**
     * Metoda służąca służy do przekazania na serwer najlepszych wyników
     */
    public void setBestScores() {

        String nameOfRequest="set bestScores";
        try {
            socket = new Socket(ip, 54321);
            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream, true);
            printWriter.println(nameOfRequest);
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            try {
                File cfgfile = new File("bestscores.properties");
                FileInputStream fis = new FileInputStream(cfgfile);
                Properties prop = new Properties();
                prop.load(fis);

                for (int j = 0; j < 10; j++) {
                    containersName[0][j] = prop.getProperty(places1[j]);
                    containersName[1][j] = prop.getProperty(places2[j]);
                    containersName[2][j] = prop.getProperty(places3[j]);
                    containersPoints[0][j] = prop.getProperty(points1[j]);
                    containersPoints[1][j] = prop.getProperty(points2[j]);
                    containersPoints[2][j] = prop.getProperty(points3[j]);
                    System.out.println(containersName[0][j]);
                }
                fis.close();
            } catch (FileNotFoundException e) {
                System.err.println("FNFException!");
                e.printStackTrace();
            } catch (IOException f) {
                System.err.println("IOException!");
                f.printStackTrace();
            }
            for (int j = 0; j < 10; j++) {
                printWriter.println("" + containersName[0][j]);
                printWriter.println("" + containersName[1][j]);
                printWriter.println("" + containersName[2][j]);
                printWriter.println("" + containersPoints[0][j]);
                printWriter.println("" + containersPoints[1][j]);
                printWriter.println("" + containersPoints[2][j]);
            }

            System.out.println(bufferedReader.readLine());

            socket.close();
        } catch (Exception e) {
        System.err.println("Client exception: " + e);
        }
    }







}
/*

    File cfgfile = new File("config.properties");
    FileOutputStream fis = new FileOutputStream(cfgfile);
    Properties propConfig = new Properties();
    propConfig.setProperty("imie", "Jacek");
    propConfig.setProperty("nazwisko", "Polak");
    propConfig.setProperty("chuj", "lolol");
    propConfig.store(fis,"Informacje");
    fis.close();

*/

