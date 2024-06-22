package io.purple.techparts.setup.resource;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import static io.purple.techparts.TechParts.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class ResourcePackAdder
{

    @SubscribeEvent
    public static void addPack(AddPackFindersEvent e)
    {

    }
}