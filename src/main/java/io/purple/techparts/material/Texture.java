package io.purple.techparts.material;

public enum Texture {
    SHINY("shiny"),
    DULL("dull");

    private final String id;

    Texture(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
