Other languages: [Vietnamese](README_VI.md)
##DOCUMENT FOR INTEGRATED SYSTEM INGAME SDK 1.0 

**Introduction**

InGame SDK is user and payment integrated system for your mobile application. This SDK provides a simple way to integrate and satisfies user account management, payment requirements on mobile. It provides solutions for payment methods such as: Phone Card, Internet Bankingand Google Play Payment.

**Steps to integrate SDK:**

​1. Download SDK

​2. Integrate & configure SDK

​3. Declare variables, initialization and call SDK function from your application

​4. Run SDK samples

<hr/>

### I. Download SDK

** Download InGame SDK for Android here: https://github.com/ingamevietnam/android-ingamesdklib/archive/master.zip

Or if you use any git tool, clone this url: https://github.com/ingamevietnam/android-ingamesdklib.git

###II. Integrate & configure SDK
####A. Integrate: 
The SDK includes 2 parts 
- <b>Requirement libraries:</b>  are files have the extension <b>*.jar</b> inside <b>libs/</b> folder: 
  
    Copy these <b>*.jar</b> files to <b>libs/</b> folder in your application <i>(If no directory libs / you can create)</i>

- <b>Resources :</b> The resources needed by the library <b>ingamesdk1.0.jar</b> 

    ![add](http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture1_zpsczrmwmy4.png)
   
    <i>The directory structure</i>
    
     Same as above you simply copy and paste the folder <b>res/</b> in your application.

####B. Configure:

<b>[Notice] You need the following information before proceeding with the configuration:</b> <br/>
> <b><i>*Get ```<application_license_key>``` value from Google</b></i><br/>
>  1> First, log into your <b>Google Play Developer Console</b>.<br/>
>  2> Next, click on <b>All Applications</b> and find the application that you'd like to review.<br/>
> 3>After that, go to the section <b>Services and APIs</b><br/>
>![add](http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture2_zpsoquddje9.jpg)<br/>
 You will find your license key under the section labeled <b>Your License Key For this Application</b>.
>
<b><i>*Get ```<your_facebook_application_id>``` value from Facebook</b></i><br/>
>1> First, log into your https://developers.facebook.com/<br/>
>2> Next, click on <b>My Apps</b> and find the application that you'd like to review.<br/>
>3> The <your_facebook_application_id> under the section <b>App ID</b><br/>
>![add](http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture3_zpsajdtkwmv.png)

<b>Notice] The values be located in the < > you need to replace them as follows:</b>
> Replace ```<your_package_name>``` with your ```application packagename```.<br/>
> Replace ```<your_facebook_application_id>``` with your ```facebook app id```.<br/>
> Replace ```<application_license_key>``` with your ```license key```.<br/>



<b>Configure strings.xml</b>

　　Open <b>strings.xml</b> file in your application
　　
``` xml
　　　　<string name="facebook_appId"><your_facebook_application_id></string>
　　　　<string name="google_license_key"><application_license_key></string>
``` 
<b>Configure AndroidMainfest.xml</b>

　　Open <b>AndroidMainfest.xml</b> file in your application
-   Add ```<permission>``` tags to grant access to the system:

```
　　<uses-permission android:name="android.permission.INTERNET" />
　　<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
　　<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
　　<uses-permission android:name="android.permission.READ_PHONE_STATE" />
　　<uses-permission android:name="android.permission.WAKE_LOCK" />
　　<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
　　<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
　　
 	<!-- Google Cloud Message Permission -->
　　<permission
　　　　android:name="<your_package_name>.permission.C2D_MESSAGE"
　　　　android:protectionLevel="signature" />
    	<uses-permission android:name="<your_package_name>.C2D_MESSAGE" />
    	<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />

　　<!-- Google IAP Permission -->
    	<uses-permission android:name="android.permission.GET_ACCOUNTS" />
　　<uses-permission android:name="com.android.vending.BILLING" />
```
- Add ```<receiver>,<service> ``` tags inside ```<application>``` tag to receive message from <b>Google Cloud Messaging</b>:

```
　　<application
　　　　...........................
　　　　<receiver
　　　　　　android:name="com.ingamesdk.pushnotification.GcmBroadcastReceiver"
　　　　　　android:permission="com.google.android.c2dm.permission.SEND" >
　　　　　　<intent-filter>
　　　　　　　　<!-- Receives the actual messages. -->
　　　　     		<action android:name="com.google.android.c2dm.intent.RECEIVE" />
　　　　　　　　<category android:name="<your_package_name>" />
　　　　　　</intent-filter>
　　　　</receiver>
　　　　<service android:name="com.ingamesdk.pushnotification.GcmIntentService" />
　　　　...........................
　　</application>
```
- Add ```<meta-data>``` tags inside ```<application>``` tag to initial value for system

