package durabilityding.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public interface ItemDamageCallback {
    Event<ItemDamageCallback> EVENT = EventFactory.createArrayBacked(ItemDamageCallback.class,
            (listeners) -> (amount, random, player) -> {
                for (ItemDamageCallback event : listeners) {
                    ActionResult result = event.damageItem(amount, random, player);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;

            }
    );

    ActionResult damageItem(int amount, Random random, @Nullable ServerPlayerEntity player);
}
