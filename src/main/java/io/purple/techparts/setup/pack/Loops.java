package io.purple.techparts.setup.pack;

import io.purple.techparts.TechParts;
import io.purple.techparts.fluid.TPFReg;
import io.purple.techparts.setup.pack.TechPartsPack;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import static io.purple.techparts.TechParts.MODID;
import static io.purple.techparts.material.CreateMaterialCombos.TPFREGS;
import static io.purple.techparts.setup.pack.TechPartsPack.resourceMap;

public class Loops {

    private static final Logger LOGGER = TechParts.LOGGER;

    public static void loopFluids(){

        for (TPFReg fluid:TPFREGS){
            String bucketId = fluid.getBucket().toString(); // Format is "ModID:FluidId_bucket"
            String fluidId = fluid.getName();               // Format is just the id without the ModID

            // Bucket Item Model
            ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(MODID, "models/item/" + fluid.getName()+"_bucket.json");
            resourceMap.put(resourceLocation,JsonHandler.ofText(JsonHandler.generateBucket(fluidId)));


        }

    }
}
