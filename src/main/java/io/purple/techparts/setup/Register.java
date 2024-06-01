package io.purple.techparts.setup;

import io.purple.techparts.REF;
import io.purple.techparts.block.MatPartBlock;
import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static io.purple.techparts.TechParts.CREATIVE_MODE_TABS;

public class Register {


    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, REF.ID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, REF.ID);


    public static void registerInit(IEventBus modEventBus) {
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
    }

    public static RegistryObject<MatPartBlock> registerMatPartBlock(Material material, Parts part) {
        return null; // TODO - Implement
    }
}
