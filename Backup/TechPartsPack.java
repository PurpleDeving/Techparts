package io.purple.techparts.resource;

import com.google.gson.JsonObject;
import io.purple.techparts.REF;
import io.purple.techparts.TechParts;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;

public class TechPartsPack implements PackResources {

    public final Map<ResourceLocation, IResourceStreamSupplier> resourceMap = new HashMap<>();

    // Singleton instance
    static TechPartsPack packInstance;

    public static TechPartsPack getPackInstance() {
        if (packInstance == null) {
            packInstance = new TechPartsPack();
        }
        return packInstance;
    }


    private boolean hasInit = false;

    public void init() {
        if (hasInit) {
            return;
        }
        hasInit = true;

        // Reset Resourcemap
        resourceMap.clear();

        ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/sapphire.json");
        resourceMap.put(itemModel, ofText("{\"parent\":\"item/generated\",\"textures\":{\"layer0\":\"techparts:item/sapphire\"}}"));

        // Example: Adding a simple block model

        ResourceLocation blockModel = new ResourceLocation(REF.ID, String.format("models/block/%s.json","sapphire_block"));
        resourceMap.put(blockModel, ofText(String.format("{\"parent\":\"block/cube_all\",\"textures\":{\"all\":\"%s:block/%s\"}}",REF.ID,"sapphire_block")));

        // Example: Adding a block item model
        ResourceLocation blockItemModel = new ResourceLocation(REF.ID, "models/item/sapphire_block.json");
        String blockItemModelJson = generateBlockItemModelJson("techparts:block/sapphire_block");
        resourceMap.put(blockItemModel, ofText(blockItemModelJson));

        // Example: Adding a blockstates JSON for sapphire_block
        ResourceLocation blockstatesModel = new ResourceLocation(REF.ID, "blockstates/sapphire_block.json");
        String blockstatesJson = generateBlockstatesJson("techparts:block/sapphire_block");
        resourceMap.put(blockstatesModel, ofText(blockstatesJson));


        // Example: Adding translations
        ResourceLocation langFile = new ResourceLocation(REF.ID, "lang/en_us.json");
        String langJson = generateLangJson();
        resourceMap.put(langFile, ofText(langJson));
    }

    private static IResourceStreamSupplier ofText(String text) {

        return IResourceStreamSupplier.create(() -> true, () -> new ByteArrayInputStream(text.getBytes()));

    }

    private String generateLangJson() {
        return "{\n" +
                "  \"item.techparts.sapphire\": \"Sapphire\",\n" +
                "  \"item.techparts.raw_sapphire\": \"Raw Sapphire\",\n" +
                "  \"block.techparts.sapphire_block\": \"Sapphire Block\"\n" +
                "}";
    }

    private String generateBlockstatesJson(String modelPath) {
        return "{\n" +
                "  \"variants\": {\n" +
                "    \"\": {\n" +
                "      \"model\": \"" + modelPath + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    private String generateBlockItemModelJson(String texturePath) {
        return "{\n" +
                "  \"parent\": \"block/cube_all\",\n" +
                "  \"textures\": {\n" +
                "    \"all\": \"" + texturePath + "\"\n" +
                "  }\n" +
                "}";
    }


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
        if (!namespace.equals(REF.ID)){
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
        return Collections.singleton(REF.ID);
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
    public String packId() {
        return "TechParts";
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