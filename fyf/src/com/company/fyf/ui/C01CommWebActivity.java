package com.company.fyf.ui;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.company.fyf.R;
import com.company.fyf.widget.TitleBar;

public class C01CommWebActivity extends B01BaseActivity {
	
	public static final String PARAM_TITLE = "param_title" ;
	public static final String PARAM_URL = "param_url" ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_c01_layout) ;
		
		TitleBar titleBar = (TitleBar) findViewById(R.id.titlebar) ;
		WebView webView = (WebView) findViewById(R.id.webView) ;
		
		Intent intent = getIntent() ;
		titleBar.setTitle(intent.getStringExtra(PARAM_TITLE)) ;
		webView.setWebViewClient(new WebViewClient(){
		      public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        view.loadUrl(url);
		        return true;
		      }
		    });
		webView.loadUrl(intent.getStringExtra(PARAM_URL))  ;
		webView.getSettings().setJavaScriptEnabled(true);
	}

}
