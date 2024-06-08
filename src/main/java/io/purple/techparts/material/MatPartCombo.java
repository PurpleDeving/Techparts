package io.purple.techparts.material;

import java.util.ArrayList;

public interface MatPartCombo {

    Material getMaterial();
    Parts getPart();

    String getId();

    String getTooltip(); // TODO - Implement
    default String getTexPath(){
        return String.join("",getMaterial().getTexture().getID(),"/",getPart().getID());
    };



}
