package com.cedkilleur.cedendcake.handler;

import com.cedkilleur.cedendcake.CedMain;
import com.cedkilleur.cedendcake.message.CedMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class EventHandler {

	public static boolean fromEnd = false;

	@SubscribeEvent
	public void onLogin(PlayerLoggedInEvent e) {
		if (e.player.dimension == 1) {
			fromEnd = true;
		}
	}

	@SubscribeEvent
	public void onChangedDimension(PlayerChangedDimensionEvent e) {
		fromEnd = false;
		if (e.toDim == 1)
		{
			fromEnd = true;
		}
	}

	@SubscribeEvent
	public  void onRespawn(PlayerRespawnEvent e) {
	}

	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone e) {
		if (fromEnd  && (e.getEntity() instanceof EntityPlayer) && !e.isWasDeath()) {
			fromEnd = false;
			e.getEntity().changeDimension(66);
			CedMain.network.sendToServer(new CedMessage(0,66));
		}
	}

}
