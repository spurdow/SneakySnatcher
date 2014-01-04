package com.teampora.sneakysnatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.teampora.interfaces.IActionResolver;
import com.teampora.managers.ProfileManager;
import com.teampora.managers.SettingsManager;
import com.teampora.sneakysnatcher.R;

public class AndroidActionResolver implements IActionResolver{

	
	public static final String URL_RETRIEVE = "http://192.168.0.113/teampora/login/retrieveProfile";
	
	public static final String URL_SAVE = "http://192.168.0.113/teampora/login/save";
	
	private Context app;
	private Handler uiThread;
	
	public AndroidActionResolver(Context app){
		uiThread = new Handler();
		this.app = app;
	}

	private AlertDialog.Builder builder ;
	private AlertDialog alert;
	private String dataResult;
	
	
	
	
	@Override
	public void exit() {
		// TODO Auto-generated method stub
		if(builder != null || alert != null) return;
		
	      uiThread.post(new Runnable() {
	            public void run() {
	                     builder = new AlertDialog.Builder(app);
	                     builder.setInverseBackgroundForced(true)
	                    	  .setIcon(R.drawable.sneaky_snatcher)
	                    	  .setTitle("SneakySnatcher")
	                    	  .setMessage("Are you sure you want to quit?")
	                          .setCancelable(false)
	                          .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                     
	                             public void onClick(DialogInterface dialog, int which) {
	                                Gdx.app.exit();
	                             }
	                          })
	                          .setNegativeButton("No", new DialogInterface.OnClickListener(){
	                             public void onClick(DialogInterface dialog, int which) {
	                                dialog.dismiss();
	                                alert = null;
	                                builder = null;
	                             }
	                          });
	                    alert = builder.create();
	                    alert.show();
	            }
	      });
		
	}

	@Override
	public void buyPremium() {
		// TODO Auto-generated method stub
		uiThread.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent i = new Intent(app , WebActivity.class);
				app.startActivity(i);
			}
			
		});
		
		
	}

	@Override
	public boolean checkConnection() {
		// TODO Auto-generated method stub
		return ((ConnectivityManager)app.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()!=null;
	}

	@Override
	public ArrayList<String> emails() {
		// TODO Auto-generated method stub
		ArrayList<String> getAllAccounts = new ArrayList<String>();
		
		final Pattern emailPattern = Patterns.EMAIL_ADDRESS;
		Account[] accounts = AccountManager.get(app).getAccounts();
		
		for(Account acc: accounts){
			if(emailPattern.matcher(acc.name).matches()){
				getAllAccounts.add(acc.name);
			}
		}		
		return getAllAccounts;
	}
	private SaveTask saveTask;
	@Override
	public void save(final ArrayList<String> emails, final String json) {
		// TODO Auto-generated method stub
		uiThread.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				saveTask = new SaveTask(emails , json);
				saveTask.execute(URL_SAVE);
			}
			
			
		});
		
	}
	private RetrieveTask retrieveTask ;
	@Override
	public void retrieve(final ArrayList<String> emails) {
		// TODO Auto-generated method stub
		//final String[] emailsString = (String[]) emails.toArray();
		if(retrieveTask != null) return;
		uiThread.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				retrieveTask = new RetrieveTask(emails);
				retrieveTask.execute(URL_RETRIEVE);
			}
			
			
		});
	}
	
	
	
	@Override
	public void toast(final String message) {
		// TODO Auto-generated method stub
		uiThread.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(app, message, Toast.LENGTH_LONG).show();
			}
			
			
		});
	}
	
	private class RetrieveTask extends AsyncTask<String , String , String>{
		private ArrayList<String> emails;
		
		public RetrieveTask(ArrayList<String> emails){
			this.emails = emails;
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			InputStream is = null;
			String result = null;
			
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(arg0[0]);
			ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>(emails.size());
			for(int i = 0 ; i < emails.size() ; i++){
				pairs.add(new BasicNameValuePair("email"+i,emails.get(i)));
				Log.v("ANDROID", emails.get(i));
			}
			
			
				try {
					post.setEntity(new UrlEncodedFormEntity(pairs));

				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			//SettingsManager.getFile("", type)
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			result = android.text.Html.fromHtml(result).toString();
			dataResult = result;	
			
			//Toast.makeText(app,  result , Toast.LENGTH_LONG).show();
			Log.v("ANDROID ACTIVITY", result.length() + " " +result);
		}
		
		
		
		
	}
	
	private class SaveTask extends AsyncTask<String , Void , String>{

		private ArrayList<String> emails;
		private String json;
		
		public SaveTask(ArrayList<String> emails , String json){
			this.emails = emails;
			this.json = json;
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			
			String result = null;
			
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(arg0[0]);
			ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>(emails.size());
			for(int i = 0 ; i < emails.size() ; i++){
				pairs.add(new BasicNameValuePair("email"+i,emails.get(i)));
			}
			pairs.add(new BasicNameValuePair("json",json));
			
			try {
				post.setEntity(new UrlEncodedFormEntity(pairs));

				client.execute(post);
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}



		
	}

	@Override
	public String retrieveProfile() {
		// TODO Auto-generated method stub
		if(dataResult==null || dataResult.equals("")) {
			retrieve(emails());
		}
		return dataResult;
	}


}
