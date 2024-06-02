package io.purple.techparts.material;


import io.purple.techparts.block.MatPartBlock;
import io.purple.techparts.block.MatPartBlockItem;
import io.purple.techparts.item.MatPartItem;
import io.purple.techparts.setup.Register;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

import static io.purple.techparts.material.Parts.*;
import static io.purple.techparts.material.Material.*;
import static io.purple.techparts.setup.Register.MATERIAL_PART_BLOCKS;
import static io.purple.techparts.setup.Register.MATERIAL_PART_ITEMS;

public class MatDeclaration{





    public static void init(){
    /*
        Parts Sets
     */

        EnumSet<Parts> metal = EnumSet.of(DUST,Parts.INGOT, NUGGET,BLOCK);
        EnumSet<Parts> vanillaMetals = EnumSet.of(Parts.INGOT,Parts.ORE_RAW, NUGGET,BLOCK);

        EnumSet<Parts> dusts = EnumSet.of(DUST,DUST_SMALL,DUST_TINY,DUST_IMPURE,DUST_PURE);
        EnumSet<Parts> ores = EnumSet.of(ORE_PURIFIED,ORE_CENTRIFUGED,ORE_RAW,ORE_CHUNK,ORE_CRUSHED,ORE_CLUMP,CRYSTAL);

        EnumSet<Parts> dustsandores = EnumSet.copyOf(dusts);
        dustsandores.addAll(ores);

        EnumSet<Parts> gem = EnumSet.of(Parts.GEM,Parts.LENS);
        EnumSet<Parts> all = EnumSet.allOf(Parts.class);

    /*
        Material Sets
     */
        EnumSet<Material> mats_allNonVanilla = EnumSet.range(BRASS,QUARTZITE);

        //Vanilla needs to Exclude existing parts
        createMaterialGroupExcludeGroup(GOLD,all,vanillaMetals);
        createMaterialGroupExcludeGroup(IRON,all,vanillaMetals);
        createMaterialGroupExcludeSingles(GLOWSTONE,all,DUST,BLOCK);
        createMaterialGroupExcludeSingles(COPPER,all,INGOT,ORE_RAW,BLOCK); //Not ExcludeGroup vanillaMetals, because there is no vanilla Copper Nugget
        createMaterialGroupExcludeSingles(REDSTONE,ores);
        createMaterialGroupAddSingles(CHARCOAL,dustsandores,BLOCK,PLATE);
        createMaterialGroupAddSingles(COAL,dustsandores,BLOCK,PLATE);
        createMaterialGroupAddSingles(QUARTZ,ores,DUST,PLATE);
        createMaterialSingles(NETHERITE,DUST,PLATE,NUGGET,PLATE_DENSE,ROD,GEAR_SMALL,LENS);

        //Common Mod Materials
        createMaterialGroupAddSingles(CERTUS_QUARTZ,ores,PLATE,PLATE_DENSE,RING);
        createMaterialGroupAddSingles(CINNABAR,dustsandores,LENS,GEM_POLISHED);
        createMaterialGroupExcludeSingles(SULFUR,dustsandores,DUST);
        createMaterialGroupExcludeSingles(OBSIDIAN,all,GEAR,GEM_POLISHED,LENS);
        createMaterialSingles(REFINED_OBSIDIAN,GEAR,GEM_POLISHED,LENS);

        //Liquids
        //createLiquid(GLUE);

        //ALL loop for WIP things
        for(Material material:mats_allNonVanilla){
            createMaterialGroupAddSingles(material,all);
        }

    }

    private static void createLiquid(Material glue) {

    }

    private static void createMaterialGroupExcludeGroup(Material material, EnumSet<Parts> Group, EnumSet<Parts> exclude) {
        //Array that contains all parts from the group minus the Blacklist
        ArrayList<Parts> listOfParts = new ArrayList<Parts>(Group);
        listOfParts.removeAll(exclude);
        createMaterialFromList(material,listOfParts);
    }


    private static void createMaterialGroupExcludeSingles(Material material, EnumSet<Parts> Group, Parts... blackList){

        //Array that contains all parts from the group minus the Blacklist
        ArrayList<Parts> listOfParts = new ArrayList<Parts>(Group);
        listOfParts.removeAll(List.of(blackList));
        createMaterialFromList(material,listOfParts);
    }

    private static void createMaterialGroupAddSingles(Material material, EnumSet<Parts> Group, Parts... whiteList){

        //Array that contains all parts from whitelist and group
        ArrayList<Parts> listOfParts = new ArrayList<Parts>(Arrays.asList(whiteList));
        listOfParts.addAll(Group);
        createMaterialFromList(material,listOfParts);
    }

    private static void createMaterialSingles(Material material, Parts... whiteList){

        //Array that contains all parts from whitelist and group
        ArrayList<Parts> listOfParts = new ArrayList<Parts>(Arrays.asList(whiteList));
        createMaterialFromList(material,listOfParts);

    }

    private static void createMaterialFromList(Material material, ArrayList<Parts> listOfParts) {
        for(Parts part:listOfParts){

            //Add itemlike (Block or ITEM) to materialParts_XXXX for datagen
            if(part==FRAME ||part==BLOCK){

                MATERIAL_PART_BLOCKS.add(Register.registerMatPartBlock(material,part));
            } else{
                MATERIAL_PART_ITEMS.add(Register.registerMatPartItem(material,part));
            }
        }
    }





}
