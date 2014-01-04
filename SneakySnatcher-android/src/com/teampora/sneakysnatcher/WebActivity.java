package com.teampora.sneakysnatcher;

import com.teampora.sneakysnatcher.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebActivity extends Activity {

	private final static String URL = "http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=SneakySnatcher";
	
	private WebView web;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webviewer);
		
		web = (WebView) findViewById(R.id.web_id);
		web.getSettings().setJavaScriptEnabled(true);
		web.loadUrl(URL);
		
		
	}

	
}
