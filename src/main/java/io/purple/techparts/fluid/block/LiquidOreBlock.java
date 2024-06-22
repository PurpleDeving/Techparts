package io.purple.techparts.fluid.block;

import io.purple.techparts.fluid.LiquidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.Optional;
import java.util.function.Supplier;

public class LiquidOreBlock extends LiquidSolidifyBlock {

	public LiquidOreBlock(Block.Properties properties, Supplier<? extends FlowingFluid> supplier, Supplier<Block> blockSupplier) {
		super(properties, supplier, blockSupplier);
	}

	@Override
	public void convertBlock(Level level, BlockPos pos) {
		level.removeBlockEntity(pos);
		level.removeBlock(pos, false);
		level.setBlockAndUpdate(pos, getRandomOre(level));
	}

	public BlockState getRandomOre(Level level) {
		int oreChance = 5 /* FIXME LiquidConfig.COMMON.oreChance.get()*/;
		int randNumber = level.random.nextInt(oreChance);

		if (randNumber == 0) {
			Optional<HolderSet.Named<Block>> oresTag = BuiltInRegistries.BLOCK.getTag(LiquidTags.ORES);
			if (oresTag.isPresent()) {
				Optional<Holder<Block>> blockHolder = oresTag.get().getRandomElement(level.random);
				Block oreState = blockHolder.map(Holder::value).orElse(Blocks.STONE);
				return oreState.defaultBlockState();
			} else {
				return Blocks.STONE.defaultBlockState();
			}
		} else {
			return Blocks.STONE.defaultBlockState();
		}
	}
}
