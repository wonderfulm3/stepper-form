package io.tammen.stepper.widget.mobile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import io.tammen.stepper.R;
import io.tammen.stepper.widget.mobile.exception.AnnotationErrorCode;
import io.tammen.stepper.widget.mobile.exception.StepperElementException;

/**
 * Created by Tammen Bruccoleri on 12/28/2017.
 *
 */

public class Vertical extends RelativeLayout implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private RelativeLayout relativeLayout;
    int verticalBarWidth;
    int verticalBarMarginTop = getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_8dp);
    int verticalBarMargin = getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_36dp);
    int margin = verticalBarWidth = getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_24dp);

    private ArrayList<StepElementDetail> stepElementDetails = null;

    public Vertical(Context context) {
        this(context, null);
    }

    public Vertical(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Vertical(Context context, AttributeSet attributeSet, int defStyleAttr) {
        this(context, attributeSet, defStyleAttr, 0);
    }

    public Vertical(Context context, AttributeSet attributeSet, int defStyleAttr, int defStylesRes) {
        super(context, attributeSet, defStyleAttr, defStylesRes);
        relativeLayout = (RelativeLayout) inflate(context, R.layout.mobile_vertical_stepper, this);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        renderStepElements();
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    private void renderStepElements() {
        try {
            if (stepElementDetails.size() > 0) {
                //Draw StepElement's
                drawStepElement();
            } else {
                throw new StepperElementException("StepElementDetail may not be null or have a size <= 0",
                        new AnnotationErrorCode(AnnotationErrorCode.INVALID_ELEMENT_SIZE));
            }
        } catch (StepperElementException ex) {
            Log.e(TAG, "Exception: " + ex.getMessage());
        }
    }

    /**
     * Initializer of the Vertical layout with Step Elements.
     *
     * @param stepElementDetail ArrayList elements that contain build objects that represent the StepElementDetail
     * @throws StepperElementException Throws an Exception. Better catch/handle it...
     */
    public void setStepElementDetail(@NonNull ArrayList<StepElementDetail> stepElementDetail) throws StepperElementException {
        if (stepElementDetail.size() > 0) {
            Log.d(TAG, "Number of elements: " + stepElementDetail.size());
            this.stepElementDetails = stepElementDetail;
        } else {
            throw new StepperElementException("StepElementDetail may not be null or have a size <= 0",
                    new AnnotationErrorCode(AnnotationErrorCode.INVALID_ELEMENT_SIZE));
        }
    }

    /**
     *
     * Renders the Step Element within the Vertical layout view.
     *
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    private void drawStepElement() {
        Log.d(TAG, "Step Icon - 1 (int): " + stepElementDetails.get(0).getStepIcon());
        Log.d(TAG, "Layout width: " + relativeLayout.getWidth() + " height: " + relativeLayout.getHeight());
        int idValue = 1;
        //Time to bust out the drawing
        StepElement stepElement = new StepElement(this.getContext());
        stepElement.setTvTitle(stepElementDetails.get(0).stepTitle);
        stepElement.setStepIcon(stepElementDetails.get(0).getStepIcon(), stepElementDetails.get(0).stepNumber);
        LayoutParams dimensions = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        dimensions.topMargin = margin;
        dimensions.setMarginStart(margin);

        stepElement.setLayoutParams(dimensions);
        stepElement.setId(idValue);
        stepElement.setStepElementDetails(stepElementDetails.get(0));

        relativeLayout.addView(stepElement);

        AppCompatImageView verticalSpace = new AppCompatImageView(this.getContext());
        verticalSpace.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_vertical_layer_line, null));

        LayoutParams verticalBar = new LayoutParams(LayoutParams.WRAP_CONTENT, verticalBarWidth);
        verticalBar.addRule(RelativeLayout.BELOW, stepElement.getId());
        verticalBar.setMarginStart(verticalBarMargin);
        verticalBar.setMargins(0, verticalBarMarginTop, 0, 0);
        verticalSpace.setLayoutParams(verticalBar);
        idValue++;
        verticalSpace.setId(idValue);

        relativeLayout.addView(verticalSpace);

        StepElement stepElement2 = new StepElement(this.getContext());
        stepElement2.setTvTitle(stepElementDetails.get(1).stepTitle);
        stepElement2.setStepIcon(stepElementDetails.get(1).getStepIcon(), stepElementDetails.get(1).stepNumber);

        LayoutParams dimensions1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        dimensions1.addRule(RelativeLayout.BELOW, verticalSpace.getId());
        dimensions1.setMargins(0, verticalBarMarginTop, 0, 0);
        dimensions1.setMarginStart(margin);

        stepElement2.setLayoutParams(dimensions1);
        idValue++;
        stepElement2.setId(idValue);
        stepElement2.setStepElementDetails(stepElementDetails.get(1));

        relativeLayout.addView(stepElement2);
    }
}
