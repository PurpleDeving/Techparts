package io.purple.techparts.block;

import io.purple.techparts.item.BasicItem;
import io.purple.techparts.item.MatPartItem;
import io.purple.techparts.material.MatPartCombo;
import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import net.minecraft.world.level.block.Block;

public class MatPartBlock extends BasicBlock implements MatPartCombo {

    private final Material material;
    private final Parts part;

    private MatPartBlock(BlockBuilder bblock) {
        super(bblock);
        this.material = bblock.material;
        this.part = bblock.part;
    }

    public Material getMaterial() {
        return material;
    }

    public Parts getPart() {
        return part;
    }

    public String getTexPath(){
        return String.join("","block/material/",getMaterial().getTexture().getID(),"/",part.getID());
    }

    @Override
    public String getId() {
        return getMaterial().getID() + "_" + getPart().getID();
    }

    @Override
    public String getTooltip() {
        return "";
    }
    public static class BlockBuilder extends BasicBlock.BlockBuilder {

        Material material;
        Parts part;

        public BlockBuilder mat(Material material){
            this.material = material;
            return this;
        }

        public BlockBuilder part(Parts parts){
            this.part = parts;
            return this;
        }

        public MatPartBlock build(){
            this.id(material.getID() + "_" + this.part.getID());
            return new MatPartBlock(this);
        }


    }

}
