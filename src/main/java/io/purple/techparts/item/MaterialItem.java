package io.purple.techparts.item;

import io.purple.techparts.material.MatPartCombo;
import io.purple.techparts.material.Material;
import io.purple.techparts.material.Part;

public class MaterialItem implements MatPartCombo {

    private final Part part;
    private final Material material;
    private final String id;

    public MaterialItem(Part part, Material material) {
        this.part = part;
        this.material = material;
        this.id = material.matId()+"_"+part.getId();
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public Part getPart() {
        return this.part;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getTexPath() {
        return "item/material/" + this.material.texture().getId()  ;
    }
}
