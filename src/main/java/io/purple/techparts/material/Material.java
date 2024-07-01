package io.purple.techparts.material;

import com.mojang.logging.LogUtils;
import io.purple.techparts.fluid.FluidBuilder;
import org.slf4j.Logger;

import java.awt.*;
import java.util.*;

public record Material (String matId, String matName, Color primary, Color secondary, Texture texture, LinkedList<Part> parts,
                        Boolean glow, FluidBuilder fluidBuilder){

    private static final Logger LOGGER = LogUtils.getLogger();

    public static class Builder {
        private final String matId;
        private String matName;
        private Color primaryColor;
        private Color secondaryColor;
        private Texture texture;
        private final LinkedList<Part> parts = new LinkedList<>();
        private final FluidBuilder fluidBuilder = new FluidBuilder();
        private Boolean glow = false;

        public Builder(String matId){
            this.matId = matId;
            this.fluidBuilder.fluidId(matId+"_fluid");
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

            /*
                A Group of last checks to guarantee that important things exist.
             */

            if(primaryColor == null){
                LOGGER.info("Material: " + matId + " has no set primaryColor. One is assigned at random.");
                Random rand = new Random();
                primaryColor = new Color(rand.nextInt(0xFFFFFF + 1));
            }

            // Fluid Color fix if needed.
            if (fluidBuilder.getColor() == -1){
                fluidBuilder.color(this.primaryColor.getRGB());
            }


            if (parts.contains(Part.LIQUID) || parts.contains(Part.GAS)){
                LOGGER.info("TEST 6 - Mat has Liquid or Gas");
                liquidFixes();
            }






            return new Material(matId,matName,primaryColor,secondaryColor,texture,parts,glow,fluidBuilder);
        }

        /*****************************

         Shared Attributes (between Normal and Fluid Parts)

         ****************************/

        public Builder glow(){
            this.glow = true;
            return this;
        }



        /*****************************

         Fluid Part

         ****************************/

        public Builder fluidHot(){
            this.fluidBuilder.hot();
            return this;
        }


        public Builder fluidSlow() {
            // FIXME
            return this;
        }


        public Builder fluidUp() {
            fluidBuilder.goUp();
            return this;
        }

        private void liquidFixes(){
            if (fluidBuilder.getLuminosity() == -1){ // If Luminosity isn't overwritten -> Set it to whats logical
                if (this.glow){
                    fluidBuilder.luminosity(15);
                } else if (fluidBuilder.isHot()) {
                    fluidBuilder.luminosity(7);
                } else {
                    fluidBuilder.luminosity(0);
                }
            }
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
