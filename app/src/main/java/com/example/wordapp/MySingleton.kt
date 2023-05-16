package com.example.wordapp

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley


class MySingleton private constructor(context: Context) {
    private var requestQueue: RequestQueue?
    val imageLoader: ImageLoader

    init {
        ctx = context
        requestQueue = getRequestQueue()
        imageLoader = ImageLoader(requestQueue,
            object : ImageLoader.ImageCache {
                private val cache: LruCache<String, Bitmap> = LruCache<String, Bitmap>(20)
                override fun getBitmap(url: String): Bitmap? {
                    return cache.get(url)
                }

                override fun putBitmap(url: String, bitmap: Bitmap) {
                    cache.put(url, bitmap)
                }
            })
    }

    fun getRequestQueue(): RequestQueue {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext())
        }
        return requestQueue as RequestQueue
    }

    fun <T> addToRequestQueue(req: Request<T>?) {
        getRequestQueue().add<Any>(req)
    }

    companion object {
        private var instance: MySingleton? = null
        private lateinit var ctx: Context
        @Synchronized
        fun getInstance(context: Context): MySingleton? {
            if (instance == null) {
                instance = MySingleton(context)
            }
            return instance
        }
    }
}

private fun <T> RequestQueue.add(req: T?) {

}
