
import java.awt.*;

/**
 * Created by Bartłomiej on 2016-05-23.
 */
public class Collisions {

    public Collisions(int width, int height, Graphics g, int x, int y, Image bI){

        bI = bI.getScaledInstance(width/10,height/10,100);
        g.drawImage(bI, x-width/20,y-width/20, null);
    }
}
