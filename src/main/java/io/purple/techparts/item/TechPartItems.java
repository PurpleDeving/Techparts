package io.purple.techparts.item;

import io.purple.techparts.setup.Register;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import static io.purple.techparts.setup.Register.ITEMS;
import static io.purple.techparts.setup.Register.registerBasicItem;

public class TechPartItems {

    public static final RegistryObject<BasicItem> SAPPHIRE = registerBasicItem(new BasicItem.ItemBuilder().id("sapphire").props(Register.baseItemProps().food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(2f).build())));

    public static void init() {
    }
}
