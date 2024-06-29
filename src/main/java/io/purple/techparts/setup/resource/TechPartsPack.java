package io.purple.techparts.setup.resource;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.datafixers.util.Pair;
import io.purple.techparts.TechParts;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

import static io.purple.techparts.TechParts.MODID;

public class TechPartsPack extends AbstractPackResources implements PreparableReloadListener {
    public static final PackLocationInfo LOCATION_INFO = new PackLocationInfo(
            "techparts.pack", Component.translatable("techparts.pack.title"), PackSource.BUILT_IN, Optional.empty()
    );
    public static final Set<String> NAMESPACES = ImmutableSet.of(MODID);

    private final PackMetadataSection packInfo;

    private Map<ResourceLocation, IoSupplier<InputStream>> resourceMap = new HashMap<>();



    public TechPartsPack() {
        super(LOCATION_INFO);
        this.packInfo = new PackMetadataSection(
                Component.translatable("techparts.pack.description"),
                SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES)
        );
    }



    @Override
    public CompletableFuture<Void> reload(
            PreparationBarrier stage,
            ResourceManager manager,
            ProfilerFiller workerProfiler,
            ProfilerFiller mainProfiler,
            Executor workerExecutor,
            Executor mainExecutor
    ) {
        TechParts.LOGGER.info("Reach 1 - Reload Method");
        return CompletableFuture.supplyAsync(() -> null, workerExecutor)
                .thenCompose(stage::wait)
                .thenAcceptAsync((noResult) -> {
                    String test = JsonHandler.generateLangJson();
                    TechParts.LOGGER.info("Reach 2 - Inside Reload");
                    TechParts.LOGGER.info(test);
                    addResource(ResourceLocation.fromNamespaceAndPath(MODID,"lang/en_us.json"),test);
                }, mainExecutor);
    }


    public void addResource(ResourceLocation location, String jsonContent) {
        resourceMap.put(location, () -> new ByteArrayInputStream(jsonContent.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String getName() {
        return Component.translatable("techparts.pack.title").getString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> serializer) {
        return serializer == PackMetadataSection.TYPE ? (T) this.packInfo : null;
    }

    @Override
    public IoSupplier<InputStream> getRootResource(String... fileName) {
        return null;
    }
    @Override
    public void close() {}


    @Override
    public Set<String> getNamespaces(PackType type) {
        return NAMESPACES;
    }

    @Override

    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation id) {
        return resourceMap.get(id);
    }

    @Override
    public void listResources(PackType type, String namespace, String id, ResourceOutput output) {
        if (namespace.equals(MODID)) {
            resourceMap.forEach((name, supplier) -> {
                if (name.getPath().startsWith(id)) {
                    output.accept(name, getResource(type, name));
                }
            });
        }
    }
}