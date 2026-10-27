package eu.asangarin.arikeys.neoforge;

import eu.asangarin.arikeys.neoforge.mixin.AKKeyboardNeoForgeMixin;
import com.mojang.blaze3d.platform.InputConstants;
import eu.asangarin.arikeys.AriKeysPlatform;
import eu.asangarin.arikeys.util.AriKeysPayloads;
import eu.asangarin.arikeys.util.network.KeyPressData;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.Collection;

public class AriKeysPlatformImpl implements AriKeysPlatform.Adapter {
	private boolean pendingHandshake;

	/* NeoForge refuses to send payloads on channels that a non-NeoForge server
	 hasn't registered yet. Bukkit registers plugin channels only after the login
	 packet, so the handshake is delayed until the server announces the channel. */
	public void sendHandshake() {
		pendingHandshake = true;
		tick();
	}

	public void tick() {
		if (!pendingHandshake) return;

		ClientPacketListener connection = Minecraft.getInstance().getConnection();
		if (connection == null) pendingHandshake = false;
		else if (connection.hasChannel(AriKeysPayloads.Handshake.ID)) {
			pendingHandshake = false;
			ClientPacketDistributor.sendToServer(new AriKeysPayloads.Handshake());
		}
	}

	public Collection<KeyMapping> getKeyBinding(InputConstants.Key code) {
		return AKKeyboardNeoForgeMixin.getKeyBindings().getAll(code);
	}

	public void sendKey(KeyPressData data) {
		if (canSend(AriKeysPayloads.Key.ID))
			ClientPacketDistributor.sendToServer(new AriKeysPayloads.Key(data));
	}

	private boolean canSend(CustomPacketPayload.Type<?> type) {
		ClientPacketListener connection = Minecraft.getInstance().getConnection();
		return connection != null && connection.hasChannel(type);
	}
}
