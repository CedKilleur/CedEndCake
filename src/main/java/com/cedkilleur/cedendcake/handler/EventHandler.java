package com.cedkilleur.cedendcake.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
		//		if (e.isEndConquered() && fromEnd) {
		//			fromEnd = false;
		//			e.player.changeDimension(-1);
		//		}
	}

	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent e) {
		if (fromEnd && (e.getEntity() instanceof EntityPlayer)) {
			fromEnd = false;
			//e.getEntity().changeDimension(-1);//CRASH
		}
	}

}
