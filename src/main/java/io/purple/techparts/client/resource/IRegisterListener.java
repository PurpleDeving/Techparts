package io.purple.techparts.client.resource;

import net.minecraft.resources.ResourceLocation;

public interface IRegisterListener {
    default void onPostRegistered() {
    }

    default void onPreRegistered() {
    }

    default void onPostRegistered(ResourceLocation id) {
        onPostRegistered();
    }

    default void onPreRegistered(ResourceLocation id) {
        onPreRegistered();
    }
}
