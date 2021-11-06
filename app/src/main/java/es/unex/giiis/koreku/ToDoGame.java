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
import es.unex.giiis.koreku.roomdb.StatusConverter;

@Entity(tableName = "game")
public class ToDoGame {

	@Ignore
	public static final String ITEM_SEP = System.getProperty("line.separator");


	public enum Status {
		NOTFINISHED, FINISHED
	};

	@Ignore //No queremos guardar un valor inmutable en bd
	public final static String ID = "ID";
	@Ignore
	public final static String TITLE = "title";
	@Ignore
	public final static String STATUS = "status";
	@Ignore
	public final static String BUYDATE = "buydate";
	@Ignore
	public final static String DESC = "desc";
	@Ignore
	public final static String IMAGE = "image";
	@Ignore
	public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.US);

	//Atributos de la clase
	@PrimaryKey (autoGenerate = true) //Clave primaria autogenerada para la bd
	private long id;
	@ColumnInfo(name = "title") //Nombre que tendrá el atributo en nuestra bd
	private String title = new String();
	@TypeConverters(StatusConverter.class)
	private Status status = Status.NOTFINISHED;
	@TypeConverters(DateConverter.class) //Le indica a Room que para convertir a timestamp debe usar esa clase
	private Date buydate = new Date();
	@ColumnInfo(name = "desc") //Descripcion del juego
	private String desc = new String();
	@ColumnInfo(name = "image") //URI de la portada del juego
	private String image = new String();

	@Ignore //Room no tiene porque saber los constructores de la clase
    ToDoGame(String title, Status status, Date buydate, String desc, String image) {
		this.title = title;
		this.status = status;
		this.buydate = buydate;
		this.desc = desc;
		this.image = image;
	}

	@Ignore
	public ToDoGame(long ID, String title, String status, String buydate, String desc, String image) {
        this.id = ID;
        this.title = title;
        this.status = Status.valueOf(status);
        try {
            this.buydate = ToDoGame.FORMAT.parse(buydate);
        } catch (ParseException e) {
            this.buydate = new Date();
        }
        this.desc = desc;
        this.image = image;
    }

	// Create a new ToDoItem from data packaged in an Intent
	@Ignore
    ToDoGame(Intent intent) {
		id = intent.getLongExtra(ToDoGame.ID,0); //TODO think best default value for ID
		title = intent.getStringExtra(ToDoGame.TITLE);
		status = Status.valueOf(intent.getStringExtra(ToDoGame.STATUS));
		try {
			buydate = ToDoGame.FORMAT.parse(intent.getStringExtra(ToDoGame.BUYDATE));
		} catch (ParseException e) {
			buydate = new Date();
		}
		desc = intent.getStringExtra(ToDoGame.DESC);
		image = intent.getStringExtra(ToDoGame.IMAGE);
	}

	public ToDoGame(long id, String title, Status status, Date buydate, String desc, String image){
		this.id =id;
		this.title =title;
		this.status =status;
		this.buydate = buydate;
		this.desc = desc;
		this.image = image;
	}

    public long getId() { return id; }

    public void setId(long ID) { this.id = ID; }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getBuydate() {
		return buydate;
	}

	public void setBuydate(Date buydate) {
		this.buydate = buydate;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	// Take a set of String data values and 
	// package them for transport in an Intent

	public static void packageIntent(Intent intent, String title,
									 Status status, String buydate, String desc, String image) {

		intent.putExtra(ToDoGame.TITLE, title);
		intent.putExtra(ToDoGame.STATUS, status.toString());
		intent.putExtra(ToDoGame.BUYDATE, buydate);
		intent.putExtra(ToDoGame.DESC, buydate);
		intent.putExtra(ToDoGame.IMAGE, buydate);
	}

	public String toString() {
		return id + ITEM_SEP + title + ITEM_SEP +  status + ITEM_SEP +
				FORMAT.format(buydate) + desc + ITEM_SEP + image;
	}

	public String toLog() {
		return "ID: " + id + ITEM_SEP + "Title:" + title + ITEM_SEP + "Status:" + status +
				ITEM_SEP + "Buy Date:" + FORMAT.format(buydate) + ITEM_SEP + "Description:" +
				desc + ITEM_SEP + "Image URI:" + image;
	}

}