```
　　<application
　　　　...........................
　　　　<meta-data
　　　　　　android:name="com.facebook.sdk.ApplicationId"
　　　　　　android:value="@string/facebook_appId" />
　　　　<meta-data
　　　　　　android:name="com.google.android.gms.version"
　　　　　　android:value="@integer/google_play_services_version" />
　　　　<meta-data
　　　　　　android:name="com.IngameSDK.GoogleIAPKey"
　　　　　　android:value="@string/google_license_key" />
　　　　...........................
　　</application>
```
- Add ```<activity>``` tags to configure interface:

```
　　<application>
　　　　...........................
 		<activity
　　　　　　android:name="com.ingamesdk.ui.LoginActivity"
            	android:configChanges="orientation|keyboardHidden|screenSize"
            	android:windowSoftInputMode="adjustPan" />
        	<activity
            	android:name="com.ingamesdk.ui.PaymentActivity"
            	android:configChanges="orientation|keyboardHidden|screenSize"
            	android:windowSoftInputMode="adjustPan" />
        	<activity
            	android:name="com.ingamesdk.ui.UserInfoActivity"
            	android:configChanges="orientation|keyboardHidden|screenSize|locale"
            	android:windowSoftInputMode="adjustPan" />
        	<activity android:name="com.facebook.LoginActivity" />
　　　　...........................
　　</application>
```
###III. Declare variables, initialization and call SDK function from your application
<b>Create ```Receiver.java``` to receive events from the SDK</b>
```
　　private class GameReceiver extends IGReceiver {
		
　　　　@Override
		public void onLoginSuccess(IGSession session) {
		}

		@Override
		public void onLogoutSuccess() {
		}
	}
	```
>Through the session variable you can get account information by calling the following functions:<br/>
> ```session.getUserName()```: Username <br/>
> ```session.getUserId()```: Account ID <br/>
> ```session.getAccessToken()```: Access token <br/>
> ```session.getEmail()```: Email <br/>
> ```session.getPhone()```: Phone number <br/>

　　
<b>Declare the following variables inside the main Activity class of applications:</b>

	public String app_id = "911fe9dbd0f94625b4b591301c0f3818"; // your Application ID
	public String app_key = "a2de0d67a7aecbcc89293fbed6712b1a"; // your Application Key
	private GameReceiver game_receiver = new GameReceiver();
	private IntentFilter filter = new IntentFilter();
	public static InGameSDK ingame_sdk = InGameSDK.getInstance(); // instance of InGameSDK
　　
<b>Set the values to the function InGameSDK inside onCreate(...)</b>

	ingame_sdk.init(this, true, true, callback_url, this.app_id, this.app_key);

<b>The parameters of ingame_sdk.init(...)</b>

```
　　public void init(Activity context, boolean isButton, boolean isAutoLogin, String callback_url, String app_id, String 	app_key) {...}
```

>```context```:  The main Activity of your application<br/>
>```isButton```: use SDK shorcut Button (True/False)<br/>
>```isAutoLogin```: use Auto login function (True/False)<br/>
>```callback_url```: The call back link of your server<br/>
>```app_id```: Your application ID issued by Ingame<br/>
>```app_key```: Your application key issued by Ingame<br/>

<b>Add the following to the corresponding handle each functions</b>
```
　　@Override
	protected void onResume() {
		super.onResume();
		filter.addAction(this.getPackageName() + "ingame.login.success");
		filter.addAction(this.getPackageName() + "ingame.logout.success");
		registerReceiver(game_receiver, filter);
		ingame_sdk.addSDKButton(this);
		ingame_sdk.setContext(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		ingame_sdk.removeSDKButton(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(game_receiver);
	}
```

<b>Call the function corresponding that the SDK provides functionality for each operation:</b>
```
　　Registration: 				ingame_sdk.callRegister();
　　Log in:				       ingame_sdk.callLogin();
　　Log out: 					 ingame_sdk.callLogout();
　　User information: 			ingame_sdk.callshowUserInfo();
　　Payment: 				 	ingame_sdk.callPayment(String game_order); // game_order: Transaction code is created by Developer (less than 50 characters).
```


####IV. Run SDK samples

You can see the more detail in the attached sample code. 