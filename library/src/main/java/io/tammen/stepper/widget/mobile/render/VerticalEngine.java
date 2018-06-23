package io.tammen.stepper.widget.mobile.render;

import android.content.Context;
import android.widget.RelativeLayout;

import io.tammen.stepper.R;
import io.tammen.stepper.widget.mobile.StepElement;
import io.tammen.stepper.widget.mobile.StepElementDetail;

/**
 * Created by Tammen Bruccoleri on 6/16/2018.
 */
public class VerticalEngine {
    private static volatile VerticalEngine instance = null;
    //TODO these will need to be supported during the calculateDimensions step
    /*private final int verticalBarMarginTop = getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_8dp);
    private final int verticalBarMargin = getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_36dp);
    private int verticalBarWidth;
    private final int margin = verticalBarWidth = getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_24dp);*/

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

    public StepElement renderStepElement(Context context, StepElementDetail stepElementDetail, int index) {
        //Time to bust out the drawing
        StepElement stepElement = new StepElement(context);
        stepElement.setId(index);
        stepElement.setStepElementDetails(stepElementDetail);
        stepElement.setLayoutParams(calculateDimensions(context, index));

        return stepElement;
    }

    private RelativeLayout.LayoutParams calculateDimensions(Context context, int stepElementIndex) {
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

            dimensions.topMargin = context.getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_8dp);
            dimensions.setMarginStart(context.getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_24dp));
        }

        return dimensions;
    }
}
