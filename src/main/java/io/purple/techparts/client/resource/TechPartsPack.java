package io.purple.techparts.client.resource;

import com.google.gson.JsonObject;
import io.purple.techparts.REF;
import io.purple.techparts.block.BasicBlock;
import io.purple.techparts.block.MatPartBlock;
import io.purple.techparts.item.BasicItem;
import io.purple.techparts.item.MatPartItem;
import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.BooleanSupplier;

import static io.purple.techparts.TechParts.LOGGER;
import static io.purple.techparts.setup.Register.*;

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

        // TODO - Make the overlap smaller - They do basically the same thing


        for (RegistryObject<MatPartItem> entry : MATERIAL_PART_ITEMS){
            MatPartItem item = entry.get();
            String name = item.getName();
            String id = item.getId();
            Material mat = item.getMaterial();
            String tex = mat.getTexture().getID();
            Parts part = item.getPart();

            translatables.put(String.format("item.%s.%s", REF.ID, id), name);
            ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/" + id + ".json");
            String itemModelJson = generateItemModelJson("material/" + tex + "/" + part.getID());
            resourceMap.put(itemModel, ofText(itemModelJson));
        }

        for (RegistryObject<BasicItem> entry : BASIC_ITEMS) {
            BasicItem item = entry.get();
            String name = item.getName();
            String id = item.getId();

            if(id.contains("block")){
                continue;
            }

            translatables.put(String.format("item.%s.%s", REF.ID, id), name);
            ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/" + id + ".json");
            String itemModelJson = generateItemModelJson("basic/" + id);
            resourceMap.put(itemModel, ofText(itemModelJson));
        }

        for (RegistryObject<BasicBlock> entry : BASIC_BLOCKS) {
            String blockID = entry.get().getId();
            String path = entry.get().getTexPath();
            // FIXME - Translation name is broken

            translatables.put(String.format("block.%s.%s", REF.ID, blockID), entry.get().getClearName()); //TODO - Exchange for final Name Reference

            ResourceLocation blockstate = new ResourceLocation(REF.ID, "blockstates/" + blockID + ".json");
            ResourceLocation blockModel = new ResourceLocation(REF.ID, "models/block/basic/" + blockID + ".json");
            ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/" + blockID + ".json");

            String blockstateJson = generateBlockstatesJson(path); // Path to Block Model
            String blockModelJson = generateBlockModelJson(path);     // Path to texture
            String itemModelJson = generateBlockItemModelJson(path);  // Path to texture

            /*LOGGER.info("Blockstate JSON 266: " + blockstateJson);
            LOGGER.info("Block model JSON: " + blockModelJson);
            LOGGER.info("Item model JSON: " + itemModelJson);


            LOGGER.info("Blockstate path: " + blockstate);
            LOGGER.info("Block model path: " + blockModel);
            LOGGER.info("Item model path: " + itemModel);*/

            resourceMap.put(blockstate, ofText(blockstateJson));
            resourceMap.put(blockModel, ofText(blockModelJson));
            resourceMap.put(itemModel, ofText(itemModelJson));
        }

        for (RegistryObject<MatPartBlock> entry : MATERIAL_PART_BLOCKS){
            String blockID = entry.get().getId();
            String path = entry.get().getTexPath();

            //translatables.put(String.format("block.%s.%s", REF.ID, blockID), "Sapphire Block"); //TODO - Exchange for final Name Reference

            ResourceLocation blockstate = new ResourceLocation(REF.ID, "blockstates/" + blockID + ".json");
            ResourceLocation blockModel = new ResourceLocation(REF.ID, "models/" + path + ".json");
            ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/" + blockID + ".json");

            String blockstateJson = generateBlockstatesJson(path); // Path to Block Model
            String blockModelJson = generateBlockModelJson(path); // Path to Texture
            String itemModelJson = generateBlockItemModelJson(path); // Path to Texture
/*            LOGGER.info("Blockstate JSON 296: " + blockstateJson);
            LOGGER.info("Block model JSON: " + blockModelJson);
            LOGGER.info("Item model JSON: " + itemModelJson);


            LOGGER.info("Blockstate path: " + blockstate);
            LOGGER.info("Block model path: " + blockModel);
            LOGGER.info("Item model path: " + itemModel); */

            resourceMap.put(blockstate, ofText(blockstateJson));
            resourceMap.put(blockModel, ofText(blockModelJson));
            resourceMap.put(itemModel, ofText(itemModelJson));
        }
        /**/



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
                "      \"model\": \"" +REF.ID + ":" + modelPath + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    private String generateBlockItemModelJson(String texturePath) {
        return "{\n" +
                "  \"parent\": \"" + REF.ID + ":" + texturePath + "\"\n}";
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
                "    \"all\": \"" + REF.ID + ":" + path + "\"\n" +
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