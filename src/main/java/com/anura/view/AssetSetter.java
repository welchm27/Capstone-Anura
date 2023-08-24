package com.anura.view;

import com.anura.model.guientity.*;
import com.anura.model.object.OBJ_Backpack;
import com.anura.model.object.OBJ_BottleCap;
import com.anura.model.object.OBJ_GlassBead;
import com.anura.model.object.OBJ_Leaf;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

//    public void createObject(int index, int x, int y, Class class){
//
//    }

    public void objectSettings(int index, int x, int y){
        gp.obj[index].worldX = x * gp.tileSize;
        gp.obj[index].worldY = y * gp.tileSize;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Backpack();
        objectSettings(0, 18, 20);

        gp.obj[1] = new OBJ_Leaf();
        objectSettings(1, 40, 29);

        gp.obj[2] = new OBJ_GlassBead();
        objectSettings(2, 7, 29);

        gp.obj[3] = new OBJ_BottleCap();
        objectSettings(3, 54, 23);
    }

    public void npcSettings(int index, int x, int y){
        gp.npc[index].worldX = x * gp.tileSize;
        gp.npc[index].worldY = y * gp.tileSize;
    }

    public void setNPC() {
        gp.npc[0] = new NPC_Toad(gp);
        npcSettings(0, 18, 26);

        gp.npc[1] = new NPC_violet(gp);
        npcSettings(1, 55, 4);

        gp.npc[2] = new NPC_Karen(gp);
        npcSettings(2, 60, 4);

        gp.npc[3] = new NPC_Dawei(gp);
        npcSettings(3, 21, 16);

        gp.npc[4] = new NPC_Tallulah(gp);
        npcSettings(4, 41, 20);

        gp.npc[5] = new NPC_Buckley(gp);
        npcSettings(5, 10, 26);

        gp.npc[6] = new NPC_Triton(gp);
        npcSettings(6, 21, 42);

        gp.npc[7] = new NPC_Sting(gp);
        npcSettings(7, 35, 38);

        gp.npc[8] = new NPC_Destiny(gp);
        npcSettings(8, 50, 4);

    }
}
