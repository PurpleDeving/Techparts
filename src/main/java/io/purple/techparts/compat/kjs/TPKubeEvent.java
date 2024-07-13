package io.purple.techparts.compat.kjs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface TPKubeEvent {
    EventGroup GROUP = EventGroup.of("TechParts");
    EventHandler CREATE_MATERIALS = GROUP.server("createmats", () -> CreateMaterialEventJS.class);

}
