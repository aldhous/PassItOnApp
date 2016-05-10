package uk.ac.napier.passitonapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/** The ItemAdapter inflates a view to show the item details **/
public class ItemAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private final ArrayList<Item> values;

    /** Create an array for a view to show items in the items list **/
    public ItemAdapter(Context context, ArrayList<Item> values) {
        super(context, R.layout.activity_items, values);
        this.context = context;
        this.values = values;
    }

    /** Create a view for each individual item **/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if(convertView == null) {
            view = inflater.inflate(R.layout.item_list, parent, false);
        } else {
            view = convertView;
        }

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView desc = (TextView) view.findViewById(R.id.desc);
        TextView local = (TextView) view.findViewById(R.id.local);
        TextView pickup = (TextView) view.findViewById(R.id.pickup);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);

        String nameStr = values.get(position).getName();
        String descStr = values.get(position).getDesc();
        String localStr = values.get(position).getLocal();
        String pickupStr = values.get(position).getPickup();
        Bitmap image = values.get(position).getImage();

        name.setText(nameStr);
        desc.setText(descStr);
        local.setText(localStr);
        pickup.setText(pickupStr);
        icon.setImageBitmap(image);
        return view;
    }
}
