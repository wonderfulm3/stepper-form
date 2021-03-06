package io.tammen.stepper.widget.mobile.render;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import io.tammen.stepper.R;
import io.tammen.stepper.widget.mobile.StepElement;
import io.tammen.stepper.widget.mobile.StepElementDetail;

/**
 * Created by Tammen Bruccoleri on 6/16/2018.
 */
public class VerticalEngine {
    private static volatile VerticalEngine instance = null;

    private VerticalEngine() {
    }

    public static VerticalEngine getInstance() {
        if (instance == null) {
            synchronized (VerticalEngine.class) {
                if (instance == null) {
                    instance = new VerticalEngine();
                }
            }
        }
        return instance;
    }

    public StepElement renderStepElement(Context context, StepElementDetail stepElementDetail, int index, int priorElementStep) {
        //Time to bust out the drawing
        StepElement stepElement = new StepElement(context);
        stepElement.setId(View.generateViewId());
        stepElement.setStepElementDetails(stepElementDetail);
        stepElement.setLayoutParams(calculateDimensions(context, index, priorElementStep));

        return stepElement;
    }

    public ViewGroup.LayoutParams setVerticalBarHeight(boolean isExpanded, boolean isShowingProgress, Context context, int locationPivot, ViewGroup.LayoutParams layoutParams) {
        if (isExpanded) {
            layoutParams.height = (locationPivot - layoutParams.height) + (int) DrawingHelper.getInstance().convertDpToPixel(24, context);
            return layoutParams;
        } else if (isShowingProgress) {
            //TODO this needs to be worked better. Should be getting the next step circle and measure the distance
            layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_72dp);
            return layoutParams;
        } else {
            layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_32dp);
            return layoutParams;
        }
    }

    private RelativeLayout.LayoutParams calculateDimensions(Context context, int stepElementIndex, int priorElementStep) {
        RelativeLayout.LayoutParams dimensions;
        // 0 == The very most top StepElement otherwise every other StepElement gets secondary dimensions
        if (stepElementIndex == 0) {
            dimensions = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            dimensions.topMargin = context.getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_8dp);
            dimensions.setMarginStart(context.getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_24dp));
        } else {
            dimensions = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            dimensions.addRule(RelativeLayout.BELOW, priorElementStep);
            dimensions.topMargin = context.getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_8dp);
            dimensions.setMarginStart(context.getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_24dp));
        }

        return dimensions;
    }
}
