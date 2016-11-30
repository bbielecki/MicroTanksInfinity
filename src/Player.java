/**
 * Klasa <code>Exit</code> odpowiada za obsługę wyjścia z programu,
 * dziedziczy ona po klasie abstrakcyjnej <code>Frame</code>
 *
 * @author      Bartłomiej Bielecki <address @ example.com>
 * @author      Jacek Polak <polakjacek@gmail.com>
 * @version     1.1
 * @since       2016-03-26
 */
/**
 * Klasa opisująca gracza.
 */
public class Player
{
    private String tankColour = "Czerwony";
    private String name = "player1";
    private int points;
    private int hit;
    private int numberOfMoves;
    private int allShots;

    public Player(String colour, String name)
    {
        tankColour = colour;
        this.name = name;
        points = 0;
        hit = 0;
        allShots = 0;
        numberOfMoves=5;
    }

    public String getTankColour() {return tankColour;}
    public String getName() {return name;}
    public void addPoints(int addedPoints){points=points+addedPoints;}
    public int getPoints() {return points;}
    public int getHit() {return hit;}
    public int getAllShots() {return allShots;}
    public void oneMove(){numberOfMoves--;}
    public int checkNumberOfMoves(){return numberOfMoves;}



    public void addHit(){hit++;}
    public void addShot(){allShots++;}

    public void resetAll()
    {
        hit = 0;
        allShots = 0;
        numberOfMoves = 5;
    }
}