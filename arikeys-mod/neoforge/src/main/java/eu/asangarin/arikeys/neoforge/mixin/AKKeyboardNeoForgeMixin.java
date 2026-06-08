package eu.asangarin.arikeys.neoforge.mixin;

import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyMappingLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyMapping.class)
public interface AKKeyboardNeoForgeMixin {
	@Accessor("MAP")
	static KeyMappingLookup getKeyBindings() {
		throw new AssertionError();
	}
}
