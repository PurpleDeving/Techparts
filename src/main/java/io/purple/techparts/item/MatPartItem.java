package io.purple.techparts.item;

import io.purple.techparts.material.MatPartCombo;
import io.purple.techparts.material.Material;
import io.purple.techparts.material.Parts;
import io.purple.techparts.setup.Register;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MatPartItem extends BasicItem implements MatPartCombo {

    private final Material material;
    private final Parts part;

    public MatPartItem(ItemBuilder bitem) {
        super(bitem);
        this.material = bitem.material;
        this.part = bitem.part;
    }

    public Material getMaterial() {
        return material;
    }

    public Parts getPart() {
        return part;
    }

    @Override
    public String getTexPath() {
        return String.join("","item/",getMaterial().getTexture().getID(),"/",part.getID()); // TODO - Fucked the bird ?
    }

    public static class ItemBuilder extends BasicItem.ItemBuilder {
        public Material material;
        public Parts part;

        public ItemBuilder mat(Material material) {
            this.material = material;
            return this;
        }

        public ItemBuilder part(Parts part) {
            this.part = part;
            return this;
        }

        public MatPartItem build(){
            this.id(material.getID() + "_" + this.part.getID());
            return new MatPartItem(this);
        }
    }

}
