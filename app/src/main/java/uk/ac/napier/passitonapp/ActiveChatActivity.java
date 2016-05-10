package uk.ac.napier.passitonapp;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/** The active chat messages screen **/
public class ActiveChatActivity extends AppCompatActivity {

    // variables for the active chat activity
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private boolean side = false;

    /** Create the active chat messages view **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activechat);

        /** Send button for new messages **/
        Button buttonSend = (Button) findViewById(R.id.send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        // Create the list view for the conversation
        listView = (ListView) findViewById(R.id.msgview);
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_right);
        listView.setAdapter(chatArrayAdapter);

        // Create the message text field and listen for a key press
        chatText = (EditText) findViewById(R.id.msg);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return (event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER) && sendChatMessage();
            }
        });

        // List and scroll a transcript of any messages for the view
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        // Scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    /** Send and display the message **/
    private boolean sendChatMessage() {
        chatArrayAdapter.add(new ChatMessage(side, chatText.getText().toString()));
        chatText.setText("");
        side = !side;
        return true;
    }

    /** Create the menu for this activity. **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** Create the functions for each menu item shown in this activity. **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /** View my account. **/
            case R.id.action_view:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 1);
                Intent activityA = new Intent(getApplicationContext(), SettingsActivity.class);
                activityA.putExtras(dataBundle);
                startActivity(activityA);
                return true;
            /** View items in map view. **/
            case R.id.action_maps:
                Intent activityB = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(activityB);
                break;
            /** Log out of the app. **/
            case R.id.action_logout:
                Intent activityC = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activityC);
                break;
            /** View items in the list view. **/
            case R.id.action_list:
                Intent activityD = new Intent(getApplicationContext(), ItemsActivity.class);
                startActivity(activityD);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
