package io.purple.techparts;

import io.purple.techparts.blockentity.LiquidBlockEntity;
import io.purple.techparts.fluid.TPFReg;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
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

    public static final TPFReg LIQUID_CYAN_CONCRETE = new TPFReg.Builder("liquid_cyan_concrete", () -> Blocks.CYAN_CONCRETE, 5013401).mapColor(DyeColor.CYAN.getMapColor()).build();
    public static final TPFReg LIQUID_BLUE_CONCRETE = new TPFReg.Builder("liquid_blue_concrete", () -> Blocks.BLUE_CONCRETE, 3361970).mapColor(DyeColor.BLUE.getMapColor()).build();
    public static final TPFReg LIQUID_GREEN_CONCRETE = new TPFReg.Builder("liquid_green_concrete", () -> Blocks.GREEN_CONCRETE, 6717235).mapColor(DyeColor.GREEN.getMapColor()).build();
    public static final TPFReg LIQUID_BLACK_CONCRETE = new TPFReg.Builder("liquid_black_concrete", () -> Blocks.BLACK_CONCRETE, 1644825).mapColor(DyeColor.BLACK.getMapColor()).build();


    public static final Supplier<BlockEntityType<LiquidBlockEntity>> LIQUID_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("liquid_tile",
            () -> BlockEntityType.Builder.of(LiquidBlockEntity::new,
                    LIQUID_CYAN_CONCRETE.getFluidblock(),
                    LIQUID_BLUE_CONCRETE.getFluidblock(),
                    LIQUID_GREEN_CONCRETE.getFluidblock(),
                    LIQUID_BLACK_CONCRETE.getFluidblock()).build(null));




    public static void init() {

    }
}
