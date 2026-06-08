package eu.asangarin.arikeys.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.List;
import java.util.Map;
import net.minecraft.client.KeyMapping;

@Mixin(KeyMapping.class)
public interface AKKeyboardFabricMixin {
	@Accessor("MAP")
	static Map<InputConstants.Key, List<KeyMapping>> getKeyBindings() {
		throw new AssertionError();
	}
}
