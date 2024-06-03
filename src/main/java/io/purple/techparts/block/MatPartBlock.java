package io.purple.techparts.block;

import io.purple.techparts.material.MatPartCombo;
import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import net.minecraft.world.level.block.Block;

public class MatPartBlock extends Block implements MatPartCombo {

    private final Material material;
    private final Parts part;

    public MatPartBlock(Material material, Parts part, Properties pProperties) {
        super(pProperties);
        this.material = material;
        this.part = part;
    }

    public Material getMaterial() {
        return material;
    }

    public Parts getPart() {
        return part;
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
