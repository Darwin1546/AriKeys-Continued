package eu.asangarin.arikeys.neoforge;

import eu.asangarin.arikeys.neoforge.mixin.AKKeyboardNeoForgeMixin;
import com.mojang.blaze3d.platform.InputConstants;
import eu.asangarin.arikeys.AriKeysPlatform;
import eu.asangarin.arikeys.util.AriKeysPayloads;
import eu.asangarin.arikeys.util.network.KeyPressData;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.Collection;

public class AriKeysPlatformImpl implements AriKeysPlatform.Adapter {
	public void sendHandshake() {
		ClientPacketDistributor.sendToServer(new AriKeysPayloads.Handshake());
	}

	public Collection<KeyMapping> getKeyBinding(InputConstants.Key code) {
		return AKKeyboardNeoForgeMixin.getKeyBindings().getAll(code);
	}

	public void sendKey(KeyPressData data) {
		ClientPacketDistributor.sendToServer(new AriKeysPayloads.Key(data));
	}
}
