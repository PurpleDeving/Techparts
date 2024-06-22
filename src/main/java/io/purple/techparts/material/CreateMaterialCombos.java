package io.purple.techparts.material;

import com.mojang.logging.LogUtils;
import io.purple.techparts.TechParts;
import org.slf4j.Logger;

public class CreateMaterialCombos {

    private static final Logger LOGGER = TechParts.LOGGER;

    public static void init(){

        for(Material material:Materials.MAT_LIST.values()){
            for(Part part: material.parts()){
                switch (part){
                    case FRAME,BLOCK,SCAFFOLDING:
                        // TODO - Implement Blocks
                        break;
                    case LIQUID:

                    default:
                        // TODO - Imnplement Items
                }
            }
        }




    }

}
