package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CPlayerAbilitiesPacket implements IPacket<IServerPlayNetHandler>
{
    private boolean flying;

    public CPlayerAbilitiesPacket()
    {
    }

    public CPlayerAbilitiesPacket(PlayerAbilities capabilities)
    {
        this.flying = capabilities.isFlying;
    }

    public void readPacketData(PacketBuffer buf) throws IOException
    {
        byte b0 = buf.readByte();
        this.flying = (b0 & 2) != 0;
    }

    public void writePacketData(PacketBuffer buf) throws IOException
    {
        byte b0 = 0;

        if (this.flying)
        {
            b0 = (byte)(b0 | 2);
        }

        buf.writeByte(b0);
    }

    public void processPacket(IServerPlayNetHandler handler)
    {
        handler.processPlayerAbilities(this);
    }

    public boolean isFlying()
    {
        return this.flying;
    }
}
