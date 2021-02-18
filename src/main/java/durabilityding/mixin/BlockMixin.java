package durabilityding.mixin;

import durabilityding.events.BlockBreakCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {

    //Simple hook into block break method that triggers an event.
    @Inject(at = @At("TAIL"), method = "onBreak", cancellable = true) //Inject into block break method.
    private void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo info) {
        ActionResult result = BlockBreakCallback.EVENT.invoker().blockBreak(world,pos,state,player); //Call custom BlockBreakCallback event.

        if (result == ActionResult.FAIL) {
            info.cancel(); //If action result is FAIL do not break the block. Unnecessary for this mod because we aren't doing any logic that would warrant canceling.
        }
    }
}
