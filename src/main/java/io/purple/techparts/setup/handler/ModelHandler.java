package io.purple.techparts.setup.handler;

import io.purple.techparts.REF;
import io.purple.techparts.setup.TechPartsPack;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ModelHandler {

    public static String generateItemModelJson(String path) {
        return "{\n" +
                "  \"parent\": \"item/generated\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"" + REF.ID + ":item/" + path + "\"\n" +
                "  }\n" +
                "}";
    }

    public static String generateBlockstatesJson(String modelPath) {
        return "{\n" +
                "  \"variants\": {\n" +
                "    \"\": {\n" +
                "      \"model\": \"" +REF.ID + ":" + modelPath + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    public static String generateMaterialItemModelJson(String path) {
        return "{\n" +
                "  \"parent\": \"item/generated\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"" + REF.ID + ":item/material/" + path + "\",\n" +
                "    \"layer1\": \"" + REF.ID + ":item/material/" + path + "_secondary\",\n" +
                "    \"layer2\": \"" + REF.ID + ":item/material/" + path + "_overlay\"\n" +
                "  }\n" +
                "}";
    }



    public static String generateBlockItemModelJson(String texturePath) {
        return "{\n" +
                "  \"parent\": \"" + REF.ID + ":" + texturePath + "\"\n}";
    }



    public static String generateBlockModelJson(String path) {
        return "{\n" +
                "  \"parent\": \"block/cube_all\",\n" +
                "  \"textures\": {\n" +
                "    \"all\": \"" + REF.ID + ":" + path + "\"\n" +
                "  }\n" +
                "}";
    }

    public static String generateFrameModelJson(String path) {
        return "{\"parent\":\"minecraft:block/cube_all\",\"textures\":{\"all\":\"" +
                REF.ID+ ":" +path +  "\",\"particle\":\"#all\"},\"elements\":[{\"from\":[0,0,0],\"to\":[16,16,16],\"faces\":{\"down\":{\"texture\":\"#all\",\"cullface\":\"down\",\"tintindex\":0},\"up\":{\"texture\":\"#all\",\"cullface\":\"up\",\"tintindex\":0},\"north\":{\"texture\":\"#all\",\"cullface\":\"north\",\"tintindex\":0},\"south\":{\"texture\":\"#all\",\"cullface\":\"south\",\"tintindex\":0},\"west\":{\"texture\":\"#all\",\"cullface\":\"west\",\"tintindex\":0},\"east\":{\"texture\":\"#all\",\"cullface\":\"east\",\"tintindex\":0}}},{\"from\":[15.984375,15.984375,15.984375],\"to\":[0.015625,0.015625,0.015625],\"faces\":{\"down\":{\"texture\":\"#all\",\"uv\":[0.0,0.0,16.0,16.0],\"tintindex\":0},\"up\":{\"texture\":\"#all\",\"uv\":[0.0,0.0,16.0,16.0],\"tintindex\":0},\"north\":{\"texture\":\"#all\",\"uv\":[0.0,0.0,16.0,16.0],\"tintindex\":0},\"south\":{\"texture\":\"#all\",\"uv\":[0.0,0.0,16.0,16.0],\"tintindex\":0},\"west\":{\"texture\":\"#all\",\"uv\":[0.0,0.0,16.0,16.0],\"tintindex\":0},\"east\":{\"texture\":\"#all\",\"uv\":[0.0,0.0,16.0,16.0],\"tintindex\":0}}}]}";
    }

    /*******************************************************
     *
     *  Fluid and Buckets
     *
     *****************************************************/

    public static String generateFluidModelJson() {
        return "{\n" +
                "  \"parent\": \"block/lava_still\",\n" +
                "  \"textures\": {\n" +
                "    \"particle\": \"minecraft:block/lava_still\",\n" +
                "    \"flowing\": \"minecraft:block/lava_flow\",\n" +
                "    \"still\": \"minecraft:block/lava_still\"\n" +
                "  }\n" +
                "}";
    }

    public static String generateFluidBucketModelJson() {
        return "{\n" +
                "  \"parent\": \"minecraft:item/generated\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"" + REF.ID + ":item/basic/bucket_base" + "\",\n" +
                "    \"layer1\": \"" + REF.ID + ":item/basic/bucket_overlay" + "\"\n" +
                "  }\n" +
                "}";

    }
}
