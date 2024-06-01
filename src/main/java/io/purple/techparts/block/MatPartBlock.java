package io.purple.techparts.block;

import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import net.minecraft.world.level.block.Block;

public class MatPartBlock extends Block {

    private final Material material;
    private final Parts part;

    public MatPartBlock(Properties pProperties, Material material, Parts part) {
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
}
