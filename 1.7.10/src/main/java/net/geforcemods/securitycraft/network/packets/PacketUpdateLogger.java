package net.geforcemods.securitycraft.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.geforcemods.securitycraft.tileentity.TileEntityLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PacketUpdateLogger implements IMessage{

	private int x, y, z, i;
	private String username;

	public PacketUpdateLogger(){

	}

	public PacketUpdateLogger(int x, int y, int z, int i, String username){
		this.x = x;
		this.y = y;
		this.z = z;
		this.i = i;
		this.username = username;
	}

	@Override
	public void toBytes(ByteBuf par2ByteBuf) {
		par2ByteBuf.writeInt(x);
		par2ByteBuf.writeInt(y);
		par2ByteBuf.writeInt(z);
		par2ByteBuf.writeInt(i);
		ByteBufUtils.writeUTF8String(par2ByteBuf, username);
	}

	@Override
	public void fromBytes(ByteBuf par2ByteBuf) {
		x = par2ByteBuf.readInt();
		y = par2ByteBuf.readInt();
		z = par2ByteBuf.readInt();
		i = par2ByteBuf.readInt();
		username = ByteBufUtils.readUTF8String(par2ByteBuf);
	}

	public static class Handler extends PacketHelper implements IMessageHandler<PacketUpdateLogger, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketUpdateLogger packet, MessageContext context) {
			int x = packet.x;
			int y = packet.y;
			int z = packet.z;
			int i = packet.i;
			String username = packet.username;
			EntityPlayer par1EntityPlayer = Minecraft.getMinecraft().thePlayer;

			TileEntityLogger te = (TileEntityLogger) getClientWorld(par1EntityPlayer).getTileEntity(x, y, z); //((TileEntityLogger) getWorld()

			if(te != null)
				te.players[i] = username;

			return null;
		}
	}

}
