package com.Cicadellidea.alwaysasleep;


import com.Cicadellidea.alwaysasleep.tracker.BedTracker;
import com.mojang.logging.LogUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AlwaysAsleep.MODID)
public class AlwaysAsleep
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "alwaysasleep";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    public AlwaysAsleep()
    {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new BedTracker());




        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us


    }





    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent

}
