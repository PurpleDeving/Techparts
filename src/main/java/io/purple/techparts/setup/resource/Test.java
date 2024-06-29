package io.purple.techparts.setup.resource;

import com.google.gson.JsonObject;
import io.purple.techparts.TechParts;
import io.purple.techparts.setup.resource.JsonHandler;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

import static io.purple.techparts.TechParts.LOGGER;
import static io.purple.techparts.setup.resource.JsonHandler.ofText;

public class Test extends AbstractPackResources implements PackResources, PreparableReloadListener {

    public static final PackLocationInfo LOCATION_INFO = new PackLocationInfo(
            "techparts.pack", Component.translatable("techparts.pack.title"), PackSource.BUILT_IN, Optional.empty()
    );

    public final Map<ResourceLocation, IResourceStreamSupplier> resourceMap = new HashMap<>();

    // Singleton instance
    static Test packInstance;

    protected Test() {
        super(LOCATION_INFO);
    }

    public static Test getPackInstance() {
        if (packInstance == null) {
            packInstance = new Test();
        }
        return packInstance;
    }

    //TODO - 3 Layer Textures

    private boolean hasInit = false;

    public void init() {

        if (hasInit) {
            return;
        }
        hasInit = true;


        // Reset resourceMap
        resourceMap.clear();

        // Loops
        //LoopHandler.basicItemLoop(resourceMap);
        //LoopHandler.basicBlockLoop(resourceMap);
        //LoopHandler.MatPartItemLoop(resourceMap);
        //LoopHandler.MatPartBlockLoop(resourceMap);
        //LoopHandler.fluidLoop(resourceMap);
        //LoopHandler.bucketLoop(resourceMap);



        /*******************************************************
         *
         *  Tag Creation (Where collected in the LoopHandler)
         *
         *****************************************************/

        // Adding Tags


        /*******************************************************
         *
         *  Translation Creations (Where collected in the LoopHandler)
         *
         *****************************************************/


        // Adding Translations
        ResourceLocation langFile = ResourceLocation.fromNamespaceAndPath(TechParts.MODID, "lang/en_us.json");
        String langJson = JsonHandler.generateLangJson();
        resourceMap.put(langFile, ofText(langJson));


    }

















    /*******************************************************
     *
     *  ResourcePack Setup
     *
     *****************************************************/

    @Override
    public void close() {
    }


    @Override
    public boolean isHidden() {
        return true;
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... path) {
        return null;
    }

    @Override
    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location) {
        var res = resourceMap.get(location);
        if (res == null) {
            return null;
        }
        if (!res.exists()) {
            return null;
        }
        return () -> {
            try {
                init();
                return res.create();
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IOException)
                    throw (IOException) e.getCause();
                throw e;
            }
        };
    }


    @Override
    public void listResources(PackType type, String namespace, String dir, ResourceOutput out) {
        if (!namespace.equals(TechParts.MODID)) {
            return;
        }
        init();
        for (var e : resourceMap.entrySet())
            if (e.getKey().getPath().startsWith(dir) && e.getValue().exists())
                out.accept(e.getKey(), e.getValue()::create);
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        init();
        return Collections.singleton(TechParts.MODID);
    }

    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) throws IOException {
        if (deserializer.getMetadataSectionName().equals("pack")) {
            JsonObject obj = new JsonObject();
            obj.addProperty("pack_format", SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            obj.addProperty("description", "Generated resources for TechParts");
            return deserializer.fromJson(obj);
        }
        return null;
    }

    @Override
    public PackLocationInfo location() {
        return null;
    }

    @Override
    public String packId() {
        return "TechParts";
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

    public interface IResourceStreamSupplier {
        static IResourceStreamSupplier create(BooleanSupplier exists, IIOSupplier<InputStream> streamable) {
            return new IResourceStreamSupplier() {
                @Override
                public boolean exists() {
                    return exists.getAsBoolean();
                }

                @Override
                public InputStream create() throws IOException {
                    return streamable.get();
                }
            };
        }

        boolean exists();

        InputStream create() throws IOException;
    }

    @FunctionalInterface
    public interface IIOSupplier<T> {
        T get() throws IOException;
    }
}