package com.king.app.workhelper;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author huoguangxu
 * @since 19-8-27.
 */
@GlideModule
public class CustomGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(client);
        glide.getRegistry().replace(GlideUrl.class, InputStream.class, factory);
    }
}
