package io.purple.techparts.material;

public interface MatPartCombo {

    public Material getMaterial();
    public Parts getPart();

    public String getId();

    public String getTooltip(); // TODO - Implement
    public default String getTexPath(){
        return String.join("",getMaterial().getTexture().getID(),"/",getPart().getID());
    };

}
