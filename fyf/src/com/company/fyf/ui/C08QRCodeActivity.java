package com.company.fyf.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.company.fyf.R;
import com.company.fyf.db.UserInfoDb;
import com.company.fyf.model.UserInfo;
import com.lyx.utils.QRCodeHelper;

public class C08QRCodeActivity extends B01BaseActivity {
	
	private Bitmap bitmap = null ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_c08_layout) ;
		
		final ImageView imageView = (ImageView) findViewById(R.id.imageView) ;
		imageView.post(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				int width = imageView.getWidth() ;
				int height = imageView.getHeight() ;
				bitmap = QRCodeHelper.createQRCodeImage(getInfomation(), width, height) ;
				if(bitmap == null){
					showToast("数据有误") ;
					finish() ;
				}
				imageView.setImageBitmap(bitmap) ;
			}
		}) ;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(bitmap !=null){
			bitmap.recycle() ;
			bitmap=null ;
		}
	}
	
	private String getInfomation(){
		UserInfo user = UserInfoDb.INSTANCE.get() ;
		return  "{\"tag\":\"25ec1187-c3e0-473c-ad49-71ee0b9f7d2b\",\"data\":\""+user.getUsername()+"\"}" ;
	}

}
