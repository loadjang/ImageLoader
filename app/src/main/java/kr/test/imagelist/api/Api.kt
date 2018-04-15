package kr.test.imagelist.api

import android.app.Activity
import android.util.Log
import kr.test.imagelist.BaseApplication
import kr.test.imagelist.MainActivity
import kr.test.imagelist.lib.ImageUtil
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


object Api {

    val HOSTNAME="http://www.gettyimagesgallery.com"


    //@TODO HTTP 응답결과를 Img url 리스트로 변환후 콜백 실행
    fun getHttpImageList( activity: Activity) {


        val client = OkHttpClient.Builder()
                .connectTimeout(5,TimeUnit.SECONDS)
                 .retryOnConnectionFailure(true)
                .build()
        val retrofit = Retrofit.Builder()
                .client(client)

                .baseUrl(HOSTNAME)
                .addConverterFactory(ToStringConverterFactory())
                .build()


        BaseApplication.instance?.progressON(activity)
        val service = retrofit.create(ApiList::class.java!!)
        val call = service.getImageList("")
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                BaseApplication.instance?.progressOFF()
                if (response.isSuccessful) {

                    val str = response.body().string()
                    if (!str.isNullOrEmpty()) {
                        val resultList = ImageUtil.getHtmlImageList(str, "/Images/Thumbnails/")
                        if (activity is MainActivity) {
                            activity.successImageLoaded(resultList)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                BaseApplication.instance?.progressOFF()
                Log.d("HttpOnFailure", t.toString())

            }
        })


    }



}