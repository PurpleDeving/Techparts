package io.purple.techparts.item;

import io.purple.techparts.setup.Register;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

public class BasicItem extends Item {

    private final String id;
    private final String tooltip;
    private final String name;

    public BasicItem(ItemBuilder bitem) {
        super(bitem.properties);
        this.tooltip = bitem.tooltip;
        this.id = bitem.id;
        this.name = fixname(bitem.name);
    }

    public String getId() {
        return id;
    }

    public String getTooltip() {
        return tooltip;
    }

    public String getName() {
        return name;
    }

    // Helper Method

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

    // Basic Item Builder

    public static class ItemBuilder{
        private Properties properties = Register.baseItemProps();
        private String tooltip;
        private String name;

        private String id;

        public ItemBuilder tip(String tip){
            this.tooltip = tip;
            return this;
        }
        public ItemBuilder name(String name){
            this.name = name;
            return this;
        }
        public ItemBuilder id(String id){
            this.id = id;
            return this;
        }

        public ItemBuilder props(Properties props){
            this.properties = props;
            return this;
        }

        public BasicItem build(){
            return new BasicItem(this);
        }

        public String getId() {
            return id;
        }
    }
}
