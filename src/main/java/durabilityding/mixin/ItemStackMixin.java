package durabilityding.mixin;

import durabilityding.events.ItemDamageCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(at = @At("TAIL"), method = "damage", cancellable = true)
    public boolean triggerItemDamageCallback (int amount, Random random, @Nullable ServerPlayerEntity player, CallbackInfoReturnable info) {
        ActionResult result = ItemDamageCallback.EVENT.invoker().damageItem(amount, random, player);
        if (result == ActionResult.FAIL) {
            info.cancel();
        }

        return info.getReturnValueZ();
    }
}
