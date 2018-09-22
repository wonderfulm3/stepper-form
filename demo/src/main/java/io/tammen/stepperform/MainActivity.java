package io.tammen.stepperform;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import io.tammen.stepper.widget.mobile.StepElementDetail;
import io.tammen.stepper.widget.mobile.Vertical;
import io.tammen.stepper.widget.mobile.exception.StepperElementException;
import io.tammen.stepper.widget.mobile.interfaces.StepButtonListener;
import io.tammen.stepperform.view.Step1;
import io.tammen.stepperform.view.Step2;

/**
 * Created by Tammen Bruccoleri on 12/28/2017.
 *
 */

public class MainActivity extends AppCompatActivity implements StepButtonListener {
    private final String TAG = this.getClass().getSimpleName();

    //Step 1. Define ArrayList of Step Elements
    private final ArrayList<StepElementDetail> stepElementDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //Step 2. Init the Vertical view
        Vertical vertical = findViewById(R.id.vertical_example);

        //Step 3. Create Step Element objects
        StepElementDetail step1;
        StepElementDetail step2;
        StepElementDetail step3;

        //Step 3b. Create the Step view's
        Step1 step1View = new Step1(this);
        Step2 step2View = new Step2(this);
        try {
            step1 = new StepElementDetail.StepElementBuilder("Select pizza toppings")
                    .stepSubText("This is a required step!")
                    .stepButtonListener(step1View)
                    .stepRequiresValidation(true)
                    .stepContinueOnValidationFailure(false)
                    .stepHasValidationProgressBar(true)
                    .stepView(step1View)
                    .build();
            //Step 3c. Handling the step validation in Step1 View.
            step1View.stepValidationListener = step1.stepValidationListener;

            step2 = new StepElementDetail.StepElementBuilder("This is a second step example")
                    .stepButtonListener(step2View)
                    .stepRequiresValidation(true)
                    .stepContinueOnValidationFailure(true)
                    .stepSubText("While optional, please select a crust. Otherwise, the pizza will have random crust")
                    .stepView(step2View)
                    .stepOptional(true)
                    .build();
            //Step 3c. Handling the step validation in Step2 View.
            step2View.stepValidationListener = step2.stepValidationListener;

            step3 = new StepElementDetail.StepElementBuilder("This is a third step example")
                    .stepButtonListener(this)
                    .build();

        } catch (StepperElementException ex) {
            Log.e(TAG, "We have a Builder issue: " + ex.getMessage());
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        //Step 4. Add Step Elements to ArrayList
        stepElementDetails.add(step1);
        stepElementDetails.add(step2);
        stepElementDetails.add(step3);

        //Step 5. Pass the off ArrayList to render step objects.
        try {
            vertical.setStepElementDetail(stepElementDetails);
        } catch (StepperElementException ex) {
            Log.e(TAG, "We have a rendering issue");
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStepContinueClicked(int stepClicked) {
        Toast.makeText(this, "Step clicked: " + stepClicked, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStepCancelClicked(int stepClicked) {

    }
}
