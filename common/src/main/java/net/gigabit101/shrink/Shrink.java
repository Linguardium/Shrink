package net.gigabit101.shrink;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.platform.Platform;
import net.creeperhost.polylib.PolyLib;
import net.creeperhost.polylib.config.ConfigBuilder;
import net.fabricmc.api.EnvType;
import net.gigabit101.shrink.api.ShrinkAPI;
import net.gigabit101.shrink.init.ModContainers;
import net.gigabit101.shrink.init.ModItems;
import net.gigabit101.shrink.init.ShrinkComponentTypes;
import net.gigabit101.shrink.items.ItemShrinkBottle;
import net.gigabit101.shrink.network.PacketHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class Shrink
{
    public static final String MOD_ID = "shrink";

    public static ConfigBuilder configBuilder;
    public static ShrinkConfig shrinkConfig;

    public static void init()
    {
        // initialize PolyLib's item components
        PolyLib.initPolyItemData();

        configBuilder = new ConfigBuilder(MOD_ID, Platform.getConfigFolder().resolve(MOD_ID + ".json5"), new ShrinkConfig());
        shrinkConfig = (ShrinkConfig) configBuilder.getConfigData();

        ModItems.CREATIVE_MODE_TABS.register();
        ModItems.ITEMS.register();
        ModContainers.CONTAINERS.register();
        ShrinkComponentTypes.COMPONENTS.register();
        PacketHandler.init();

        if(Platform.getEnv() == EnvType.CLIENT)
        {
            ClientLifecycleEvent.CLIENT_SETUP.register(instance -> ShrinkClient.init());
        }

        InteractionEvent.INTERACT_ENTITY.register((player, entity, hand) ->
        {
            if(!player.getItemInHand(hand).isEmpty() && player.getItemInHand(hand).getItem() == Items.GLASS_BOTTLE)
            {
                if(entity instanceof LivingEntity livingEntity)
                {
                    if(ShrinkAPI.isEntityShrunk(livingEntity))
                    {
                        player.getItemInHand(hand).shrink(1);
                        ItemStack output = ItemShrinkBottle.setContainedEntity(new ItemStack(ModItems.SHRINK_BOTTLE), livingEntity);
                        boolean added = player.getInventory().add(output);
                        if(!added)
                        {
                            ItemEntity itemEntity = new ItemEntity(player.level(), player.blockPosition().getX(), player.blockPosition().getY(), player.blockPosition().getZ(), output);
                            player.level().addFreshEntity(itemEntity);
                            return EventResult.pass();
                        }
                    }
                }
            }
            return EventResult.pass();
        });
    }
}
