package com.anura;

import com.anura.view.GamePanel;
import object.OBJ_Backpack;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Font arial_40;
    BufferedImage backpackImage;
    Graphics2D g2;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Backpack backpack = new OBJ_Backpack();
        backpackImage = backpack.image;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gp.gameState == gp.playState) {
            // do playState logic
            g2.drawString("Inventory: ", 10, 20);
            // g2.drawImage(backpackImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    // helper method to get center of screen x to display text centered
    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}


