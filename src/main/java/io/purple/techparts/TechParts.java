package io.purple.techparts;

import io.purple.techparts.client.ClientHandler;
import io.purple.techparts.client.CreativeTab;
import io.purple.techparts.fluid.TPFReg;
import io.purple.techparts.material.CreateMaterialCombos;
import io.purple.techparts.material.Materials;
import io.purple.techparts.material.Part;
import io.purple.techparts.setup.pack.ResourcePackAdder;
import io.purple.techparts.setup.pack.TechPartsPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static io.purple.techparts.Registry.*;
import static io.purple.techparts.client.CreativeTab.CREATIVE_MODE_TABS;
import static io.purple.techparts.material.CreateMaterialCombos.TPFREGS;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(TechParts.MODID)
public class TechParts {

    // TODO - Version thingy in build.gradle

    // Define mod id in a common place for everything to reference
    public static final String MODID = "techparts";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

/*    // Create a Deferred Register to hold Blocks which will all be registered under the "techparts" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "techparts" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);*/

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public TechParts(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Make sure Materials and Parts Exist
        Materials.init();

        Registry.init();

        // Create Material Items
        CreateMaterialCombos.init();

        ResourcePackAdder.registerResourcePack(TechPartsPack.getPackInstance());

        // Last Step before .register calls
        CreativeTab.creativeTabInit();

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        FLUIDS.register(modEventBus);
        FLUID_TYPES.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);

        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));

        // Add Dispenser Interaction for the Fluids similar to LAVA
        DispenseItemBehavior dispenseitembehavior1 = new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack execute(BlockSource p_338850_, ItemStack p_338251_) {
                DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem) p_338251_.getItem();
                BlockPos blockpos = p_338850_.pos().relative(p_338850_.state().getValue(DispenserBlock.FACING));
                Level level = p_338850_.level();
                if (dispensiblecontaineritem.emptyContents(null, level, blockpos, null, p_338251_)) {
                    dispensiblecontaineritem.checkExtraContent(null, level, p_338251_, blockpos);
                    return this.consumeWithRemainder(p_338850_, p_338251_, new ItemStack(Items.BUCKET));
                } else {
                    return this.defaultDispenseItemBehavior.dispense(p_338850_, p_338251_);
                }
            }
        };

        for (TPFReg tpfReg : TPFREGS) {
            DispenserBlock.registerBehavior(tpfReg.getBucket().asItem(), dispenseitembehavior1);
        }
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(EXAMPLE_BLOCK_ITEM);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

        }

    }
}
