package eu.midnightdust.swordblocking.mixin;

import eu.midnightdust.swordblocking.SwordBlockingClient;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedModel.class)
public abstract class MixinBipedEntityModel<T extends LivingEntity> {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/model/BipedModel;setupAnim(Lnet/minecraft/entity/LivingEntity;FFFFF)V", shift = At.Shift.BEFORE), method = "setupAnim")
    private void swordblocking$setBlockingAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (SwordBlockingClient.isWeaponBlocking(livingEntity)) {
            if (livingEntity.getOffhandItem().getItem() instanceof ShieldItem)
                this.poseRightArm(livingEntity);
            else this.poseLeftArm(livingEntity);
        }
    }

    protected abstract void poseRightArm(T entity);

    protected abstract void poseLeftArm(T entity);
}
