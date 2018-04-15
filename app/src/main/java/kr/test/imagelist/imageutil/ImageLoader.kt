package kr.test.imagelist.imageutil

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import okhttp3.*
import java.io.BufferedInputStream
import java.io.IOException
import java.util.concurrent.TimeUnit


object ImageLoader {


    lateinit var diskLruCache: DiskLruCache
    lateinit var memoryCache: MemoryCache
    var placeHolder: Drawable? = null
    fun init(activity: Activity) {
        diskLruCache = DiskLruCache(activity)
        memoryCache = MemoryCache()
    }


    fun StringReplace(str: String): String {

        var text=str
        text=text.replace(":","").replace("/","").replace(".","")
        text=text+".jpg"
        return text;
    }

    fun setImageView(activity: Activity, url: String, imgView: ImageView) {

        var key = StringReplace(url)
        if (placeHolder != null) {
            imgView.setImageDrawable(placeHolder)
        }
        if (memoryCache.bitmapCache.get(key) != null) {
            imgView.setImageBitmap(memoryCache.bitmapCache.get(key))
        } else if (diskLruCache.isExists(key)) {
            val diskLruInterFaceCallBack = object : DiskLruInterFaceCallBack {
                override fun readCompleteCall(activity: Activity,bitmap: Bitmap) {
                    Log.d("readCompleteCall",bitmap.byteCount.toString())
                    activity.runOnUiThread{
                        imgView.setImageBitmap(bitmap)
                    }

                }
            }
            diskLruCache.get(activity,key, diskLruInterFaceCallBack)
        } else {


            //네트워크에서 이미지를 받아온다.
            val client = OkHttpClient().newBuilder().connectTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(true).build()

            val request = Request.Builder()
                    .url(url)
                    .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    val inputStream = response?.body()?.byteStream() // Read the data from the stream
                    val bufferedInputStream = BufferedInputStream(inputStream)
                    val bitmap = BitmapFactory.decodeStream(inputStream)



                    diskLruCache.set(bitmap, key)
                    memoryCache.bitmapCache.put(key, bitmap)


                    activity.runOnUiThread() {

                        imgView.setImageBitmap(bitmap)
                    }





                }

                override fun onFailure(call: Call?, e: IOException?) {

                    System.out.println("request failed: " + e?.message);
                }


            })

        }
    }


}