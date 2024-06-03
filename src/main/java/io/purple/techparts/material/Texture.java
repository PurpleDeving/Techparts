package io.purple.techparts.material;

public enum Texture {

    // ALL

    DIAMOND("diamond"),
    ROUGH("rough"),
    SHINY("shiny"),
    REDSTONE("redstone"), //TODO - Glow Interaction
    GLASS("glass"), // TODO - ADD Glass Interaction + Transparent Block

    //Metals

    DULL ("dull"),
    EMERALD ("emerald"),
    MAGNETIC ("magnetic"),
    METALLIC ("metallic"),
    RUBY("ruby"),

    // Wood - Just Block

    WOOD("wood"),

    // Gems

    GEM_H ("gem_h"),
    GEM_V ("gem_v"),
    LAPIS ("lapis"),

    FINE ("fine"),
    FLINT ("flint"),
    LIGNITE ("lignite"),
    QUARTZ ("quartz"),


    NONE("none");

    private final String id;

    Texture(String id){
        this.id = id;
    }

    public String getID() {
        return id;
    }
}
