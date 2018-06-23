package io.tammen.stepperform;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import io.tammen.stepper.widget.mobile.StepElementDetail;
import io.tammen.stepper.widget.mobile.StepIcon;
import io.tammen.stepper.widget.mobile.Vertical;
import io.tammen.stepper.widget.mobile.exception.StepperElementException;

/**
 * Created by Tammen Bruccoleri on 12/28/2017.
 *
 */

public class MainActivity extends AppCompatActivity {
    //Step 1. Define ArrayList of Step Elements
    private final ArrayList<StepElementDetail> stepElementDetails = new ArrayList<>();
    private final String TAG = this.getClass().getSimpleName();

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
        StepElementDetail step1 = new StepElementDetail.StepElementBuilder("This is a first step example")
                .build();

        StepElementDetail step2 = new StepElementDetail.StepElementBuilder("This is a second step")
                .stepIcon(StepIcon.INACTIVE)
                .stepSubText("While optional, please provide a value")
                .stepOptional(true)
                .build();

        //Step 4. Add Step Elements to ArrayList
        stepElementDetails.add(step1);
        stepElementDetails.add(step2);

        //Step 5. Pass the off ArrayList to render step objects.
        try {
            vertical.setStepElementDetail(stepElementDetails);
        } catch (StepperElementException ex) {
            Log.e(TAG, "We have a rendering issue");
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
