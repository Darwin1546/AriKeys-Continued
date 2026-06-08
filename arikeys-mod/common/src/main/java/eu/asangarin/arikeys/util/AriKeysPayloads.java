package eu.asangarin.arikeys.util;

import eu.asangarin.arikeys.util.network.KeyAddData;
import eu.asangarin.arikeys.util.network.KeyPressData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class AriKeysPayloads {
	public record Handshake() implements CustomPacketPayload {
		public static final Type<Handshake> ID = new Type<>(AriKeysChannels.HANDSHAKE_CHANNEL);
		public static final StreamCodec<RegistryFriendlyByteBuf, Handshake> CODEC = StreamCodec.ofMember(Handshake::write, Handshake::read);

		private void write(RegistryFriendlyByteBuf buf) {}

		private static Handshake read(RegistryFriendlyByteBuf buf) {
			readFully(buf);
			return new Handshake();
		}

		@Override
		public Type<Handshake> type() {
			return ID;
		}
	}

	public record AddKey(KeyAddData data) implements CustomPacketPayload {
		public static final Type<AddKey> ID = new Type<>(AriKeysChannels.ADD_KEY_CHANNEL);
		public static final StreamCodec<RegistryFriendlyByteBuf, AddKey> CODEC = StreamCodec.ofMember(AddKey::write, AddKey::read);

		private void write(RegistryFriendlyByteBuf buf) {}

		private static AddKey read(RegistryFriendlyByteBuf buf) {
			KeyAddData keyData = KeyAddData.fromBuffer(buf);
			readFully(buf);
			return new AddKey(keyData);
		}

		@Override
		public Type<AddKey> type() {
			return ID;
		}
	}

	public record Load() implements CustomPacketPayload {
		public static final Type<Load> ID = new Type<>(AriKeysChannels.LOAD_CHANNEL);
		public static final StreamCodec<RegistryFriendlyByteBuf, Load> CODEC = StreamCodec.ofMember(Load::write, Load::read);

		private void write(RegistryFriendlyByteBuf buf) {}

		private static Load read(RegistryFriendlyByteBuf buf) {
			readFully(buf);
			return new Load();
		}

		@Override
		public Type<Load> type() {
			return ID;
		}
	}

	public record Key(KeyPressData data) implements CustomPacketPayload {
		public static final Type<Key> ID = new Type<>(AriKeysChannels.KEY_CHANNEL);
		public static final StreamCodec<RegistryFriendlyByteBuf, Key> CODEC = StreamCodec.ofMember(Key::write, Key::read);

		private void write(RegistryFriendlyByteBuf buf) {
			buf.writeByte(0);
			data.write(buf);
		}

		private static Key read(RegistryFriendlyByteBuf buf) {
			readFully(buf);
			return new Key(null);
		}

		@Override
		public Type<Key> type() {
			return ID;
		}
	}

	protected static void readFully(FriendlyByteBuf buf) {
		while(buf.readableBytes() != 0)
			buf.readByte();
	}
}
