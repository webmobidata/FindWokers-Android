package br.com.webmobidata.findworkers.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;

public class Utils {

    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            Log.d("Font","Erro ao sobreecrever a fonte: " + e.getMessage());
        }
    }

    public static Integer getDp(Context context, Integer valueDP){
        Float scale = context.getResources().getDisplayMetrics().density;
        return (Integer) (int)(valueDP * scale + 0.5f);
    }

}
