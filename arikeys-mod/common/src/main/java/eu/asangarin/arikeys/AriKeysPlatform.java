package eu.asangarin.arikeys;

import com.mojang.blaze3d.platform.InputConstants;
import eu.asangarin.arikeys.util.network.KeyPressData;
import java.util.Collection;
import net.minecraft.client.KeyMapping;

public class AriKeysPlatform {
	private static Adapter adapter;

	public static void init(Adapter platformAdapter) {
		adapter = platformAdapter;
	}

	public static void sendHandshake() {
		adapter.sendHandshake();
	}

	public static void sendKey(KeyPressData data) {
		adapter.sendKey(data);
	}

	public static Collection<KeyMapping> getKeyBinding(InputConstants.Key code) {
		return adapter.getKeyBinding(code);
	}

	public interface Adapter {
		void sendHandshake();

		void sendKey(KeyPressData data);

		Collection<KeyMapping> getKeyBinding(InputConstants.Key code);
	}
}
