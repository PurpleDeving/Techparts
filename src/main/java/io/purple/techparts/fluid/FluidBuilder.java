package io.purple.techparts.fluid;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class FluidBuilder {
    private String fluidId;
    private Supplier<Block> solidifyBlockSupplier;
    private MapColor mapColor = MapColor.WATER;
    private int color = -1;
    private boolean hot = false;
    private boolean goUp = false;                    // Act like tinker glowstone /should it move upwards
    private int luminosity = -1;

    public FluidBuilder(String name, int color, Supplier<Block> blockSupplier) {
        this.fluidId = name;
        this.solidifyBlockSupplier = blockSupplier;
        this.color = color;
    }

    public FluidBuilder(String name, int color) {
        this.fluidId = name;
        this.color = color;
    }

    public FluidBuilder() {

    }

    public FluidBuilder color(int color){
        this.color = color;
        return this;
    }

    public FluidBuilder fluidId(String fluidId){
        this.fluidId = fluidId;
        return this;
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

    // TODO - Even worth doing ?
    public FluidBuilder goUp() {
        this.goUp = true;
        return this;
    }

    public TPFReg build() {
        if(fluidId == null){
            throw new IllegalArgumentException("Techparts: Missing fluid id");
        }
        return new TPFReg(fluidId, solidifyBlockSupplier, mapColor, color, hot, luminosity,goUp);
    }

    /*****************************

     Getter Methods - Used to make needed builder adjustments

     ****************************/


    public String getFluidId() {
        return this.fluidId;
    }

    public int getColor() {
        return this.color;
    }

    public Boolean isHot(){
        return hot;
    }

    public int getLuminosity() {
        return luminosity;
    }


}
