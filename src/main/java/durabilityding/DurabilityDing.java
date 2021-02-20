package durabilityding;

import durabilityding.events.BlockBreakCallback;
import durabilityding.events.HotbarScrollCallback;
import durabilityding.mixin.PlayerInventoryMixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class DurabilityDing implements ModInitializer {

    public static final Identifier CLICK = new Identifier("durabilityding:click");
    public static SoundEvent CLICK_SOUND_EVENT = new SoundEvent(CLICK);

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "durabilityding";
    public static final String MOD_NAME = "DurabilityDing";

    public int durabilityPointThreshold = 30;

    private int durabilityOnBeginBlockBreak;
    private int durabilityOnEndBlockBreak;

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");

        Registry.register(Registry.SOUND_EVENT, DurabilityDing.CLICK, CLICK_SOUND_EVENT);

        BlockBreakCallback.EVENT.register((world, pos, state, player) -> { //Block break event.
            if (!isValid(world, player)) {
                return ActionResult.PASS;
            }

            durabilityOnEndBlockBreak = player.getStackInHand(Hand.MAIN_HAND).getDamage();

            player.playSound(CLICK_SOUND_EVENT, 1, 1);

            System.out.println("Block Break Event");
            return ActionResult.PASS;
        });

        HotbarScrollCallback.EVENT.register((scrollAmount, player) -> { //Hotbar scroll event.
            System.out.println("Scroll In Hotbar Event");
            System.out.println(player.getStackInHand(Hand.MAIN_HAND).getName());
            return ActionResult.PASS;
        });

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> { //Attack block event.
            if (!isValid(world, player)) {
                return ActionResult.PASS;
            }

            durabilityOnBeginBlockBreak = player.getStackInHand(Hand.MAIN_HAND).getDamage();

            System.out.println("Attack Block Event");
            return ActionResult.PASS;
        });

    }

    private boolean isValid(World world, PlayerEntity playerEntity) {
        if (!world.isClient) {return false;}
        if (playerEntity.getStackInHand(Hand.MAIN_HAND).getMaxDamage() == 0) {return false;}
        return true;
    }

    public static void playSound(PlayerEntity entity) {
        entity.playSound(SoundEvents.ENTITY_VILLAGER_HURT, 1, 1);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}