package net.gigabit101.shrink;

import net.gigabit101.shrink.client.KeyBindings;
import net.gigabit101.shrink.config.ShrinkConfig;
import net.gigabit101.shrink.events.RenderEvents;
import net.gigabit101.shrink.items.ShrinkItems;
import net.gigabit101.shrink.network.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Shrink.MOD_ID)
public class Shrink
{
    public static final String MOD_ID = "shrink";
    public static Shrink INSTANCE;

    public Shrink()
    {
        INSTANCE = this;
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);

        ShrinkItems.ITEMS.register(eventBus);

        ShrinkConfig.loadConfig(ShrinkConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-common.toml"));
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {
        PacketHandler.register();
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new RenderEvents());
        KeyBindings.init();
    }
}
