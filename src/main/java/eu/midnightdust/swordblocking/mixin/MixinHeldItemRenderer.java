package eu.midnightdust.swordblocking.mixin;

import eu.midnightdust.swordblocking.SwordBlockingClient;
import eu.midnightdust.swordblocking.config.SwordBlockingConfig;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
@OnlyIn(Dist.CLIENT)
public abstract class MixinHeldItemRenderer {

    @Inject(at = @At("HEAD"), cancellable = true, method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public void swordblocking$hideShield(LivingEntity entity, ItemStack stack, ItemTransforms.TransformType renderMode, boolean leftHanded, MultiBufferSource vertexConsumers, int light, CallbackInfo ci) {
        if (SwordBlockingConfig.alwaysHideShield.get() && SwordBlockingConfig.hideShield.get() && stack.getItem() instanceof ShieldItem) {
            ci.cancel();
        } else if (SwordBlockingConfig.hideShield.get() && stack.getItem() instanceof ShieldItem && SwordBlockingClient.canWeaponBlock(entity)) {
            ci.cancel();
        }
    }
}
