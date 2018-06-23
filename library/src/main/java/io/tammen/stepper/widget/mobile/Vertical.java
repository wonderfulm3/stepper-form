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
                //Draw StepElement's
                drawStepElement();
            } else {
                throw new StepperElementException(getResources().getString(R.string.io_ta_mobile_exception_stepper_element_size_invalid),
                        new AnnotationErrorCode(AnnotationErrorCode.INVALID_ELEMENT_SIZE));
            }
        } catch (StepperElementException ex) {
            Log.e(TAG, "Exception: " + ex.getMessage());
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

    /**
     * Renders the Step Element within the Vertical layout view.
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    private void drawStepElement() {
        //Going from an exception to where we now have valid Stepper elements
        if (ivWhenRenderException.getVisibility() == View.VISIBLE) {
            ivWhenRenderException.setVisibility(View.GONE);
            tvStepElementTitleException.setVisibility(View.GONE);
        }

        //TODO temp rendering for 1 element
        for (int i = 0; i < stepElementDetailArrayList.size(); i++) {
            if (i == 0) {
                relativeLayout.addView(VerticalEngine.getInstance().renderStepElement(this.getContext(),
                        this.stepElementDetailArrayList.get(i), i));
            }
        }

        //TODO leaving for reference needs to be removed when above "todo" is completed
        /*int idValue = 1;
        //Time to bust out the drawing
        StepElement stepElement = new StepElement(this.getContext());
        stepElement.setTvTitle(stepElementDetailArrayList.get(0).stepTitle);
        stepElement.setStepIcon(stepElementDetailArrayList.get(0).getStepIcon(), stepElementDetailArrayList.get(0).stepNumber);
        stepElement.invalidateAndRequestLayout();

        LayoutParams dimensions = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        dimensions.topMargin = margin;
        dimensions.setMarginStart(margin);

        stepElement.setLayoutParams(dimensions);
        stepElement.setId(idValue);
        stepElement.setStepElementDetails(stepElementDetailArrayList.get(0));

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
        stepElement2.setTvTitle(stepElementDetailArrayList.get(1).stepTitle);
        stepElement2.setStepIcon(stepElementDetailArrayList.get(1).getStepIcon(), stepElementDetailArrayList.get(1).stepNumber);
        stepElement2.setTvSubText(stepElementDetailArrayList.get(1).stepSubText);
        stepElement2.invalidateAndRequestLayout();

        LayoutParams dimensions1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        dimensions1.addRule(RelativeLayout.BELOW, verticalSpace.getId());
        dimensions1.setMargins(0, verticalBarMarginTop, 0, 0);
        dimensions1.setMarginStart(margin);

        stepElement2.setLayoutParams(dimensions1);
        idValue++;
        stepElement2.setId(idValue);
        stepElement2.setStepElementDetails(stepElementDetailArrayList.get(1));

        relativeLayout.addView(stepElement2);*/
    }
}
