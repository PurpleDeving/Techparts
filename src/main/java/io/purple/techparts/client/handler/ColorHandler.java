package io.purple.techparts.client.handler;

import io.purple.techparts.REF;
import io.purple.techparts.block.MatPartBlockItem;
import io.purple.techparts.item.BasicItem;
import io.purple.techparts.item.MatPartItem;
import io.purple.techparts.setup.Register;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = REF.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorHandler {

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register(
                (stack, tintIndex) -> {
                    if (stack.getItem() instanceof MatPartBlockItem) {
                        return ((MatPartBlockItem) stack.getItem()).getMaterial().getRbg(tintIndex);
                    }
                    if (stack.getItem() instanceof MatPartItem){
                        int rgb = ((MatPartItem) stack.getItem()).getMaterial().getRbg(tintIndex);
                        return rgb; // TODO - Change
                    }
                    return 0xFFFFFF;
                },
                Register.MATERIAL_PART_ITEMS.stream().map(RegistryObject::get).toArray(Item[]::new)
        );
    }
}
