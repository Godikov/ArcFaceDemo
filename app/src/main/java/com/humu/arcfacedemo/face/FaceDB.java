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
	public static String ft_key = "CHH7dqdf2LnaNf28BRJCbDQ1BHLetThuJziPeseokAEd";
	public static String fd_key = "CHH7dqdf2LnaNf28BRJCbDQ8LgbmnSBUc49eohvbEk9r";
	public static String fr_key = "CHH7dqdf2LnaNf28BRJCbDQczHeVL5biWY2kNiK9ADeE";
	public static String age_key = "CHH7dqdf2LnaNf28BRJCbDQsK6AodpoJtYHH1rkRnfU7";
	public static String gender_key = "CHH7dqdf2LnaNf28BRJCbDQzUVRxEeashTd3aq5hHDMB";

	String mDBPath;
	AFR_FSDKEngine mFREngine;
	AFR_FSDKVersion mFRVersion;
	boolean mUpgrade;

	public FaceDB(String path) {
		mDBPath = path;
		mFRVersion = new AFR_FSDKVersion();
		mUpgrade = false;
		mFREngine = new AFR_FSDKEngine();
		AFR_FSDKError error = mFREngine.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
		if (error.getCode() != AFR_FSDKError.MOK) {
			Log.e(TAG, "AFR_FSDK_InitialEngine fail! error code :" + error.getCode());
		} else {
			mFREngine.AFR_FSDK_GetVersion(mFRVersion);
			Log.d(TAG, "AFR_FSDK_GetVersion=" + mFRVersion.toString());
		}
	}

	public void destroy() {
		if (mFREngine != null) {
			mFREngine.AFR_FSDK_UninitialEngine();
		}
	}

	public boolean upgrade() {
		return false;
	}
}
