package io.purple.techparts.setup;

import io.purple.techparts.REF;

import java.util.ArrayList;
import java.util.List;

public enum Tags {

    CLIMBABLE("minecraft","blocks/climbable"),
    MINEABLEPICK("minecraft","blocks/minable/pickaxe");

    private final String rlLoc;
    private final String path;
    private final ArrayList<String> entries;

    Tags(String rlLoc, String path) {
        this.rlLoc = rlLoc;
        this.path = "tags/" + path + ".json";
        entries = new ArrayList<String>();
    }

    public void addItem(String entry){
        entries.add(REF.ID + ":" + entry);
    }
    public List<String> getEntries(){
        return this.entries;
    }

    public String getRlLoc() {
        return rlLoc;
    }

    public String getPath() {
        return path;
    }
}
