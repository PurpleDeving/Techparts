package io.purple.techparts.material;

import com.mojang.logging.LogUtils;
import io.purple.techparts.TechParts;
import io.purple.techparts.fluid.FluidBuilder;
import io.purple.techparts.fluid.TPFReg;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import org.slf4j.Logger;

import java.util.function.Supplier;

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
                        //LOGGER.info("REACH 9981");
                        // TODO - Color Mix from Primary and secondary
                        // TODO - Add to a List to add to the blockentities thingy if needed

                        registerFluid(material.matId()+"_fluid",material.primary().getRGB(),null, DyeColor.GREEN.getMapColor(),false,12);
                    default:
                        // TODO - Imnplement Items
                }
            }
        }




    }


    public static TPFReg registerFluid(String name, int color, Supplier<Block> solidifyBlockSupplier, MapColor mapColor, boolean hot, int luminosity) {
        FluidBuilder fluidBuilder = new FluidBuilder(name, color, solidifyBlockSupplier).mapColor(mapColor);
        if (hot) {
            fluidBuilder.hot();
        }
        if (luminosity > 0) {
            fluidBuilder.luminosity(luminosity);
        }
        return fluidBuilder.build();
    }


}
