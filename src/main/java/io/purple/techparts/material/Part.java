package io.purple.techparts.material;

public enum Part {

    DUST("dust"),
    SMALL_DUST("dust_small"),

    // BLOCKS
    FRAME("frame"),
    SCAFFOLDING("scaffolding"),
    BLOCK("block"),

    // LIQUID
    LIQUID("fluid"),
    GAS("gas");


    private final String id;

    private final String name;
    private final String namePrefix;


    Part(String id) {
        this.id = id;
        int underscoreIndex = id.indexOf("_");
        if(underscoreIndex == -1) {
            this.namePrefix = "";
            this.name = id.toUpperCase();
        } else{  // If it's a Prefix Name
            this.name = id.substring(0, underscoreIndex).toUpperCase();
            this.namePrefix = id.substring(underscoreIndex+1).toUpperCase();
        }
    }

    /********************************************************

    Getters

     ********************************************************/

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNamePrefix() {
        return namePrefix;
    }
}
