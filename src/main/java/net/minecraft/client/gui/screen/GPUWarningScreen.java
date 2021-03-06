package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;

public class GPUWarningScreen extends Screen
{
    private final ITextProperties field_241585_a_;
    private final ImmutableList<GPUWarningScreen.Option> field_241586_b_;
    private IBidiRenderer field_241587_c_ = IBidiRenderer.field_243257_a;
    private int field_241588_p_;
    private int field_241589_q_;

    protected GPUWarningScreen(ITextComponent p_i241250_1_, List<ITextProperties> p_i241250_2_, ImmutableList<GPUWarningScreen.Option> p_i241250_3_)
    {
        super(p_i241250_1_);
        this.field_241585_a_ = ITextProperties.func_240654_a_(p_i241250_2_);
        this.field_241586_b_ = p_i241250_3_;
    }

    public String getNarrationMessage()
    {
        return super.getNarrationMessage() + ". " + this.field_241585_a_.getString();
    }

    public void init(Minecraft minecraft, int width, int height)
    {
        super.init(minecraft, width, height);

        for (GPUWarningScreen.Option gpuwarningscreen$option : this.field_241586_b_)
        {
            this.field_241589_q_ = Math.max(this.field_241589_q_, 20 + this.font.func_238414_a_(gpuwarningscreen$option.field_241590_a_) + 20);
        }

        int l = 5 + this.field_241589_q_ + 5;
        int i1 = l * this.field_241586_b_.size();
        this.field_241587_c_ = IBidiRenderer.func_243258_a(this.font, this.field_241585_a_, i1);
        int i = this.field_241587_c_.func_241862_a() * 9;
        this.field_241588_p_ = (int)((double)height / 2.0D - (double)i / 2.0D);
        int j = this.field_241588_p_ + i + 9 * 2;
        int k = (int)((double)width / 2.0D - (double)i1 / 2.0D);

        for (GPUWarningScreen.Option gpuwarningscreen$option1 : this.field_241586_b_)
        {
            this.addButton(new Button(k, j, this.field_241589_q_, 20, gpuwarningscreen$option1.field_241590_a_, gpuwarningscreen$option1.field_241591_b_));
            k += l;
        }
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderDirtBackground(0);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, this.field_241588_p_ - 9 * 2, -1);
        this.field_241587_c_.func_241863_a(matrixStack, this.width / 2, this.field_241588_p_);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public boolean shouldCloseOnEsc()
    {
        return false;
    }

    public static final class Option
    {
        private final ITextComponent field_241590_a_;
        private final Button.IPressable field_241591_b_;

        public Option(ITextComponent p_i241251_1_, Button.IPressable p_i241251_2_)
        {
            this.field_241590_a_ = p_i241251_1_;
            this.field_241591_b_ = p_i241251_2_;
        }
    }
}
