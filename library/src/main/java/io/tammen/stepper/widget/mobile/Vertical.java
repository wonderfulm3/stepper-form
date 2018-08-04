package io.tammen.stepper.widget.mobile;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.Size;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import io.tammen.stepper.R;
import io.tammen.stepper.widget.mobile.exception.AnnotationErrorCode;
import io.tammen.stepper.widget.mobile.exception.StepperElementException;
import io.tammen.stepper.widget.mobile.render.VerticalEngine;

/**
 * Created by Tammen Bruccoleri on 12/28/2017.
 */

public class Vertical extends RelativeLayout implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private final int MIN_STEP_ELEMENT_SIZE = 2;
    private final RelativeLayout relativeLayout;
    private final AppCompatImageView ivWhenRenderException;
    private final TextView tvStepElementTitleException;

    @Size(min = 2)
    private ArrayList<StepElementDetail> stepElementDetailArrayList = null;

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
        ivWhenRenderException = (relativeLayout).findViewById(R.id.step_element_exception);
        tvStepElementTitleException = (relativeLayout).findViewById(R.id.step_element_title_exception);
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
            if (stepElementDetailArrayList != null && stepElementDetailArrayList.size() >= MIN_STEP_ELEMENT_SIZE) {
                //Start of drawing StepElement's
                //Going from an exception to where we now have valid Stepper elements
                if (ivWhenRenderException.getVisibility() == View.VISIBLE) {
                    ivWhenRenderException.setVisibility(View.GONE);
                    tvStepElementTitleException.setVisibility(View.GONE);
                }

                StepElement element;
                int previousId = 0;
                for (int i = 0; i < stepElementDetailArrayList.size(); i++) {
                    if (i == 0) {
                        element = VerticalEngine.getInstance().renderStepElement(this.getContext(),
                                this.stepElementDetailArrayList.get(i), i, previousId);
                        previousId = element.getId();
                        relativeLayout.addView(element);
                    } else {
                        element = VerticalEngine.getInstance().renderStepElement(this.getContext(),
                                this.stepElementDetailArrayList.get(i), i, previousId);
                        previousId = element.getId();

                        //For the last StepElement we don't want to render the vertical bar
                        if (i == stepElementDetailArrayList.size() - 1) {
                            element.verticalBarView.setVisibility(View.GONE);
                        }
                        relativeLayout.addView(element);
                    }
                }
            } else {
                throw new StepperElementException(getResources().getString(R.string.io_ta_mobile_exception_stepper_element_size_invalid),
                        new AnnotationErrorCode(AnnotationErrorCode.INVALID_ELEMENT_SIZE));
            }
        } catch (StepperElementException ex) {
            Log.e(TAG, "Exception: " + ex.getMessage());
            ex.printStackTrace();
            ivWhenRenderException.setVisibility(View.VISIBLE);
            tvStepElementTitleException.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Initializer of the Vertical layout with Step Elements.
     *
     * @param stepElementDetailArrayList ArrayList elements that contain build objects that represent the StepElementDetail
     * @throws StepperElementException Throws an Exception. Better catch/handle it...
     */
    public void setStepElementDetail(@Size(min = 2) ArrayList<StepElementDetail> stepElementDetailArrayList) throws StepperElementException {
        if (stepElementDetailArrayList.size() >= MIN_STEP_ELEMENT_SIZE) {
            Log.d(TAG, "Number of elements: " + stepElementDetailArrayList.size());

            //Determining step number assignment based off array index
            int size = stepElementDetailArrayList.size();
            for (int i = 0; i < size; i++) {
                stepElementDetailArrayList.get(i).setStepNumber(i + 1);
            }

            this.stepElementDetailArrayList = stepElementDetailArrayList;
        } else {
            throw new StepperElementException(getResources().getString(R.string.io_ta_mobile_exception_stepper_element_size_invalid),
                    new AnnotationErrorCode(AnnotationErrorCode.INVALID_ELEMENT_SIZE));
        }
    }
}
