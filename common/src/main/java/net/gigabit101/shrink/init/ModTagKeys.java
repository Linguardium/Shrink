package net.gigabit101.shrink.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModTagKeys {
    public static TagKey<EntityType<?>> CAPTURING_NOT_SUPPORTED = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("c","capturing_not_supported"));

    public static void init() { }
}
