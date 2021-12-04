package es.unex.giiis.koreku.ui.profile;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName="perfil")
public class Perfil {
	@Ignore
	public static final String ITEM_SEP = System.getProperty("line.separator");
	@Ignore
	public final static String ID = "ID";
	@Ignore
	public final static String TITLE = "title";
	@Ignore
	public final static String PHONE = "phone";
	@Ignore
	public final static String MAIL = "mail";
	@Ignore
	public final static String IMAGE = "image";
	@Ignore
	public final static String COMMENTS = "comments";
	@Ignore
	public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.US);

	@ColumnInfo(name="profile_id")
	@PrimaryKey(autoGenerate = true)
	private long id;
	@ColumnInfo(name="title")
	private String title = new String();
	@ColumnInfo(name="phone")
	private String phone = new String();
	@ColumnInfo(name="mail")
	private String mail = new String();
	@ColumnInfo(name="image")
	private String image = new String();
	@ColumnInfo(name="comments")
	private String comments = new String();
	@Ignore
    Perfil(String title, Date date, String phone, String mail, String image, String comments) {

		this.title = title;
		this.phone=phone;
		this.mail=mail;
		this.image=image;
		this.comments=comments;
	}
	@Ignore
    public Perfil(long id, String title, String phone, String mail, String image, String comments) {
        this.id = id;
        this.title = title;
		this.phone=phone;
		this.mail=mail;
		this.image=image;
		this.comments=comments;
    }

	// Create a new ToDoItem from data packaged in an Intent
	@Ignore
    public Perfil(Intent intent) {
		id = intent.getLongExtra(Perfil.ID,0);
		title = intent.getStringExtra(Perfil.TITLE);

		phone = intent.getStringExtra(Perfil.PHONE);
		mail = intent.getStringExtra(Perfil.MAIL);
		image = intent.getStringExtra(Perfil.IMAGE);
		comments = intent.getStringExtra(Perfil.COMMENTS);
	}


	public Perfil(long id, String title, String phone, String mail, String image){
		this.id =id;
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


	public String getComments() { return this.comments; }

	public void setComments(String comments) {this.comments = comments;}

	// Take a set of String data values and 
	// package them for transport in an Intent

	public static void packageIntent(Intent intent, String title,String phone,
									 String mail, String image,String comment) {

		intent.putExtra(Perfil.TITLE, title);
		intent.putExtra(Perfil.MAIL, mail.toString());
		intent.putExtra(Perfil.PHONE, phone.toString());
		intent.putExtra(Perfil.IMAGE, image.toString());
		intent.putExtra(Perfil.COMMENTS,comment.toString());
	
	}


	public String toString() {
		return id + ITEM_SEP + title +ITEM_SEP+ phone +ITEM_SEP+mail +ITEM_SEP+ image +ITEM_SEP+ comments;
	}

	public String toLog() {
		return "ID: " + id + ITEM_SEP + "Title:" + title + ITEM_SEP + ITEM_SEP + "Phone:" + phone+ ITEM_SEP+"Mail:"+mail
				+ ITEM_SEP + "Image:" + image ;
	}

}
