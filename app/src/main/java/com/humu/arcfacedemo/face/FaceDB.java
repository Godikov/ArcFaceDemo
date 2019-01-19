package com.humu.arcfacedemo.face;

import android.util.Log;

import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKVersion;

/**
 * Created by humu on 2019/1/18.
 */

public class FaceDB {

	private final String TAG = this.getClass().toString();

	/**
	 * 在虹软申请的值，换成自己的
	 */
	public static String appid = "9YhNB3R5PkY87JZ9xp6a7s8iGVMD5cMFuVk1JYJRzkNb";
	//人脸追踪key
	public static String ft_key = "CHH7dqdf2LnaNf28BRJCbDQ1BHLetThuJziPeseokAEd";
	//人脸检测key
	public static String fd_key = "CHH7dqdf2LnaNf28BRJCbDQ8LgbmnSBUc49eohvbEk9r";
	//人脸识别key
	public static String fr_key = "CHH7dqdf2LnaNf28BRJCbDQczHeVL5biWY2kNiK9ADeE";
	//年龄识别key
	public static String age_key = "CHH7dqdf2LnaNf28BRJCbDQsK6AodpoJtYHH1rkRnfU7";
	//性别识别key
	public static String gender_key = "CHH7dqdf2LnaNf28BRJCbDQzUVRxEeashTd3aq5hHDMB";

	AFR_FSDKEngine mFREngine; //人脸识别引擎
	AFR_FSDKVersion mFRVersion; //版本

	public FaceDB() {

		mFRVersion = new AFR_FSDKVersion();
		mFREngine = new AFR_FSDKEngine();

		//初始化人脸识别引擎，使用时请替换申请的 APPID 和 SDKKEY
		//AFR_FSDKError用来保存错误信息
		AFR_FSDKError error = mFREngine.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);

		//getCode()获取错误码值 AFR_FSDKError.MOK表示成功
		if (error.getCode() != AFR_FSDKError.MOK) {
			//初始化引擎失败
			Log.e(TAG, "AFR_FSDK_InitialEngine fail! error code :" + error.getCode());
		} else {
			//初始化引擎成功,获取人脸比对版本信息
			mFREngine.AFR_FSDK_GetVersion(mFRVersion);
			//mFRVersion.toString() 获取包含所有人脸比对版本信息的字符串
			Log.d(TAG, "AFR_FSDK_GetVersion=" + mFRVersion.toString());
		}

	}

	public void destroy() {
		if (mFREngine != null) {
			//销毁人脸识别引擎
			mFREngine.AFR_FSDK_UninitialEngine();
		}
	}

}
