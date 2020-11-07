package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITagCollectionSupplier;

public class STagsListPacket implements IPacket<IClientPlayNetHandler>
{
    private ITagCollectionSupplier tags;

    public STagsListPacket()
    {
    }

    public STagsListPacket(ITagCollectionSupplier p_i242087_1_)
    {
        this.tags = p_i242087_1_;
    }

    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.tags = ITagCollectionSupplier.func_242211_b(buf);
    }

    public void writePacketData(PacketBuffer buf) throws IOException
    {
        this.tags.func_242210_a(buf);
    }

    public void processPacket(IClientPlayNetHandler handler)
    {
        handler.handleTags(this);
    }

    public ITagCollectionSupplier getTags()
    {
        return this.tags;
    }
}