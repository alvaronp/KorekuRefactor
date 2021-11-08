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

@Entity(tableName="perfil")
public class Perfil {
	@Ignore
	public static final String ITEM_SEP = System.getProperty("line.separator");
	@Ignore
	public final static String ID = "ID";
	@Ignore
	public final static String TITLE = "title";
	@Ignore
	public final static String DATE = "date";
	@Ignore
	public final static String PHONE = "phone";
	@Ignore
	public final static String MAIL = "mail";
	@Ignore
	public final static String IMAGE = "image";

	@Ignore
	public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.US);

	@ColumnInfo(name="profile_id")
	@PrimaryKey(autoGenerate = true)
	private long id;
	@ColumnInfo(name="title")
	private String title = new String();
	@TypeConverters(DateConverter.class)
	private Date date = new Date();
	@ColumnInfo(name="phone")
	private String phone = new String();
	@ColumnInfo(name="mail")
	private String mail = new String();
	@ColumnInfo(name="image")
	private String image = new String();
	@Ignore
    Perfil(String title, Date date, String phone, String mail, String image) {
		this.title = title;
		this.date = date;
		this.phone=phone;
		this.mail=mail;
		this.image=image;
	}
	@Ignore
    public Perfil(long ID, String title, String date, String phone, String mail, String image) {
        this.id = ID;
        this.title = title;
        try {
            this.date = Perfil.FORMAT.parse(date);
        } catch (ParseException e) {
            this.date = new Date();
        }
		this.phone=phone;
		this.mail=mail;
		this.image=image;
    }

	// Create a new ToDoItem from data packaged in an Intent
	@Ignore
    Perfil(Intent intent) {
		id = intent.getLongExtra(Perfil.ID,0);
		title = intent.getStringExtra(Perfil.TITLE);
		try {
			date = Perfil.FORMAT.parse(intent.getStringExtra(Perfil.DATE));
		} catch (ParseException e) {
			date = new Date();
		}
		phone = intent.getStringExtra(Perfil.PHONE);
		mail = intent.getStringExtra(Perfil.MAIL);
		image = intent.getStringExtra(Perfil.IMAGE);
	}


	public Perfil(long id, String title, Date date, String phone, String mail, String image){
		this.id =id;
		this.date =date;
		this.title =title;
		this.phone=phone;
		this.mail=mail;
		this.image=image;

	}
    public long getId() { return id; }

    public void setId(long ID) { this.id = ID; }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// Take a set of String data values and 
	// package them for transport in an Intent

	public static void packageIntent(Intent intent, String title,String phone,
									 String mail, String image, String date) {

		intent.putExtra(Perfil.TITLE, title);
		intent.putExtra(Perfil.MAIL, mail.toString());
		intent.putExtra(Perfil.PHONE, phone.toString());
		intent.putExtra(Perfil.IMAGE, image.toString());
		intent.putExtra(Perfil.DATE, date);
	
	}

	public String toString() {
		return id + ITEM_SEP + title + ITEM_SEP
				+ FORMAT.format(date)+ITEM_SEP+ phone +ITEM_SEP+mail +ITEM_SEP+ image ;
	}

	public String toLog() {
		return "ID: " + id + ITEM_SEP + "Title:" + title + ITEM_SEP + ITEM_SEP + "Date:"
				+ FORMAT.format(date)+ "Phone:" + phone+ ITEM_SEP+"Mail:"+mail
				+ ITEM_SEP + "Image:" + image ;
	}

}
