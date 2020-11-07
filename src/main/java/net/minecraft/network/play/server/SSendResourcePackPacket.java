package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SSendResourcePackPacket implements IPacket<IClientPlayNetHandler>
{
    private String url;
    private String hash;

    public SSendResourcePackPacket()
    {
    }

    public SSendResourcePackPacket(String urlIn, String hashIn)
    {
        this.url = urlIn;
        this.hash = hashIn;

        if (hashIn.length() > 40)
        {
            throw new IllegalArgumentException("Hash is too long (max 40, was " + hashIn.length() + ")");
        }
    }

    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.url = buf.readString(32767);
        this.hash = buf.readString(40);
    }

    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeString(this.url);
        buf.writeString(this.hash);
    }

    public void processPacket(IClientPlayNetHandler handler)
    {
        handler.handleResourcePack(this);
    }

    public String getURL()
    {
        return this.url;
    }

    public String getHash()
    {
        return this.hash;
    }
}
