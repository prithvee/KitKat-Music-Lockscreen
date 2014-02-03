package com.ash.kitkatmusiclockscreen;


import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;

public class Ash implements IXposedHookZygoteInit {
    public static final String PACKAGE_NAME = Ash.class.getPackage().getName();
    public static String MODULE_PATH = null;
    private static XSharedPreferences prefs;
	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {


		MODULE_PATH = startupParam.modulePath;
        prefs = new XSharedPreferences(PACKAGE_NAME);

        XposedBridge.log("Current Playing Music************");

        HookClass.initZygote(prefs);
		
	}
}


