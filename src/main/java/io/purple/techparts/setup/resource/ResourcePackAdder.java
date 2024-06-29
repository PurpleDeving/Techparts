package io.purple.techparts.setup.resource;


import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.PackSelectionConfig;

import static io.purple.techparts.TechParts.MODID;


@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ResourcePackAdder {
    public static final TechPartsPack VIRTUAL_PACK = new TechPartsPack();

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        event.addRepositorySource(
                (infoConsumer) -> infoConsumer
                        .accept(Pack.readMetaAndCreate(TechPartsPack.LOCATION_INFO, new Pack.ResourcesSupplier() {
                            @Override
                            public PackResources openPrimary(PackLocationInfo p_326301_) {
                                return VIRTUAL_PACK;
                            }
                            @Override
                            public PackResources openFull(PackLocationInfo p_326241_, Pack.Metadata p_325959_) {
                                return VIRTUAL_PACK;
                            }
                        }, PackType.CLIENT_RESOURCES, new PackSelectionConfig(true, Pack.Position.TOP, false)))
        );
    }

    @SubscribeEvent
    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(VIRTUAL_PACK);
    }
}