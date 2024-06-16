package io.purple.techparts.material;

public enum Part {

    FRAME("frame"),

    DUST("dust"),
    SMALL_DUST("dust_small",true),
    BLOCK("block");

    private final String id;

    private final String name;
    private final String namePrefix;


    Part(String id){
        this(id,false);
    }


    Part(String id, Boolean prefixName) {
        this.id = id;
        if(!prefixName) {
            this.namePrefix = "";
            this.name = getNamefromId(id);
        } else{  // If Its a Prefix Name
            this.namePrefix = getPrefixName(id);
            this.name = getSuffixName(id);
        }
    }

    /********************************************************

    Getters

     ********************************************************/

    public String getId() {
        return id;
    }

    /********************************************************

     Helper

     ********************************************************/

    private static String getSuffixName(String id) {
        
    }

    private String getPrefixName(String id) {
        return null;
    }

    private static String getNamefromId(String id) {

    }

}
