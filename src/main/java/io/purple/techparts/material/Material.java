package io.purple.techparts.material;

import java.awt.*;

public record Material (String matId, String matName,String matNamePrefix,Color primary,Color secondary, Texture texture){




    public static class MaterialBuilder{
        private String matId;
        private String matName;
        private String matNamePrefix = "";   // Default is empty.
        private Color primaryColor;
        private Color secondaryColor;
        private Texture texture;

        public MaterialBuilder id(String id){
            this.matId = id;
            return this;
        }
        public MaterialBuilder name(String matName){
            this.matName = matName;
            return this;
        }
        public MaterialBuilder prefix(String matNamePrefix){
            this.matNamePrefix = matNamePrefix;
            return this;
        }
        public MaterialBuilder primary(Color primaryColor){
            this.primaryColor = primaryColor;
            return this;
        }
        public MaterialBuilder secondary(Color secondaryColor){
            this.secondaryColor = secondaryColor;
            return this;
        }
        public MaterialBuilder tex(Texture texture){
            this.texture = texture;
            return this;
        }

        public Material build(){
            if(matId == null){
                throw new IllegalArgumentException("Techparts: Missing material id");
            }
            return new Material(matId,matName,matNamePrefix,primaryColor,secondaryColor,texture);
        }

    }

}
