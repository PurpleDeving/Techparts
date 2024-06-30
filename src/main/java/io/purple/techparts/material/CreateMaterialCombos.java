package io.purple.techparts.material;

import com.mojang.logging.LogUtils;
import io.purple.techparts.TechParts;
import io.purple.techparts.fluid.FluidBuilder;
import io.purple.techparts.fluid.TPFReg;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import org.slf4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Supplier;


public class CreateMaterialCombos {

    private static final Logger LOGGER = TechParts.LOGGER;
    public static ArrayList<TPFReg> TPFREGS = new ArrayList<>();

    public static void init(){

        for(Material material:Materials.MAT_LIST.values()){
            for(Part part: material.parts()){
                switch (part){
                    case FRAME,BLOCK,SCAFFOLDING:
                        // TODO - Implement Blocks
                        break;
                    case LIQUID:
                        String fluidId = material.matId()+"_fluid";         // No MODID Present here
                        int color = material.primary().getRGB();            // Just RGB, no alpha here // FIXME - Add mix color for Primary + Secondary
                        Supplier<Block> solidifyBlockSupplier = null;       // Should the block solidify ?
                        MapColor mapColor = DyeColor.GREEN.getMapColor();   // TODO - Implement MapColor

                        // Build the Fluid
                        FluidBuilder fluidBuilder = new FluidBuilder(fluidId, color, solidifyBlockSupplier).mapColor(mapColor);

                        // Switch over the different Materials and what they do
                        switch (material.matId()){
                            case "gold":
                                LOGGER.info("TEST 5 - Gold in Liquid Creation");
                                fluidBuilder.hot();
                            case "glowstone":
                                fluidBuilder.luminosity(15);
                                fluidBuilder.gas();
                            default:
                        }


                        TPFREGS.add(registerFluid(material.matId()+"_fluid",material.primary().getRGB(),null, DyeColor.GREEN.getMapColor(),false,12));
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
