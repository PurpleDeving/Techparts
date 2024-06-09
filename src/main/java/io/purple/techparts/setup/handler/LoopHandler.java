package io.purple.techparts.setup.handler;

import io.purple.techparts.REF;
import io.purple.techparts.block.BasicBlock;
import io.purple.techparts.block.MatPartBlock;
import io.purple.techparts.item.BasicItem;
import io.purple.techparts.item.MatPartItem;
import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import io.purple.techparts.setup.TechPartsPack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.purple.techparts.setup.Register.*;
import static io.purple.techparts.setup.handler.JsonHandler.ofText;
import static io.purple.techparts.setup.handler.ModelHandler.*;

public class LoopHandler {

    public static final Map<String, String> translatables = new HashMap<>();

    public static void basicItemLoop(Map<ResourceLocation, TechPartsPack.IResourceStreamSupplier> resourceMap){

        for (RegistryObject<BasicItem> entry : BASIC_ITEMS) {
            BasicItem item = entry.get();
            String id = item.getId();
            String name = item.getName();

            if(id.contains("block")){
                continue;
            }

            /*******************************************************
             *
             *  Translation
             *
             *****************************************************/

            translatables.put(String.format("item.%s.%s", REF.ID, id), name);

            /*******************************************************
             *
             *  TAGS
             *
             *****************************************************/

            Tags.MINEABLEPICK.addItem(entry.get().getId());

            /*******************************************************
             *
             *  Model
             *
             *****************************************************/

            ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/" + id + ".json");
            String itemModelJson = ModelHandler.generateItemModelJson("basic/" + id);
            resourceMap.put(itemModel, ofText(itemModelJson));
        }
    }


    public static void basicBlockLoop(Map<ResourceLocation, TechPartsPack.IResourceStreamSupplier> resourceMap){
        for (RegistryObject<BasicBlock> entry : BASIC_BLOCKS) {
            String blockID = entry.get().getId();
            String path = entry.get().getTexPath();

            /*******************************************************
             *
             *  Translation
             *
             *****************************************************/
            translatables.put(String.format("block.%s.%s", REF.ID, blockID), entry.get().getClearName());

            /*******************************************************
             *
             *  TAGS
             *
             *****************************************************/
            Tags.MINEABLEPICK.addItem(entry.get().getId());

            /*******************************************************
             *
             *  Model
             *
             *****************************************************/

            ResourceLocation blockstate = new ResourceLocation(REF.ID, "blockstates/" + blockID + ".json");
            ResourceLocation blockModel = new ResourceLocation(REF.ID, "models/block/basic/" + blockID + ".json");
            ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/" + blockID + ".json");
            String blockstateJson = ModelHandler.generateBlockstatesJson(path); // Path to Block Model
            String blockModelJson = generateBlockModelJson(path);     // Path to texture
            String itemModelJson = generateBlockItemModelJson(path);  // Path to texture

        }
    }


    public static void MatPartItemLoop(Map<ResourceLocation, TechPartsPack.IResourceStreamSupplier> resourceMap){

        /*******************************************************
         *
         *  Material Part Items - 3 Step Flow because many Items share same json
         *
         *****************************************************/

        // Step 1: Filter the MATERIAL_PART_ITEMS to get unique combinations of textures and parts
        Set<String> uniquePaths = MATERIAL_PART_ITEMS.stream()
                .map(entry -> entry.get().getTexPath())
                .collect(Collectors.toSet());

        // Step 2: Precompute JSON strings paths for the filtered combinations
        Map<String, String> itemModelJsonMap = new HashMap<>();
        for (String path : uniquePaths) {
            String itemModelJson = generateMaterialItemModelJson(path);
            itemModelJsonMap.put(path, itemModelJson);
        }


        for (RegistryObject<MatPartItem> entry : MATERIAL_PART_ITEMS){
            String path = entry.get().getTexPath();
            String itemId = entry.get().getId();

            /*******************************************************
             *
             *  Model
             *
             *****************************************************/

            ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/" + itemId + ".json");
            resourceMap.put(itemModel, ofText(itemModelJsonMap.get(path)));

            /*******************************************************
             *
             *  Translation
             *
             *****************************************************/
            translatables.put(String.format("item.%s.%s", REF.ID, itemId), entry.get().getName());
        }
    }


    public static void MatPartBlockLoop(Map<ResourceLocation, TechPartsPack.IResourceStreamSupplier> resourceMap){

        /*******************************************************
         *
         *  Material Part Block - 3 Step Flow because many Items share same json
         *
         *****************************************************/

        // Step 1: Filter the MATERIAL_PART_ITEMS to get unique combinations of textures and parts
        Set<String> uniquePaths = MATERIAL_PART_BLOCKS.stream()
                .map(entry -> entry.get().getTexPath())
                .collect(Collectors.toSet());

        // Step 2: Precompute JSON strings and ResourceLocation paths for the filtered combinations
        Map<String, String> blockstateJsonMap = new HashMap<>();
        Map<String, String> blockModelJsonMap = new HashMap<>();
        Map<String, String> itemModelJsonMap = new HashMap<>();

        for (String path : uniquePaths) {
            String blockstateJson = generateBlockstatesJson(path);
            String blockModelJson = path.contains("frame") ? generateFrameModelJson(path) : generateBlockModelJson(path);
            String itemModelJson = generateBlockItemModelJson(path);

            blockstateJsonMap.put(path, blockstateJson);
            blockModelJsonMap.put(path, blockModelJson);
            itemModelJsonMap.put(path, itemModelJson);

            // Since the BlockModel Json and its location are only dependent on path, they can created with just the texture + path combination
            resourceMap.put(new ResourceLocation(REF.ID, "models/" + path + ".json"), ofText(blockModelJson));
        }

        for(RegistryObject<MatPartBlock> entry :MATERIAL_PART_BLOCKS){
            String blockID = entry.get().getId();
            Material material = entry.get().getMaterial();
            String path = entry.get().getTexPath(); //Path here leads to the Texture + Part combination from Step 1 and 2
            Parts part = entry.get().getPart();

            /*******************************************************
             *
             *  MODEL
             *
             *****************************************************/

            ResourceLocation blockstate = new ResourceLocation(REF.ID, "blockstates/" + blockID + ".json");
            ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/" + blockID + ".json");
            String blockstateJson = blockstateJsonMap.get(path);
            String itemModelJson = itemModelJsonMap.get(path);
            resourceMap.put(blockstate, ofText(blockstateJson));
            resourceMap.put(itemModel, ofText(itemModelJson));

            /*******************************************************
             *
             *  TAGS
             *
             *****************************************************/
            Tags.MINEABLEPICK.addItem(entry.get().getId());
            if(part == Parts.FRAME || part == Parts.SCAFFOLDING){
                Tags.CLIMBABLE.addItem(entry.get().getId());
            }

            /*******************************************************
             *
             *  Translation
             *
             *****************************************************/
            translatables.put(String.format("block.%s.%s", REF.ID, blockID), entry.get().getClearName());

        }

    }

}


