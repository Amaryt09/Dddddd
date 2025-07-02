package com.mydonut.addon;



import meteordevelopment.meteorclient.addons.MeteorAddon;

import com.mydonut.addon.modules.BaseDetectorModule;

import com.mydonut.addon.modules.AutoMineModule;

import com.mydonut.addon.modules.CoordSnapperModule;

import net.minecraft.util.Identifier;



public class MyDonutAddon extends MeteorAddon {

    @Override

    public void onInitialize() {

        modules.add(new BaseDetectorModule());

        modules.add(new AutoMineModule());

        modules.add(new CoordSnapperModule());

    }



    @Override

    public String getPackage() {

        return "com.mydonut.addon";

    }



    @Override

    public Identifier getIcon() {

        return new Identifier("minecraft", "compass");

    }

}
