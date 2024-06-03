package io.purple.techparts.block;

import io.purple.techparts.item.BasicItem;
import io.purple.techparts.setup.Register;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BasicBlock extends Block {



    protected BasicBlock(BlockBuilder<?> blockBuilder) {
        super(blockBuilder.properties);
    }


    public static class BlockBuilder<T extends BlockBuilder<T>>{
        private BlockBehaviour.Properties properties = Register.baseBlockProps();
        private String tooltip;
        private String name;
        private String id;

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
