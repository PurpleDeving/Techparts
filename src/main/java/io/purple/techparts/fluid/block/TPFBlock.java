package io.purple.techparts.fluid.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class TPFBlock extends LiquidBlock {

	public TPFBlock(Properties properties, Supplier<? extends FlowingFluid> supplier) {
		super(supplier.get(), properties);
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
