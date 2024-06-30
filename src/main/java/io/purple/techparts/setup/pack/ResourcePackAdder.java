package io.purple.techparts.setup.pack;


import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.PackSelectionConfig;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.purple.techparts.TechParts.MODID;

// HELL NO - DO NOT TOUCH THIS > Look at SolarFluxReborn if anything breaks


@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ResourcePackAdder {

        public static final List<PackResources> BUILTIN_PACKS = new ArrayList<>();

        public static void registerResourcePack(PackResources pack)
        {
            if(!BUILTIN_PACKS.contains(pack))
                BUILTIN_PACKS.add(pack);
        }

        @SubscribeEvent
        public static void addPacks(AddPackFindersEvent e)
        {
            e.addRepositorySource((add) ->
            {
                for(PackResources pack : ResourcePackAdder.BUILTIN_PACKS)
                {
                    if(pack instanceof IRegisterListener rl)
                        rl.onPreRegistered(Resources.locationOrNull(pack.packId()));

                    add.accept(Pack.readMetaAndCreate(
                            new PackLocationInfo(
                                    pack.packId(),
                                    Component.literal(pack.packId()),
                                    PackSource.BUILT_IN,
                                    Optional.empty()
                            ),
                            new Pack.ResourcesSupplier()
                            {
                                @Override
                                public PackResources openPrimary(PackLocationInfo info)
                                {
                                    return pack;
                                }

                                @Override
                                public PackResources openFull(PackLocationInfo info, Pack.Metadata meta)
                                {
                                    return pack;
                                }
                            },
                            PackType.CLIENT_RESOURCES,
                            new PackSelectionConfig(
                                    true,
                                    Pack.Position.TOP,
                                    true
                            )
                    ));

                    if(pack instanceof IRegisterListener rl)
                        rl.onPostRegistered(Resources.locationOrNull(pack.packId()));
                }
            });
        }
    public interface IRegisterListener
    {
        default void onPostRegistered()
        {
        }

        default void onPreRegistered()
        {
        }

        default void onPostRegistered(ResourceLocation id)
        {
            onPostRegistered();
        }

        default void onPreRegistered(ResourceLocation id)
        {
            onPreRegistered();
        }
    }
    class Resources
    {
        public static ResourceLocation location(String namespace, String path)
        {
            return ResourceLocation.fromNamespaceAndPath(namespace, path);
        }

        public static ResourceLocation location(String location)
        {
            return ResourceLocation.parse(location);
        }

        @Nullable
        public static ResourceLocation locationOrNull(String namespace, String path)
        {
            return ResourceLocation.tryBuild(namespace, path);
        }

        @Nullable
        public static ResourceLocation locationOrNull(String location)
        {
            return ResourceLocation.tryParse(location);
        }
    }
}

