package com.anura.controller;

import com.anura.view.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {  // won't use this
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // returns the number of the key that was pressed
        // Title State
        if(gp.gameState == gp.titleState){
            if (code == KeyEvent.VK_W) {
                gp.ui.menuNum--;
                if(gp.ui.menuNum < 0){
                    gp.ui.menuNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.menuNum++;
                if(gp.ui.menuNum > 2){
                    gp.ui.menuNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                switch(gp.ui.menuNum) {
                    case 0:
                        gp.gameState = gp.playState;
                        break;
                    case 1:
                        // not implemented yet
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }
            }
        }

        //PLAY STATE
        if (gp.gameState == gp.playState) {
            // if key is pressed, set to true
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            if (code == KeyEvent.VK_SPACE) {
                gp.gameState = gp.pauseState;
            }
        }
        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_SPACE ) {
                gp.gameState = gp.playState;
            }
        }
        //DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER ||
                    code == KeyEvent.VK_W ||
                    code == KeyEvent.VK_D ||
                    code == KeyEvent.VK_A ||
                    code == KeyEvent.VK_S) {
                gp.gameState = gp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        // when key released, set to false
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
    }
}
