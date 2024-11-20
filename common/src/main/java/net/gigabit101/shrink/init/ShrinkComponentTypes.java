package net.gigabit101.shrink.init;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.gigabit101.shrink.Shrink;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;

public class ShrinkComponentTypes
{
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Shrink.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<Double>> SHRINKING_DEVICE = COMPONENTS.register("scale", () -> DataComponentType.<Double>builder().
            persistent(Codec.DOUBLE.orElse(0D)).networkSynchronized(ByteBufCodecs.DOUBLE).build());

    // TODO: remove legacy component support
    @Deprecated(forRemoval = true)
    public static final RegistrySupplier<DataComponentType<String>> ENTITY = COMPONENTS.register("entity", () -> DataComponentType.<String>builder().
            persistent(Codec.STRING.orElse("")).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    public static final RegistrySupplier<DataComponentType<Component>> ENTITY_NAME = COMPONENTS.register("entity_name", () -> DataComponentType.<Component>builder().
            persistent(ComponentSerialization.CODEC).networkSynchronized(ComponentSerialization.STREAM_CODEC).build());
}
