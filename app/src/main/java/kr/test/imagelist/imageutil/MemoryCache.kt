package kr.test.imagelist.imageutil

import android.graphics.Bitmap
import android.util.LruCache

class MemoryCache {

    lateinit var bitmapCache: LruCache<String, Bitmap>

    constructor() {
        val cacheSize = 4 * 1024 * 1024; // 4MiB
        bitmapCache = object : LruCache<String, Bitmap>(cacheSize) {

        }


        fun makeCacheKey(url: String): String {


            return url;


        }

        fun getImageCache(key: String): Bitmap {
            synchronized(bitmapCache) {

                return bitmapCache.get(key)

            }


        }

        fun setImageCache(key: String, value: Bitmap) {
            synchronized(bitmapCache) {

                if (bitmapCache.get(key) == null) {
                    bitmapCache.put(key, value);
                }
            }

        }

    }
}