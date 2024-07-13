package io.purple.techparts.material;

import com.mojang.logging.LogUtils;
import io.purple.techparts.TechParts;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;

import static io.purple.techparts.material.Part.*;

public class Materials {

    private static final Logger LOGGER = LogUtils.getLogger();
    public static HashMap<String,Material> MAT_LIST = new HashMap<>(); // Map, so that KubeJs Integration can overwrite the Materials

    /********************************************************

         Enum Groups

     ********************************************************/

    private static EnumSet<Part> blocks = EnumSet.of(BLOCK,FRAME,SCAFFOLDING);

    private static EnumSet<Part> all = EnumSet.allOf(Part.class);

    /********************************************************

     Materials

     ********************************************************/

    // TODO - Documentation for all these Builder elements


    private static final Material Gold = new Material.Builder("gold")
            .primary(0xfdf55f).secondary(0xf25833).tex(Texture.DULL)
            .parts(all)
            .fluidHot()
            .fluidSlow()
            .fluidSlowing()
            .build();

    private static final Material Glowstone = new Material.Builder("glowstone")
            .fluidHot()
            .glow()
            .parts(all)
            .build();

    public static void init(){
        MAT_LIST.put(Gold.matId(),Gold);
        MAT_LIST.put(Glowstone.matId(),Glowstone);
    }

    // TODO - Add KubeJS Integration for this. It should add the defined elements in the HashMap, guaranteeing an overwrite if the id is the same

}
