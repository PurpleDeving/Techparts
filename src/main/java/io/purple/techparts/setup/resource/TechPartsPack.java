package io.purple.techparts.setup.resource;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.datafixers.util.Pair;
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
    public static final String TEXTURE_DIRECTORY = "textures/";
    public static final String BLOCK_DIRECTORY = "block/";
    public static final Set<String> NAMESPACES = ImmutableSet.of(MODID);

    private final PackMetadataSection packInfo;
    private Map<ResourceLocation, IoSupplier<InputStream>> resources = new HashMap<>();

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
/*        this.gatherTextureData(manager, mainProfiler);*/
        return CompletableFuture.supplyAsync(() -> null, workerExecutor)
                .thenCompose(stage::wait)
                .thenAcceptAsync((noResult) -> {}, mainExecutor);
    }

/*    protected void gatherTextureData(ResourceManager manager, ProfilerFiller profiler) {
        Map<ResourceLocation, IoSupplier<InputStream>> resourceStreams = new HashMap<>();

        textures.forEach(
                (
                        modLocation,
                        vanillaLocation
                ) -> generateImage(modLocation, vanillaLocation, Minecraft.getInstance().getResourceManager())
                        .ifPresent(pair -> {
                            NativeImage image = pair.getFirst();
                            ResourceLocation textureID = makeTextureID(modLocation);
                            resourceStreams.put(textureID, () -> new ByteArrayInputStream(image.asByteArray()));
                            pair.getSecond()
                                    .ifPresent(
                                            metadataGetter -> resourceStreams.put(getMetadataLocation(textureID), metadataGetter)
                                    );
                        })
        );

        this.resources = resourceStreams;
    }*/

    public static ResourceLocation makeTextureID(ResourceLocation id) {
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), TEXTURE_DIRECTORY + id.getPath() + ".png");
    }

    public static ResourceLocation getMetadataLocation(ResourceLocation id) {
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), id.getPath() + ".mcmeta");
    }

/*    public Optional<Pair<NativeImage, Optional<IoSupplier<InputStream>>>> generateImage(
            ResourceLocation modLocation,
            ResourceLocation vanillaLocation,
            ResourceManager manager
    ) {
        ResourceLocation parentFile = makeTextureID(vanillaLocation);
        try (InputStream inputStream = manager.getResource(parentFile).orElseThrow().open()) {
            NativeImage image = NativeImage.read(inputStream);
            NativeImage transformedImage = this.transformImage(image);
            ResourceLocation metadata = getMetadataLocation(parentFile);
            Optional<IoSupplier<InputStream>> metadataLookup = Optional.empty();
            BufferedReader bufferedReader = null;
            JsonObject metadataJson;
            if (manager.getResource(metadata).isPresent()) {
                try (InputStream metadataStream = manager.getResource(metadata).get().open()) {
                    bufferedReader = new BufferedReader(new InputStreamReader(metadataStream, StandardCharsets.UTF_8));
                    metadataJson = GsonHelper.parse(bufferedReader);
                } catch (Exception e) {
                    return Optional.empty();
                } finally {
                    IOUtils.closeQuietly(bufferedReader);
                }
                JsonObject metaDataJsonForLambda = metadataJson;
                metadataLookup =
                        Optional.of(() -> new ByteArrayInputStream(metaDataJsonForLambda.toString().getBytes()));
            }
            return Optional.of(Pair.of(transformedImage, metadataLookup));
        } catch (IOException | NoSuchElementException e) {
            return Optional.empty();
        }
    }*/

    public NativeImage transformImage(NativeImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int oldColor = image.getPixelRGBA(x, y);
            }
        }
        return image;
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
        if (this.resources.containsKey(id)) {
            IoSupplier<InputStream> streamGetter = this.resources.get(id);
            if (streamGetter == null) {
                return null;
            }

            try {
                return streamGetter;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void listResources(PackType type, String namespace, String id, ResourceOutput output) {
        if (namespace.equals(MODID)) {
            this.resources.forEach((name, supplier) -> {
                if (name.getPath().startsWith(id)) {
                    output.accept(name, getResource(type, name));
                }
            });
        }
    }
}