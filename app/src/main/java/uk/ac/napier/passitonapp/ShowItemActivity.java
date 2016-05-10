package uk.ac.napier.passitonapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/** The detailed item screen **/
public class ShowItemActivity extends AppCompatActivity {

    // Set item variable
    private Item itemShow;

    /** Create the detailed item view **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_item);

        /** Message button to chat with item owner **/
        Button button1 = (Button) findViewById (R.id.message_btn);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityA = new Intent(ShowItemActivity.this, MessageActivity.class);
                startActivity(activityA);
            }
        });

        // Get item data to show item
        Bundle data = getIntent().getExtras();
        itemShow = data.getParcelable("item");
        ImageView showimage = (ImageView) findViewById(R.id.show_image);
        TextView showname = (TextView) findViewById(R.id.show_name);
        TextView showdesc = (TextView) findViewById(R.id.show_desc);
        TextView showlocal = (TextView) findViewById(R.id.show_local);
        TextView showpickup = (TextView) findViewById(R.id.show_pickup);

        // Show the item with the following details
        showimage.setImageBitmap(itemShow.getImage());
        showname.setText(itemShow.getName());
        showdesc.setText(itemShow.getDesc());
        showlocal.setText(itemShow.getLocal());
        showpickup.setText(itemShow.getPickup());
    }

    /** Create the menu for this activity. **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** Create the functions for each menu item shown in this activity. **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /** Delete this item. **/
            case R.id.action_delete:
                Intent intent = new Intent();
                intent.putExtra("item", itemShow);
                Toast.makeText(getApplicationContext(), "Deleting item....!", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, intent);
                finish();
                break;
            /** Read my conversations. **/
            case R.id.action_chat:
                Intent activityB = new Intent(getApplicationContext(),ActiveChatActivity.class);
                startActivity(activityB);
                break;
            /** Log out of the app. **/
            case R.id.action_logout:
                Intent activityC = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(activityC);
                break;
            /** View my account. **/
            case R.id.action_view:
                Intent activityD = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(activityD);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
