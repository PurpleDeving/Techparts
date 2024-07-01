package io.purple.techparts;

import io.purple.techparts.fluid.blockentity.LiquidSolidifyBlockEntity;
import io.purple.techparts.fluid.FluidBuilder;
import io.purple.techparts.fluid.TPFReg;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static io.purple.techparts.TechParts.MODID;


public class Registry {


    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

/*
    public static final TPFReg LIQUID_CYAN_CONCRETE = new FluidBuilder("liquid_cyan_concrete", 0xFFf9d49c*/
/*5013401*//*
, () -> Blocks.CYAN_CONCRETE).mapColor(DyeColor.CYAN.getMapColor()).build();
    public static final TPFReg LIQUID_BLUE_CONCRETE = new FluidBuilder("liquid_blue_concrete", 0xFFf9d49c */
/*3361970*//*
, () -> Blocks.BLUE_CONCRETE).mapColor(DyeColor.BLUE.getMapColor()).build();
    public static final TPFReg LIQUID_GREEN_CONCRETE = new FluidBuilder("liquid_green_concrete", 6717235, () -> Blocks.GREEN_CONCRETE).mapColor(DyeColor.GREEN.getMapColor()).build();
    public static final TPFReg LIQUID_BLACK_CONCRETE = new FluidBuilder("liquid_black_concrete", 0xFFf9d49c */
/*1644825*//*
).mapColor(DyeColor.BLACK.getMapColor()).build();
*/



    public static final Supplier<BlockEntityType<LiquidSolidifyBlockEntity>> LIQUID_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("liquid_tile",
            () -> BlockEntityType.Builder.of(LiquidSolidifyBlockEntity::new/*,
                    LIQUID_CYAN_CONCRETE.getFluidBlock(),
                    LIQUID_BLUE_CONCRETE.getFluidBlock(),
                    LIQUID_GREEN_CONCRETE.getFluidBlock(),
                    //LIQUID_BLACK_CONCRETE.getFluidBlock(),
                    LIQUID_GLOWSTONE.getFluidBlock()
                    */).build(null));




    public static void init() {

    }
}
