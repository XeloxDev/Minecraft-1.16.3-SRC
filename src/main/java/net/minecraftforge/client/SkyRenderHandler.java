package net.minecraftforge.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;

public interface SkyRenderHandler extends IRenderHandler
{
default void render(int ticks, float partialTicks, ClientWorld world, Minecraft mc)
    {
    }

    void render(int var1, float var2, MatrixStack var3, ClientWorld var4, Minecraft var5);
}
