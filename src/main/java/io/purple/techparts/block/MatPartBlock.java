package io.purple.techparts.block;

import io.purple.techparts.REF;
import io.purple.techparts.TechParts;
import io.purple.techparts.item.MatPartItem;
import io.purple.techparts.material.MatPartCombo;
import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.core.registries.BuiltInRegistries;

public class MatPartBlock extends BasicBlock implements MatPartCombo {

    private final Material material;
    private final Parts part;
    private final VoxelShape collisionShape;

    private MatPartBlock(BlockBuilder bblock) {
        super(bblock);
        this.material = bblock.material;
        this.part = bblock.part;
        if (part == Parts.FRAME || part == Parts.SCAFFOLDING) {
            collisionShape = Shapes.box(0.001D, 0.0D, 0.001D, 0.95D, 1.0D, 0.95D);
        } else {
            collisionShape = Shapes.block();
        }
    }

    /*******************************************************
     *
     *  In-World interaction
     *
     *****************************************************/


    @Override
    public InteractionResult use(BlockState pState, Level plevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(plevel.isClientSide){
            return InteractionResult.sidedSuccess(plevel.isClientSide);
        }
        // PopUp the Plate from the SCAFFOLDING
        if(this.part == Parts.SCAFFOLDING && pPlayer.isShiftKeyDown() && pPlayer.getItemInHand(pHand).isEmpty()){
            Block frameBlock = BuiltInRegistries.BLOCK.get(new ResourceLocation(REF.ID,this.material.getId()+"_"+Parts.FRAME.getID()));
            plevel.setBlock(pPos, frameBlock.defaultBlockState(), 3);

            // Spawn a PLATE item on top of the new FRAME block
            Item plateItem = BuiltInRegistries.ITEM.get(new ResourceLocation(REF.ID, this.material.getId() + "_" + Parts.PLATE.getID()));
            ItemStack plateStack = new ItemStack(plateItem);
            BlockPos spawnPos = pPos.above();
            Block.popResource(plevel, spawnPos, plateStack);

            return InteractionResult.CONSUME;
        }
        // Create Scaffolding form Frame + Plate
        MatPartItem plate = (MatPartItem) BuiltInRegistries.ITEM.get(new ResourceLocation(REF.ID, this.material.getId() + "_" + Parts.PLATE.getID()));
        if(this.part == Parts.FRAME && pPlayer.isShiftKeyDown() /*&& pPlayer.getItemInHand(pHand).is(plate)*/){
            TechParts.LOGGER.info("AAAAA -232323");
            Block scaffoldBlock = BuiltInRegistries.BLOCK.get(new ResourceLocation(REF.ID,this.material.getId()+"_"+Parts.SCAFFOLDING.getID()));
            plevel.setBlock(pPos, scaffoldBlock.defaultBlockState(), 3);

            // Lower the Plate number by one
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            if (!pPlayer.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            return InteractionResult.CONSUME;
        }


        return InteractionResult.PASS;
    }

    /*******************************************************
     *
     *  Block interaction Methods
     *
     *****************************************************/

    public Material getMaterial() {
        return material;
    }

    public Parts getPart() {
        return part;
    }

    public String getTexPath() {
        return String.join("", "block/material/", getMaterial().getTexture().getID(), "/", part.getID());
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return collisionShape;
    }

    @Override
    public String getId() {
        return this.material.getId() + "_" + this.part.getID();
    }

    @Override
    public String getTooltip() {
        return "";
    }

    /*******************************************************
     *
     *  BlockBuilder
     *
     *****************************************************/

    public static class BlockBuilder extends BasicBlock.BlockBuilder<BlockBuilder> {

        Material material;
        Parts part;

        public BlockBuilder mat(Material material) {
            this.material = material;
            return this;
        }

        public BlockBuilder part(Parts parts) {
            this.part = parts;
            return this;
        }

        @Override
        public MatPartBlock build() {
            this.id(material.getId() + "_" + this.part.getID());
            return new MatPartBlock(this);
        }


    }

}
