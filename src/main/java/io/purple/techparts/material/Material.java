package io.purple.techparts.material;


import static io.purple.techparts.material.Texture.*;

public enum Material {

    //TODO: Give a look at colors: They may be ARBG and not RBGA

    /*
        VANILLA
     */

    GOLD(0xfdf55f,0xf25833,"gold",SHINY), //0xffe650

    /*
        CUTOFF of used Materials


    CHARCOAL(0x644646,"charcoal",LIGNITE),
    GLOWSTONE(0xffff00,"glowstone",SHINY),
    IRON(0xa19d94,"iron",METALLIC),
    COAL(0x131212,"coal",LIGNITE),
    QUARTZ(0xffffff,"quartz",Texture.QUARTZ),
    COPPER(0xB87333,"copper", SHINY),
    REDSTONE(0xB87333,"redstone", SHINY),
    NETHERITE(0x353935,"netherite", LIGNITE), //TODO: Does Lignite have the needed texture ?*/

    /*
        Common Mod Materials


    CERTUS_QUARTZ(0x55FFFF, "certus_quartz",Texture.QUARTZ),
    CINNABAR(0x960000,"cinnabar",ROUGH),
    SULFUR(0xc8c800,"sulfur",DULL),

    OBSIDIAN(0x503264,"obsidian",DULL),
    REFINED_OBSIDIAN(0x503264,"refined_obsidian",DULL),

    //START OF ALL
    BRASS(0xffb400,"brass"),
    SILVER(0xdcdcff, "silver",SHINY),
    RED_ALLOY(0xc80000,"red_alloy",DULL,"Red Alloy"),
    LEAD(0x8c648c,"lead",DULL),
    MANASTEEL(0x0063FF,"manasteel"),
    CONSTANTAN(0xf6d898,"constantan"),
    NICKEL(0xc8c8fa,"nickel" ,METALLIC),
    OSMIUM(0x3232ff,"osmium" ,METALLIC),

    INVAR(0xb4b478,"invar",METALLIC), */
    /*
        Rare Mod Materials

    IRIDIUM(0xf0f0f5,"iridium",DULL),
     */


