package uk.ac.napier.passitonapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/** Creates a custom object called Item used by item activities. **/
public class Item implements Parcelable {

    // Item table name
    public static final String TABLE_NAME = "items";

    // Table columns name values
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESC = "desc";
    public static final String KEY_LOCAL = "local";
    public static final String KEY_PICKUP = "pickup";
    public static final String KEY_IMAGE = "image";

    // Table columns names
    public static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_DESC, KEY_LOCAL, KEY_PICKUP, KEY_IMAGE};

    // Item variables
    private int id;
    private String name;
    private String desc;
    private String local;
    private String pickup;
    private Bitmap image;

    // Create a new item
    public Item() {
    }

    // item parameter variables
    public Item(String name, String desc, String local, String pickup, Bitmap image) {
        this.name = name;
        this.desc = desc;
        this.local = local;
        this.pickup = pickup;
        this.image = image;
    }

    /** Set parameters and get parameters for the Item. **/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


    /** Creates the custom object called Item used by item activities. **/
    @Override
    public String toString() {
       return "Item: Name - " + this.name + ";Desc - " + this.desc + ";Local - " + this.local + ";Pickup - " + this.pickup + ";ID - " + this.getId();
    }

    /** Look for the data relating to the Item. **/
    @Override public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Item) {
            Item item = (Item) o;
            result = (item.getName().equals(this.getName())) && (item.getDesc().equals(this.getDesc())) && (item.getLocal().equals(this.getLocal())) && (item.getPickup().equals(this.getPickup()));
        }
        return result;
    }

    /** Create a new descriptor for the parcel **/
    @Override
    public int describeContents() {
        return 0;
    }

    /** Get the data that makes up the Item. **/
    public Item(Parcel in) {
        String[] dataString = new String[4];
        in.readStringArray(dataString);
        this.name = dataString[0];
        this.desc = dataString[1];
        this.local = dataString[2];
        this.pickup = dataString[3];
        int[] dataInt = new int[2];
        in.readIntArray(dataInt);
        this.id = dataInt[0];
        int byteLength = dataInt[1];
        byte[] dataByte = new byte[byteLength];
        in.readByteArray(dataByte);
        this.image = Util.byteArrayToBitmap(dataByte);
    }

    /** Write the data into a parcel to make the Item object. **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.name,
                this.desc,
                this.local,
                this.pickup
        });
        dest.writeIntArray(new int[]{
                this.id,
                Util.bitmapToByteArray(this.image).length
        });
        dest.writeByteArray(Util.bitmapToByteArray(this.image));
    }

    /** Create the custom object Item to be used by the Item activities. **/
    public static final Creator CREATOR = new Creator(){
        public Item createFromParcel(Parcel in){
            return new Item(in);
        }
        public Item[] newArray(int size){
            return new Item[size];
        }
    };
}
