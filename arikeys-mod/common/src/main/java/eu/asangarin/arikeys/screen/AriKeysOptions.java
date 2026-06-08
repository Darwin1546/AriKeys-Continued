package eu.asangarin.arikeys.screen;

import com.mojang.blaze3d.platform.InputConstants;
import eu.asangarin.arikeys.AriKey;
import eu.asangarin.arikeys.AriKeys;
import eu.asangarin.arikeys.util.AriKeysIO;
import eu.asangarin.arikeys.util.ModifierKey;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class AriKeysOptions extends OptionsSubScreen {
	public AriKey focusedMKey;
	private AriKeyControlsListWidget keyBindingListWidget;
	private Button resetButton;

	public AriKeysOptions(Screen parent) {
		super(parent, Minecraft.getInstance().options, Component.translatable("arikeys.controls.title"));
	}

	@Override
	protected void addContents() {
		if (minecraft != null)
			this.keyBindingListWidget = this.layout.addToContents(new AriKeyControlsListWidget(this, this.minecraft));
	}

	@Override
	protected void addOptions() {}

	@Override
	protected void addFooter() {
		this.resetButton = Button.builder(Component.translatable("controls.resetAll"), (button) -> {
			for (AriKey keyBinding : AriKeys.getKeybinds()) {
				keyBinding.setBoundKey(keyBinding.getKeyCode(), false);
				keyBinding.resetBoundModifiers();
			}
			KeyMapping.resetMapping();
		}).build();
		LinearLayout linearlayout = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
		linearlayout.addChild(this.resetButton);
		linearlayout.addChild(Button.builder(CommonComponents.GUI_DONE, (button) -> this.onClose()).build());
	}

	@Override
	protected void repositionElements() {
		this.layout.arrangeElements();
		this.keyBindingListWidget.updateSize(this.width, this.layout);
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		if (this.focusedMKey != null) {
			if (event.key() == GLFW.GLFW_KEY_ESCAPE) {
				focusedMKey.setBoundKey(InputConstants.UNKNOWN, false);
				focusedMKey.setBoundModifiers(new HashSet<>());
			} else if (isModifier(event.key())) return super.keyPressed(event);
			else focusedMKey.setBoundKey(InputConstants.getKey(event), true);
			AriKeysIO.save();

			this.focusedMKey = null;
			KeyMapping.resetMapping();
			return true;
		} else {
			return super.keyPressed(event);
		}
	}

	private boolean isModifier(int code) {
		for (ModifierKey modifier : ModifierKey.ALL)
			if (modifier.getCode() == code) return true;
		return false;
	}

	@Override
	public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
		if (this.focusedMKey != null) {
			focusedMKey.setBoundKey(InputConstants.Type.MOUSE.getOrCreate(event.button()), true);
			AriKeysIO.save();

			this.focusedMKey = null;
			KeyMapping.resetMapping();
			return true;
		} else {
			return super.mouseClicked(event, doubleClick);
		}
	}

	@Override
	public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		/*this.keyBindingListWidget.render(context, mouseX, mouseY, delta);
		context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);*/

		boolean canReset = false;

		for (AriKey ariKey : AriKeys.getKeybinds()) {
			if (ariKey.hasChanged()) {
				canReset = true;
				break;
			}
		}

		this.resetButton.active = canReset;
	}
}
