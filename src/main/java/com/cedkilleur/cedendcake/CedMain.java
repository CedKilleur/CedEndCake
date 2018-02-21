package com.cedkilleur.cedendcake;

import com.cedkilleur.cedendcake.block.CedCake;
import com.cedkilleur.cedendcake.message.CedMessage;
import com.cedkilleur.cedendcake.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid=CedMain.MODID, name=CedMain.MODNAME, version=CedMain.VERSION, acceptedMinecraftVersions = CedMain.MCVERSION, useMetadata = true)
public class CedMain {

	public static final String MODID = "cedendcake";
	public static final String MODNAME="CedKilleur's End Cake";
	public static final String VERSION = "0.1";
	public static final String MCVERSION = "[1.12,)";
	public static final CreativeTabs TAB = new CreativeTabs(MODID) {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(CommonProxy.cedCake));
		}
	};

	@Instance(MODID)
	public static CedMain instance;

	@SidedProxy(clientSide="com.cedkilleur.cedendcake.proxy.ClientProxy", serverSide="com.cedkilleur.cedendcake.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static Configuration config;
	public static boolean eatCakeWhenFull;

	public static CedCake cedCake;
	public static SimpleNetworkWrapper network;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		CedMain.network = NetworkRegistry.INSTANCE.newSimpleChannel("CedUC");
		CedMain.network.registerMessage(CedMessage.Handler.class, CedMessage.class, 0, Side.SERVER);
		config = new Configuration(event.getSuggestedConfigurationFile());
		eatCakeWhenFull = config.getBoolean("CanEatWhenFull","CedCake", true, "Set to true to enable eating the cake when saturation is full.");
		if (config.hasChanged()) {
			config.save();
		}

		proxy.preInit(event);
	}


	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}

}
