package durabilityding.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface HotbarScrollCallback {
    Event<HotbarScrollCallback> EVENT = EventFactory.createArrayBacked(HotbarScrollCallback.class,
            (listeners) -> (scrollAmount, player) -> {
                for (HotbarScrollCallback event : listeners) {
                    ActionResult result = event.scrollHotBar(scrollAmount, player);
                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;

            }
    );

    ActionResult scrollHotBar(double scrollAmount, PlayerEntity player);
}
