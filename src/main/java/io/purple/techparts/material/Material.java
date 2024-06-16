package io.purple.techparts.material;

import java.awt.*;

public record Material (String matId, String matName,Color primary,Color secondary, Texture texture){

    public static class Builder {
        private String matId;
        private String matName;
        private Color primaryColor;
        private Color secondaryColor;
        private Texture texture;

        public Builder(){

        }


        public Builder id(String id){
            this.matId = id;
            return this;
        }
        public Builder name(String matName){
            this.matName = matName;
            return this;
        }
        public Builder primary(Color primaryColor){
            this.primaryColor = primaryColor;
            return this;
        }
        public Builder secondary(Color secondaryColor){
            this.secondaryColor = secondaryColor;
            return this;
        }
        public Builder tex(Texture texture){
            this.texture = texture;
            return this;
        }

        public Material build(){
            if(matId == null){
                throw new IllegalArgumentException("Techparts: Missing material id");
            }
            return new Material(matId,matName,primaryColor,secondaryColor,texture);
        }

    }

}
