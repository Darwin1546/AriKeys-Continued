package eu.asangarin.arikeys.util;

import com.mojang.blaze3d.platform.InputConstants;

import java.util.HashMap;
import java.util.Map;

/* Since 26.x Minecraft uses SDL scancodes instead of GLFW key codes.
 The server plugin still sends GLFW key codes, so they are translated
 here through the key names, which stayed the same between versions. */
public class GlfwKeyCodes {
	private static final Map<Integer, String> NAMES = new HashMap<>();

	static {
		put(32, "space");
		put(39, "apostrophe");
		put(44, "comma");
		put(45, "minus");
		put(46, "period");
		put(47, "slash");
		for (int i = 0; i <= 9; i++)
			put(48 + i, String.valueOf(i));
		put(59, "semicolon");
		put(61, "equal");
		for (char c = 'a'; c <= 'z'; c++)
			put(65 + (c - 'a'), String.valueOf(c));
		put(91, "left.bracket");
		put(92, "backslash");
		put(93, "right.bracket");
		put(96, "grave.accent");
		put(161, "world.1");
		put(162, "world.2");

		put(256, "escape");
		put(257, "enter");
		put(258, "tab");
		put(259, "backspace");
		put(260, "insert");
		put(261, "delete");
		put(262, "right");
		put(263, "left");
		put(264, "down");
		put(265, "up");
		put(266, "page.up");
		put(267, "page.down");
		put(268, "home");
		put(269, "end");
		put(280, "caps.lock");
		put(281, "scroll.lock");
		put(282, "num.lock");
		put(283, "print.screen");
		put(284, "pause");
		for (int i = 1; i <= 24; i++)
			put(289 + i, "f" + i);

		for (int i = 0; i <= 9; i++)
			put(320 + i, "keypad." + i);
		put(330, "keypad.period");
		put(331, "keypad.divide");
		put(332, "keypad.multiply");
		put(333, "keypad.subtract");
		put(334, "keypad.add");
		put(335, "keypad.enter");
		put(336, "keypad.equal");

		put(340, "left.shift");
		put(341, "left.control");
		put(342, "left.alt");
		put(343, "left.win");
		put(344, "right.shift");
		put(345, "right.control");
		put(346, "right.alt");
		put(347, "right.win");
		put(348, "application");
	}

	private static void put(int glfwCode, String name) {
		NAMES.put(glfwCode, "key.keyboard." + name);
	}

	public static InputConstants.Key toKey(int glfwCode) {
		String name = NAMES.get(glfwCode);
		return name == null ? InputConstants.UNKNOWN : InputConstants.getKey(name);
	}
}
