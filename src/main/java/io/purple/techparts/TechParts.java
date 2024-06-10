package io.purple.techparts;

import com.mojang.logging.LogUtils;
import io.purple.techparts.block.MatPartBlockItem;
import io.purple.techparts.item.MatPartItem;
import io.purple.techparts.client.resource.ResourcePackAdapter;
import io.purple.techparts.setup.Register;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import io.purple.techparts.setup.TechPartsPack;

import static io.purple.techparts.setup.Register.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(REF.ID)
public class TechParts {

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();








    public TechParts() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        Register.registerProcess(modEventBus);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        //TEST
        modEventBus.addListener(this::onClientSetup);
        ResourcePackAdapter.registerResourcePack(TechPartsPack.getPackInstance());

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
       if (event.getTabKey() == TECHPARTS_TAB.getKey()){
           LOGGER.info("257 Test");
           for (RegistryObject<MatPartItem> matPartItem : MATERIAL_PART_ITEMS){
               event.accept(matPartItem);
           };
           for (RegistryObject<MatPartBlockItem> matPartBlockItem : MATERIAL_PART_BLOCKITEMS){
               event.accept(matPartBlockItem);
           }
           for (RegistryObject<BlockItem> blockitem : BASIC_BLOCKITEMS){
               event.accept(blockitem);
           }
           for (BucketItem bucketItem : Register.getBuckets().toList()){
               event.accept(bucketItem);
           }
       }

     /*    }
*/
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Enable this to allow Ladder climbing on Frames etc.
        ForgeConfig.SERVER.fullBoundingBoxLadders.set(true);
    }

    @SubscribeEvent
    public /*static*/ void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        LOGGER.info("HELLO FROM CLIENT SETUP");
        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

    }
}
