package io.purple.techparts.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static io.purple.techparts.TechParts.MODID;

public class LiquidTags {
	public static final TagKey<Block> ORES = BlockTags.create(ResourceLocation.fromNamespaceAndPath(MODID, "ores"));
}
