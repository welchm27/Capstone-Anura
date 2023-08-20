package com.anura;

import com.anura.view.GamePanel;
import object.OBJ_Backpack;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Font arial_40;
    BufferedImage backpackImage;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Backpack backpack = new OBJ_Backpack();
        backpackImage = backpack.image;
    }

    public void draw(Graphics2D g2){

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawString("Inventory: " ,10,20);
       // g2.drawImage(backpackImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);

    }
}


