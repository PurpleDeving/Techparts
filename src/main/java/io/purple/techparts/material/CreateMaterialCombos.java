package io.purple.techparts.material;

import io.purple.techparts.TechParts;
import io.purple.techparts.fluid.FluidBuilder;
import io.purple.techparts.fluid.TPFReg;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.function.Supplier;

import static io.purple.techparts.material.Part.GAS;


public class CreateMaterialCombos {

    private static final Logger LOGGER = TechParts.LOGGER;
    public static ArrayList<TPFReg> TPFREGS = new ArrayList<>();


    public static void init(){
        for(Material material:Materials.MAT_LIST.values()){
            for(Part part: material.parts()){
                LOGGER.info(material.matId() + " - " + part.getId());
                switch (part){
                    case FRAME,BLOCK,SCAFFOLDING:
                        // TODO - Implement Blocks
                        break;
                    case LIQUID:
                        FluidBuilder fluidBuilder = material.fluidBuilder();
                        TPFREGS.add(fluidBuilder.build());
                        break;
                    case GAS:
                        //TODO - Mekanism interaction
                        break;
                    default:

                        // TODO - Imnplement Items
                }
            }
        }
    }
}
