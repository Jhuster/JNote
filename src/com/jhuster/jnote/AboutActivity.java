/*
 *  Copyright (C) 2015, Jhuster, All Rights Reserved
 *
 *  Author:  Jhuster(lujun.hust@gmail.com)
 *  
 *  https://github.com/Jhuster/JNote
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 */
package com.jhuster.jnote;

import com.jhuster.jnote.markdown.MDReader;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class AboutActivity extends Activity {
    
    private TextView mTextView;
    private MDReader mMDReader;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    getActionBar().setDisplayHomeAsUpEnabled(true);  
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);               
        mTextView = (TextView)findViewById(R.id.DisplayTextView);
        mMDReader = new MDReader(getAboutAuthor());        
        mTextView.setTextKeepState(mMDReader.getFormattedContent(),BufferType.SPANNABLE);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {        
	    switch (item.getItemId() ) {
	    case android.R.id.home:
	         finish();
	    default:
	         break;
	    }
	    return true;
	}
	
	protected String getVersionDescription() {        
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName + " for Android";
        } 
        catch (NameNotFoundException e) {            
            e.printStackTrace();
        }
        return "Unknow";
    }
	
	protected String getAboutAuthor() {
	    StringBuilder builder = new StringBuilder();
        builder.append("# **关于软件:**\n\n");
        builder.append("- 版本号: " + getVersionDescription() + "\n\n");
        builder.append("# **关于作者:**\n\n");
        builder.append("### 卢俊\n\n");
        builder.append("- 联系方式: lujun.hust@gmail.com \n\n");
        builder.append("- 个人网站: http://www.jhuster.com \n\n");        
        return builder.toString();
	}
}
