package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_GlassBead extends SuperObject{
    public OBJ_GlassBead() {

        name = "marble";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("../objects/glassbead.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
