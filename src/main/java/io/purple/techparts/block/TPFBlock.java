package io.purple.techparts.block;

import io.purple.techparts.Registry;
import io.purple.techparts.blockentity.LiquidBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class TPFBlock extends LiquidBlock {
	private final Supplier<Block> blockSupplier;

	public TPFBlock(Properties properties, Supplier<? extends FlowingFluid> supplier, Supplier<Block> blockSupplier) {
		super(supplier.get(), properties);
		this.blockSupplier = blockSupplier;
	}

	public void convertBlock(Level level, BlockPos pos) {
		level.removeBlockEntity(pos);
		level.setBlockAndUpdate(pos, blockSupplier.get().defaultBlockState());
		int fireChance = 5; //LiquidConfig.COMMON.netherrackFireChance.get(); // FIXME
		if (fireChance > 0) {
			if (blockSupplier.get() == Blocks.NETHERRACK) {
				if (!level.getBlockState(pos.above()).canOcclude() || level.getBlockState(pos.above()).canBeReplaced()) {
					if (level.random.nextInt(fireChance) <= 1) {
						level.setBlockAndUpdate(pos.above(), Blocks.FIRE.defaultBlockState().setValue(FireBlock.AGE, level.random.nextInt(15)));
					}
				}
			}
		}
	}

	public Supplier<Block> getLiquifiedBlock() {
		return blockSupplier;
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entityIn) {
		super.entityInside(state, level, pos, entityIn);
		if (entityIn instanceof LivingEntity entity) {
			if (state.getBlock() instanceof TPFBlock) {
				if (true  /* FIXME LiquidConfig.COMMON.liquidCausesNausea.get()*/) {
					entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 1, false, false));
				}
				if (true /* FIXME LiquidConfig.COMMON.liquidCausesSlowness.get()*/) {
					entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1, false, false));
				}
			}
		}
	}

}
