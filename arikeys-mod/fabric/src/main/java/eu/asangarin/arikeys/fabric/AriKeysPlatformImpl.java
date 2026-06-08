package eu.asangarin.arikeys.fabric;

import eu.asangarin.arikeys.fabric.mixin.AKKeyboardFabricMixin;
import com.mojang.blaze3d.platform.InputConstants;
import eu.asangarin.arikeys.AriKeysPlatform;
import eu.asangarin.arikeys.util.AriKeysPayloads;
import eu.asangarin.arikeys.util.network.KeyPressData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import java.util.Collection;
import java.util.List;

public class AriKeysPlatformImpl implements AriKeysPlatform.Adapter {
	public void sendHandshake() {
		ClientPlayNetworking.send(new AriKeysPayloads.Handshake());
	}

	public Collection<KeyMapping> getKeyBinding(InputConstants.Key code) {
		return AKKeyboardFabricMixin.getKeyBindings().getOrDefault(code, List.of());
	}

	public void sendKey(KeyPressData data) {
		ClientPlayNetworking.send(new AriKeysPayloads.Key(data));
	}
}
