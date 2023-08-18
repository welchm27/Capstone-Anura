package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Marble extends SuperObject{
    public OBJ_Marble() {

        name = "marble";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("../objects/placeholder.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
