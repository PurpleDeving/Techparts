package io.purple.techparts.setup;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.purple.techparts.REF;
import io.purple.techparts.TechParts;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Consumer;

import static io.purple.techparts.item.TechPartItems.SAPPHIRE;

@Mod.EventBusSubscriber(modid = REF.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabs {

    private static final Registrate REGISTRATE = TechParts.registrate();

    public static final ResourceKey<CreativeModeTab> MAIN = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(REF.ID, "main"));
    public static final RegistryEntry<CreativeModeTab> MAIN_TAB = createTab(MAIN, "main", "Ender IO",
            tab -> tab.icon(() -> new ItemStack(SAPPHIRE.get())).withTabsBefore(CreativeModeTabs.SPAWN_EGGS));

    private static RegistryEntry<CreativeModeTab> createTab(ResourceKey<CreativeModeTab> key, String name, String english, Consumer<CreativeModeTab.Builder> config) {
        return REGISTRATE.generic(name, Registries.CREATIVE_MODE_TAB, () -> {
            var builder = CreativeModeTab.builder().title(REGISTRATE.addLang("itemGroup", key.location(), english));
            config.accept(builder);
            return builder.build();
        }).register();
    }

    public static void register(){

    }
}
