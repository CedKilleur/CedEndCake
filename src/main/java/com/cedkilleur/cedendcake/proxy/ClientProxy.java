package com.cedkilleur.cedendcake.proxy;

import com.cedkilleur.cedendcake.integration.CedTOPIntegration;
import com.cedkilleur.cedendcake.integration.CedWailaIntegration;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		MinecraftForge.EVENT_BUS.register(this);

		if (Loader.isModLoaded("theoneprobe")) {
			CedTOPIntegration.register();
		}
		if (Loader.isModLoaded("waila")) {
			CedWailaIntegration.register();
		}
	}
	@SubscribeEvent
	public void onModelRegistry(ModelRegistryEvent event) {
		for (int i = 0; i <= 6; i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(cedCake), i,
					new ModelResourceLocation(cedCake.getRegistryName(), "inventory"));
		}

	}
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

}