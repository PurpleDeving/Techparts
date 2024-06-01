package io.purple.techparts.item;

import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import net.minecraft.world.item.Item;

public class MatPartItem extends Item {

    private final Material material;
    private final Parts part;

    public MatPartItem(Properties pProperties, Material material, Parts part) {
        super(pProperties);
        this.material = material;
        this.part = part;
    }
}
