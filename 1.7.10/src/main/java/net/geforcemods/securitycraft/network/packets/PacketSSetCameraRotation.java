package net.geforcemods.securitycraft.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.geforcemods.securitycraft.entity.EntitySecurityCamera;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSSetCameraRotation implements IMessage {

	private float rotationYaw, rotationPitch;

	public PacketSSetCameraRotation(){

	}

	public PacketSSetCameraRotation(float yaw, float pitch){
		rotationYaw = yaw;
		rotationPitch = pitch;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		rotationYaw = buf.readFloat();
		rotationPitch = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(rotationYaw);
		buf.writeFloat(rotationPitch);
	}

	public static class Handler extends PacketHelper implements IMessageHandler<PacketSSetCameraRotation, IMessage>{

		@Override
		public IMessage onMessage(PacketSSetCameraRotation packet, MessageContext ctx) {
			EntityPlayer player = ctx.getServerHandler().playerEntity;

			if(player.ridingEntity != null && player.ridingEntity instanceof EntitySecurityCamera){
				player.ridingEntity.rotationYaw = packet.rotationYaw;
				player.ridingEntity.rotationPitch = packet.rotationPitch;
			}

			return null;
		}

	}

}
