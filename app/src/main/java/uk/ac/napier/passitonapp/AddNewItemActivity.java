package uk.ac.napier.passitonapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/** The add a new item screen **/
public class AddNewItemActivity extends AppCompatActivity {

    // Set integer for the new image request
    private static final int REQUEST_CODE_NEW_IMAGE = 1;

    // Set the variables for the new item details
    private ImageView picture;
    private EditText name;
    private EditText desc;
    private EditText local;
    private EditText pickup;
    private Bitmap image;

    /** Create the add new item view **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);

        picture = (ImageView) findViewById(R.id.add_image);
        name = (EditText) findViewById(R.id.name);
        desc = (EditText) findViewById(R.id.desc);
        local = (EditText) findViewById(R.id.local);
        pickup = (EditText) findViewById(R.id.pickup);

        // Set an on click listener to take a photo when the image placeholder is pressed
        picture.setOnClickListener(onImageClick());

        /** Save button to save the new item details **/
        Button button1 = (Button) findViewById (R.id.saveBtn);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                if (image != null && name.getText().toString().length() > 0 && desc.getText().toString().length() > 0 && local.getText().toString().length() > 0 && pickup.getText().toString().length() > 0) {
                    intent.putExtra("item", new Item(name.getText().toString(), desc.getText().toString(), local.getText().toString(), pickup.getText().toString(), image));
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill in all info!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /** The on click listener to take a picture **/
    private View.OnClickListener onImageClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        };
    }

    /** Use the phone camera to take the picture **/
    public void takePicture() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_NEW_IMAGE);
        }
    }

    /** Store and show the image results **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_NEW_IMAGE && resultCode == RESULT_OK) {
            image = data.getParcelableExtra("data");
            picture.setImageBitmap(image);
        }
    }

    /** Create the menu for this activity. **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** Create the functions for each menu item shown in this activity. **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /** View my account. **/
            case R.id.action_view:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 1);
                Intent activityA = new Intent(getApplicationContext(),SettingsActivity.class);
                activityA.putExtras(dataBundle);
                startActivity(activityA);
                return true;
            /** View items in the list view. **/
            case R.id.action_list:
                Intent activityB = new Intent(getApplicationContext(),ItemsActivity.class);
                startActivity(activityB);
                break;
            /** Log out of the app. **/
            case R.id.action_logout:
                Intent activityC = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(activityC);
                break;
            /** The activity back button. **/
            case R.id.action_back:
                Intent activityD = new Intent(getApplicationContext(),ItemsActivity.class);
                startActivity(activityD);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
