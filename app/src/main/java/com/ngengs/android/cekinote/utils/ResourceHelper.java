package com.ngengs.android.cekinote.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatDrawableManager;

/**
 * Created by ngengs on 12/6/2016.
 */

public class ResourceHelper {
    public static int getColor(Context context, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getColor(colorId, null);
        } else {
            return context.getResources().getColor(colorId);
        }
    }

    public static Drawable getDrawable(Context context, int drawableId) {
        Drawable placeholder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            placeholder = context.getResources().getDrawable(drawableId, null);
        } else {
            placeholder = AppCompatDrawableManager.get().getDrawable(context, drawableId);
        }
        return placeholder;
    }
}
