package io.purple.techparts.item;

import io.purple.techparts.REF;
import io.purple.techparts.TechParts;
import io.purple.techparts.block.MatPartBlock;
import io.purple.techparts.material.MatPartCombo;
import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import static net.minecraft.commands.arguments.blocks.BlockStateArgument.getBlock;

public class MatPartItem extends BasicItem implements MatPartCombo {

    private final Material material;
    private final Parts part;

    private MatPartItem(ItemBuilder bitem) {
        super(bitem);
        this.material = bitem.material;
        this.part = bitem.part;
    }

    public Material getMaterial() {
        return material;
    }

    public Parts getPart() {
        return part;
    }

    /*******************************************************
     *
     *  Interaction
     *
     *****************************************************/


    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (block instanceof MatPartBlock){
            Material blockmat = ((MatPartBlock) block).getMaterial();
            Parts blockpart = ((MatPartBlock) block).getPart();
            if(this.part == Parts.PLATE && this.material == blockmat && blockpart == Parts.FRAME){
                if (level.isClientSide) {
                    return InteractionResult.SUCCESS;
                }
                Block scaffoldBlock = BuiltInRegistries.BLOCK.get(new ResourceLocation(REF.ID,this.material.getId()+"_"+Parts.SCAFFOLDING.getID()));
                level.setBlock(blockpos, scaffoldBlock.defaultBlockState(), 3);
                pContext.getItemInHand().shrink(1);
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.PASS;

    }



    /*******************************************************
     *
     *  Item Builder
     *
     *****************************************************/

    @Override
    public String getTexPath() {
        return String.join("",getMaterial().getTexture().getID(),"/",part.getID()); // TODO - Fucked the bird ?
    }

    public static class ItemBuilder extends BasicItem.ItemBuilder {
        public Material material;
        public Parts part;

        public ItemBuilder mat(Material material) {
            this.material = material;
            return this;
        }

        public ItemBuilder part(Parts part) {
            this.part = part;
            return this;
        }

        public MatPartItem build(){
            this.id(material.getId() + "_" + this.part.getID());
            return new MatPartItem(this);
        }
    }

}
