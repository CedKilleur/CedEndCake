package com.cedkilleur.cedendcake.proxy;

import com.cedkilleur.cedendcake.block.CedCake;
import com.cedkilleur.cedendcake.handler.EventHandler;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {

	public static CedCake cedCake;

	public static EventHandler eventHandler = new EventHandler();

	public void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(eventHandler);
	}

	public void init(FMLInitializationEvent e) {
	}

	public void postInit(FMLPostInitializationEvent e) {
	}

	@SubscribeEvent
	public void onBlockRegistry(RegistryEvent.Register<Block> event) {
		final IForgeRegistry<Block> registry = event.getRegistry();
		registry.registerAll(cedCake = new CedCake());
	}

	@SubscribeEvent
	public void onItemRegistry(RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		registry.registerAll(new ItemBlock(cedCake) {
			@Override
			public EnumRarity getRarity(ItemStack stack) {
				return EnumRarity.RARE;
			}
		}.setRegistryName(cedCake.getRegistryName()));
	}

	@SubscribeEvent
	public void onRecipeRegistry(RegistryEvent.Register<IRecipe> event) {
		GameRegistry.addShapedRecipe(cedCake.getRegistryName(), null, new ItemStack(Item.getItemFromBlock(cedCake)),
				"EEE", "GNG", "EEE", 'E', Items.ENDER_EYE, 'N', Items.NETHER_STAR, 'G', Items.EGG);
	}


}