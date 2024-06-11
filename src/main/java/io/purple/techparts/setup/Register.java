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
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.*;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static io.purple.techparts.TechParts.LOGGER;
import static io.purple.techparts.item.TechPartItems.SAPPHIRE;


public class Register {


    // Create a Deferred Register to hold Blocks which will all be registered under the REF.ID namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, REF.ID);
    // Create a Deferred Register to hold Items which will all be registered under the REF.ID namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, REF.ID);
    // Create a Deferred Register to hold Fluids which will all be registered under the REF.ID namespace

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, REF.ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, REF.ID);
    public static final DeferredRegister<Block> LIQUID_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, REF.ID);
    public static final DeferredRegister<Item> BUCKETS = DeferredRegister.create(ForgeRegistries.ITEMS, REF.ID);

    public static final Collection<RegistryObject<MatPartItem>> MATERIAL_PART_ITEMS = new ArrayList<>();
    public static final Collection<RegistryObject<MatPartBlock>> MATERIAL_PART_BLOCKS = new ArrayList<>();
    public static final Collection<RegistryObject<MatPartBlockItem>> MATERIAL_PART_BLOCKITEMS = new ArrayList<>();

    public static final Collection<RegistryObject<BasicItem>> BASIC_ITEMS = new ArrayList<>();
    public static final Collection<RegistryObject<BasicBlock>> BASIC_BLOCKS = new ArrayList<>();
    public static final Collection<RegistryObject<BlockItem>> BASIC_BLOCKITEMS = new ArrayList<>();



    public static final Collection<RegistryObject<Fluid>> FLUID_BLOCKS = new ArrayList<>();


    //TODO - Use the bucket etc

    //TODO - GET RID OF THESE

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the REF.ID namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, REF.ID);

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> TECHPARTS_TAB = CREATIVE_MODE_TABS.register("techparts_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable("creativetab.techparts_tab"))
            .icon(() -> SAPPHIRE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(SAPPHIRE.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    public static void registerProcess(IEventBus modEventBus) {
        // Creat the Deffered Registries for all Items and Blocks
        MatDeclaration.init();
        TechPartItems.init();



        // Change them through KubeJS Startup Script

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so fluids get registered
        FLUIDS.register(modEventBus);
        FLUID_TYPES.register(modEventBus);
        LIQUID_BLOCKS.register(modEventBus);
        BUCKETS.register(modEventBus);

        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
    }

    /*******************************************************
     *
     *  Basic
     *
     *****************************************************/
    public static RegistryObject<BasicItem> registerBasicItem(BasicItem.ItemBuilder bitem) {
        LOGGER.info("Creating BasicItems");
        RegistryObject<BasicItem> item = ITEMS.register(bitem.getId(),() -> new BasicItem(bitem));
        BASIC_ITEMS.add(item);
        return item;
    }

    public static RegistryObject<BasicBlock> registerBasicBlock(BasicBlock.BlockBuilder bblock) {
        // Create Block
        RegistryObject<BasicBlock> block = BLOCKS.register(bblock.getId(),() -> new BasicBlock(bblock));
        BASIC_BLOCKS.add(block);
        // Create BlockItem
        BASIC_BLOCKITEMS.add(registerBasicBlockItem(bblock.getId(), block));
        return block;
    }

    private static<T extends BasicBlock> RegistryObject<BlockItem> registerBasicBlockItem(String id, RegistryObject<T> block){
        RegistryObject<BlockItem> item =  ITEMS.register(id, () -> new BlockItem(block.get(),Register.baseItemProps()));
        return item;
    }


    /*******************************************************
     *
     *  MatPartCombo Elements
     *
     *****************************************************/

    public static void registerFluid(String pName, FluidType.Properties pFluidProperties, int pColor, int pSlopeFindDistance, int pDecreasePerBlock) {

        var ref = new Object() {
            ForgeFlowingFluid.Properties properties = null;
        };

        RegistryObject<FluidType> fluidType = FLUID_TYPES.register(pName, () -> new FluidType(pFluidProperties) {
            @Override
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {
                    @Override
                    public ResourceLocation getStillTexture() {
                        return new ResourceLocation("block/water_still");
                    }

                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return new ResourceLocation("block/water_flow");
                    }

                    @Override
                    public ResourceLocation getOverlayTexture() {
                        return new ResourceLocation("block/water_overlay");
                    }

                    @Override
                    public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                        return new ResourceLocation("minecraft", "textures/misc/underwater.png");
                    }
                    @Override
                    public int getTintColor() {
                        return pColor;
                    }

                    @Override
                    public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                        return pColor;
                    }
                });
            }
        });

        RegistryObject<FlowingFluid> fluidSource = FLUIDS.register(pName, () -> new ForgeFlowingFluid.Source(ref.properties));
        RegistryObject<FlowingFluid> fluidFlowing = FLUIDS.register(String.format("%s_flowing", pName), () -> new ForgeFlowingFluid.Flowing(ref.properties));
        RegistryObject<LiquidBlock> liquidBlock = LIQUID_BLOCKS.register(pName, () -> new LiquidBlock(fluidSource, BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable().pushReaction(PushReaction.DESTROY).liquid()));
        RegistryObject<Item> bucket = BUCKETS.register(String.format("%s_bucket", pName), () -> new BucketItem(fluidSource, new Item.Properties().stacksTo(1)));

        ref.properties = new ForgeFlowingFluid.Properties(fluidType, fluidSource, fluidFlowing)
                .slopeFindDistance(pSlopeFindDistance)
                .levelDecreasePerBlock(pDecreasePerBlock)
                .block(liquidBlock)
                .bucket(bucket);
    }

    public static Stream<LiquidBlock> getLiquidBlocks() {
        return LIQUID_BLOCKS.getEntries().stream().map(RegistryObject::get).map(block -> (LiquidBlock) block);
    }

    public static Stream<BucketItem> getBuckets() {
        return BUCKETS.getEntries().stream().map(RegistryObject::get).map(item -> (BucketItem) item);
    }

    public static RegistryObject<MatPartItem> registerMatPartItem(Material material, Parts part) {
        return ITEMS.register(material.getId()+"_"+part.getID(),() -> new MatPartItem.ItemBuilder().mat(material).part(part).build());
    }

    private static<T extends MatPartBlock> RegistryObject<MatPartBlockItem> registerMatPartBlockItem(String id, RegistryObject<T> block){
        return ITEMS.register(id, ()->new MatPartBlockItem(block.get(), baseItemProps()));
    }

    // Also provides custom things to all MatPartBlocks that dont need a seperate class
    public static RegistryObject<MatPartBlock> registerMatPartBlock(Material material, Parts part, BlockBehaviour.Properties properties) {
        if(part == Parts.FRAME){
            properties.noOcclusion();
        }
        if(material == Material.GLOWSTONE){
            properties.lightLevel((p_50874_) -> {
                return 15;
            });
        }
        RegistryObject<MatPartBlock> toReturn = BLOCKS.register(material.getId()+"_"+part.getID(),() -> new MatPartBlock.BlockBuilder().mat(material).part(part).props(properties).build());
        MATERIAL_PART_BLOCKITEMS.add(registerMatPartBlockItem(material.getId()+ "_" + part.getID(),toReturn));
        return toReturn;
    }

    /*******************************************************
     *
     *  Helper Methods
     *
     *****************************************************/

    public static BlockBehaviour.Properties baseBlockProps() {
        return BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK);
    }

    public static Item.Properties baseItemProps() {
        return new Item.Properties();
    }



}
