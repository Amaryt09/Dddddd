package com.mydonut.addon.modules;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.setting.IntegerSetting;
import meteordevelopment.meteorclient.setting.SettingGroup;
import meteordevelopment.meteorclient.utils.player.MeteorPlayer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import com.mydonut.addon.util.WebhookSender;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.events.world.TickEvent;

public class BaseDetectorModule extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final IntegerSetting chestThreshold = sgGeneral.add(new IntegerSetting.Builder()
        .name("chest-threshold")
        .description("Number of chests to treat as a base")
        .defaultValue(10)
        .min(1).max(100).build()
    );
    private boolean alerted = false;

    public BaseDetectorModule() {
        super(meteorClient.getCategory(), "base-detector", "Detects spawner or chest cluster");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (alerted || mc.player == null) return;
        BlockPos center = mc.player.getBlockPos();

        int chestCount = 0;
        boolean spawnerFound = false;

        for (int x = -16; x <= 16; x++) {
            for (int y = -16; y <= 16; y++) {
                for (int z = -16; z <= 16; z++) {
                    BlockPos pos = center.add(x, y, z);
                    Block b = mc.world.getBlockState(pos).getBlock();

                    if (b == Blocks.SPAWNER) spawnerFound = true;
                    else if (b == Blocks.CHEST) chestCount++;
                }
            }
        }

        if (spawnerFound || chestCount >= chestThreshold.get()) {
            alerted = true;
            int px = center.getX(), py = center.getY(), pz = center.getZ();
            String coords = px + ", " + py + ", " + pz;
            WebhookSender.send("Base detected at: " + coords);
            info("Base detected – disconnecting.");
            MeteorPlayer.disconnect();
        }
    }
}
