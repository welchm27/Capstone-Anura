package com.anura.model.guientity;

import com.anura.view.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_Ladies extends Entity{

    public NPC_Ladies(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 0;
        getImage();
    }

    public void getImage(){
        try{
            down1 = ImageIO.read(getClass().getResourceAsStream("/entities/frog_down1.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
