package net.lux.requite;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class DelayedTeleportManager {

    private static final Map<UUID, TeleportData> pendingTeleports = new HashMap<>();

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            Iterator<Map.Entry<UUID, TeleportData>> iterator = pendingTeleports.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<UUID, TeleportData> entry = iterator.next();
                TeleportData data = entry.getValue();

                data.ticks--;

                if (data.ticks <= 0) {
                    ServerPlayerEntity player = server.getPlayerManager().getPlayer(entry.getKey());

                    if (player != null) {
                        ServerWorld targetWorld = server.getWorld(data.dimension);

                        if (targetWorld != null) {
                            // Teleport first
                            player.teleport(
                                    targetWorld,
                                    data.pos.getX() + 0.5,
                                    data.pos.getY(),
                                    data.pos.getZ() + 0.5,
                                    player.getYaw(),
                                    player.getPitch()
                            );

                            // Play the teleport sound at the player's new position
                            targetWorld.playSound(
                                    null, // null = plays for all nearby players
                                    player.getX(), player.getY(), player.getZ(), // coordinates
                                    SoundEvents.ENTITY_ENDERMAN_TELEPORT, // sound
                                    SoundCategory.PLAYERS, // category
                                    1.0f, // volume
                                    1.0f  // pitch
                            );
                        }
                    }

                    iterator.remove();
                }
            }
        });
    }

    public static void scheduleTeleport(ServerPlayerEntity player, RegistryKey<World> dimension, BlockPos pos, int delayTicks) {
        pendingTeleports.put(player.getUuid(), new TeleportData(dimension, pos, delayTicks));
    }

    private static class TeleportData {
        RegistryKey<World> dimension;
        BlockPos pos;
        int ticks;

        TeleportData(RegistryKey<World> dimension, BlockPos pos, int ticks) {
            this.dimension = dimension;
            this.pos = pos;
            this.ticks = ticks;
        }
    }
}