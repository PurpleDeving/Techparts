package io.purple.techparts.fluid.blockentity;

import io.purple.techparts.Registry;
import io.purple.techparts.fluid.block.LiquidSolidifyBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LiquidSolidifyBlockEntity extends BlockEntity {
	private int solidifyTimer;

	public LiquidSolidifyBlockEntity(BlockPos pos, BlockState state) {
		super(Registry.LIQUID_BLOCK_ENTITY.get(), pos, state);
		this.solidifyTimer = -1;
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
		super.saveAdditional(tag, provider);
		tag.putInt("TimeLeft", this.solidifyTimer);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
		super.loadAdditional(tag, provider);
		this.solidifyTimer = tag.getShort("TimeLeft");
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, LiquidSolidifyBlockEntity blockEntity) {
		if (level.getGameTime() % 20 == 0) {
			if (blockEntity.solidifyTimer == -1) {
				if (state.getBlock() instanceof LiquidSolidifyBlock liquidBlock) {
					if (liquidBlock.getLiquifiedBlock().get() != null) {
						float hardness = liquidBlock.getLiquifiedBlock().get().defaultBlockState().getDestroySpeed(level, pos);
						if (hardness > 0.0F) {
							blockEntity.solidifyTimer = (int) Math.ceil((double) hardness * 7);
						}
					}
				}
			}
			if (blockEntity.solidifyTimer == 0) {
				if (state.getBlock() instanceof LiquidSolidifyBlock liquid) {
					liquid.convertBlock(level, pos);
				}
			} else if (blockEntity.solidifyTimer > 0) {
				blockEntity.solidifyTimer--;
				if (false /*FIXME !LiquidConfig.COMMON.completelyFill.get()*/) {
					if (state.getBlock() instanceof LiquidSolidifyBlock) {
						boolean flag = !(state.getValue(LiquidSolidifyBlock.LEVEL) == 0);
						if (flag) {
							blockEntity.decrementAgain();
						}

						if (state.getValue(LiquidSolidifyBlock.LEVEL) > 5) {
							blockEntity.decrementAgain();
						}
					}
				}
			}
		}
	}

	public void decrementAgain() {
		solidifyTimer--;
		if (solidifyTimer < 0) {
			solidifyTimer = 0;
		}
	}
}
