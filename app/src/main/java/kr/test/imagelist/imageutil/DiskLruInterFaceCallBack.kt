package kr.test.imagelist.imageutil

import android.app.Activity
import android.graphics.Bitmap

interface  DiskLruInterFaceCallBack
{

    fun readCompleteCall(activity: Activity,bitmap : Bitmap){


    }

    fun writeCompleteCall(){


    }


}