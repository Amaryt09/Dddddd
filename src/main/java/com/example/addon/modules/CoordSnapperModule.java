package com.mydonut.addon.modules;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.setting.KeybindSetting;
import meteordevelopment.meteorclient.setting.SettingGroup;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.settings.Keybind;

public class CoordSnapperModule extends Module {
    private final SettingGroup sg = settings.getDefaultGroup();
    private final KeybindSetting snapKey = sg.add(new KeybindSetting.Builder()
        .name("snap-key")
        .description("Copy current coords to chat/clipboard")
        .defaultValue(Keybind.fromKey(314)) // "K"
        .build()
    );

    public CoordSnapperModule() {
        super(meteorClient.getCategory(), "coord-snapper", "Snaps coords with keybind");
    }

    @Override
    public void onTick() {
        if (snapKey.get().isPressed()) {
            int x = mc.player.getBlockX();
            int y = mc.player.getBlockY();
            int z = mc.player.getBlockZ();
            String c = x + ", " + y + ", " + z;

            mc.keyboard.setClipboard(c);
            ChatUtils.info("Coords snapped: " + c);
        }
    }
}
