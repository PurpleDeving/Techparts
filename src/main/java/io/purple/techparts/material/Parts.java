package io.purple.techparts.material;

public enum Parts {

    //Liquid
    LIQUID("liquid"),

    //OREPROCESS
    DUST("dust"),
    DUST_SMALL("dust_small"),
    DUST_IMPURE("dust_impure"),
    DUST_PURE("dust_pure"),
    DUST_TINY("dust_tiny"),
    ORE_RAW("raw_ore"),
    ORE_CRUSHED("crushed_ore"),
    ORE_CENTRIFUGED("crushed_centrifuged"),
    ORE_PURIFIED("crushed_purified"),
    ORE_CHUNK("chunk"),

    ORE_CLUMP("clump"),
    CRYSTAL("crystal"),

    //DONE INGOT
    INGOT("ingot"),
    INGOT_HOT("ingot_hot"),
    NUGGET("nugget"),
    BLOCK("block"),


    //PARTS
    PLATE("plate"),
    ROD("rod"),
    RING("ring"),
    FOIL("foil"),
    BOLT("bolt"),
    SCREW("screw"),
    GEAR("gear"),
    GEAR_SMALL("gear_small"),
    WIRE("wire_fine"),
    ROTOR("rotor"),
    PLATE_DENSE("plate_dense"),
    FRAME("frame"),

    //TOOLS
    TOOL_BUZZSAW("buzzsaw_blade","Buzzsaw Blade"),
    TOOL_CHAINSAW("chainsaw_bit","Chainsaw Bit"),
    TOOL_DRILL("drill_bit","Drill Bit"),
    CELL("cell"),

    //GEM
    GEM("gem"),
    GEM_BRITTLE("gem_brittle"),
    GEM_CHIPPED("gem_chipped"),
    GEM_EXQUISITE("gem_exquisite"),
    GEM_FLAWED("gem_flawed"),
    GEM_FLAWLESS("gem_flawless"),
    GEM_POLISHED("gem_polished"),
    LENS("lens");







    private final String id;
    private final String name;

    Parts(String id) {
        this.id = id;
        this.name = genName(id);
    }


    Parts(String id,String name){
        this.id = id;
        this.name = name;
    }

    private String genName(String id){
        String[] words = id.split("_");
        StringBuilder nameBuilder = new StringBuilder();
        for (String word : words) {
            nameBuilder.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return nameBuilder.toString().trim();
    }

    // TODO - Fix name with pre + suffix

    public final String getID(){
        return this.id;
    }

    @Override
    public String toString() {
        return id;
    }

    public String getName() {
        return name;
    }
}
