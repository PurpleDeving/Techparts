package io.purple.techparts.block;

import io.purple.techparts.TechParts;
import io.purple.techparts.item.BasicItem;
import io.purple.techparts.item.MatPartItem;
import io.purple.techparts.material.MatPartCombo;
import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MatPartBlock extends BasicBlock implements MatPartCombo {

    private final Material material;
    private final Parts part;
    private final VoxelShape collisionShape;

    private MatPartBlock(BlockBuilder bblock) {
        super(bblock);
        this.material = bblock.material;
        this.part = bblock.part;
        if(part == Parts.FRAME){
            TechParts.LOGGER.info("Test552");
            collisionShape = Shapes.box(0.001D, 0.0D, 0.001D, 0.95D, 1.0D, 0.95D);
        }else{
            collisionShape = Shapes.block();
        }
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
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return collisionShape;
    }



    @Override
    public String getId() {
        return getMaterial().getID() + "_" + getPart().getID();
    }

    @Override
    public String getTooltip() {
        return "";
    }
    public static class BlockBuilder extends BasicBlock.BlockBuilder<BlockBuilder> {

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

        @Override
        public MatPartBlock build(){
            this.id(material.getID() + "_" + this.part.getID());
            return new MatPartBlock(this);
        }


    }

}
