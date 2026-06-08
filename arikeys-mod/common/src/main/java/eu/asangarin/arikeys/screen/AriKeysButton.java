package eu.asangarin.arikeys.screen;

import eu.asangarin.arikeys.AriKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.Identifier;

public class AriKeysButton extends ImageButton {
	private static final WidgetSprites BUTTON_TEXTURES = new WidgetSprites(
		Identifier.fromNamespaceAndPath(AriKeys.MOD_ID, "arikeys/ak_button_enabled"),
		Identifier.fromNamespaceAndPath(AriKeys.MOD_ID, "arikeys/ak_button_disabled"),
		Identifier.fromNamespaceAndPath(AriKeys.MOD_ID, "arikeys/ak_button_focused")
	);

	public AriKeysButton(Screen parent) {
		super(20, 20, BUTTON_TEXTURES,
			(action) -> Minecraft.getInstance().setScreen(new AriKeysOptions(parent)), CommonComponents.EMPTY);
	}
}
