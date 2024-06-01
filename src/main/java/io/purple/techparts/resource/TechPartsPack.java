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
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.BooleanSupplier;

import static io.purple.techparts.TechParts.LOGGER;
import static io.purple.techparts.setup.Register.BLOCKS;
import static io.purple.techparts.setup.Register.ITEMS;

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

        Map<String, String> translatables = new HashMap<>();

        for (RegistryObject<Item> entry : ITEMS.getEntries()) {
            Item item = entry.get();
            // TODO - Rework with real Item Arraylist

            if(item.toString().contains("block")){
                continue;
            }

            translatables.put(String.format("item.%s.%s", REF.ID, item.toString()), "Sapphire"); //TODO - Exchange for final Name Reference

            ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/" + item.toString() + ".json");
            String itemModelJson = generateItemModelJson(item.toString());
            resourceMap.put(itemModel, ofText(itemModelJson));
        }

        for (RegistryObject<Block> entry : BLOCKS.getEntries()) {
            // TODO - Rework with real Block ArrayList

            String blockID = entry.getId().getPath();

            translatables.put(String.format("block.%s.%s", REF.ID, blockID), "Sapphire Block"); //TODO - Exchange for final Name Reference

            ResourceLocation blockstate = new ResourceLocation(REF.ID, "blockstates/" + blockID + ".json");
            ResourceLocation blockModel = new ResourceLocation(REF.ID, "models/block/" + blockID + ".json");
            ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/" + blockID + ".json");

            String blockstateJson = generateBlockstatesJson(blockID);
            String blockModelJson = generateBlockModelJson(blockID);
            String itemModelJson = generateBlockItemModelJson(blockID);

            LOGGER.info("Blockstate JSON 266: " + blockstateJson);
            LOGGER.info("Block model JSON: " + blockModelJson);
            LOGGER.info("Item model JSON: " + itemModelJson);


            LOGGER.info("Blockstate path: " + blockstate);
            LOGGER.info("Block model path: " + blockModel);
            LOGGER.info("Item model path: " + itemModel);


            resourceMap.put(blockstate, ofText(blockstateJson));
            resourceMap.put(blockModel, ofText(blockModelJson));
            resourceMap.put(itemModel, ofText(itemModelJson));
        }

        //ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/sapphire.json");
        //resourceMap.put(itemModel, ofText("{\"parent\":\"item/generated\",\"textures\":{\"layer0\":\"techparts:item/sapphire\"}}"));

        // Example: Adding a simple block model

        ResourceLocation blockModel = new ResourceLocation(REF.ID, String.format("models/block/%s.json","sapphire_block"));
        //resourceMap.put(blockModel, ofText(String.format("{\"parent\":\"block/cube_all\",\"textures\":{\"all\":\"%s:block/%s\"}}",REF.ID,"sapphire_block")));

        // Example: Adding a block item model
        //ResourceLocation blockItemModel = new ResourceLocation(REF.ID, "models/item/sapphire_block.json");
        //String blockItemModelJson = generateBlockItemModelJson("techparts:block/sapphire_block");
        //resourceMap.put(blockItemModel, ofText(blockItemModelJson));

        // Example: Adding a blockstates JSON for sapphire_block
        //ResourceLocation blockstatesModel = new ResourceLocation(REF.ID, "blockstates/sapphire_block.json");
        //String blockstatesJson = generateBlockstatesJson("techparts:block/sapphire_block");
        //resourceMap.put(blockstatesModel, ofText(blockstatesJson));


        // Example: Adding translations
        ResourceLocation langFile = new ResourceLocation(REF.ID, "lang/en_us.json");
        String langJson = generateLangJson(translatables);
        resourceMap.put(langFile, ofText(langJson));
    }

    private static IResourceStreamSupplier ofText(String text) {

        return IResourceStreamSupplier.create(() -> true, () -> new ByteArrayInputStream(text.getBytes()));

    }

    private String generateLangJson(Map<String, String> translatables) {
        StringBuilder translateString = new StringBuilder("{\n");
        // Go through every thing that needs to be translated and join them together
        for (Map.Entry<String, String> entry : translatables.entrySet()) {
            translateString
                    .append(" \"")
                    .append(entry.getKey())
                    .append("\": \"")
                    .append(entry.getValue())
                    .append("\",\n");
        }
        // Remove the last ",\n"
        if (translateString.length() > 2) {
            translateString.setLength(translateString.length() - 2);
        }
        translateString.append("\n}");
        return translateString.toString();
    }


    private String generateItemModelJson(String path) {
        return "{\n" +
                "  \"parent\": \"item/generated\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"" + REF.ID + ":item/" + path + "\"\n" +
                "  }\n" +
                "}";
    }

    private String generateBlockstatesJson(String modelPath) {
        return "{\n" +
                "  \"variants\": {\n" +
                "    \"\": {\n" +
                "      \"model\": \"" +REF.ID + ":block/" + modelPath + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    private String generateBlockItemModelJson(String texturePath) {
        return "{\n" +
                "  \"parent\": \"" + REF.ID + ":block/" + texturePath + "\"\n}";
    }

    /*private String generateBlockItemModelJson(String texturePath) {
        return "{\n" +
                "  \"parent\": \"block/cube_all\",\n" +
                "  \"textures\": {\n" +
                "    \"all\": \"" + texturePath + "\"\n" +
                "  }\n" +
                "}";
    } */

    //ResourceLocation blockModel = new ResourceLocation(REF.ID, String.format("models/block/%s.json","sapphire_block"));
    //resourceMap.put(blockModel, ofText(String.format("{\"parent\":\"block/cube_all\",\"textures\":{\"all\":\"%s:block/%s\"}}",REF.ID,"sapphire_block")));


    private String generateBlockModelJson(String path) {
        return "{\n" +
                "  \"parent\": \"block/cube_all\",\n" +
                "  \"textures\": {\n" +
                "    \"all\": \"" + REF.ID + ":block/" + path + "\"\n" +
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
        if (!namespace.equals(REF.ID)) {
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