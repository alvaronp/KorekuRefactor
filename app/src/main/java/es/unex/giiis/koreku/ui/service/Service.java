package es.unex.giiis.koreku.ui.service;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.unex.giiis.koreku.roomdb.DateConverter;

@Entity(tableName = "service") //Etiquetamos la clase e indicamos el nombre de la tabla donde
                                // se va a guardar
public class Service {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");
    @Ignore
    public final static String ID = "id";
    @Ignore
    public final static String TITLE = "title";
    @Ignore
    public final static String SUBSCRIPTION = "subscription";
    @Ignore
    public final static String EMAIL = "email";
    @Ignore
    public final static String PRICE = "price";
    @Ignore
    public final static String DUEDATE = "dueDate";
    @Ignore
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.US);

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "title")
    private String title = new String();
    @ColumnInfo(name = "subscription")
    private String subscription = new String();
    @ColumnInfo(name = "email")
    private String email = new String();
    @ColumnInfo(name = "price")
    private String price;
    @ColumnInfo(name = "dueDate")
    @TypeConverters(DateConverter.class)
    private Date dueDate = new Date();

    @Ignore
     Service(String title, String subscription, String email, String price,
                   Date dueDate) {

        this.title = title;
        this.subscription = subscription;
        this.email = email;
        this.price = price;
        this.dueDate = dueDate;
    }

    @Ignore
    public Service(Intent intent) {

        id = intent.getLongExtra(Service.ID,0);
        title = intent.getStringExtra(Service.TITLE);
        subscription = intent.getStringExtra(Service.SUBSCRIPTION);
        email = intent.getStringExtra(Service.EMAIL);
        price =intent.getStringExtra(Service.PRICE);

        try {

            dueDate = Service.FORMAT.parse(intent.getStringExtra(Service.DUEDATE));

        } catch (ParseException e) {

            dueDate = new Date();

        }

    }

    public Service(long id, String title, String subscription, String email, String price,
                   Date dueDate){

        this.id = id;
        this.title = title;
        this.subscription = subscription;
        this.email = email;
        this.price = price;
        this.dueDate = dueDate;

    }



    public long getId() {

        return id;

    }

    public void setId(long id) {

        this.id = id;

    }

    public String getTitle() {

        return title;

    }

    public void setTitle(String title) {

        this.title = title;

    }

    public String getSubscription() {

        return subscription;

    }

    public void setSubscription(String subscription) {

        this.subscription = subscription;

    }

    public String getEmail() {

        return email;

    }

    public void setEmail(String email) {

        this.email = email;

    }

    public String getPrice() {

        return price;

    }

    public void setPrice(String price) {

        this.price = price;

    }

    public Date getDueDate() {

        return dueDate;

    }

    public void setDueDate(Date dueDate) {

        this.dueDate = dueDate;

    }



    public static void packageIntent(Intent intent, String title, String subscription, String email,
                                     String price,  String dueDate) {

        intent.putExtra(Service.TITLE, title);
        intent.putExtra(Service.SUBSCRIPTION, subscription);
        intent.putExtra(Service.EMAIL, email);
        intent.putExtra(Service.PRICE, price);
        intent.putExtra(Service.DUEDATE, dueDate);
    }

    public String toString() {

        return id + ITEM_SEP + title + ITEM_SEP + subscription + ITEM_SEP +  email + ITEM_SEP
                + price + ITEM_SEP + FORMAT.format(dueDate);

    }

    public String toLog() {

        return "ID: " + id + ITEM_SEP + "Title:" + title + ITEM_SEP + "Subscription: "
                + subscription + ITEM_SEP + "Email: " + email + ITEM_SEP + "Price " + price
                + ITEM_SEP + "DueDate: " + dueDate;

    }

}