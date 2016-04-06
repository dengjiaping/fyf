package com.company.fyf.ui;

import java.io.File;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.dao.FileVo;
import com.company.fyf.dao.RubbishVo;
import com.company.fyf.db.UserInfoDb;
import com.company.fyf.model.UserInfo;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.RubbishServer;
import com.company.fyf.net.UploadServer;
import com.company.fyf.notify.KeyList;
import com.company.fyf.notify.NotifyCenter;
import com.company.fyf.utils.CommConfig;
import com.company.fyf.utils.CompressUtils;
import com.company.fyf.utils.FyfUtils;
import com.lyx.utils.ImageLoaderUtils;

public class N03ClassificationAddActivity extends B01BaseActivity implements View.OnClickListener{
	
	public static final String PARAM_INT_FROM = "param_int_from";
	public static final String PARAM_SERIALIZABLE_RUBBISH = "param_serializable_rubbish";
	
	public static final int FROM_ADD = 0 ;
	public static final int FROM_EDIT = FROM_ADD + 1;
	
	public static final int REQUESTCODE_TAKE_PICTURE = 101;
	
	public static final int WHAT_TAKE_PICTURE = 102;
	
	private EditText name,note ;
	private RadioGroup complete ;
	private ImageView pic ;
	private View submit ;
	
	private File picFile ;
	
