package io.tammen.stepper.widget.mobile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
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

        //Time to bust out the drawing
        StepElement stepElement = new StepElement(this.getContext());
        stepElement.setTvTitle(stepElementDetails.get(0).getStepTitle());
        stepElement.setStepIcon(stepElementDetails.get(0).getStepIcon(), stepElementDetails.get(0).getStepNumber());
        LayoutParams dimensions = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int margin = getResources().getDimensionPixelSize(R.dimen.io_ta_stepper_form_24dp);
        dimensions.topMargin = margin;
        dimensions.setMarginStart(margin);

        stepElement.setLayoutParams(dimensions);
        relativeLayout.addView(stepElement);
    }
}
