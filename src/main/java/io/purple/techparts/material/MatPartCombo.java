package io.purple.techparts.material;

public interface MatPartCombo {

    Material getMaterial();
    Part getPart();
    String getId();

    String getTexPath(); // Different for blocks and items, don't combine this


}
