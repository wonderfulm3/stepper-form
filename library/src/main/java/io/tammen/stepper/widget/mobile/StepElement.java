package io.tammen.stepper.widget.mobile;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.tammen.stepper.R;
import io.tammen.stepper.widget.mobile.interfaces.StepValidationListener;
import io.tammen.stepper.widget.mobile.render.DrawingHelper;
import io.tammen.stepper.widget.mobile.render.VerticalEngine;

/**
 * Created by Tammen Bruccoleri on 12/30/2017.
 */

public class StepElement extends RelativeLayout implements StepValidationListener, View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private final TextView tvIcon, tvTitle, tvSubText;
    private String stepSubText;
    private StepElementDetail stepElementDetail = new StepElementDetail();
    public final View verticalBarView;
    private View viewStub;
    private final Button btnContinue, btnCancel;
    private boolean touchEventOccurred;
    private final RelativeLayout rlRowElement;
    private final ProgressBar stepValidationProgressBar;//TODO may want to provide custom progress feedback
    private final Handler handler = new Handler();//TODO this is for mocking purpose only
    private boolean retryValidation;

    public StepElement(Context context) {
        this(context, null);
    }

    public StepElement(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StepElement(Context context, AttributeSet attributeSet, int defStyleAttr) {
        this(context, attributeSet, defStyleAttr, 0);
    }

    //TODO remove this as this is for mocking purpose only
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //mocking validation failed (this could be a server side response, etc)
            processValidationDrawing(stepElementDetail);
            if (retryValidation) {
                handler.postDelayed(runnable, 1000);
            }
        }
    };

    public StepElement(Context context, AttributeSet attributeSet, int defStyleAttr, int defStylesRes) {
        super(context, attributeSet, defStyleAttr, defStylesRes);
        View view = inflate(context, R.layout.mobile_step_element, this);
        view.setId(View.generateViewId());

        rlRowElement = view.findViewById(R.id.rl_row_element);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //TODO need to animate this. Appears to be a bug where if animateLayoutChanges == true
                Log.d(TAG, "onGlobalLayout: " + stepElementDetail.isStepExpanded + ", " + touchEventOccurred);
                if (stepElementDetail.isStepExpanded && touchEventOccurred) {
                    touchEventOccurred = false;
                    int locationPivot = (int) btnContinue.getY() + btnContinue.getLayoutParams().height;
                    verticalBarView.setLayoutParams(VerticalEngine
                            .getInstance().setVerticalBarHeight(true,
                                    stepElementDetail.isStepInValidationState, getContext(),
                                    locationPivot, verticalBarView.getLayoutParams()));
                } else if (!stepElementDetail.isStepExpanded && touchEventOccurred) {
                    touchEventOccurred = false;
                    verticalBarView.setLayoutParams(VerticalEngine
                            .getInstance().setVerticalBarHeight(false,
                                    stepElementDetail.isStepInValidationState, getContext(),
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
        stepValidationProgressBar = view.findViewById(R.id.row_progress_bar);

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
                setViewElements(View.GONE, false);
            } else {
                stepElementDetail.isStepDirty = true;
                setStepIcon(StepIcon.EDIT, 0);
                Log.d(TAG, "Step " + stepElementDetail.stepNumber + " is not expanded. Time to expand");
                setViewElements(View.VISIBLE, false);
            }
        } else if (i == R.id.row_element_continue) {
            //TODO Need to use a ObservableBoolean or listener callbacks this is for mocking only!!!
            handler.postDelayed(runnable, 1000);

            stepElementDetail.stepButtonListener.onStepContinueClicked(stepElementDetail.stepNumber);
            processValidationDrawing(stepElementDetail);
        } else if (i == R.id.row_element_cancel) {
            stepElementDetail.stepButtonListener.onStepCancelClicked(stepElementDetail.stepNumber);
            stepElementDetail.cancelStepElement();
            setViewElements(View.GONE, false);
            setStepIcon(stepElementDetail.getStepIcon(), stepElementDetail.stepNumber);
        }
    }

    //TODO this is a temp method and will need to be adjusted on Observer callbacks
    private void processValidationDrawing(StepElementDetail stepElementDetail) {
        Log.d(TAG, "Validation checking...");
        //Honor the User cancelling the step. This takes precedences over the validation.
        if (stepElementDetail.stepHasValidationProgressBar && stepElementDetail.stepIsCancelled) {
            setViewElements(View.VISIBLE, false);
            stepElementDetail.stepIsCancelled = false;
            return;
        }

        if (stepElementDetail.getStepRequiresValidation()) {
            setStepIcon(StepIcon.EDIT, stepElementDetail.stepNumber);

            if (stepElementDetail.stepHasValidationProgressBar && stepElementDetail.isStepInValidationState) {
                setViewElements(View.GONE, true);
            }

            if (stepElementDetail.isStepValid()) {
                setStepIcon(StepIcon.CHECKED, stepElementDetail.stepNumber);
                setViewElements(View.GONE, false);
            } else if (!stepElementDetail.isStepValid() && stepElementDetail.stepContinueOnValidationFailure) {
                setStepIcon(StepIcon.ERROR, stepElementDetail.stepNumber);
                setViewElements(View.GONE, false);
            } else if (!stepElementDetail.isStepValid() && !stepElementDetail.isStepInValidationState) {
                setStepIcon(StepIcon.ERROR, stepElementDetail.stepNumber);
                setViewElements(View.VISIBLE, false);
            }
        } else {
            setStepIcon(StepIcon.CHECKED, stepElementDetail.stepNumber);
            setViewElements(View.GONE, false);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw being called");
    }

    private void setViewElements(int visibility, boolean showProgressBar) {
        if (showProgressBar) {
            stepValidationProgressBar.setVisibility(View.VISIBLE);
            retryValidation = true;
            touchEventOccurred = true;
        } else {
            stepValidationProgressBar.setVisibility(View.GONE);
            retryValidation = false;
            touchEventOccurred = true;
        }
        if (visibility == View.GONE) {
            stepElementDetail.isStepExpanded = false;
            if (stepValidationProgressBar.getVisibility() != View.VISIBLE) {
                btnCancel.setVisibility(View.GONE);
            }
            btnContinue.setVisibility(View.GONE);
            viewStub.setVisibility(View.GONE);
        } else if (visibility == View.VISIBLE) {
            stepElementDetail.isStepExpanded = true;
            btnCancel.setVisibility(View.VISIBLE);
            btnContinue.setVisibility(View.VISIBLE);
            viewStub.setVisibility(View.VISIBLE);
        }
    }

    public void invalidateAndRequestLayout() {
        invalidate();
        requestLayout();
    }

    public void setStepElementDetails(StepElementDetail stepElementDetail) {
        this.stepElementDetail = stepElementDetail;
        tvTitle.setText(this.stepElementDetail.stepTitle);
        if (!TextUtils.isEmpty(this.stepElementDetail.stepSubText)) {
            tvSubText.setText(this.stepElementDetail.stepSubText);
            //By default this view is GONE (will need to set to GONE if subtext is empty)
            tvSubText.setVisibility(View.VISIBLE);
        }
        if (this.stepElementDetail.getStepView() != null) {
            viewStub = DrawingHelper.getInstance().replaceViewStub(rlRowElement,
                    viewStub, this.stepElementDetail.getStepView());
            rlRowElement.addView(viewStub);
        } else {
            Log.e(TAG, "Step View element is null");
        }
        this.setStepIcon(this.stepElementDetail.getStepIcon(), this.stepElementDetail.stepNumber);
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

    // ------- Methods that are part of the Step Element interface --------------->
    public void setStepIcon(@StepIcon.StepIconInterface int stepIcon, int stepNumber) {
        switch (stepIcon) {
            case StepIcon.EDIT:
                tvIcon.setText("");
                tvIcon.setBackgroundResource(R.drawable.ic_edit_circle);
                tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_active_step);
                tvSubText.setTextAppearance(R.style.io_ta_stepper_form_style_optional_step);
                break;
            case StepIcon.ACTIVE:
                tvIcon.setBackgroundResource(R.drawable.ic_default_circle);
                tvIcon.setText(String.valueOf(stepNumber));
                tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_active_step);
                tvSubText.setTextAppearance(R.style.io_ta_stepper_form_style_optional_step);
                break;
            case StepIcon.INACTIVE:
                tvIcon.setBackgroundResource(R.drawable.ic_inactive_circle);
                tvIcon.setText(String.valueOf(stepNumber));
                tvTitle.setTextAppearance(R.style.io_ta_stepper_form_style_inactive_step);
                tvSubText.setTextAppearance(R.style.io_ta_stepper_form_style_optional_step);
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

    @Override
    public void isStepInValidationState(boolean validating) {

    }

    @Override
    public void isStepValid(boolean valid) {

    }

    @Override
    public void cancelStep() {

    }
}
