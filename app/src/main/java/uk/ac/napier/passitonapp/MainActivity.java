package uk.ac.napier.passitonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/** The main launch screen allowing users to sign in or join up **/
public class MainActivity extends AppCompatActivity {

    /** Create the Login and Join now buttons for the main activity **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(null);

        // Button to go to the Login activity
        Button button1 = (Button) findViewById (R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityA = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(activityA);
            }
        });

        // Button to go to the profile Settings activity
        Button button2 = (Button) findViewById (R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Pass an integer of 0 to create a new account.
                int add = 0;
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", add);

                Intent activityB = new Intent(MainActivity.this, SettingsActivity.class);
                activityB.putExtras(dataBundle);
                startActivity(activityB);
            }
        });

    }
}
