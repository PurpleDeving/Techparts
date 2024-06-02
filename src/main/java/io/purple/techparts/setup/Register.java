package io.purple.techparts.setup;

import io.purple.techparts.REF;
import io.purple.techparts.block.BasicBlock;
import io.purple.techparts.block.MatPartBlock;
import io.purple.techparts.block.MatPartBlockItem;
import io.purple.techparts.item.BasicItem;
import io.purple.techparts.item.MatPartItem;
import io.purple.techparts.item.TechPartItems;
import io.purple.techparts.material.MatDeclaration;
import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collection;

import static io.purple.techparts.TechParts.CREATIVE_MODE_TABS;


public class Register {


    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, REF.ID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, REF.ID);

    public static final Collection<RegistryObject<MatPartItem>> MATERIAL_PART_ITEMS = new ArrayList<>();
    public static final Collection<RegistryObject<MatPartBlock>> MATERIAL_PART_BLOCKS = new ArrayList<>();
    public static final Collection<RegistryObject<MatPartBlockItem>> MATERIAL_PART_BLOCKITEMS = new ArrayList<>();

    public static final Collection<RegistryObject<BasicItem>> BASIC_ITEMS = new ArrayList<>();
    public static final Collection<RegistryObject<BasicBlock>> BASIC_BLOCKS = new ArrayList<>();

    public static void registerInit(IEventBus modEventBus) {
        // Call up all MatPart Declarations
        MatDeclaration.init();
        TechPartItems.init();
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
    }


    // Basic
    public static RegistryObject<BasicItem> registerBasicItem(BasicItem.ItemBuilder bitem) {
        RegistryObject<BasicItem> item = ITEMS.register(bitem.getId(),() -> new BasicItem(bitem));
        BASIC_ITEMS.add(item);
        return item;
    }


    // Mat Part

    public static RegistryObject<MatPartItem> registerMatPartItem(Material material, Parts part) {
        return ITEMS.register(material.getID()+"_"+part.getID(),() -> new MatPartItem.ItemBuilder().mat(material).part(part).build());
    }

    private static<T extends MatPartBlock> RegistryObject<MatPartBlockItem> registerMatPartBlockItem(String name, RegistryObject<T> block){
        return ITEMS.register(name, ()->new MatPartBlockItem(block.get(), baseItemProps()));
    }

    public static RegistryObject<MatPartBlock> registerMatPartBlock(Material material, Parts part) {
        BlockBehaviour.Properties properties = Register.baseBlockProps();
        if(part == Parts.FRAME){
            properties.noOcclusion();
        }
        RegistryObject<MatPartBlock> toReturn = BLOCKS.register(material.getID()+"_"+part.getID(),() -> new MatPartBlock(material,part,properties));
        MATERIAL_PART_BLOCKITEMS.add(registerMatPartBlockItem(material.getID()+ part.getID(),toReturn));
        return null; // TODO - Implement
    }

    // Helper Methods

    public static BlockBehaviour.Properties baseBlockProps() {
        return BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK);
    }

    public static Item.Properties baseItemProps() {
        return new Item.Properties();
    }


}