    /*
        To Sort







    ALUMINIUM(0x80c8f0,"aluminium",DULL),
    BERYLIUM(0x64b464,"berylium",METALLIC),
    BISMUTH(0x64a0a0,"bismuth",METALLIC),
    CARBON(0x141414,"carbon",DULL),
    CHROME(0xffe6e6,"chrome",SHINY),
    COBALT(0x5050fa,"cobalt",METALLIC),

    MAGANESE(0xfafafa,"maganese" ,DULL),
    MOLYBDENUM(0xb4b4dc,"molybdenum" ,SHINY),
    Neodymium(0x646464,"neodymium" ,METALLIC),
    NEUTRONIUM(0xfafafa,"neutronium" ,DULL),

    PALLADIUM(0x808080,"palladium" ,SHINY),
    PLATINUM(0xffffc8,"platinum" ,SHINY),
    PLUTONIUM(0xf03232,"plutonium",METALLIC),
    THORIUM(0x001e00,"thorium",SHINY),
    TITANIUM(0xdca0f0,"titanium",METALLIC),
    TUNGSTEN(0x323232,"tungsten",METALLIC),
    URANIUM(0x32f032,"uranium",METALLIC),
    Graphite(0x808080,"graphite",DULL),
    AMERICIUM(0xc8c8c8,"americium",METALLIC),
    ANTIMONY(0xdcdcf0,"antimony",SHINY),
    ARGON(0xff00f0,"argon",SHINY),
    ARSENIC(0xffffff,"arsenic",DULL),
    LANTHANUM(0xffffff,"lanthanum",METALLIC),
    BARIUM(0xffffff,"barium",METALLIC),
    CERIUM(0xffffff,"cerium",SHINY),
    BORON(0xfafafa,"boron",DULL),
    CALCIUM(0xfff5f5,"calcium",METALLIC),
    CADIUM(0x32323c,"cadium",SHINY),
    CHLORINE(0x00ffff,"chlorine",SHINY),
    GALLIUM(0xdcdcff,"gallium",SHINY),
    INDIUM(0x400080,"indium",METALLIC),
    LITHIUM(0xe1dcff,"lithium",DULL),
    MAGNESIUM(0xffc8c8,"magnesium",METALLIC),
    MERCURY(0xffdcdc,"mercury",SHINY),
    NIOBIUM(0xbeb4c8,"niobium",METALLIC),
    PHOSPHOR(0xffff00,"phosphor",DULL),
    POTASSIUM(0xfafafa,"potassium",METALLIC),
    SILICON(0x3c3c50,"silicon",METALLIC),
    SODIUM(0x000096,"sodium",METALLIC),

    TIN(0xdcdcdc,"tin",DULL),
    TRITIUM(0xff0000,"tritium",METALLIC),
    VANADIUM(0x323232,"vanadium",METALLIC),
    YTTRIUM(0xdcfadc,"yttrium",METALLIC),
    ZINC(0xfaf0f0,"zinc",METALLIC),



    GRAPHENE(0x808080,"graphene",DULL),
    RARE_EARTH(0x808064,"rare_earth",FINE),
    ALMANDINE(0xff0000,"almandine",ROUGH),
    ANDRADITE(0x967800,"andradite",ROUGH),
    BRANDED_IRON(0x967800,"branded_iron",DULL,"Branded Iron"),
    BROWNLIMONITE(0xc86400,"brownlimonite",METALLIC),
    CALCITE(0xfae6dc,"calcite",DULL),
    CLAY(0xc8c8dc,"clay",ROUGH),
    COBALITE(0x5050fa,"cobalite",METALLIC),
    COOPERITE(0xffffc8,"cooperite",METALLIC),
    DARKASH(0x323232,"dark_ash",METALLIC),
    GALENA(0x643c64,"galena",DULL),
    GARNIERITE(0x32c846,"garnierite",METALLIC),
    GROSSULAR(0xc86400,"grossular",ROUGH),
    ILMENITE(0x463732,"ilmenite",METALLIC),
    RUTILE(0xd40d5c,"rutile",GEM_H),
    MAGNESIUM_CHLORITE(0xd40d5c,"magnesium_chloride",DULL,"Magnesium Chloride"),
    MAGNESITE(0xfafab4,"magnesite",METALLIC),
    MAGNETITE(0x1e1e1e,"magnetite",METALLIC),
    MOLYDENITE(0x91919,"molybdenite",METALLIC),

    PHOSPHATE(0xffff00,"phosphate",DULL),
    POLYDIMETHYLSILOXANE(0xf5f5f5,"polydimethylsiloxane",DULL),
    PYRITE(0x967828,"pyrite",ROUGH),
    PYROLUSITE(0x967828,"pyrolusite",DULL),
    PYROPE(0x783264,"pyrope",METALLIC),
    RUBBER(0xccc789,"rubber",DULL),
    SALTPETER(0xe6e6e6,"saltpeter",FINE),
    SCHNEELITE(0xc88c14,"scheelite",DULL),
    MASSICOT(0xffdd55,"massicot",DULL),
    ARSENICTRIOXIDE(0xffffff,"arsenic_trioxide",SHINY),
    COBALTOXIDE(0x668000,"cobalt_oxide",DULL),
    MAGNESIA(0xffffff,"magnesia",DULL),
    QUICKLIME(0xf0f0f0,"quicklime",DULL),
    POT_ASH(0x784237,"pot_ash",DULL),
    SODA_ASH(0xdcdcff,"soda_ash",DULL),
    BRICK(0x9b5643,"brick",ROUGH),
    FIRECLAY(0xada09b,"fireclay",ROUGH),
    SPHALERITE(0xffffff,"sphalerite",DULL),
    STIBNITE(0x464646,"stibnite",METALLIC),
    TETRAHEDRITE(0xc82000,"tetrahedrite",DULL),
    TUNGSTATE(0x373223,"tungstate",DULL),
    URANITE(0x232323,"uraninite",METALLIC),
    YELLOWLIMONITE(0xc8c800,"yellow_limonite",METALLIC,"Yellow Limonite"),
    BLAZE(0xffc800,"blaze",METALLIC),
    POTASSIUMFELDSPAR(0x782828,"potassium_feldspar",FINE),
    BASTNASITE(0xc86e2d,"bastnasite",FINE),
    PENTLANDITE(0xa59605,"pentlandite",DULL),
    MALACHITE(0x055f05,"malachite",DULL),
    BARITE(0xe6ebff,"barite",DULL),
    TALC(0x5ab45a,"talc",DULL),
    SOAPSTONE(0x5f915f,"soapstone",DULL),
    CONCRETE(0x646464,"concrete",ROUGH),*/


