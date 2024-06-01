package io.purple.techparts.block;

import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class MatPartBlockItem extends BlockItem {


    public MatPartBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    public Material getMaterial(){
        return ((MatPartBlock) super.getBlock()).getMaterial();
    }

    public Parts getPart(){
        return ((MatPartBlock) super.getBlock()).getPart();
    }

    public int getItemColor(ItemStack stack, int i) {
        return this.getMaterial().getRbg();
    }

}
