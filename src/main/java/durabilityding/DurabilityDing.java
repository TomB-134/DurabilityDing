package durabilityding;

import durabilityding.events.BlockBreakCallback;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class DurabilityDing implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "durabilityding";
    public static final String MOD_NAME = "DurabilityDing";

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        //TODO: Initializer

        BlockBreakCallback.EVENT.register((world, pos, state, player) -> {
            System.out.println("Stupid player just broke block yippie");
            return ActionResult.PASS;
        });
    }

    public static void playSound(PlayerEntity entity) {
        entity.playSound(SoundEvents.ENTITY_VILLAGER_HURT, 1, 1);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}