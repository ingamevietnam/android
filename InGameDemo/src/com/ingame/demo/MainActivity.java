package com.ingame.demo;

import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ingamesdk.Listener.IGListenerInterface;
import com.ingamesdk.manager.InGameSDK;

public class MainActivity extends Activity {

	public static InGameSDK m_InGameSDK = null;
	public String appId = "911fe9dbd0f94625b4b591301c0f3818";
	public String appKey = "a2de0d67a7aecbcc89293fbed6712b1a";
	public static MainActivity instance;
	Button btnLogin, btnPayment, btnShowUser;
	TextView tvInfo;
	private boolean isloggedin = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		instance = this;

		Listener listener = new Listener();
		m_InGameSDK = InGameSDK.getInstance();
		m_InGameSDK.callSendInstallationEvent(this);
		m_InGameSDK.setListener(listener);
		m_InGameSDK.init(this, true, true, "");
		m_InGameSDK.setgameOrderId(random());
		tvInfo = (TextView) findViewById(R.id.main_tvInfo);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnPayment = (Button) findViewById(R.id.btnPayment);
		btnShowUser = (Button) findViewById(R.id.btnShowUser);

		if (!isloggedin)
			InGameSDK.getInstance().callLogin();
	}

	public static String random() {
		Random generator = new Random();
		StringBuilder randomStringBuilder = new StringBuilder();
		int randomLength = generator.nextInt(15);
		char tempChar;
		for (int i = 0; i < randomLength; i++) {
			tempChar = (char) (generator.nextInt(96) + 32);
			randomStringBuilder.append(tempChar);
		}
		return randomStringBuilder.toString();
	}

	@Override
	protected void onResume() {
		super.onResume();
		InGameSDK.getInstance().onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		InGameSDK.getInstance().onPause();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		InGameSDK.getInstance().onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
		}
		return super.onKeyDown(keyCode, event);
	}

	// Alternative variant for API 5 and higher
	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}

	public void login(View v) {
		if (!isloggedin)
			m_InGameSDK.callLogin();
		else
			m_InGameSDK.callLogout();
	}

	public void Register(View v) {
		m_InGameSDK.callRegister();
	}

	public void showUserInfo(View v) {
		m_InGameSDK.callshowUserInfo();
	}

	public void makePayment(View v) {
		m_InGameSDK.callPayment(random());
	}

	public void Invite(View v) {
		m_InGameSDK.callInviteFriend();
	}

	public void GetFriend(View v) {
		m_InGameSDK.callGetFBFriendList();
	}

	public void Share(View v) {
		m_InGameSDK.callShareMessageFromGame("Test", null);
	}

	public void LogOut(View v) {

		m_InGameSDK.callLogout();
	}

	public class Listener implements IGListenerInterface {

		@Override
		public void LoginSuccessListener(JSONObject json) {
			// TODO Auto-generated method stub
			try {
				String id = json.getString("userID");
				String name = json.getString("userName");
				String accesstoken = json.getString("accessToken");
				String phone = json.getString("phone");
				String email = json.getString("email");
				tvInfo.setText("Hello " + name);
				tvInfo.setVisibility(View.VISIBLE);
				btnLogin.setText("Logout");
				btnPayment.setVisibility(View.VISIBLE);
				btnShowUser.setVisibility(View.VISIBLE);
				isloggedin = true;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void LogOutSuccessListener() {
			// TODO Auto-generated method stub
			tvInfo.setVisibility(View.GONE);
			btnPayment.setVisibility(View.GONE);
			btnShowUser.setVisibility(View.GONE);
			btnLogin.setText("Login");
			isloggedin = false;
			System.out.println("XXXX Logged out successfully");
		}

		@Override
		public void GetFriendListSuccessListener(JSONObject json) {
			System.out.println("XXXX GetFriendListSuccessListener == " + json.toString());
		}

		@Override
		public void LoginFailListener() {
		System.out.println(">>>>>>>>>>>>>>>LOGIN FAILLLLLLLLL");

		}

		@Override
		public void InviteFriendSuccessListener(List arg0) {
			// TODO Auto-generated method stub
			
		}

	}
}
