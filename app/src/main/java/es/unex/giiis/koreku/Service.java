package es.unex.giiis.koreku;

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

@Entity(tableName = "service") //Etiquetamos la clase e indicamos el nombre de la tabla donde se va a guardar

public class Service {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");

    @Ignore
    public final static String ID = "id";
    @Ignore
    public final static String SUBSCRIPTION = "subscription";
    @Ignore
    public final static String EMAIL = "email";
    @Ignore
    public final static String PRICE = "price";
    @Ignore
    public final static String STARTDATE = "startDate";
    @Ignore
    public final static String DUEDATE = "dueDate";

    @Ignore
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.US);



    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "subscription")
    private String subscription = new String();
    @ColumnInfo(name = "email")
    private String email = new String();
    @ColumnInfo(name = "price")
    private String price;
    @TypeConverters(DateConverter.class)
    private Date startDate = new Date();
    @TypeConverters(DateConverter.class)
    private Date dueDate = new Date();



    @Ignore
    public Service(String subscription, String email, String price, Date startDate, Date dueDate) {

        this.subscription = subscription;
        this.email = email;
        this.price = price;
        this.startDate = startDate;
        this.dueDate = dueDate;

    }

    @Ignore
    public Service(long id, String subscription, String email, String price, String startDate, String dueDate) {

        this.id = id;
        this.subscription = subscription;
        this.email = email;
        this.price = price;

        try {

            this.startDate = Service.FORMAT.parse(startDate);

        } catch (ParseException e) {

            this.startDate = new Date();

        }

        try {

            this.dueDate = Service.FORMAT.parse(dueDate);

        } catch (ParseException e) {

            this.dueDate = new Date();

        }

    }

    @Ignore
    public Service(Intent intent) {

        id = intent.getLongExtra(Service.ID,0);
        subscription = intent.getStringExtra(Service.SUBSCRIPTION);
        email = intent.getStringExtra(Service.EMAIL);
        price =intent.getStringExtra(Service.PRICE);

        try {

            startDate = Service.FORMAT.parse(intent.getStringExtra(Service.STARTDATE));

        } catch (ParseException e) {

            startDate = new Date();

        }

        try {

            dueDate = Service.FORMAT.parse(intent.getStringExtra(Service.DUEDATE));

        } catch (ParseException e) {

            dueDate = new Date();

        }

    }

    public Service(long id, String subscription, String email, String price, Date startDate, Date dueDate){

        this.id = id;
        this.subscription = subscription;
        this.email = email;
        this.price = price;
        this.startDate = startDate;
        this.dueDate = dueDate;

    }



    public long getId() {

        return id;

    }

    public void setId(long id) {

        this.id = id;

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

    public Date getStartDate() {

        return startDate;

    }

    public void setStartDate(Date startDate) {

        this.startDate = startDate;

    }

    public Date getDueDate() {

        return dueDate;

    }

    public void setDueDate(Date dueDate) {

        this.dueDate = dueDate;

    }



    public static void packageIntent(Intent intent, String subscription, String email, String price, String startDate, String dueDate) {

        intent.putExtra(Service.SUBSCRIPTION, subscription);
        intent.putExtra(Service.EMAIL, email);
        intent.putExtra(Service.PRICE, price);
        intent.putExtra(Service.STARTDATE, startDate);
        intent.putExtra(Service.DUEDATE, dueDate);

    }

    public String toString() {

        return id + ITEM_SEP + subscription + ITEM_SEP +  email + ITEM_SEP + price + ITEM_SEP + FORMAT.format(startDate) + ITEM_SEP + FORMAT.format(dueDate);

    }

    public String toLog() {
        return "ID: " + id + ITEM_SEP + "Subscription: " + subscription + ITEM_SEP + "Email: " + email
                + ITEM_SEP + "Price " + price + ITEM_SEP + "StartDate: " + startDate +  ITEM_SEP + "DueDate: " + dueDate;
    }

}