package io.purple.techparts.setup.pack;

import io.purple.techparts.TechParts;

import java.io.ByteArrayInputStream;
import java.util.List;
import com.google.gson.JsonObject;

import static io.purple.techparts.TechParts.MODID;

public class JsonHandler {

    /*******************************************************
     *
     *  Buckets
     *
     *****************************************************/

    public static JsonObject generateBucket(String fluidId){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("parent", "neoforge:item/bucket");
        jsonObject.addProperty("fluid", MODID + ":" + fluidId);
        jsonObject.addProperty("loader", "neoforge:fluid_container");
        return  jsonObject;
    }


    /*******************************************************
     *
     *  Translation Keys
     *
     *****************************************************/
    public static String generateLangJson() {
/*        StringBuilder translateString = new StringBuilder("{\n");
        // Go through every thing that needs to be translated and join them together
*//*        for (Map.Entry<String, String> entry : LoopHandler.translatables.entrySet()) {
            translateString
                    .append(" \"")
                    .append(entry.getKey())
                    .append("\": \"")
                    .append(entry.getValue())
                    .append("\",\n");
        }*//*
        // Remove the last ",\n"
        if (translateString.length() > 2) {
            translateString.setLength(translateString.length() - 2);
        }
        translateString.append("\n}");
        return translateString.toString();*/
        JsonObject json = new JsonObject();
        json.addProperty("item.techparts.example_item", "Example Item");
        return json.toString();

    }

    /*******************************************************
     *
     *  Loottables
     *
     *****************************************************/

    public static String generateDefaultLootJson(String blockId){
        return "{\n" +
                "  \"type\": \"minecraft:block\",\n" +
                "  \"pools\": [\n" +
                "    {\n" +
                "      \"rolls\": 1,\n" +
                "      \"bonus_rolls\": 0,\n" +
                "      \"entries\": [\n" +
                "        {\n" +
                "          \"type\": \"minecraft:item\",\n" +
                "          \"name\": \""+ blockId + "\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"conditions\": [\n" +
                "        {\n" +
                "          \"condition\": \"minecraft:survives_explosion\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
    }

    public static String generateItemModelJson(String path) {
        return "{\n" +
                "  \"parent\": \"item/generated\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"" + MODID + ":item/" + path + "\"\n" +
                "  }\n" +
                "}";
    }


    /*******************************************************
     *
     *  Block and Item Tags
     *
     *****************************************************/
    public static String generateTagJson(List<String> entries) {
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

    public static TechPartsPack.IResourceStreamSupplier ofText(String text) {
        return TechPartsPack.IResourceStreamSupplier.create(() -> true, () -> new ByteArrayInputStream(text.getBytes()));
    }


    public static TechPartsPack.IResourceStreamSupplier ofText(JsonObject json) {

        return ofText(json.toString());

    }
}
