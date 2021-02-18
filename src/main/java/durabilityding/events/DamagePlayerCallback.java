package durabilityding.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface DamagePlayerCallback {
    Event<DamagePlayerCallback> EVENT = EventFactory.createArrayBacked(DamagePlayerCallback.class,
            (listeners) -> (source, amount) -> {
                for (DamagePlayerCallback event : listeners) {
                    ActionResult result = event.damagePlayer(source, amount);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;

            }
    );

    ActionResult damagePlayer(DamageSource source, float amount);
}
