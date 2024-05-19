package eu.midnightdust.swordblocking;

import eu.midnightdust.swordblocking.config.SwordBlockingConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ShieldItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = SwordBlocking.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwordBlockingClient {

    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SwordBlockingClient::onClientSetup);
        MinecraftForge.EVENT_BUS.register(new SwordBlockingClient());
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        for (Item item : Item.REGISTRY) {
            if (item instanceof SwordItem || item instanceof AxeItem) {
                ItemModelsProperties.registerProperty(item, new ResourceLocation(SwordBlocking.MODID, "blocking"),
                        (stack, world, entity) -> entity != null && isWeaponBlocking(entity) ? 1.0F : 0.0F);
            }
        }
    }

    public static boolean isWeaponBlocking(LivingEntity entity) {
        return (entity.isHandActive() && canWeaponBlock(entity));
    }

    public static boolean canWeaponBlock(LivingEntity entity) {
        return (SwordBlockingConfig.enabled.get() &&
                (entity.getHeldItemMainhand().getItem() instanceof SwordItem || entity.getHeldItemMainhand().getItem() instanceof AxeItem) &&
                entity.getHeldItemOffhand().getItem() instanceof ShieldItem) ||
                ((entity.getHeldItemOffhand().getItem() instanceof SwordItem || entity.getHeldItemOffhand().getItem() instanceof AxeItem) &&
                        entity.getHeldItemMainhand().getItem() instanceof ShieldItem);
    }
}


