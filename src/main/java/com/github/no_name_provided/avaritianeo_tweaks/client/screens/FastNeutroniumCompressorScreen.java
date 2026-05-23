package com.github.no_name_provided.avaritianeo_tweaks.client.screens;

import com.github.no_name_provided.avaritianeo_tweaks.common.menus.FastNeutroniumCompressorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class FastNeutroniumCompressorScreen extends AbstractContainerScreen<FastNeutroniumCompressorMenu> {
    public static final ResourceLocation TEXTURE_GROUP = ResourceLocation.fromNamespaceAndPath("avaritia", "textures/gui/compressor.png");
    
    public FastNeutroniumCompressorScreen(FastNeutroniumCompressorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        
        graphics.renderFakeItem(menu.getRecipeInput().getDefaultInstance(), getGuiLeft() + 17, getGuiTop() + 35);
        graphics.renderFakeItem(menu.getRecipeOutput().getDefaultInstance(), getGuiLeft() + 145,  getGuiTop()+ 35);
    }
    
    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(TEXTURE_GROUP, getGuiLeft(), getGuiTop(), 0, 0.0f, 0, 176, 256, 256, 256);
        float decimalPercentProgress = Mth.clamp((float) menu.getAmountAbsorbed() / (float) menu.getAmountRequired(), 0, 1);
        graphics.blit(TEXTURE_GROUP, getGuiLeft() + 62, getGuiTop() + 35, 1, 176f, 0, (int) (22 * decimalPercentProgress), 16, 256, 256);
        graphics.blit(TEXTURE_GROUP, getGuiLeft() + 90, getGuiTop() + 50 - Math.round(15 * decimalPercentProgress), 1, 176f, 16 + Math.round(15 * (1 - decimalPercentProgress)), 16, Math.round(15 * decimalPercentProgress), 256, 256);
    }
    
    /**
     * Text must be drawn here, or it'll silently fail to render.
     */
    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);
        if (menu.getAmountAbsorbed() > 0) {
            String progress = menu.getAmountAbsorbed() + "/" + menu.getAmountRequired();
            graphics.drawString(font, progress, 82 - font.width(progress) / 2, 60, 4210752, false);
        }
    }
}
