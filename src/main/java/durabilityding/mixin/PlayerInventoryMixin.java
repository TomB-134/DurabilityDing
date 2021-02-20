package durabilityding.mixin;

import durabilityding.events.HotbarScrollCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    @Shadow public final PlayerEntity player;

    public PlayerInventoryMixin(PlayerEntity player) {
        this.player = player;
    }

    @Inject(at = @At("TAIL"), method = "scrollInHotbar", cancellable = true) //Inject into scroll hotbar method.
    private void triggerScrollEvent(double scrollAmount, CallbackInfo info) {
        ActionResult result = HotbarScrollCallback.EVENT.invoker().scrollHotBar(scrollAmount, player); //Invoke scroll event.

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
