package io.purple.techparts.setup.handler;

import io.purple.techparts.setup.TechPartsPack;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

public class JsonHandler {

    /*******************************************************
     *
     *  Translation Keys
     *
     *****************************************************/
    public static String generateLangJson() {
        StringBuilder translateString = new StringBuilder("{\n");
        // Go through every thing that needs to be translated and join them together
        for (Map.Entry<String, String> entry : LoopHandler.translatables.entrySet()) {
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
}
