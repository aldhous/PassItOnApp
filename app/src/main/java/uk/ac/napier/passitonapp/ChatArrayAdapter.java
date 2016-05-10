package uk.ac.napier.passitonapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/** The ChatAdapter inflates a view to show the item details **/
class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

    // Variable for array to list messages
    private List<ChatMessage> chatMessageList = new ArrayList<>();

    // Add message to list
    @Override
    public void add(uk.ac.napier.passitonapp.ChatMessage object) {
        chatMessageList.add(object);
        super.add(object);
    }

    // Create an array and assign an ID to the message
    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    // Count the messages in the list
    public int getCount() {
        return this.chatMessageList.size();
    }

    // Get the list of messages
    public uk.ac.napier.passitonapp.ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    // Create the message view and place the message on the left or right
    public View getView(int position, View convertView, ViewGroup parent) {
        uk.ac.napier.passitonapp.ChatMessage chatMessageObj = getItem(position);
        View row;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (chatMessageObj.left) {
            row = inflater.inflate(R.layout.chat_right, parent, false);
        }else{
            row = inflater.inflate(R.layout.chat_left, parent, false);
        }

        // Return the message in a new row
        TextView chatText = (TextView) row.findViewById(R.id.msgr);
        chatText.setText(chatMessageObj.message);
        return row;
    }

}