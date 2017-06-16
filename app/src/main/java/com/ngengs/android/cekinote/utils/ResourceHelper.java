package com.ngengs.android.cekinote.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatDrawableManager;

/**
 * Created by ngengs on 12/6/2016.
 */

public class ResourceHelper {
    @SuppressWarnings("deprecation")
    public static int getColor(@NonNull Context context, @NonNull Integer colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getColor(colorId, null);
        } else {
            return context.getResources().getColor(colorId);
        }
    }

    @SuppressWarnings({"deprecation", "unused"})
    public static Drawable getDrawable(@NonNull Context context, @NonNull Integer drawableId) {
        Drawable placeholder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            placeholder = context.getResources().getDrawable(drawableId, null);
        } else {
            placeholder = AppCompatDrawableManager.get().getDrawable(context, drawableId);
        }
        return placeholder;
    }
}
