package com.cedkilleur.cedendcake.integration;


import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.cedkilleur.cedendcake.block.CedCake;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class CedWailaIntegration implements IWailaDataProvider {

	public static final CedWailaIntegration INSTANCE = new CedWailaIntegration();

	private CedWailaIntegration() {}


	private static boolean registered;
	private static boolean loaded;

	public static void load(IWailaRegistrar registrar) {
		if (!registered) {
			throw new RuntimeException("Please register this handler using the provided method.");
		}
		if (!loaded) {
			registrar.registerHeadProvider(INSTANCE, CedCake.class);
			registrar.registerBodyProvider(INSTANCE, CedCake.class);
			registrar.registerTailProvider(INSTANCE, CedCake.class);
			loaded = true;
		}
	}

	public static void register() {
		if (registered) {
			return;
		}
		registered = true;
		FMLInterModComms.sendMessage("waila", "register", "com.cedkilleur.cedendcake.integration.CedWailaIntegration.load");
	}

	@Nullable
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Nonnull
	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Nonnull
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		if (accessor.getBlock() instanceof ICedWailaIntegration) {
			return ((ICedWailaIntegration)accessor.getBlock()).getWailaBody(itemStack, currenttip, accessor, config);
		}
		return currenttip;
	}

	@Nonnull
	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Nonnull
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
		return tag;
	}

}

