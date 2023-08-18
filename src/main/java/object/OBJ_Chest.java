package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends SuperObject {
    public OBJ_Chest() {

        name = "key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("../objects/placeholder.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
