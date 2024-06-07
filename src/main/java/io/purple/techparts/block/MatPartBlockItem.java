package io.purple.techparts.block;

import io.purple.techparts.material.MatPartCombo;
import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class MatPartBlockItem extends BlockItem implements MatPartCombo {

    public MatPartBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    public Material getMaterial(){
        return ((MatPartBlock) super.getBlock()).getMaterial();
    }

    public Parts getPart(){
        return ((MatPartBlock) super.getBlock()).getPart();
    }

    @Override
    public String getId() {
        return getMaterial().getID() + "_" + getPart().getID();
    }

    @Override
    public String getTooltip() {
        return "";
    }

}
