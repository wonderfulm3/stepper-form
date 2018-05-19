package io.tammen.stepperform;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
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
    private final ArrayList<StepElementDetail> stepElementDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyDialog()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Vertical vertical = findViewById(R.id.vertical_example);

        StepElementDetail step1 = new StepElementDetail.StepElementBuilder(1)
                .stepIcon(StepIcon.INACTIVE)
                .stepTitle("This is a first step example")
                .build();

        StepElementDetail step2 = new StepElementDetail.StepElementBuilder(2)
                .stepIcon(StepIcon.ERROR)
                .stepTitle("This is a second step")
                .build();

        stepElementDetails.add(step1);
        stepElementDetails.add(step2);

        try {
            vertical.setStepElementDetail(stepElementDetails);
        } catch (StepperElementException ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
