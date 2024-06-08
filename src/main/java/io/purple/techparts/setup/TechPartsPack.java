package io.purple.techparts.setup;

import com.google.gson.JsonObject;
import io.purple.techparts.REF;
import io.purple.techparts.block.BasicBlock;
import io.purple.techparts.block.MatPartBlock;
import io.purple.techparts.item.BasicItem;
import io.purple.techparts.item.MatPartItem;
import io.purple.techparts.material.Parts;
import io.purple.techparts.setup.handler.Tags;
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
import java.util.stream.Collectors;

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

    //TODO - 3 Layer Textures

    private boolean hasInit = false;

    public void init() {
        if (hasInit) {
            return;
        }
        hasInit = true;

        // Reset Resourcemap
        resourceMap.clear();

        Map<String, String> translatables = new HashMap<>();


        /*******************************************************
         *
         *  BASIC
         *
         *****************************************************/


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

            // No reduction like for the MAT Blocks because there should be no basic block thats sharing a base texture with another,
            // and if then it's a rare case with no consequences
            String blockstateJson = generateBlockstatesJson(path); // Path to Block Model
            String blockModelJson = generateBlockModelJson(path);     // Path to texture
            String itemModelJson = generateBlockItemModelJson(path);  // Path to texture

            resourceMap.put(blockstate, ofText(blockstateJson));
            resourceMap.put(blockModel, ofText(blockModelJson));
            resourceMap.put(itemModel, ofText(itemModelJson));
        }

        /*******************************************************
         *
         *  Material Part Items - 3 Step Flow because many Items share same jsons
         *
         *****************************************************/

        // Step 1: Filter the MATERIAL_PART_ITEMS to get unique combinations of textures and parts
        Set<String> uniquePaths = MATERIAL_PART_ITEMS.stream()
                .map(entry -> entry.get().getTexPath())
                .collect(Collectors.toSet());

        // Step 2: Precompute JSON strings paths for the filtered combinations
        Map<String, String> itemModelJsonMap = new HashMap<>();

        for (String path : uniquePaths) {
            String itemModelJson = generateMaterialItemModelJson(path);
            itemModelJsonMap.put(path, itemModelJson);
        }

        // Step 3: Iterate over the ITEMS - Including ResourceLocation (because its id dependent)
        for (RegistryObject<MatPartItem> entry : MATERIAL_PART_ITEMS){
            String path = entry.get().getTexPath();
            String itemId = entry.get().getId();

            // Translation
            translatables.put(String.format("item.%s.%s", REF.ID, itemId), entry.get().getName());

            // Texture stuff
            ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/" + itemId + ".json");
            resourceMap.put(itemModel, ofText(itemModelJsonMap.get(path)));
        }

        /*******************************************************
         *
         *  Material Part Block - 3 Step Flow because many Blocks share same jsons
         *
         *****************************************************/

        // Step 1: Filter the MATERIAL_PART_BLOCKS to get unique combinations of textures and parts
        uniquePaths = MATERIAL_PART_BLOCKS.stream()
                .map(entry -> entry.get().getTexPath())
                .collect(Collectors.toSet());

        for (String entry: uniquePaths){
            LOGGER.info("243" + entry);
        }

        // Step 2: Precompute JSON strings and ResourceLocation paths for the filtered combinations
        Map<String, String> blockstateJsonMap = new HashMap<>();
        Map<String, String> blockModelJsonMap = new HashMap<>();
        itemModelJsonMap.clear();

        for (String path : uniquePaths) {
            String blockstateJson = generateBlockstatesJson(path);
            String blockModelJson = path.contains("frame") ? generateFrameModelJson(path) : generateBlockModelJson(path);
            String itemModelJson = generateBlockItemModelJson(path);

            blockstateJsonMap.put(path, blockstateJson);
            blockModelJsonMap.put(path, blockModelJson);
            itemModelJsonMap.put(path, itemModelJson);

            // Since the BlockModel Json and its location are only dependent on path, they can created with just the texture + path combination
            resourceMap.put(new ResourceLocation(REF.ID, "models/" + path + ".json"), ofText(blockModelJson));
        }

        // Step 3: Iterate over the Blocks, including for the 2 ResourceLocations that are blockId dependent. Then put them in the resourceMap
        for (RegistryObject<MatPartBlock> entry : MATERIAL_PART_BLOCKS){
            String blockID = entry.get().getId();
            String path = entry.get().getTexPath(); //Path here leads to the Texture + Part combination from Step 1 and 2
            Parts parts = entry.get().getPart();

            translatables.put(String.format("block.%s.%s", REF.ID, blockID), entry.get().getClearName());

            // ADD Tags
            Tags.MINEABLEPICK.addItem(entry.get().getId());
            if(parts == Parts.FRAME || parts == Parts.SCAFFOLDING){
                Tags.CLIMBABLE.addItem(entry.get().getId());
            }

            // Models

            ResourceLocation blockstate = new ResourceLocation(REF.ID, "blockstates/" + blockID + ".json");
            ResourceLocation itemModel = new ResourceLocation(REF.ID, "models/item/" + blockID + ".json");

            String blockstateJson = blockstateJsonMap.get(path);
            String itemModelJson = itemModelJsonMap.get(path);

            resourceMap.put(blockstate, ofText(blockstateJson));
            resourceMap.put(itemModel, ofText(itemModelJson));
        }


        // Adding Tags
        for(Tags tag : Tags.values()){
            ResourceLocation tagfile = new ResourceLocation(tag.getRlLoc(),tag.getPath());
            String tagJson = generateTagJson(tag.getEntries());
            resourceMap.put(tagfile, ofText(tagJson));
        }

        // Adding Translations
        ResourceLocation langFile = new ResourceLocation(REF.ID, "lang/en_us.json");
        String langJson = generateLangJson(translatables);
        resourceMap.put(langFile, ofText(langJson));
    }



    private static IResourceStreamSupplier ofText(String text) {

        return IResourceStreamSupplier.create(() -> true, () -> new ByteArrayInputStream(text.getBytes()));

    }

    /*******************************************************
     *
     *  Block and Item Tags
     *
     *****************************************************/

    private String generateTagJson(List<String> entries) {
        StringBuilder tagString = new StringBuilder("{\n");
        tagString.append("  \"replace\": false,\n")
                 .append("  \"values\": [\n");
        for (String entry: entries){
            tagString.append("    \"")
                    .append(entry)
                    .append("\",\n");
        }
        if (!entries.isEmpty()) {
            tagString.setLength(tagString.length() - 2);
        }
        tagString.append("\n  ]\n")
                 .append("}");
        tagString.append("");

        return tagString.toString();
    }


    /*******************************************************
     *
     *  Translation Keys
     *
     *****************************************************/


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

    /*******************************************************
     *
     *  Block and Item Models
     *
     *****************************************************/


    private String generateItemModelJson(String path) {
        return "{\n" +
                "  \"parent\": \"item/generated\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"" + REF.ID + ":item/" + path + "\"\n" +
                "  }\n" +
                "}";
    }

    private String generateMaterialItemModelJson(String path) {
        return "{\n" +
                "  \"parent\": \"item/generated\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"" + REF.ID + ":item/material/" + path + "\",\n" +
                "    \"layer1\": \"" + REF.ID + ":item/material/" + path + "_secondary\",\n" +
                "    \"layer2\": \"" + REF.ID + ":item/material/" + path + "_overlay\"\n" +
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



    private String generateBlockModelJson(String path) {
        return "{\n" +
                "  \"parent\": \"block/cube_all\",\n" +
                "  \"textures\": {\n" +
                "    \"all\": \"" + REF.ID + ":" + path + "\"\n" +
                "  }\n" +
                "}";
    }

    public String generateFrameModelJson(String path) {
        return "{\"parent\":\"minecraft:block/cube_all\",\"textures\":{\"all\":\"" +
                REF.ID+ ":" +path +  "\",\"particle\":\"#all\"},\"elements\":[{\"from\":[0,0,0],\"to\":[16,16,16],\"faces\":{\"down\":{\"texture\":\"#all\",\"cullface\":\"down\",\"tintindex\":0},\"up\":{\"texture\":\"#all\",\"cullface\":\"up\",\"tintindex\":0},\"north\":{\"texture\":\"#all\",\"cullface\":\"north\",\"tintindex\":0},\"south\":{\"texture\":\"#all\",\"cullface\":\"south\",\"tintindex\":0},\"west\":{\"texture\":\"#all\",\"cullface\":\"west\",\"tintindex\":0},\"east\":{\"texture\":\"#all\",\"cullface\":\"east\",\"tintindex\":0}}},{\"from\":[15.984375,15.984375,15.984375],\"to\":[0.015625,0.015625,0.015625],\"faces\":{\"down\":{\"texture\":\"#all\",\"uv\":[0.0,0.0,16.0,16.0],\"tintindex\":0},\"up\":{\"texture\":\"#all\",\"uv\":[0.0,0.0,16.0,16.0],\"tintindex\":0},\"north\":{\"texture\":\"#all\",\"uv\":[0.0,0.0,16.0,16.0],\"tintindex\":0},\"south\":{\"texture\":\"#all\",\"uv\":[0.0,0.0,16.0,16.0],\"tintindex\":0},\"west\":{\"texture\":\"#all\",\"uv\":[0.0,0.0,16.0,16.0],\"tintindex\":0},\"east\":{\"texture\":\"#all\",\"uv\":[0.0,0.0,16.0,16.0],\"tintindex\":0}}}]}";
    }


    /*******************************************************
     *
     *  RessourcePack Setup
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