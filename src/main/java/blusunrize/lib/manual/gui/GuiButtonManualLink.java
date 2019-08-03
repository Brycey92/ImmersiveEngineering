/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.lib.manual.gui;

import blusunrize.lib.manual.ManualInstance.ManualLink;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TextFormatting;

import java.util.Collections;
import java.util.List;

public class GuiButtonManualLink extends Button
{
	public String localized;
	public ManualLink link;
	GuiManual gui;
	public List<GuiButtonManualLink> otherParts = ImmutableList.of();

	public GuiButtonManualLink(GuiManual gui, int x, int y, int w, int h, ManualLink link, String localized)
	{
		super(x, y, w, h, "", btn -> link.changePage(gui, true));
		this.gui = gui;
		this.link = link;
		this.localized = localized;
		if(gui.manual.improveReadability())
			this.localized = TextFormatting.BOLD+localized;
	}

	@Override
	public void render(int mx, int my, float partialTicks)
	{
		Minecraft mc = Minecraft.getInstance();
		isHovered = mx >= this.x&&my >= this.y&&mx < this.x+this.width&&my < this.y+this.height;
		if(isHovered)
		{
			drawHovered(mc, true, mx, my);
			for(GuiButtonManualLink btn : otherParts)
				if(btn!=this)
					btn.drawHovered(mc, false, mx, my);
			GlStateManager.enableBlend();
		}
	}

	private void drawHovered(Minecraft mc, boolean mouse, int mx, int my)
	{
		FontRenderer font = mc.fontRenderer;
		font.drawString(localized, x, y, gui.manual.getHighlightColour());
		gui.renderTooltip(Collections.singletonList(gui.manual.formatLink(link)), mx+8, my+4, font);
		GlStateManager.disableLighting();
	}
}