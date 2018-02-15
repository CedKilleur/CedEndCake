package com.cedkilleur.cedendcake.integration;

import javax.annotation.Nullable;

import com.cedkilleur.cedendcake.CedMain;
import com.google.common.base.Function;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class CedTOPIntegration {

	private static boolean registered;

	public static void register() {
		if (registered) {
			return;
		}
		registered = true;
		FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "com.cedkilleur.cedendcake.integration.CedTOPIntegration$GetTheOneProbe");
	}


	public static class GetTheOneProbe implements Function<ITheOneProbe, Void> {

		public static ITheOneProbe probe;

		@Override @Nullable
		public Void apply(ITheOneProbe theOneProbe) {
			probe = theOneProbe;
			probe.registerProvider(new IProbeInfoProvider() {
				@Override
				public String getID() {
					return CedMain.MODID + ".cedCake";
				}
				@Override
				public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
					if (blockState.getBlock() instanceof ICedTOPIntegration) {
						ICedTOPIntegration provider = (ICedTOPIntegration)blockState.getBlock();
						provider.addProbeInfo(mode, probeInfo, player, world, blockState, data);
					}

				}
			});
			return null;
		}
	}

}
