package kr.test.imagelist.imageutil

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


class DiskLruCache {
    private val executorService = ThreadPoolExecutor(0, 1,
            60L, TimeUnit.SECONDS, LinkedBlockingQueue())
    lateinit var cacheDir: String
    private val DISK_CACHE_SIZE = 1024 * 1024 * 50
    private val DISK_CACHE_SUBDIR = "thumbnails"
    @Throws(IOException::class)


    constructor(context: Context) {

        var cachePath = ""
        if ((Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
                        || !Environment.isExternalStorageRemovable())) {
            cachePath = context.getExternalCacheDir().getPath()
        } else {
            cachePath = context.getCacheDir().getPath();
        }


        cacheDir = cachePath + File.separator + DISK_CACHE_SUBDIR + File.separator

        val file = File(cachePath + File.separator + DISK_CACHE_SUBDIR)

        if (!file.exists()) {
            file.mkdir()
        }

    }


    fun set(bitmap: Bitmap, key: String) {


            executorService.execute {


                try {

                    FileOutputStream(cacheDir + key).use({ out ->

                        // write a byte sequence
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)

                        out.close()

                    })
                } catch (e: IOException) {
                    e.printStackTrace()
                }

        }
    }

    fun isExists(key: String): Boolean {


        val file = File(cacheDir + key)

        return file.exists() && file.length() > 0


    }

    fun get(activity: Activity, key: String, diskLruInterFaceCallBack: DiskLruInterFaceCallBack) {


            executorService.execute {


                val bitmap = BitmapFactory.decodeFile(cacheDir + key)

                diskLruInterFaceCallBack.readCompleteCall(activity, bitmap)



        }
    }


}