    /**
     * Gems
     **/

    //DILITHIUM(0xfffafa,"dilithium",DIAMOND),
    //NETHER_STAR(0xffffff,"nether_star",DIAMOND,"Nether Star"),

    /**
     * Gems BRITTLE
     **/
    /*
    BLUE_TOPAZ(0x0000ff,"blue_topaz",GEM_H),

    CoalCoke(0x8c8caa,"coal_coke",LIGNITE),
    LIGNITE_COKE(0x8c6464,"lignite_coke",LIGNITE,"Lignite Coke"),
    GREEN_SAPPHIRE(0x64c882,"green_sapphire",GEM_H),
    LAZURITE(0x6478ff,"lazurite",LAPIS),
    RED_RUBY(0xff6464,"red_ruby",RUBY,"Red Ruby"),
    BLUE_SAPPHIRE(0x6464c8,"blue_sapphire",GEM_V),
    SODALITE(0x1414ff,"sodalite",LAPIS),
    TANZANITE(0x4000c8,"tanzanite",GEM_V),
    YELLOW_TOPAZ(0xff8000,"topaz",GEM_H,"Yellow Topaz"),
    GLASS(0xfafafa,"glass",SHINY),
    OBLIVE(0x96ff96,"olivine",RUBY),
    OPAL(0x0000ff,"opal",RUBY),
    AMETHYST(0xd232d2,"amethyst",RUBY),
    PHOSPHORUS(0xffff00,"phosphorus",FLINT),
    RED_GARNET(0xc85050,"red_garnet",RUBY,"Red Garnet"),
    YELLOW_GARNET(0xc8c850,"yellow_garnet",RUBY,"Yellow Garnet"), */

    /*


     */




