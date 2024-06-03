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
    private final String id;
    private final String name;


    public MatPartItem(ItemBuilder bitem) {
        super(bitem);
        this.id = bitem.id;
        this.name = bitem.name;
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
        return String.join(getMaterial().getTexture().getID(),"/",getId());
    }

    public static class ItemBuilder extends BasicItem.ItemBuilder {
        public Material material;
        public Parts part;
        private Properties properties = Register.baseItemProps();
        private String tooltip;
        private String name;

        private String id; // For MatParts it will be generated from material + part

        public ItemBuilder mat(Material material) {
            this.material = material;
            return this;
        }

        public ItemBuilder part(Parts part) {
            this.part = part;
            return this;
        }

        public ItemBuilder name(String name){
            this.name = name;
            return this;
        }

        public ItemBuilder props(Properties props){
            this.properties = props;
            return this;
        }

        public MatPartItem build(){
            this.id = this.material.getID() + "_" + this.part.getID();
            return new MatPartItem(this);
        }
    }

    // Helper Methods

    private String fixname(String name) {
        if(name==null){
            String temp = id.substring(0,1).toUpperCase() + id.substring(1).toLowerCase();
            temp = temp.replaceAll("_"," ");
            temp =  Arrays.stream(temp.split(" "))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                    .collect(Collectors.joining(" "));
            return temp;
        }else {
            return name;
        }
    }

}
