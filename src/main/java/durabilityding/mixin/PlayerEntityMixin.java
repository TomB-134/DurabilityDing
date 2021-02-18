package durabilityding.mixin;

import durabilityding.events.DamagePlayerCallback;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(at = @At("TAIL"), method = "applyDamage", cancellable = true)
    private void triggerDamageEvent(DamageSource source, float amount, CallbackInfo info) {
        ActionResult result = DamagePlayerCallback.EVENT.invoker().damagePlayer(source, amount);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
