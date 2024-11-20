package net.gigabit101.shrink.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.gigabit101.shrink.init.ShrinkComponentTypes;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ShrinkComponentUtils {
    private static final List<String> IGNORED_ENTITY_TAGS = Arrays.asList(
            "Pos",
            "SleepingX",
            "SleepingY",
            "SleepingZ",
            "Passengers",
            "leash",
            "Motion",
            "Rotation",
            "FallDistance",
            "Fire",
            "Air"
    );
    private static final MapCodec<String> ENTITY_TYPE_ID_MAPCODEC = Codec.STRING.fieldOf("id");
    private static final MapCodec<EntityType<?>> ENTITY_TYPE_MAPCODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");

    // TODO: remove legacy component support
    public static void convertLegacyDataComponent(ItemStack stack) {

        @SuppressWarnings("removal") DataComponentType<String> LEGACY_COMPONENT_TYPE = ShrinkComponentTypes.ENTITY.get();
        if (LEGACY_COMPONENT_TYPE == null || !stack.has(LEGACY_COMPONENT_TYPE)) return;
        String id = stack.get(LEGACY_COMPONENT_TYPE);
        if (!stack.has(DataComponents.ENTITY_DATA) && id != null && !id.isBlank()) {
            CompoundTag tag = new CompoundTag();
            tag.putString("id", id);
            stack.set(DataComponents.ENTITY_DATA, CustomData.of(tag));
        }
        stack.remove(LEGACY_COMPONENT_TYPE);
    }

    public static Optional<ResourceLocation> getEntityTypeId(ItemStack stack) {
        return getEntityData(stack).read(ENTITY_TYPE_ID_MAPCODEC).result().map(ResourceLocation::tryParse);
    }

    public static Optional<EntityType<?>> getEntityType(ItemStack stack) {
        return getEntityData(stack).read(ENTITY_TYPE_MAPCODEC).result();
    }

    public static CustomData getEntityData(ItemStack stack) {
        return stack.getOrDefault(DataComponents.ENTITY_DATA,CustomData.EMPTY);
    }
    public static CompoundTag stripTag(CompoundTag tag) {
        IGNORED_ENTITY_TAGS.forEach(tag::remove);
        return tag;
    }
}
