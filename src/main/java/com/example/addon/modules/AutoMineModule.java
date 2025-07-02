package com.mydonut.addon.modules;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.setting.BooleanSetting;
import meteordevelopment.meteorclient.setting.SettingGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class AutoMineModule extends Module {
    private final SettingGroup sg = settings.getDefaultGroup();
    private final BooleanSetting downOnly = sg.add(new BooleanSetting.Builder()
        .name("mine-down-only")
        .defaultValue(true)
        .build()
    );

    public AutoMineModule() {
        super(meteorClient.getCategory(), "auto-mine", "Mines automatically");
    }

    @Override
    public void onActivate() {
        info("AutoMine active.");
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        BlockPos target = downOnly.get() ?
            mc.player.getBlockPos().down() :
            mc.player.getBlockPos();

        BlockState state = mc.world.getBlockState(target);
        if (!state.isAir() && state.getBlock() != Blocks.BEDROCK) {
            mc.interactionManager.breakBlock(target);
        }
    }
}
