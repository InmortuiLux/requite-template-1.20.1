package net.lux.requite.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.lux.requite.Requite;
import net.lux.requite.block.custom.SoulAnchorBlockEntity;

public class ModBlockEntities {
    public static BlockEntityType<SoulAnchorBlockEntity> SOUL_ANCHOR_ENTITY;

    public static void registerBlockEntities() {
        SOUL_ANCHOR_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Requite.MOD_ID, "soul_anchor"),
                FabricBlockEntityTypeBuilder.create(SoulAnchorBlockEntity::new, ModBlocks.Soul_anchor).build(null)
        );
    }
}