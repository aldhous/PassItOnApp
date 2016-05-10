package uk.ac.napier.passitonapp;

/** Set the initial message to the left of the view
 *  Display the message text **/
public class ChatMessage {
    public boolean left;
    public String message;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }
}

