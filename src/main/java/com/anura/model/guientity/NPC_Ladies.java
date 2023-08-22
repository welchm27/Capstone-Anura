package com.anura.model.guientity;

import com.anura.view.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class NPC_Ladies extends Entity {

    public NPC_Ladies(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 0;
        getImage();
        setDialogue();
    }

    public void getImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/entities/frog_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/entities/frog_up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/entities/frog_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/entities/frog_down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/entities/frog_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/entities/frog_left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/entities/frog_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/entities/frog_right2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            //set the characters behavior
//        Random random = new Random();
//        int i = random.nextInt(100)+1;
//
//        if(i <= 25){
//            direction = "up";
//        }
//        if(25 < i && i <= 50){
//            direction = "down";
//        }
//        if(50 < i && i <= 75){
//            direction = "left";
//        }
//        if(75 < i && i <= 100){
//            direction = "right";
//        }
        }
        actionLockCounter = 0;
    }

    public void setDialogue() {

        dialogues[0] = "Hello, there good looking";
        dialogues[1] = "Frog, Pink and, Handsome";
        dialogues[2] = "Frog, Pink and, Handsome";
    }

    public void speak() {
        super.speak();
    }
}
