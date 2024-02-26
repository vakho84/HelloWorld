package com.example.helloworld;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class MyGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        builder
                .setMemorySizeCalculator(calculator)
                .setMemoryCache(new ImageMemoryCache(calculator.getMemoryCacheSize()));
    }

    private static class ImageMemoryCache implements MemoryCache {
        private final MemoryCache internal;

        public ImageMemoryCache(int memoryCacheSize) {
            internal = new LruResourceCache(memoryCacheSize);
        }

        @Override
        public long getCurrentSize() {
            return internal.getCurrentSize();
        }

        @Override
        public long getMaxSize() {
            return internal.getMaxSize();
        }

        @Override
        public void setSizeMultiplier(float multiplier) {
            internal.setSizeMultiplier(multiplier);
        }

        @Nullable
        @Override
        public Resource<?> remove(@NonNull Key key) {
            return internal.remove(key);
        }

        @Nullable
        @Override
        public Resource<?> put(@NonNull Key key, @Nullable Resource<?> resource) {
            return internal.put(key, resource);
        }

        @Override
        public void setResourceRemovedListener(@NonNull ResourceRemovedListener listener) {
            internal.setResourceRemovedListener(listener);
        }

        @Override
        public void clearMemory() {
            internal.clearMemory();
        }

        @Override
        public void trimMemory(int level) {
            internal.trimMemory(level);
        }
    }
}