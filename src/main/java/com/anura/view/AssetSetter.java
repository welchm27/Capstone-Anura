package com.anura.view;

import object.OBJ_Backpack;
import object.OBJ_BottleCap;
import object.OBJ_Leaf;
import object.OBJ_GlassBead;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

//    public void createObject(int index, int x, int y, Class class){
//
//    }

    public void setObject(){
        gp.obj[0] = new OBJ_Backpack();
        gp.obj[0].worldX = 18 * gp.tileSize;
        gp.obj[0].worldY = 20 * gp.tileSize;

        gp.obj[1] = new OBJ_Leaf();
        gp.obj[1].worldX = 40 * gp.tileSize;
        gp.obj[1].worldY = 29 * gp.tileSize;

        gp.obj[2] = new OBJ_GlassBead();
        gp.obj[2].worldX = 6 * gp.tileSize;
        gp.obj[2].worldY = 29 * gp.tileSize;

        gp.obj[3] = new OBJ_BottleCap();
        gp.obj[3].worldX = 54 * gp.tileSize;
        gp.obj[3].worldY = 23 * gp.tileSize;

    }
}
