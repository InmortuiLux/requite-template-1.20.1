package net.lux.requite.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.lux.requite.Requite;
import net.lux.requite.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup Soul_Group = Registry.register(Registries.ITEM_GROUP ,
            new Identifier(Requite.MOD_ID,"soul" ),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.soul"))
                    .icon(() -> new ItemStack(ModItems.Soul)).entries((displayContext, entries) -> {
                        entries.add(ModItems.Soul);
                        entries.add(ModItems.Bone_fragment);
                        entries.add(ModItems.Dark_Leather);
                        entries.add(ModBlocks.Dark_stone);
                        entries.add(ModItems.Soul_tether);


                    }).build());



    public static  void registerItemGroups() {
        Requite.LOGGER.info("Registering Item Groups for "+Requite.MOD_ID);
    }
}
