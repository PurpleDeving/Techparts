package io.purple.techparts.setup.pack;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import io.purple.techparts.TechParts;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

import static io.purple.techparts.TechParts.MODID;
import static io.purple.techparts.setup.pack.JsonHandler.ofText;

public class TechPartsPack implements PackResources {
    public static final PackLocationInfo LOCATION_INFO = new PackLocationInfo(
            "techparts.pack", Component.translatable("techparts.pack.title"), PackSource.BUILT_IN, Optional.empty()
    );

    public static final Set<String> NAMESPACE = ImmutableSet.of(TechParts.MODID);

    public static final Map<ResourceLocation, IResourceStreamSupplier> resourceMap = new HashMap<>();
    private boolean hasInit = false;

    // Singleton
    static TechPartsPack packInstance;

    public static TechPartsPack getPackInstance()
    {
        if(packInstance == null)
            packInstance = new TechPartsPack();
        return packInstance;
    }

    private void init(){

        if(hasInit) return;
        hasInit = true;

        resourceMap.clear();



        String test = JsonHandler.generateLangJson();
        TechParts.LOGGER.info("Reach 2 - Inside Reload");
        TechParts.LOGGER.info(test);
        resourceMap.put(ResourceLocation.fromNamespaceAndPath(MODID,"lang/en_us.json"), ofText(test));

        // Fluids, Buckets and FluidBlocks
        Loops.loopFluids();

    }



    @Override
    public void close() {}

    // Literally hides the ResourcePack in the Client
    @Override
    public boolean isHidden()
    {
        return false;
    }

    @Override
    public IoSupplier<InputStream> getRootResource(String... fileName) {
        return null;
    }
    //SFR
    @Override
    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location)
    {
        var res = resourceMap.get(location);
        if(res == null) return null;

        if(!res.exists()) return null;

        return () ->
        {
            try
            {
                init();
                return res.create();
            } catch(RuntimeException e)
            {
                if(e.getCause() instanceof IOException)
                    throw (IOException) e.getCause();
                throw e;
            }
        };
    }

    @Override
    public void listResources(PackType type, String namespace, String dir, ResourceOutput out)
    {
        if(!namespace.equals("techparts")) return;
        init();

        for(var e : resourceMap.entrySet())
            if(e.getKey().getPath().startsWith(dir) && e.getValue().exists())
                out.accept(e.getKey(), e.getValue()::create);
    }

    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer)
            throws IOException
    {
        if(deserializer.getMetadataSectionName().equals("pack"))
        {
            JsonObject obj = new JsonObject();
            obj.addProperty("pack_format", SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            obj.addProperty("description", "Generated resources for SolarFlux");
            return deserializer.fromJson(obj);
        }
        return null;
    }

    @Override
    public PackLocationInfo location() {
        return LOCATION_INFO;
    }





    @Override
    public Set<String> getNamespaces(PackType type) {
        return NAMESPACE;
    }







    /*************************************************************
     *
     *  Map Interface Shenanigans
     *
     ************************************************************/


    public interface IResourceStreamSupplier
    {
        static IResourceStreamSupplier create(BooleanSupplier exists, IIOSupplier<InputStream> streamable)
        {
            return new IResourceStreamSupplier()
            {
                @Override
                public boolean exists()
                {
                    return exists.getAsBoolean();
                }

                @Override
                public InputStream create()
                        throws IOException
                {
                    return streamable.get();
                }
            };
        }

        boolean exists();

        InputStream create()
                throws IOException;
    }
    @FunctionalInterface
    public interface IIOSupplier<T>
    {
        T get()
                throws IOException;
    }
}
