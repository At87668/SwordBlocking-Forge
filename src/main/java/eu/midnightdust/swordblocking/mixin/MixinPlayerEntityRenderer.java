package eu.midnightdust.swordblocking.mixin;

import eu.midnightdust.swordblocking.SwordBlockingClient;
import eu.midnightdust.swordblocking.config.SwordBlockingConfig;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerEntityRenderer {

    @Inject(at = @At(value = "RETURN"), method = "getArmPose", cancellable = true)
    private void swordblocking$getArmPose(Player player, InteractionHand hand, CallbackInfoReturnable<PlayerModel.ArmPose> cir) {
        if (!SwordBlockingConfig.enabled.get()) {
            return;
        }
        ItemStack handStack = player.getItemInHand(hand);
        ItemStack offStack = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
        if (!SwordBlockingConfig.alwaysHideShield.get() && (handStack.getItem() instanceof ShieldItem) && !SwordBlockingClient.canWeaponBlock(player)) {
            return;
        }

        if (offStack.getItem() instanceof ShieldItem && player.isUsingItem()) {
            cir.setReturnValue(PlayerModel.ArmPose.BLOCK);
        } else if (handStack.getItem() instanceof ShieldItem && SwordBlockingConfig.hideShield.get()) {
            cir.setReturnValue(PlayerModel.ArmPose.EMPTY);
        }
    }
}
