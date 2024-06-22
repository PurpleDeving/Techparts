package io.purple.techparts.material;

import java.awt.*;
import java.util.*;

public record Material (String matId, String matName,Color primary,Color secondary, Texture texture, LinkedList<Part> parts){

    public static class Builder {
        private String matId;
        private String matName;
        private Color primaryColor;
        private Color secondaryColor;
        private Texture texture;
        private LinkedList<Part> parts = new LinkedList<>();

        public Builder(String matId){
            this.matId = matId;
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

        public Builder primary(int i) {
            this.primaryColor = new Color(i);
            return this;
        }

        public Builder secondary(int i) {
            this.secondaryColor = new Color(i);
            return this;
        }

        public Builder parts(EnumSet<Part> enumSet) {
            this.parts.addAll(enumSet);
            return this;
        }

        public Builder parts(Part... parts){
            this.parts.addAll(Arrays.stream(parts).toList());
            return this;
        }

        public Material build(){
            if(matId == null){
                throw new IllegalArgumentException("Techparts: Missing material id");
            }
            return new Material(matId,matName,primaryColor,secondaryColor,texture,parts);
        }


    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material)) {
            return false;
        }
        Material material = (Material) o;
        return Objects.equals(matId, material.matId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(matId);
    }


}
