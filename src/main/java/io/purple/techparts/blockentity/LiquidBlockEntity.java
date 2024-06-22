package io.purple.techparts.blockentity;

import io.purple.techparts.Registry;
import io.purple.techparts.block.LiquidEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LiquidBlockEntity extends BlockEntity {
	private int solidifyTimer;

	public LiquidBlockEntity(BlockPos pos, BlockState state) {
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

	public static void serverTick(Level level, BlockPos pos, BlockState state, LiquidBlockEntity blockEntity) {
		if (level.getGameTime() % 20 == 0) {
			if (blockEntity.solidifyTimer == -1) {
				if (state.getBlock() instanceof LiquidEntityBlock liquidBlock) {
					if (liquidBlock.getLiquifiedBlock().get() != null) {
						float hardness = liquidBlock.getLiquifiedBlock().get().defaultBlockState().getDestroySpeed(level, pos);
						if (hardness > 0.0F) {
							blockEntity.solidifyTimer = (int) Math.ceil((double) hardness * 7);
						}
					}
				}
			}
			if (blockEntity.solidifyTimer == 0) {
				if (state.getBlock() instanceof LiquidEntityBlock liquid) {
					liquid.convertBlock(level, pos);
				}
			} else if (blockEntity.solidifyTimer > 0) {
				blockEntity.solidifyTimer--;
				if (false /*FIXME !LiquidConfig.COMMON.completelyFill.get()*/) {
					if (state.getBlock() instanceof LiquidEntityBlock) {
						boolean flag = !(state.getValue(LiquidEntityBlock.LEVEL) == 0);
						if (flag) {
							blockEntity.decrementAgain();
						}

						if (state.getValue(LiquidEntityBlock.LEVEL) > 5) {
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
