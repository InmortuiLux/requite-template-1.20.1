package net.lux.requite.item.custom;

import net.lux.requite.DelayedTeleportManager;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;

import java.util.Optional;

public class DeathReturnItem extends Item {
    public DeathReturnItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        // Server-side only
        if (!world.isClient && user instanceof ServerPlayerEntity serverPlayer) {

            // Get last death position
            Optional<GlobalPos> optional = serverPlayer.getLastDeathPos();

            if (optional.isPresent()) {
                GlobalPos globalPos = optional.get();
                BlockPos pos = globalPos.getPos();
                RegistryKey<World> dimension = globalPos.getDimension();
                ServerWorld targetWorld = serverPlayer.getServer().getWorld(dimension);

                if (targetWorld != null) {

                    // Spawn soul particles immediately for dramatic effect
                    targetWorld.spawnParticles(
                            ParticleTypes.SOUL,
                            serverPlayer.getX(),
                            serverPlayer.getY() + 1,
                            serverPlayer.getZ(),
                            40,     // number of particles
                            0.5,    // offsetX
                            1.0,    // offsetY
                            0.5,    // offsetZ
                            0.02    // speed
                    );

                    // Freeze player slightly with Slowness for 2 seconds (40 ticks)
                    serverPlayer.addStatusEffect(
                            new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 4)
                    );

                    // Optional: Play dramatic soul sound

                    world.playSound(
                            null,
                            serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                            SoundEvents.BLOCK_SOUL_SAND_BREAK,
                            net.minecraft.sound.SoundCategory.PLAYERS,
                            0.5f,
                            1.2f
                    );

                    // Schedule the actual teleport after 40 ticks (2 seconds)
                    DelayedTeleportManager.scheduleTeleport(serverPlayer, dimension, pos, 40);

                    // Consume item (if not in creative)
                    if (!serverPlayer.getAbilities().creativeMode) {
                        stack.decrement(1);
                    }

                    return TypedActionResult.success(stack);
                }

            } else {
                serverPlayer.sendMessage(Text.literal("Your soul has not fractured yet."), true);
            }
        }

        return TypedActionResult.pass(stack);
    }
}