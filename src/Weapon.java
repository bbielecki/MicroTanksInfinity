import java.awt.*;

/**
 * Created by Bartłomiej on 2016-06-13.
 */
public class Weapon {

    public Weapon(Graphics g,Image weapon, int x, int y, int width, int height ){

        weapon = weapon.getScaledInstance(width,height,100);
        g.drawImage(weapon, x,y, null);

        }

}
