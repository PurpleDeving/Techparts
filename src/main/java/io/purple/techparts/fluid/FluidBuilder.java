package io.purple.techparts.fluid;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class FluidBuilder {
    private String name;
    private Supplier<Block> solidifyBlockSupplier;
    private MapColor mapColor = MapColor.WATER;
    private int color;
    private boolean hot = false;
    private int luminosity = 0;

    public FluidBuilder(String name, int color, Supplier<Block> blockSupplier) {
        this.name = name;
        this.solidifyBlockSupplier = blockSupplier;
        this.color = color;
    }

    public FluidBuilder(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public FluidBuilder mapColor(MapColor mapColor) {
        this.mapColor = mapColor;
        return this;
    }

    public FluidBuilder hot() {
        this.hot = true;
        return this;
    }

    public FluidBuilder luminosity(int luminosity) {
        this.luminosity = luminosity;
        return this;
    }

    public TPFReg build() {
        return new TPFReg(name, solidifyBlockSupplier, mapColor, color, hot, luminosity);
    }

}
