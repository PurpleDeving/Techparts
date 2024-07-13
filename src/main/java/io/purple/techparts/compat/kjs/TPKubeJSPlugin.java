package io.purple.techparts.compat.kjs;

import dev.latvian.mods.kubejs.bindings.event.StartupEvents;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.ServerRegistryRegistry;
import dev.latvian.mods.kubejs.script.ScriptManager;
import dev.latvian.mods.kubejs.script.ScriptType;
import io.purple.techparts.material.Material;

public class TPKubeJSPlugin implements KubeJSPlugin {



    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(TPKubeEvent.GROUP);
    }


}
