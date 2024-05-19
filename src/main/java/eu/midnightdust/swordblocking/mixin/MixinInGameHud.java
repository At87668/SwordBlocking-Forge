package eu.midnightdust.swordblocking.mixin;

import eu.midnightdust.swordblocking.config.SwordBlockingConfig;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public abstract class MixinInGameHud {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getOffhandItem()Lnet/minecraft/world/item/ItemStack;"), method = "renderHotbar")
    public ItemStack swordblocking$hideOffHandSlot(AbstractClientPlayer player) {
        ItemStack realStack = player.getOffhandItem();
        if (SwordBlockingConfig.enabled.get() && SwordBlockingConfig.hideOffhandSlot.get() && realStack.getItem() instanceof ShieldItem) {
            return ItemStack.EMPTY;
        } else {
            return realStack;
        }
    }
}
