package com.anura.model.guientity;

import com.anura.view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    GamePanel gp;
    public int worldX, worldY, speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public boolean collisionOn = false;

    //For item interactions
    public int solidAreaDefaultX, solidAreaDefaultY;

    public Entity (GamePanel gp) {
        this.gp = gp;
    }
}
