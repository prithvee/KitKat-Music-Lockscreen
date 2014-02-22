package com.ash.kitkatmusiclockscreen;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewManager;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;


public class HookClass {
	
	
    //private static final String CLASS_KGVIEW_MANAGER = "com.android.internal.policy.impl.keyguard.KeyguardViewManager";
    
    static Drawable background;
    static String state = "not";
    static String state1;
    static Context context;
	static Context gbContext;
	static String color = "#00000000";
	static Context acontext = MyApplication.getAppContext();
	//private static XSharedPreferences mPrefs;

	static BroadcastReceiver mReceiver;

	
	public static abstract interface ShowListener
	  {
	    public abstract void onShown(IBinder paramIBinder);
	  }
	public static void initZygote(XSharedPreferences prefs) 
    {
		
		//mPrefs = prefs;
		//XposedBridge.log("ash..running music");
    	
    	
    	final BroadcastReceiver Br = new BroadcastReceiver() {
			
				@SuppressWarnings("deprecation")
				@Override
				public void onReceive(Context context,Intent intent) {
					//.log("ash..Broadcast received");
					state = intent.getStringExtra("state");
					Bitmap bm = (Bitmap) intent.getParcelableExtra("art");
					
					background = new BitmapDrawable(bm);
					if(state.equals("running"))
					{
						background.setAlpha(150);
						color = "#ff000000";
						//XposedBridge.log("ash..bg fine");
					}
					else
					{
						background.setAlpha(0);
						color = null;
						//XposedBridge.log("ash..bg null");
					}
					
				}
			};
			
			final BroadcastReceiver Br2 = new BroadcastReceiver() {
				
				@Override
				public void onReceive(Context context,Intent intent) {
					//XposedBridge.log("ash..Broadcast received");
					background.setAlpha(0);
					color = null;
					
					
				}
			};
    	
			
        try {
			//final Class<?> kgViewManagerClass = XposedHelpers.findClass(CLASS_KGVIEW_MANAGER, null);
			
			/*XposedHelpers.findAndHookMethod(kgViewManagerClass, "maybeCreateKeyguardLocked",boolean.class, boolean.class, Bundle.class, new XC_MethodHook() {
				@Override
                protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                	XposedBridge.log("ash..inside 1st method");
                    mPrefs.reload();
                    ViewManager viewManager = (ViewManager) XposedHelpers.getObjectField(param.thisObject, "mViewManager");
                    FrameLayout keyGuardHost = (FrameLayout) XposedHelpers.getObjectField(param.thisObject, "mKeyguardHost");
                    WindowManager.LayoutParams windowLayoutParams = (WindowManager.LayoutParams) XposedHelpers.getObjectField(param.thisObject, "mWindowLayoutParams");

                    windowLayoutParams.flags |= WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER;
                    
                    viewManager.updateViewLayout(keyGuardHost, windowLayoutParams);
                }
            });*/
			
			
			
			if (Build.VERSION.SDK_INT > 16) {
				
				String classname="com.android.internal.policy.impl.keyguard.KeyguardViewManager";
				
				if( Build.VERSION.SDK_INT == 19)
					classname="com.android.keyguard.KeyguardViewManager";
				
				Class<?> kgViewManagerClass = XposedHelpers.findClass(classname, null);
				XposedHelpers.findAndHookMethod(kgViewManagerClass, "show",Bundle.class,new XC_MethodHook() {

							@Override
							protected void beforeHookedMethod(
									MethodHookParam param) throws Throwable {
								//XposedBridge.log("ash..found device with 4.2 or above");

								super.beforeHookedMethod(param);
								context = (Context) XposedHelpers
										.getObjectField(param.thisObject,
												"mContext");
								gbContext = context.createPackageContext(
										Ash.PACKAGE_NAME, 0);

								context = (Context) XposedHelpers
										.getObjectField(param.thisObject,
												"mContext");
								gbContext = context.createPackageContext(
										Ash.PACKAGE_NAME, 0);

								IntentFilter filter = new IntentFilter("ART");
								gbContext.registerReceiver(Br, filter);
								IntentFilter filter2 = new IntentFilter("ART2");
								gbContext.registerReceiver(Br2, filter2);

								/*try
								{
									final Class<?> keyguardUpdateMonitorClass = XposedHelpers.findClass("com.android.internal.policy.impl.keyguard.KeyguardHostView",context.getClassLoader());
									if(keyguardUpdateMonitorClass!=null)
									{
										XposedBridge.log("Ash..found");
										try 
										{
											FrameLayout keyguardView = (FrameLayout) XposedHelpers.getObjectField(param.thisObject,"mKeyguardView");
											if(keyguardView!=null)
											{
												XposedBridge.log("Ash..keyguardView found!!");
											}else
												XposedBridge.log("Ash..keyguardView not found!!");
										}
										catch (Exception e)
										{
											XposedBridge.log("Ash..keyguardView fail!!");
										}								
									}
									else
										XposedBridge.log("Ash..not found");
								} 
								catch (Exception e)
								{
									XposedBridge.log("Ash..failed to find");

								}*/
							}

							@SuppressLint("NewApi")
							@Override
							protected void afterHookedMethod(
									final MethodHookParam param)
									throws Throwable {

								FrameLayout keyguardView = (FrameLayout) XposedHelpers
										.getObjectField(param.thisObject,
												"mKeyguardView");
								/*Resources re = null;
								re.getResourceName(R.drawable.background);
								Drawable d = new BitmapDrawable(re);*/
								try {
									/*XposedBridge
											.log("ash..running music before");*/
									context = (Context) XposedHelpers
											.getObjectField(param.thisObject,
													"mContext");
									FrameLayout flayout = new FrameLayout(
											context);
									flayout.setLayoutParams(new LayoutParams(
											ViewGroup.LayoutParams.MATCH_PARENT,
											ViewGroup.LayoutParams.MATCH_PARENT));
									gbContext = context.createPackageContext(
											Ash.PACKAGE_NAME, 0);

									ViewManager viewManager = (ViewManager) XposedHelpers
											.getObjectField(param.thisObject,
													"mViewManager");
									FrameLayout keyGuardHost = (FrameLayout) XposedHelpers
											.getObjectField(param.thisObject,
													"mKeyguardHost");
									WindowManager.LayoutParams windowLayoutParams = (WindowManager.LayoutParams) XposedHelpers
											.getObjectField(param.thisObject,
													"mWindowLayoutParams");

									//Drawable background = gbContext.getResources().getDrawable(R.drawable.background);
									/*if(state.equals("running"))
									{*/
									if (background != null) {
										final ImageView mLockScreenWallpaperImage = new ImageView(
												context);
										//XposedBridge.log("ash..yeah!!!!done");
										mLockScreenWallpaperImage
												.setScaleType(ScaleType.CENTER_CROP);
										try {

											mLockScreenWallpaperImage
													.post(new Runnable() {
														@Override
														public void run() {
															mLockScreenWallpaperImage
																	.setImageDrawable(background);
														}
													});

											keyguardView
													.setBackgroundColor(Color
															.parseColor(color));
											flayout.addView(
													mLockScreenWallpaperImage,
													-1, -1);
											keyguardView.addView(flayout, 0);

											viewManager.updateViewLayout(
													keyGuardHost,
													windowLayoutParams);
										} catch (Exception e) {

											//XposedBridge.log("ash..error!!");
										}
									}

									/*else
									{
										
										XposedBridge.log("f@$&!!!!!!!");
										background.setAlpha(0);
										ImageView mLockScreenWallpaperImage = new ImageView(context);
										mLockScreenWallpaperImage.setScaleType(ScaleType.CENTER_CROP);
										mLockScreenWallpaperImage.setImageDrawable(background);
										keyguardView.setBackgroundColor(Color.parseColor("#00000000"));
										flayout.addView(mLockScreenWallpaperImage, -1,-1);
										keyguardView.addView(flayout, 0);
									}*/
									//XposedBridge.log("ash..running music after");

									/*Context context = (Context) XposedHelpers.getObjectField(param.thisObject,"mContext");
									FrameLayout flayout = new FrameLayout(context);
									flayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
									Context gbContext = context.createPackageContext(Ash.PACKAGE_NAME, 0);
									
									Bitmap background = BitmapFactory.decodeResource(null, R.drawable.background);
									Drawable d = new BitmapDrawable(context.getResources(), background);
									ImageView mLockScreenWallpaperImage = new ImageView(context);
									mLockScreenWallpaperImage.setScaleType(ScaleType.CENTER_CROP);
									mLockScreenWallpaperImage.setImageDrawable(d);
									flayout.addView(mLockScreenWallpaperImage, -1,-1);
									keyguardView.addView(flayout, 0);
									Log.e("ash", "yes.. running2222");
									XposedBridge.log("ash..running music");*/
								} catch (Exception e) {
									//XposedBridge.log("ash..failed!!!!");
								}

							}

						});
			}
			else
			{
				Class<?> kgViewManagerClass = XposedHelpers.findClass("com.android.internal.policy.impl.KeyguardViewManager", null);
				XposedHelpers.findAndHookMethod(kgViewManagerClass, "show", new XC_MethodHook() {
					
					@Override
					protected void beforeHookedMethod(
							MethodHookParam param) throws Throwable {

						super.beforeHookedMethod(param);
						//XposedBridge.log("ash..found device with 4.1 or below");
						context = (Context) XposedHelpers
								.getObjectField(param.thisObject,
										"mContext");
						gbContext = context.createPackageContext(
								Ash.PACKAGE_NAME, 0);

						context = (Context) XposedHelpers
								.getObjectField(param.thisObject,
										"mContext");
						gbContext = context.createPackageContext(
								Ash.PACKAGE_NAME, 0);

						IntentFilter filter = new IntentFilter("ART");
						gbContext.registerReceiver(Br, filter);
						IntentFilter filter2 = new IntentFilter("ART2");
						gbContext.registerReceiver(Br2, filter2);

						/*try
						{
							final Class<?> keyguardUpdateMonitorClass = XposedHelpers.findClass("com.android.internal.policy.impl.keyguard.KeyguardHostView",context.getClassLoader());
							if(keyguardUpdateMonitorClass!=null)
							{
								XposedBridge.log("Ash..found");
								try 
								{
									FrameLayout keyguardView = (FrameLayout) XposedHelpers.getObjectField(param.thisObject,"mKeyguardView");
									if(keyguardView!=null)
									{
										XposedBridge.log("Ash..keyguardView found!!");
									}else
										XposedBridge.log("Ash..keyguardView not found!!");
								}
								catch (Exception e)
								{
									XposedBridge.log("Ash..keyguardView fail!!");
								}								
							}
							else
								XposedBridge.log("Ash..not found");
						} 
						catch (Exception e)
						{
							XposedBridge.log("Ash..failed to find");

						}*/
					}

					@SuppressLint("NewApi")
					@Override
					protected void afterHookedMethod(
							final MethodHookParam param)
							throws Throwable {

						FrameLayout keyguardView = (FrameLayout) XposedHelpers
								.getObjectField(param.thisObject,
										"mKeyguardView");
						/*Resources re = null;
						re.getResourceName(R.drawable.background);
						Drawable d = new BitmapDrawable(re);*/
						try {
							/*XposedBridge
									.log("ash..running music before");*/
							context = (Context) XposedHelpers
									.getObjectField(param.thisObject,
											"mContext");
							FrameLayout flayout = new FrameLayout(
									context);
							flayout.setLayoutParams(new LayoutParams(
									ViewGroup.LayoutParams.MATCH_PARENT,
									ViewGroup.LayoutParams.MATCH_PARENT));
							gbContext = context.createPackageContext(
									Ash.PACKAGE_NAME, 0);

							ViewManager viewManager = (ViewManager) XposedHelpers
									.getObjectField(param.thisObject,
											"mViewManager");
							FrameLayout keyGuardHost = (FrameLayout) XposedHelpers
									.getObjectField(param.thisObject,
											"mKeyguardHost");
							WindowManager.LayoutParams windowLayoutParams = (WindowManager.LayoutParams) XposedHelpers
									.getObjectField(param.thisObject,
											"mWindowLayoutParams");

							//Drawable background = gbContext.getResources().getDrawable(R.drawable.background);
							/*if(state.equals("running"))
							{*/
							if (background != null) {
								final ImageView mLockScreenWallpaperImage = new ImageView(
										context);
								//XposedBridge.log("ash..yeah!!!!done");
								mLockScreenWallpaperImage
										.setScaleType(ScaleType.CENTER_CROP);
								try {

									mLockScreenWallpaperImage
											.post(new Runnable() {
												@Override
												public void run() {
													mLockScreenWallpaperImage
															.setImageDrawable(background);
												}
											});

									keyguardView
											.setBackgroundColor(Color
													.parseColor(color));
									flayout.addView(
											mLockScreenWallpaperImage,
											-1, -1);
									keyguardView.addView(flayout, 0);

									viewManager.updateViewLayout(
											keyGuardHost,
											windowLayoutParams);
								} catch (Exception e) {

									//XposedBridge.log("ash..error!!");
								}
							}

							/*else
							{
								
								XposedBridge.log("f@$&!!!!!!!");
								background.setAlpha(0);
								ImageView mLockScreenWallpaperImage = new ImageView(context);
								mLockScreenWallpaperImage.setScaleType(ScaleType.CENTER_CROP);
								mLockScreenWallpaperImage.setImageDrawable(background);
								keyguardView.setBackgroundColor(Color.parseColor("#00000000"));
								flayout.addView(mLockScreenWallpaperImage, -1,-1);
								keyguardView.addView(flayout, 0);
							}*/
							//XposedBridge.log("ash..running music after");

							/*Context context = (Context) XposedHelpers.getObjectField(param.thisObject,"mContext");
							FrameLayout flayout = new FrameLayout(context);
							flayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
							Context gbContext = context.createPackageContext(Ash.PACKAGE_NAME, 0);
							
							Bitmap background = BitmapFactory.decodeResource(null, R.drawable.background);
							Drawable d = new BitmapDrawable(context.getResources(), background);
							ImageView mLockScreenWallpaperImage = new ImageView(context);
							mLockScreenWallpaperImage.setScaleType(ScaleType.CENTER_CROP);
							mLockScreenWallpaperImage.setImageDrawable(d);
							flayout.addView(mLockScreenWallpaperImage, -1,-1);
							keyguardView.addView(flayout, 0);
							Log.e("ash", "yes.. running2222");
							XposedBridge.log("ash..running music");*/
						} catch (Exception e) {
							//XposedBridge.log("ash..failed!!!!");
						}

					}

				});
			}
		} catch (Exception e) {
			XposedBridge.log("ash..crashed!!!!");
		}
    	
    	
    }
	
	
	
	
}
