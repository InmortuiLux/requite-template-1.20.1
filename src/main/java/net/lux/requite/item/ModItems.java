package net.lux.requite.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.lux.requite.Requite;
import net.lux.requite.item.custom.DeathReturnItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item Soul = registerItem("soul", new Item(new FabricItemSettings()));
    public static final Item Bone_fragment = registerItem("bone_fragment", new Item(new FabricItemSettings()));
    public static final Item Dark_Leather = registerItem("dark_leather", new Item(new FabricItemSettings()));
    public static final Item Soul_tether = registerItem("soul_tether", new DeathReturnItem(new FabricItemSettings()));

    private static  void addItemsToIngredientsItemGroup(FabricItemGroupEntries entries){
        entries.add(Soul);
        entries.add(Bone_fragment);
        entries.add(Dark_Leather);
        entries.add(Dark_Leather);

        }
    private static  void addItemsToToolItemGroup(FabricItemGroupEntries entries){
        entries.add(Soul_tether);
    }



    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(Requite.MOD_ID, name), item );
    }

    public  static  void  registerModItems(){
        Requite.LOGGER.info("Registering Mod Items for"+ Requite.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems ::addItemsToIngredientsItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems ::addItemsToToolItemGroup);
        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.WHATEVERVANILLAGROUP).register(ModItems ::addItemsToIngredientsItemGroup);

    }
}
