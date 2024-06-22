package io.purple.techparts.fluid;

import io.purple.techparts.block.LiquidEntityBlock;
import io.purple.techparts.block.LiquidOreBlock;
import io.purple.techparts.Registry;
import io.purple.techparts.TechParts;
import io.purple.techparts.block.TPFBlock;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TPFReg {
    private static final ResourceLocation STILL_METAL = ResourceLocation.fromNamespaceAndPath(TechParts.MODID, "block/molten_block_still");
    private static final ResourceLocation FLOWING_METAL = ResourceLocation.fromNamespaceAndPath(TechParts.MODID, "block/molten_block_flow");

    private final String name;
    private final DeferredHolder<FluidType, FluidType> fluidType;
    private DeferredHolder<Fluid, BaseFlowingFluid> source;
    private DeferredHolder<Fluid, BaseFlowingFluid> flowing;
    private DeferredBlock<LiquidBlock> fluidblock;
    private DeferredItem<LiquidBucketItem> bucket;

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public DeferredHolder<FluidType, FluidType> getFluidType() {
        return fluidType;
    }

    @Nonnull
    public DeferredHolder<Fluid, BaseFlowingFluid> getSourceRegistry() {
        return source;
    }

    @Nonnull
    public BaseFlowingFluid getSource() {
        return source.get();
    }

    @Nonnull
    public DeferredHolder<Fluid, BaseFlowingFluid> getFlowing() {
        return flowing;
    }

    @Nonnull
    public LiquidBlock getFluidblock() {
        return fluidblock.get();
    }

    public DeferredItem<LiquidBucketItem> getBucketRegistry() {
        return bucket;
    }

    public Item getBucket() {
        return bucket.get();
    }

    public static BaseFlowingFluid.Properties createProperties(Supplier<FluidType> type, Supplier<BaseFlowingFluid> still, Supplier<BaseFlowingFluid> flowing,
                                                               DeferredItem<LiquidBucketItem> bucket, Supplier<LiquidBlock> block) {
        return new BaseFlowingFluid.Properties(type, still, flowing)
                .bucket(bucket).block(block);
    }

    public TPFReg(String name, Supplier<Block> blockSupplier, MapColor mapColor, int color, boolean coldLiquid, int luminosity) {
        this.name = name;
        fluidType = Registry.FLUID_TYPES.register(name, () -> new FluidType(FluidHelper.createTypeProperties().temperature(coldLiquid ? 300 : 1000).lightLevel(luminosity)) {
            @Override
            public double motionScale(Entity entity) {
                return entity.level().dimensionType().ultraWarm() ? 0.007D : 0.0023333333333333335D;
            }

            @Override
            public void setItemMovement(ItemEntity entity) {
                Vec3 vec3 = entity.getDeltaMovement();
                entity.setDeltaMovement(vec3.x * (double) 0.95F, vec3.y + (double) (vec3.y < (double) 0.06F ? 5.0E-4F : 0.0F), vec3.z * (double) 0.95F);
            }

            @Override
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {

                    @Override
                    public ResourceLocation getStillTexture() {
                        return STILL_METAL;
                    }

                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return FLOWING_METAL;
                    }

                    @Override
                    public int getTintColor() {
                        return color;
                    }

                    @Override
                    public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                        int color = this.getTintColor();
                        return new Vector3f((color >> 16 & 0xFF) / 255F, (color >> 8 & 0xFF) / 255F, (color & 0xFF) / 255F);
                    }
                });
            }
        });
        source = Registry.FLUIDS.register(name, () -> new TPFFluid.Source(
                createProperties(fluidType, source, flowing, bucket, fluidblock))
        );
        flowing = Registry.FLUIDS.register(name + "_flowing", () -> new TPFFluid.Flowing(
                createProperties(fluidType, source, flowing, bucket, fluidblock))
        );

        if (name.equals("ore")) {
            fluidblock = Registry.BLOCKS.register(name, () -> new LiquidOreBlock(
                    Block.Properties.of().mapColor(mapColor).pushReaction(PushReaction.DESTROY).liquid().noCollission().strength(100.0F).randomTicks().noLootTable().lightLevel(state -> luminosity), source, blockSupplier));
        } else {
            fluidblock = Registry.BLOCKS.register(name, () -> new TPFBlock(
                    Block.Properties.of().mapColor(mapColor).pushReaction(PushReaction.DESTROY).liquid().noCollission().strength(100.0F).randomTicks().noLootTable().lightLevel(state -> luminosity), source, blockSupplier));
        }
        bucket = Registry.ITEMS.register(name + "_bucket", () -> new LiquidBucketItem(new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1), source));
    }

    public static class Builder {
        private String name;
        private Supplier<Block> blockSupplier;
        private MapColor mapColor = MapColor.WATER;
        private int color;
        private boolean hot = false;
        private int luminosity = 0;

        public Builder(String name, Supplier<Block> blockSupplier, int color) {
            this.name = name;
            this.blockSupplier = blockSupplier;
            this.color = color;
        }

        public Builder mapColor(MapColor mapColor) {
            this.mapColor = mapColor;
            return this;
        }

        public Builder hot() {
            this.hot = true;
            return this;
        }

        public Builder luminosity(int luminosity) {
            this.luminosity = luminosity;
            return this;
        }

        public TPFReg build() {
            return new TPFReg(name, blockSupplier, mapColor, color, hot, luminosity);
        }
    }
}
