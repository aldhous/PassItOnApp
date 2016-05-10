package uk.ac.napier.passitonapp;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/** The user account settings screen **/
public class SettingsActivity extends AppCompatActivity {

        // Set the database
        private DBHelper settingsdb;

        // Set the text views variables
        TextView name;
        TextView last;
        TextView email;
        TextView post;
        TextView phone;
        TextView password;

        // Set the form to view not edit
        int id_To_Update = 0;

        /** Create the settings view **/
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);

            // Apply the form field variables to display text
            name = (TextView) findViewById(R.id.editFirstName);
            last = (TextView) findViewById(R.id.editLastName);
            email = (TextView) findViewById(R.id.editEmail);
            phone = (TextView) findViewById(R.id.editPhone);
            post = (TextView) findViewById(R.id.editPostcode);
            password = (TextView) findViewById(R.id.editPassword);

            // Set the DBHelper for the database
            settingsdb = new DBHelper(this);

            /** Check if the intent is to view the settings or create new settings. **/
            Bundle extras = getIntent().getExtras();
            if(extras !=null){
                int Value = extras.getInt("id");

                if(Value>0){
                    // Integer value of 1 means this is the view part not the add account part.
                    Cursor rs = settingsdb.getData(Value);
                    id_To_Update = Value;

                    // Go to the first record set in the db.
                    rs.moveToFirst();

                    // Set variables for the database column string data.
                    String nam = rs.getString(rs.getColumnIndex(DBHelper.SETTINGS_COLUMN_NAME));
                    String las = rs.getString(rs.getColumnIndex(DBHelper.SETTINGS_COLUMN_SURNAME));
                    String emai = rs.getString(rs.getColumnIndex(DBHelper.SETTINGS_COLUMN_EMAIL));
                    String pos = rs.getString(rs.getColumnIndex(DBHelper.SETTINGS_COLUMN_POSTCODE));
                    String phon = rs.getString(rs.getColumnIndex(DBHelper.SETTINGS_COLUMN_PHONE));
                    String pass = rs.getString(rs.getColumnIndex(DBHelper.SETTINGS_COLUMN_PASSWORD));

                    // If the record set isn't closed then close it.
                    if (!rs.isClosed()){
                        rs.close();
                    }

                    // Hide the save button if the user is just viewing the account
                    Button b = (Button)findViewById(R.id.saveBtn);
                    b.setVisibility(View.INVISIBLE);

                    // Make the text strings viewable, but not clickable
                    name.setText(nam);
                    name.setFocusable(false);
                    name.setClickable(false);

                    last.setText(las);
                    last.setFocusable(false);
                    last.setClickable(false);

                    email.setText(emai);
                    email.setFocusable(false);
                    email.setClickable(false);

                    post.setText(pos);
                    post.setFocusable(false);
                    post.setClickable(false);

                    phone.setText(phon);
                    phone.setFocusable(false);
                    phone.setClickable(false);

                    password.setText(pass);
                    password.setFocusable(false);
                    password.setClickable(false);
                }
            }
        }

        /** Create the menu for this activity. **/
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_settings, menu);
            return true;
        }

        /** Create the functions for each menu item shown in this activity. **/
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            super.onOptionsItemSelected(item);
            switch(item.getItemId()) {
                /** Edit my account. **/
                case R.id.Edit_account:
                    Button b = (Button)findViewById(R.id.saveBtn);
                    b.setVisibility(View.VISIBLE);
                    name.setEnabled(true);
                    name.setFocusableInTouchMode(true);
                    name.setClickable(true);

                    last.setEnabled(true);
                    last.setFocusableInTouchMode(true);
                    last.setClickable(true);

                    email.setEnabled(true);
                    email.setFocusableInTouchMode(true);
                    email.setClickable(true);

                    post.setEnabled(true);
                    post.setFocusableInTouchMode(true);
                    post.setClickable(true);

                    phone.setEnabled(true);
                    phone.setFocusableInTouchMode(true);
                    phone.setClickable(true);

                    password.setEnabled(true);
                    password.setFocusableInTouchMode(true);
                    password.setClickable(true);

                    return true;
                /** Delete my account. **/
                case R.id.Delete_account:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.deleteSetting)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    settingsdb.deleteSetting(id_To_Update);
                                    Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    AlertDialog d = builder.create();
                    d.setTitle("Are you sure");
                    d.show();
                    return true;
                /** The activity back button. **/
                case R.id.action_back:
                    this.finish();
                    return true;
                /** Log out of the app. **/
                case R.id.action_logout:
                    Intent activityC = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(activityC);
                    break;
                default:
                    return super.onOptionsItemSelected(item);
            }
            return true;
        }

        public void run(View view) {
            Bundle extras = getIntent().getExtras();
            if(extras !=null) {
                int Value = extras.getInt("id");
                if(Value>0){
                    if(settingsdb.updateSetting(id_To_Update, name.getText().toString(), last.getText().toString(), email.getText().toString(), post.getText().toString(), phone.getText().toString(), password.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),ItemsActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Not updated", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(settingsdb.insertSetting(name.getText().toString(), last.getText().toString(), email.getText().toString(), post.getText().toString(), phone.getText().toString(), password.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Not done", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(),ItemsActivity.class);
                    startActivity(intent);
                }
            }
        }

}
