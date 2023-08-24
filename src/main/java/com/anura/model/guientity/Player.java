package com.anura.model.guientity;

import com.anura.controller.KeyHandler;
import com.anura.view.GamePanel;
import com.anura.view.Music;
import com.anura.view.TopPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Player extends Entity {


    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public final List<String> inventory = new LinkedList<>();
    private TopPanel topPanel;

    public Player(GamePanel gp, KeyHandler keyH, TopPanel topPanel) {
        // I removed your calls to GamePanel as this supersedes that
        // also the npc video states we need to make the super call for later npc's
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 18;  // starting x (original 18)
        worldY = gp.tileSize * 14; // starting y (original 14)
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entities/frog_up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entities/frog_up2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entities/frog_down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entities/frog_down2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entities/frog_left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entities/frog_left2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entities/frog_right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entities/frog_right2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        // only switch sprites when keys are being pressed
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            spriteCounter++;
            if (keyH.upPressed) {
                direction = "up";
            }
            if (keyH.downPressed) {
                direction = "down";
            }
            if (keyH.leftPressed) {
                direction = "left";
            }
            if (keyH.rightPressed) {
                direction = "right";
            }
            // check for tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //CHECK NPC Collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // if collision is false, player can move
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
        }

        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }


    public void interactNPC(int i) {

        if (i != 999) {
                gp.gameState = gp.dialogueState;
                if (gp.npc[i].name == "violet"){
                    if(gp.player.inventory.contains("glassbead")){
                        Music.stopBackgroundMusic();
                        gp.ui.musicPlaying = false;
                        gp.gameState = gp.winState;
                    }
                }
                gp.npc[i].speak();
        }
        gp.keyH.enterPressed = false;
    }


    public void pickUpObject(int i) {

        if (i != 999) {

            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "backpack":
                case "bottlecap":
                case "glassbead":
                case "leaf":
                    inventory.add(objectName);
                    TopPanel.updateInventory(inventory);
                    gp.obj[i] = null;
                    break;
            }
        }
    }

    public void contactMonster(int i){

        if (i != 999){
            if(!invincible){
                life -=1;
                invincible = true;
            }
        }
    }

    public void draw(Graphics2D g2, GamePanel gamePanel) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        if (invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        //reset opacity level
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }
}
