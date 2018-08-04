package io.tammen.stepper.widget.mobile;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.tammen.stepper.R;
import io.tammen.stepper.widget.mobile.render.VerticalEngine;

/**
 * Created by Tammen Bruccoleri on 12/30/2017.
 */

public class StepElement extends RelativeLayout implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private final TextView tvIcon, tvTitle, tvSubText;
    private String stepSubText;
    private StepElementDetail stepElementDetail = new StepElementDetail();
    public final View viewStub, verticalBarView;
    private final Button btnContinue, btnCancel;
    private boolean touchEventOccurred;

    public StepElement(Context context) {
        this(context, null);
    }

    public StepElement(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StepElement(Context context, AttributeSet attributeSet, int defStyleAttr) {
        this(context, attributeSet, defStyleAttr, 0);
    }

    public StepElement(Context context, AttributeSet attributeSet, int defStyleAttr, int defStylesRes) {
        super(context, attributeSet, defStyleAttr, defStylesRes);
        View view = inflate(context, R.layout.mobile_step_element, this);
        view.setId(View.generateViewId());

        RelativeLayout rlRowElement = view.findViewById(R.id.rl_row_element);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //TODO need to animate this. Appears to be a bug where if animateLayoutChanges == true
                if (stepElementDetail.isStepExpanded && touchEventOccurred) {
                    touchEventOccurred = false;
                    int locationPivot = (int) btnContinue.getY() + btnContinue.getLayoutParams().height;
                    verticalBarView.setLayoutParams(VerticalEngine
                            .getInstance().setVerticalBarHeight(true, getContext(),
                                    locationPivot, verticalBarView.getLayoutParams()));
                } else if (!stepElementDetail.isStepExpanded && touchEventOccurred) {
                    touchEventOccurred = false;
                    verticalBarView.setLayoutParams(VerticalEngine
                            .getInstance().setVerticalBarHeight(false, getContext(),
                                    0, verticalBarView.getLayoutParams()));
                }
            }
        });
        rlRowElement.setOnClickListener(this);

        btnContinue = view.findViewById(R.id.row_element_continue);
        btnContinue.setOnClickListener(this);

        btnCancel = view.findViewById(R.id.row_element_cancel);
        btnCancel.setOnClickListener(this);

        tvIcon = view.findViewById(R.id.row_element_icon);
        tvTitle = view.findViewById(R.id.row_element_title);
        tvSubText = view.findViewById(R.id.row_element_subtext);
        viewStub = view.findViewById(R.id.row_element_viewstub);
        verticalBarView = view.findViewById(R.id.row_element_vertical_bar);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.StepElement,
                0, 0);
        try {
            String stepText = a.getString(R.styleable.StepElement_stepTitle);
            stepSubText = a.getString(R.styleable.StepElement_stepSubtext);
            stepElementDetail.setStepNumber(a.getInt(R.styleable.StepElement_stepNumber, 0));
            boolean stepOptional = a.getBoolean(R.styleable.StepElement_stepOptional, false);

            @StepIcon.StepIconInterface int stepIcon = a.getInteger(R.styleable.StepElement_stepIcon, 0);
            setStepIcon(stepIcon, stepElementDetail.stepNumber);

            tvTitle.setText(stepText);
            if (stepSubText != null || stepOptional) {
                addOrRemoveProperty(tvTitle, RelativeLayout.CENTER_IN_PARENT, false);
                tvSubText.setVisibility(View.VISIBLE);

                //If the step is Optional && no step subtext is provided
                // default of "Optional" subtext will be provided (at no additional cost)
                if (stepOptional && stepSubText == null) {
                    tvSubText.setText(R.string.io_ta_optional_step);
                } else {
                    tvSubText.setText(stepSubText);
                }
            }
        } finally {
            a.recycle();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        touchEventOccurred = true;
        if (i == R.id.rl_row_element) {
            if (stepElementDetail.isStepExpanded) {
                if (stepElementDetail.isStepDirty) {
                    setStepIcon(StepIcon.CHECKED, stepElementDetail.stepNumber);
                }
                Log.d(TAG, "Step " + stepElementDetail.stepNumber + " already expanded. Time to collapse");
                setViewElements(View.GONE);
            } else {
                stepElementDetail.isStepDirty = true;
                setStepIcon(StepIcon.EDIT, 0);
                Log.d(TAG, "Step " + stepElementDetail.stepNumber + " is not expanded. Time to expand");
                setViewElements(View.VISIBLE);
            }
        } else if (i == R.id.row_element_continue) {
            mockValidateStep(stepElementDetail);
        } else if (i == R.id.row_element_cancel) {
            stepElementDetail.cancelStepElement();
            setViewElements(View.GONE);
            setStepIcon(stepElementDetail.getStepIcon(), stepElementDetail.stepNumber);
        }
    }

    //TODO need to abstract this as an interface to a client to validate if step is valid
    private void mockValidateStep(StepElementDetail stepElementDetail) {
        if (stepElementDetail.getStepRequiresValidation()) {
            stepElementDetail.isStepValid = true;
            if (stepElementDetail.isStepValid()) {
                setStepIcon(StepIcon.CHECKED, stepElementDetail.stepNumber);
                setViewElements(View.GONE);
            } else {
                setStepIcon(StepIcon.ERROR, stepElementDetail.stepNumber);
                if (stepElementDetail.getStepContinueOnValidationFailure()) {
                    setViewElements(View.GONE);
                }
            }
        } else {
            setStepIcon(StepIcon.CHECKED, stepElementDetail.stepNumber);
            setViewElements(View.GONE);
        }
    }

    private void setViewElements(int visibility) {
        if (visibility == View.GONE) {
            stepElementDetail.isStepExpanded = false;
            btnCancel.setVisibility(View.GONE);
            btnContinue.setVisibility(View.GONE);
            viewStub.setVisibility(View.GONE);
        } else if (visibility == View.VISIBLE) {
            stepElementDetail.isStepExpanded = true;
            btnCancel.setVisibility(View.VISIBLE);
            btnContinue.setVisibility(View.VISIBLE);
            viewStub.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw being called");
    }

    // ------- Methods that are part of the Step Element interface --------------->
    public void setStepIcon(@StepIcon.StepIconInterface int stepIcon, int stepNumber) {
        switch (stepIcon) {
            case StepIcon.EDIT:
                tvIcon.setText("");
                tvIcon.setBackgroundResource(R.drawable.ic_edit_circle);
                tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_active_step);
                break;
            case StepIcon.ACTIVE:
                tvIcon.setBackgroundResource(R.drawable.ic_default_circle);
                tvIcon.setText(String.valueOf(stepNumber));
                tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_active_step);
                break;
            case StepIcon.INACTIVE:
                tvIcon.setBackgroundResource(R.drawable.ic_inactive_circle);
                tvIcon.setText(String.valueOf(stepNumber));
                tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_inactive_step);
                break;
            case StepIcon.ERROR_ACTIVE:
                tvIcon.setBackgroundResource(R.drawable.ic_alert);
                stepSubText = getResources().getString(R.string.io_ta_mobile_subtext_alert_message);
                tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_active_error_step);
                tvSubText.setTextAppearance(R.style.io_ta_stepper_form_style_error_subtext);
                break;
            case StepIcon.ERROR:
                tvIcon.setText("");
                tvIcon.setBackgroundResource(R.drawable.ic_alert);
                stepSubText = getResources().getString(R.string.io_ta_mobile_subtext_alert_message);
                tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_inactive_error_step);
                tvSubText.setTextAppearance(R.style.io_ta_stepper_form_style_error_subtext);
                break;
            case StepIcon.CHECKED:
                tvIcon.setText("");
                tvIcon.setBackgroundResource(R.drawable.ic_checkmark_circle_done);
                tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_inactive_step);
                break;
            default:
                tvIcon.setBackgroundResource(R.drawable.ic_inactive_circle);
                tvIcon.setText(stepNumber);
                tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_inactive_step);
                break;
        }
        invalidateAndRequestLayout();
    }

    public void setTvTitle(String stepTitle) {
        Log.d(TAG, "setTvTitle called");
        tvTitle.setText(stepTitle);
    }

    public void setTvSubText(String stepSubText) {
        Log.d(TAG, "setTvSubText called");
        tvSubText.setText(stepSubText);
        //By default this view is GONE (will need to set to GONE if subtext is empty)
        tvSubText.setVisibility(View.VISIBLE);
    }

    public void invalidateAndRequestLayout() {
        invalidate();
        requestLayout();
    }

    public void setStepElementDetails(StepElementDetail stepElementDetails) {
        this.setTvTitle(stepElementDetails.stepTitle);
        this.setStepIcon(stepElementDetails.getStepIcon(), stepElementDetails.stepNumber);
        this.stepElementDetail = stepElementDetails;
    }

    private void addOrRemoveProperty(View view, int property, boolean flag) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (flag) {
            layoutParams.addRule(property);
        } else {
            layoutParams.removeRule(property);
        }
        view.setLayoutParams(layoutParams);
    }
}
