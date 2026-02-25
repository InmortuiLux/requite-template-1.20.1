package net.lux.requite.block.custom;

import net.lux.requite.block.ModBlockEntities;
import net.lux.requite.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoulAnchorBlockEntity extends BlockEntity {

    private int ritualTimer = 0;
    private boolean ritualActive = false;
    private PlayerEntity playerRef = null;
    private ItemStack input = ItemStack.EMPTY;

    public SoulAnchorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_ANCHOR_ENTITY, pos, state);
    }

    // Called when player right-clicks the block with Unstable Soul
    public void insertSoul(ItemStack stack, PlayerEntity player) {
        if (!ritualActive && stack.getItem() == ModItems.Soul_unstable) {
            input = stack.copy();
            stack.decrement(1);
            playerRef = player;
            startRitual();
        }
    }

    private void startRitual() {
        ritualActive = true;
        ritualTimer = 40; // 2 seconds

        if (playerRef != null) {
            World world = playerRef.getWorld();
            world.playSound(null, playerRef.getBlockPos(),
                    SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE,
                    SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }

    // This method is now called by a ticker registered in the block
    public void tick() {
        if (!ritualActive) return;

        ritualTimer--;

        world.addParticle(ParticleTypes.SOUL,
                this.pos.getX() + 0.5,
                this.pos.getY() + 1.0,
                this.pos.getZ() + 0.5,
                0.0, 0.1, 0.0);

        if (ritualTimer <= 0) {
            completeRitual();
        }
    }

    private void completeRitual() {
        ritualActive = false;

        if (playerRef != null && !input.isEmpty()) {
            // Give Refined Soul to the player
            playerRef.getInventory().offerOrDrop(new ItemStack(ModItems.Soul_Stable));
            input = ItemStack.EMPTY;
        }
    }
}