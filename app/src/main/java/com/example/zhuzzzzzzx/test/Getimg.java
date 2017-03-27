package com.example.zhuzzzzzzx.test;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.attr.key;

/**
 * Created by Zhuzzzzzzx on 2017/3/19.
 */

public class Getimg extends Application{
    private String uri;
    private ImageView imageView;
    private Bitmap bitmap;
    private InputStream is;
    int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
    int cacheSize = maxMemory/2;
    private LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
        protected int sizeOf(String key, Bitmap bitmap) {
            return bitmap.getByteCount();
        }
    }; // 初始化

    /**
     * 获取图片的url地址
     *
     * @param uri
     */
    public void load(String uri) {
        this.uri = uri;
        new Thread(runnable).start();
    }

    public void into(ImageView imageView) {
        this.imageView = imageView;
    }

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Bitmap bitmap2 = null;
                if(mMemoryCache.get(uri)!=null) {
                    bitmap2 = mMemoryCache.get(uri);
                    Log.d("0123", "handleMessage: "+uri);
                    Log.d("0123", "handleMessage: "+bitmap2);
                    imageView.setImageBitmap(bitmap2);
                }
                else{
                    new Thread(runnable).start();
                }
            }
            }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(uri);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(5000);
                if (conn.getResponseCode() == 200) {
                    is = conn.getInputStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 10;
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeStream(is,null,options);
                    if (mMemoryCache.get(uri)==null&&uri!=null&&bitmap!=null) {
                        mMemoryCache.put(uri, bitmap);
                    }
                    Message message = new Message();
                    message.what = 1;
                    handle.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


}
