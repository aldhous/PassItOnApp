package uk.ac.napier.passitonapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;

/** The available items list screen **/
public class ItemsActivity extends AppCompatActivity {

    // Set values to add a new item to the list a particular show an item
    private static final int REQUEST_CODE_NEW_ITEM = 1;
    private static final int REQUEST_CODE_SHOW_ITEM = 4;

    // Variables for the Items list activity
    private ListView list;
    private ArrayList<Item> items = new ArrayList<>();
    private DBItemHelper db;

    /** Create the items list **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        // Set the DBHelper for the Items database
        db = new DBItemHelper(this);
        // Get all the items
        items = db.getAllItems();
        // Create the items list
        list = (ListView) findViewById(R.id.list_view);
        useCustomAdapter();
        // Set a click listener for the items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) list.getAdapter().getItem(position);
                Intent i = new Intent(ItemsActivity.this, ShowItemActivity.class);
                i.putExtra("item", item);
                startActivityForResult(i, REQUEST_CODE_SHOW_ITEM);
            }
        });
    }

    /** Use the ItemAdapter class to create the list of items **/
    private void useCustomAdapter(){
        ListAdapter adapter = new ItemAdapter(this, items);
        list.setAdapter(adapter);
    }

    /** Create the menu for this activity. **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** Create the functions for each menu item shown in this activity. **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /** Add a new item. **/
            case R.id.action_add:
                Intent i = new Intent(ItemsActivity.this, AddNewItemActivity.class);
                startActivityForResult(i, REQUEST_CODE_NEW_ITEM);
                break;
            /** View my account. **/
            case R.id.action_view:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 1);
                Intent activityA = new Intent(getApplicationContext(),SettingsActivity.class);
                activityA.putExtras(dataBundle);
                startActivity(activityA);
                return true;
            /** View items in the map view. **/
            case R.id.action_maps:
                Intent activityB = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(activityB);
                break;
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

    /** Show all items or display any new items  **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_CODE_NEW_ITEM && resultCode == RESULT_OK) {
                Item item = data.getParcelableExtra("item");
                db.addItem(item);
                items = db.getAllItems();
                useCustomAdapter();
            }
        else if (requestCode == REQUEST_CODE_SHOW_ITEM && resultCode == RESULT_OK) {
                Item itemRec = data.getParcelableExtra("item");
                Item item = db.getItemByDesc(itemRec.getDesc());
                db.deleteItem(item);
                items = db.getAllItems();
                useCustomAdapter();
        }
    }
}