    /*
        METALS

    BATTERYALLOY(0x9c7ca0,"battery_alloy",DULL),
    BRONZE(0xff8000,"bronze",METALLIC),
    CUPRONICKEL(0xe39680,"cupronickel",METALLIC),
    Electrum(0xffff64,"electrum",SHINY),
    KANTHAL(0xc2d2df,"kanthal",METALLIC),
    MAGNALIUM(0xc8beff,"magnalium",DULL),
    NICHROME(0xcdcef6,"nichrome",METALLIC),
    SOLDERING_ALLOY(0xdcdce6,"soldering_alloy",DULL),
    STEEL(0x808080,"steel",METALLIC),
    STAINLESS_STEEL(0xc8c8dc,"stainless_steel",SHINY),
    WROUGH_IRON(0xc8b4b4,"wrought_iron",METALLIC,"Wrough Iron"),
    STERLING_SILVER(0xfadce1,"sterling_silver",SHINY,"Sterling Silver"),
    ROSE_GOLD(0xffe61e,"rose_gold",SHINY,"Rose Gold"),
    BLACK_BRONZE(0x64327d,"black_bronze",DULL,"Black Bronze"),
    BISMUTH_BRONZE(0x647d7d,"bismuth_bronze",DULL,"Bismuth Bronze"),
    BLACK_STEEL(0x646464,"black_steel",METALLIC,"Black Steel"),
    RED_STEEL(0x8c6464,"red_steel",METALLIC,"Red Steel"),
    BLUE_STEEL(0x64648c,"blue_steel",METALLIC,"Blue Steel"),
    TUNGSTEN_STEEL(0x6464a0,"tungsten_steel",METALLIC,"Tungsten Steel"),

    COBALT_BRASS(0xb4b4a0,"cobalt_brass",METALLIC,"Cobalt Brass"),
    MAGNETIC_IRON(0xc8c8c8,"magnetic_iron",METALLIC,"Magnetic Iron"),
    MAGNETIC_STEEL(0xc8c8c8,"magnetic_steel",METALLIC,"Magnetic Steel"),

    HSSG(0x999900,"hssg",METALLIC,"HSSG"),
    HSSE(0x336600,"hsse",METALLIC,"HSSE"),
    HSSS(0x660033,"hsss",METALLIC,"HSSS"),

    OSMIRIDIUM(0x6464ff,"osmiridium",METALLIC),
    DURANIUM(0xffffff,"duranium",METALLIC),
    NAQUADAH(0x323232,"naquadah",METALLIC),
    NAQUADAH_ALLOY(0x282828,"naquadah_alloy",METALLIC,"Naquadah Alloy"),
    NAQUADAH_ENRICHED(0x323232,"enriched_naquadah",SHINY,"Enriched Naquadah"),

    VIBRANIUM(0x00ffff,"vibranium",SHINY),
*/
    /*
        PLASTICS


    POLYETHYLENE(0xc8c8c8,"polyethylene",DULL),
    SILICON_RUBBER(0x9fadbb,"silicon_rubber",NONE,"Silicon-Rubber"),
    EPOXID(0xc88c14,"epoxid",DULL),
    SILICONE(0xdcdcdc,"silicone",DULL),
    POLYTYRENE(0xbeb4aa,"polystyrene",DULL),
    EPOXID_FIBER(0xa07010,"fiber_reinforced_epoxy_resin",DULL,"Fiber Reinforced Epoxy"),
  */
    /*
        SOLIDS


    RED_GRANITE(0xff0080,"red_granite",ROUGH,"Red Granite"),
    BLACK_GRANITE(0x0a0a0a,"black_granite",ROUGH,"Black Granite"),
    MARBLE(0xc8c8c8,"marble",NONE),
    KOMATITE(0xbebe69,"komatiite",NONE),
    LIMESTONE(0xe6c882,"limestone",NONE),
    GREEN_SCHIST(0x69be69,"green_schist",NONE,"Greenschist"),
    BLUE_SCHIST(0x0569be,"blue_schist",NONE,"Blueschist"),
    KIMBERLITE(0x64460a,"kimberlite",NONE),
    QUARTZITE(0xe6cdcd,"quartzite",Texture.QUARTZ),
    //ALL STOPS HERE
    */

    /*
        LIQUIDS
     */
    GLUE(255,22, "glue",NONE);
    private final int mainColor;
    private final int secondColor;
    private final int overlaycolor;
    private final String id;
    private final String name;

    private final Texture texture;


    Material(int mainColor, int secondcolor, String id, Texture tex){
        this(mainColor,secondcolor,id, tex,id.substring(0,1).toUpperCase() + id.substring(1).toLowerCase());
    }

    Material(int alpha, int red, String id, Texture tex, String name){
        this.mainColor = alpha;
        this.secondColor = red;
        this.overlaycolor = 0x80404040;
        this.id = id;
        this.texture = tex;
        this.name = name;
    }

    // TODO - How does GT do Tintindex > Also look at default color ?

    public final int getRbg(int tintindex) {
        switch (tintindex) {
            case 0:
                return mainColor;
            case 1:
                return secondColor;
            case 3:
                return overlaycolor;
            default:
                return 0x80404040; // Might need to be changed to 0xFFFFFF
        }
    }

    public String getName() {
        return name;
    }

    public final String getID()
    {
        return this.id;
    }

    public Texture getTexture() {
        return this.texture;
    }
}
