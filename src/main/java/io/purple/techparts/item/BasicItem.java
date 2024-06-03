package io.purple.techparts.item;

import io.purple.techparts.setup.Register;
import net.minecraft.world.item.Item;
import io.purple.techparts.TechParts;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BasicItem extends Item {


    private final String id;
    private final String tooltip;
    private final String name;

    public BasicItem(ItemBuilder<?> bitem) {
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
        if (id == null | id == "" ){
            TechParts.LOGGER.info("274 - fixname DEBBUG");
            TechParts.LOGGER.info(this.id);
            TechParts.LOGGER.info(this.name);
            TechParts.LOGGER.info(String.valueOf(this));
        }
        if (name == null) {
            String temp = id.substring(0, 1).toUpperCase() + id.substring(1).toLowerCase();
            temp = temp.replaceAll("_", " ");
            temp = Arrays.stream(temp.split(" "))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                    .collect(Collectors.joining(" "));
            return temp;
        } else {
            return name;
        }
    }

    public interface NameStep {
        ItemBuilder id(String id);
    }

    // Basic Item Builder

    public static class ItemBuilder<T extends ItemBuilder<T>> implements NameStep{
        private Properties properties = Register.baseItemProps();
        private String tooltip;
        private String name;
        private String id;

        public ItemBuilder tip(String tip) {
            this.tooltip = tip;
            return this;
        }

        public ItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ItemBuilder props(Properties props) {
            this.properties = props;
            return this;
        }

        public BasicItem build() {
            return new BasicItem(this);
        }

        // Exception for now. TODO - Can remove this somehow ?
        public String getId() {
            return id;
        }
    }
}
