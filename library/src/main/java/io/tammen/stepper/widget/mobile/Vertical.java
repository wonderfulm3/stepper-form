package io.tammen.stepper.widget.mobile;

import android.content.Context;
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
        inflate(context, R.layout.mobile_vertical_stepper, this);
    }

    @Override
    public void onClick(View v) {
    }

    public void setStepElementDetail(ArrayList<StepElementDetail> stepElementDetail) throws StepperElementException {
        if (stepElementDetail != null && stepElementDetail.size() > 0) {
            Log.d(TAG, "Number of elements: " + stepElementDetail.size());
            //TODO need to build out the layout dynamically off here...
            //Coming in the next week
            // TODO need to handle animation of growing and collapse of step
            this.stepElementDetails = stepElementDetail;
        } else {
            throw new StepperElementException("StepElementDetail may not be null or have a size <= 0",
                    new AnnotationErrorCode(AnnotationErrorCode.INVALID_ELEMENT_SIZE));
        }
    }
}
