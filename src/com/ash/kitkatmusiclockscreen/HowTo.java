package com.ash.kitkatmusiclockscreen;

import android.support.v7.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class HowTo extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.howto);
		ActionBar bar = getSupportActionBar();
	    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ee1c25")));
	    bar.setTitle("Help");
	}

}
