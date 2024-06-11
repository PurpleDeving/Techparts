package io.purple.techparts.client.handler;

import io.purple.techparts.REF;
import io.purple.techparts.TechParts;
import io.purple.techparts.block.MatPartBlock;
import io.purple.techparts.block.MatPartBlockItem;
import io.purple.techparts.item.BasicItem;
import io.purple.techparts.item.MatPartItem;
import io.purple.techparts.material.Parts;
import io.purple.techparts.setup.Register;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

import static io.purple.techparts.TechParts.LOGGER;
import static io.purple.techparts.setup.Register.*;

@Mod.EventBusSubscriber(modid = REF.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorHandler {

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {

        ItemColors itemColors = event.getItemColors();

        for(RegistryObject<MatPartBlockItem> mp:MATERIAL_PART_BLOCKITEMS){
            itemColors.register((stack, index) -> index > 0 ? -1 : mp.get().getMaterial().getRbg(index),mp.get());
        }

        for(RegistryObject<MatPartItem> mp:MATERIAL_PART_ITEMS){
            itemColors.register((stack, index) -> mp.get().getMaterial().getRbg(index),mp.get());
        }


/*        event.getItemColors().register(
                (stack, tintIndex) -> {
                    if (stack.getItem() instanceof MatPartBlockItem) {
                        return ((MatPartBlockItem) stack.getItem()).getMaterial().getRbg(tintIndex);
                    }
                    if (stack.getItem() instanceof MatPartItem){
                        int rgb = ((MatPartItem) stack.getItem()).getMaterial().getRbg(tintIndex);
                        return rgb;
                    }
                    return 0xFFFFFF;
                },
                Stream.concat(
                        MATERIAL_PART_ITEMS.stream().map(RegistryObject::get),
                        Register.MATERIAL_PART_BLOCKITEMS.stream().map(RegistryObject::get)
                ).toArray(Item[]::new)
        );*/
    }
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event){


        for (RegistryObject<MatPartBlock> block : MATERIAL_PART_BLOCKS) {

            event.getBlockColors().register((state, level, pos, tintIndex) -> block.get().getMaterial().getRbg(tintIndex), block.get());

            if (block.get().getPart() == Parts.FRAME){
                ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.translucent());
            }
        }
    }

    private static Object getColor(int tintIndex) {
        return 0xB87333;
    }

}
