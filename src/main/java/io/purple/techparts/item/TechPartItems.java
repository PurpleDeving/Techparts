package io.purple.techparts.item;

import io.purple.techparts.block.BasicBlock;
import io.purple.techparts.setup.Register;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

import static io.purple.techparts.setup.Register.*;

public class TechPartItems {

    /***********************************************************************

     ITEMS

     ***********************************************************************/


    public static final RegistryObject<BasicItem> SAPPHIRE = registerBasicItem(new BasicItem.ItemBuilder().id("sapphire").props(Register.baseItemProps().food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(2f).build())));


    /***********************************************************************

     BLOCKS

     ***********************************************************************/

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<BasicBlock> EXAMPLE_BLOCK = Register.registerBasicBlock(new BasicBlock.BlockBuilder().id("sapphire_block"));




    public static void init() {
    }
}
