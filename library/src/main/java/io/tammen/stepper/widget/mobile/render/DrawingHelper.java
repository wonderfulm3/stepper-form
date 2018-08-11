package io.tammen.stepper.widget.mobile.render;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Tammen Bruccoleri on 6/16/2018.
 */
public class DrawingHelper {
    private static volatile DrawingHelper instance = null;

    private DrawingHelper() {
    }

    public static DrawingHelper getInstance() {
        if (instance == null) {
            synchronized (DrawingHelper.class) {
                if (instance == null) {
                    instance = new DrawingHelper();
                }
            }
        }
        return instance;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public View replaceViewStub(RelativeLayout parent, View viewStub, View stepView) {
        int id = viewStub.getId();
        ViewGroup.LayoutParams layout = viewStub.getLayoutParams();
        parent.removeView(viewStub);
        viewStub = stepView;
        viewStub.setLayoutParams(layout);
        viewStub.setId(id);
        viewStub.setVisibility(View.GONE);
        return viewStub;
    }
}
