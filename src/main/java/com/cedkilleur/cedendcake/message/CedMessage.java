package com.cedkilleur.cedendcake.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class CedMessage implements IMessage {
	
	int value;
	int dim;
	
	public CedMessage(int value, int dim) {
		this.value = value;
		this.dim = dim;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.value = buf.readByte();
		if (buf.readableBytes() > 0) {
			this.dim = buf.readInt();
		}

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(this.value);
		buf.writeInt(this.dim);
	}

	public static class Handler implements IMessageHandler<CedMessage, IMessage> {
		@Override
		public IMessage onMessage(CedMessage message, MessageContext ctx) {
			if (ctx.side == Side.SERVER) {
				final EntityPlayerMP player = ctx.getServerHandler().player;
				((WorldServer) player.world).addScheduledTask(() -> {
					if ((message.value == 0)) { //Change dimension
						player.changeDimension(message.dim);
					}
				});
			}
			return null;
		}
	}
}