	private int from = FROM_ADD ;
	private RubbishVo rubbish ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_n03_layout);
		initComponent() ;
		initData() ;
	}

	private void initData() {
		// TODO Auto-generated method stub
		onGetIntentData();
		if(FROM_EDIT == from && rubbish != null){
			note.setText(rubbish.getNote());
			name.setText(rubbish.getName());
			complete.check(getCompleteId(rubbish.getComplete()));
			if(!TextUtils.isEmpty(rubbish.getPic_url()))
				ImageLoaderUtils.displayPicWithAutoStretch(rubbish.getPic_url(), pic) ;
		}
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		name = (EditText) findViewById(R.id.name) ;
		note = (EditText) findViewById(R.id.note) ;
		complete = (RadioGroup) findViewById(R.id.complete) ;
		pic = (ImageView) findViewById(R.id.pic) ;
		pic.setOnClickListener(this) ;
		submit = findViewById(R.id.submit) ;
		submit.setOnClickListener(this) ;
		
		TextView area = (TextView) findViewById(R.id.area) ;
		UserInfo userInfo = UserInfoDb.INSTANCE.get() ;
		
		if(userInfo == null){
			showWarnDlg("用户登录超时，请重新登录!") ;
			NotifyCenter.sendEmptyMsg(KeyList.KEY_USER_INFO_UPDATE) ;
			finish() ;
			return ;
		}
		
		area.setText(userInfo.getFjy_quyu() + "\n" + userInfo.getFjy_banshichu()) ;
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pic:
			doPhoto() ;
			break;
		case R.id.submit:
			doSubmit() ;
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE_TAKE_PICTURE && resultCode == RESULT_OK) { //处理拍照
			doPicDisplayBefore() ;
            return;
        }
	}
	
	private Handler mhandler = new Handler(){
		
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case WHAT_TAKE_PICTURE:
				doPicDisplay() ;
				break;

			default:
				break;
			}
		};
	} ;
	
	private void doPhoto(){
		File file = new File(Environment.getExternalStorageDirectory() + CommConfig.PATH_IMAGE_CACHE_DIR);
        if(!file.exists()){
        	file.mkdirs() ;
        }
		// 初始化文件保存路径
        String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        picFile = new File(file, fileName);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile));
        startActivityForResult(intent, REQUESTCODE_TAKE_PICTURE);
	}
	
	private void doPicDisplayBefore(){
		new Thread(new Runnable() {
            @Override
            public void run() {
                if(picFile == null || TextUtils.isEmpty(picFile.getPath()))
                    return;
                picFile = CompressUtils.getCompressedBitmapFileSyc(picFile.getPath(), 512, 512);
                Message msg = new Message();
                msg.what = WHAT_TAKE_PICTURE;
                mhandler.sendMessage(msg);
            }
        }).start();
	}
	
	private void doPicDisplay(){
		ImageLoaderUtils.displayPicWithAutoStretch(Uri.fromFile(picFile).toString(), pic) ;
	}
	
	private void doSubmit(){
		String name = this.name.getText().toString() ;
		if(FyfUtils.checkInputEmpty(name)){
			showToast("请输入小区名称位置") ;
			return ;
		}
		int completeCode = getCompleteCode() ; 
		if(completeCode == 0){
			showToast("请选择任务完成量") ;
			return ;
		}
		if(from == FROM_EDIT){
			doEdit(name,completeCode + "");
		}else{
			doAdd(name,completeCode + "");
		}
	}

	private void doEdit(final String name,final String completeCode) {
		// TODO Auto-generated method stub
		if(picFile == null 
				|| TextUtils.isEmpty(picFile.getPath())
				|| !picFile.exists()
				|| picFile.length() == 0){
			editMyRubbishInfoById(rubbish.getPic_url(),name,completeCode) ;
			return ;
		}
		UploadServer uploadServer = new UploadServer(this) ;
		uploadServer.upload(picFile,new CallBack<List<FileVo>>() {
			@Override
			public void onSuccess(List<FileVo> t) {
				super.onSuccess(t);
				if(t != null && t.size() > 0){
					String pic = t.get(0).getFilepath() ;
					editMyRubbishInfoById(pic,name,completeCode) ;
				}
			}
		});
	}

	private void doAdd(final String name,final String completeCode) {
		if(picFile == null 
				|| TextUtils.isEmpty(picFile.getPath())
				|| !picFile.exists()
				|| picFile.length() == 0){
			showToast("请上传图片") ;
			return ;
		}
		UploadServer uploadServer = new UploadServer(this) ;
		uploadServer.upload(picFile,new CallBack<List<FileVo>>() {
			@Override
			public void onSuccess(List<FileVo> t) {
				super.onSuccess(t);
				if(t != null && t.size() > 0){
					String pic = t.get(0).getFilepath() ;
					uploadRubbish(pic,name,completeCode) ;
				}
			}
		});
	}
	
	private void uploadRubbish(String pic,String name,String complete){
		RubbishServer server = new RubbishServer(this) ;
		server.uploadRubbish(pic, name, complete, note.getText().toString(), new CallBack<Void>() {
			@Override
			public void onSuccess(Void t) {
				super.onSuccess(t);
				showToast("提交成功");
				setResult(RESULT_OK) ;
				finish();
			}
		});
	}
	
	private void editMyRubbishInfoById(String pic,String name,String complete){
		RubbishServer server = new RubbishServer(this) ;
		server.editMyRubbishInfoById(rubbish.getId(),pic, name, complete, note.getText().toString(), new CallBack<Void>() {
			@Override
			public void onSuccess(Void t) {
				super.onSuccess(t);
				showToast("修改成功");
				setResult(RESULT_OK) ;
				finish();
			}
		});
	}
	
	private int getCompleteCode(){
		int checkid = complete.getCheckedRadioButtonId() ; 
		switch (checkid) {
		case R.id.complete_01:
			return 1;
		case R.id.complete_02:
			return 2;
		case R.id.complete_03:
			return 3;
		case R.id.complete_04:
			return 4;
		default:
			return 0;
		}
	}
	
	private int getCompleteId(String code){
		int _code = Integer.parseInt(code) ;
		switch (_code) {
		case 1:
			return R.id.complete_01;
		case 2:
			return R.id.complete_02;
		case 3:
			return R.id.complete_03;
		case 4:
			return R.id.complete_04;
		default:
			return 0;
		}
	}


}
