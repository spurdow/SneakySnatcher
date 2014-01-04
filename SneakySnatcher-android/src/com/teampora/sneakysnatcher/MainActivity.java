package com.teampora.sneakysnatcher;


import java.math.BigDecimal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.teampora.interfaces.IActivityRequestHandler;
import com.teampora.sneakysnatcher.SneakySnatcher;

public class MainActivity extends AndroidApplication implements IActivityRequestHandler{
   
	public final int SHOW_ADS = 0x00;
	public final int HIDE_ADS = 0x01;
	public final int TOP_ADS = 0x02;
	public final int BOT_ADS = 0x03;
	
	
	/*
	 *  admob instace for ads
	 */
	private  AdView adView;
	private RelativeLayout.LayoutParams adParamsBot;
	private RelativeLayout.LayoutParams adParamsTop;
	private RelativeLayout layout ;
	
	

	
	protected Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case SHOW_ADS : adView.setVisibility(View.VISIBLE);
							//startApp.show(); 
							break;
			case HIDE_ADS : adView.setVisibility(View.GONE);
									break;
			case TOP_ADS :  layout.removeView(adView);
							layout.addView(adView , adParamsBot);break;
			case BOT_ADS : layout.removeView(adView);
						   layout.addView(adView, adParamsTop);break;
			default: assert false ; break;
			}
		}
		
		
	};
	
	
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.backends.android.AndroidApplication#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.backends.android.AndroidApplication#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}



	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        
        // Create the layout
        layout = new RelativeLayout(this);
        
        /*
         *  start app unified sdk integration 
         */
        
        // remove this method once testing mode is over
        //AndroidSDKProvider.setTestMode(true);
        // initialize the start app unified sdk
        //AndroidSDKProvider.initSDK(this);
        

        // Do the stuff that initialize() would do for you
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        
        // Create the libgdx View
        View gameView = initializeForView(new SneakySnatcher(this , new AndroidActionResolver(this)), cfg);
       

        // Create and setup the AdMob view
        adView = new AdView(this, AdSize.BANNER, "a151e085e1c0e90"); // Put in your secret key here
        adView.loadAd(new AdRequest());
        
        // Add the libgdx view
        layout.addView(gameView);

        // Add the AdMob view
        adParamsBot = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParamsBot.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adParamsBot.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        
        adParamsTop =  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParamsTop.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParamsTop.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        
        


        layout.addView(adView, adParamsBot);
       
        // Hook it all up
        setContentView(layout);
    }

	
	
	



	@Override
	public void setType(int type) {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(type);
		
	}

}