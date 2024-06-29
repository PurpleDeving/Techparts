package io.purple.techparts.client;

import io.purple.techparts.Registry;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidUtil;

import static io.purple.techparts.TechParts.LOGGER;
import static io.purple.techparts.TechParts.MODID;


@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHandler {

	@SubscribeEvent
	public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
		for (var blockObject : Registry.BLOCKS.getEntries()) {
			event.register((state, getter, pos, tintIndex) -> {
				if (getter != null && pos != null) {
					FluidState fluidState = getter.getFluidState(pos);
					return IClientFluidTypeExtensions.of(fluidState).getTintColor(fluidState, getter, pos);
				} else return 0xFFFFFFFF;
			}, blockObject.get());
		}
	}

	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		final ItemColors colors = event.getItemColors();

		LOGGER.info("TEST 3392");
		for (var itemObject : Registry.ITEMS.getEntries()) {
			//LOGGER.info(itemObject.toString());
			//LOGGER.info(itemObject.get().getDescriptionId());
			if (itemObject.get() instanceof BucketItem){
				//LOGGER.info("REACH 3393");
				colors.register((stack, index) -> {
					if (index == 1 && stack.getItem() instanceof BucketItem bucketItem) {
						return IClientFluidTypeExtensions.of(bucketItem.content).getTintColor();
					}
					return 0xFFFFFFFF;
				}, itemObject.get());
			}
/*			event.register((stack, tintIndex) -> {
				if (tintIndex != 1) return 0xFFFFFFFF;
				return FluidUtil.getFluidContained(stack)
						.map(fluidStack -> IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack))
						.orElse(0xFFFFFFFF);
			}, itemObject.get());*/
		}
	}
}
