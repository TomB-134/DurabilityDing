package durabilityding;

import durabilityding.events.BlockBreakCallback;
import durabilityding.events.DamagePlayerCallback;
import durabilityding.events.ItemDamageCallback;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class DurabilityDing implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "durabilityding";
    public static final String MOD_NAME = "DurabilityDing";

    private long timeSinceLastWarn;
    public int timeBetweenWarns = 500;

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        //TODO: Initializer

//        ItemDamageCallback.EVENT.register((amount, random, player) -> {
//            log(Level.INFO, "DAMAGED ITEM");
//
//            return ActionResult.PASS;
//        });

        BlockBreakCallback.EVENT.register((world, pos, state, player) -> { //Block break event.
            if (!world.isClient) { //Check if world is server and return if so.
                return ActionResult.PASS;
            }

            ItemStack heldItem = player.getStackInHand(Hand.MAIN_HAND); //Get player's item in main hand.

            if (heldItem.getMaxDamage() == 0) { //If the max damage is equal to zero, then the item used to mine the block has no durability.
                return ActionResult.PASS;
            }

            log(Level.INFO, heldItem.getTag().toString());

            log(Level.INFO, Integer.toString(heldItem.getDamage()));

            log(Level.INFO, "blockbroke");

            return ActionResult.PASS;
        });

        DamagePlayerCallback.EVENT.register((source, amount) -> {
            log(Level.INFO, "DAMAGE TAKEN");
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