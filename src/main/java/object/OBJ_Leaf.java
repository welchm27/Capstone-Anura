package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Leaf extends SuperObject {

    public OBJ_Leaf() {
        name = "leaf";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("../objects/placeholder.png"));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
