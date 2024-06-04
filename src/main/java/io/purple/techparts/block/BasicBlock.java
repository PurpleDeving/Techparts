package io.purple.techparts.block;

import io.purple.techparts.REF;
import io.purple.techparts.TechParts;
import io.purple.techparts.item.BasicItem;
import io.purple.techparts.material.Material;
import io.purple.techparts.setup.Register;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BasicBlock extends Block {

    private final String tooltip;
    private final String name;
    private final String id;


    public BasicBlock(BlockBuilder<?> blockBuilder) {
        super(blockBuilder.properties);
        this.tooltip = blockBuilder.tooltip;
        this.id = blockBuilder.id;
        this.name = fixname(blockBuilder.name);
    }

    public String getId() {
        return id;
    }

    public String getTexPath(){
        return String.join("", "block/basic/",getId());
    }

    // TODO - Idk yet what to do with this
    public String getClearName() {
        return name;
    }

    public String getTooltip() {
        return tooltip;
    }

    // Helper Method

    private String fixname(String name) {
        TechParts.LOGGER.info("289-1" + id);
        TechParts.LOGGER.info(name);
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

    // Builder

    public static class BlockBuilder<T extends BlockBuilder<T>>{
        private BlockBehaviour.Properties properties = Register.baseBlockProps();
        private String tooltip;
        private String name;
        private String id;
        private BlockItem blockItem;

        public BlockBuilder tip(String tip){
            this.tooltip = tip;
            return this;
        }
        public BlockBuilder name(String name){
            this.name = name;
            return this;
        }
        public BlockBuilder id(String id){
            this.id = id;
            return this;
        }

        public BlockBuilder props(BlockBehaviour.Properties props){
            this.properties = props;
            return this;
        }

        public BasicBlock build(){
            return new BasicBlock(this);
        }

        public String getId() {
            return id;
        }


    }


}
