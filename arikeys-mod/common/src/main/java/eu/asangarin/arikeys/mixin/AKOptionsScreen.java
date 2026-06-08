package eu.asangarin.arikeys.mixin;

import eu.asangarin.arikeys.AriKeys;
import eu.asangarin.arikeys.screen.AriKeysButton;
import java.util.List;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsSubScreen.class)
public class AKOptionsScreen extends Screen {
	@Unique
	private AriKeysButton arikeys$ak_button;

	protected AKOptionsScreen(Component title) {
		super(title);
	}

	@Inject(method = "init", at = @At("TAIL"))
	protected void initAriKeysButton(CallbackInfo ci) {
		if (!((Object) this instanceof ControlsScreen) || minecraft == null || minecraft.isLocalServer()) return;

		arikeys$ak_button = new AriKeysButton(this);
		addRenderableWidget(arikeys$ak_button);
		arikeys$prioritizeInput();
		arikeys$refresh();

		boolean hasKeybinds = !AriKeys.getKeybinds().isEmpty();
		arikeys$ak_button.active = hasKeybinds;
		arikeys$ak_button.setTooltip(hasKeybinds ? null : Tooltip.create(Component.translatable("arikeys.disabled_message")));
	}

	@Inject(method = "repositionElements", at = @At("TAIL"))
	protected void repositionAriKeysButton(CallbackInfo ci) {
		arikeys$refresh();
	}

	@Unique
	private void arikeys$refresh() {
		if (arikeys$ak_button != null)
			arikeys$ak_button.setPosition(this.width / 2 + 158, 37);
	}

	@Unique
	@SuppressWarnings("unchecked")
	private void arikeys$prioritizeInput() {
		List<GuiEventListener> children = (List<GuiEventListener>) (List<?>) children();
		children.remove(arikeys$ak_button);
		children.addFirst(arikeys$ak_button);
	}
}
