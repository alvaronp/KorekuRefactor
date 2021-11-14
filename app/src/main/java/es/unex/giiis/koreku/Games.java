package es.unex.giiis.koreku;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
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
public class Games {

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
	@Ignore
	public final static String GENERO = "genero";
	//Atributos de la clase
	@ColumnInfo(name = "game_id")
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
	@ColumnInfo(name = "genero") //URI de la portada del juego
	private String genero = new String();


	@Ignore //Room no tiene porque saber los constructores de la clase
	public Games(String title, Status status, Date buydate, String desc, String image, String genero) {
		this.title = title;
		this.status = status;
		this.buydate = buydate;
		this.desc = desc;
		this.image = image;
		this.genero = genero;
	}

	@Ignore
	public Games(long ID, String title, String status, String buydate, String desc, String image, String genero) {
        this.id = ID;
        this.title = title;
        this.status = Status.valueOf(status);
        try {
            this.buydate = Games.FORMAT.parse(buydate);
        } catch (ParseException e) {
            this.buydate = new Date();
        }
        this.desc = desc;
        this.image = image;
		this.genero = genero;
    }

	// Create a new ToDoItem from data packaged in an Intent
	@Ignore
    public Games(Intent intent) {
		id = intent.getLongExtra(Games.ID,0);
		title = intent.getStringExtra(Games.TITLE);
		status = Status.valueOf(intent.getStringExtra(Games.STATUS));
		try {
			buydate = Games.FORMAT.parse(intent.getStringExtra(Games.BUYDATE));
		} catch (ParseException e) {
			buydate = new Date();
		}
		desc = intent.getStringExtra(Games.DESC);
		image = intent.getStringExtra(Games.IMAGE);
		genero = intent.getStringExtra(Games.GENERO);;
	}

	public Games(long id, String title, Status status, Date buydate, String desc, String image,String genero){
		this.id =id;
		this.title =title;
		this.status =status;
		this.buydate = buydate;
		this.desc = desc;
		this.image = image;
		this.genero = genero;
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

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}


	// Take a set of String data values and 
	// package them for transport in an Intent

	public static void packageIntent(Intent intent, String title,
									 Status status, String buydate, String desc, String image, String genero) {

		intent.putExtra(Games.TITLE, title);
		intent.putExtra(Games.STATUS, status.toString());
		intent.putExtra(Games.BUYDATE, buydate);
		intent.putExtra(Games.DESC, desc);
		intent.putExtra(Games.IMAGE, image);
		intent.putExtra(Games.GENERO, genero);
	}

	public String toString() {
		return id + ITEM_SEP + title + ITEM_SEP +  status + ITEM_SEP +
				FORMAT.format(buydate) + desc + ITEM_SEP + image + ITEM_SEP + genero;
	}

	public String toLog() {
		return "ID: " + id + ITEM_SEP + "Title:" + title + ITEM_SEP + "Status:" + status +
				ITEM_SEP + "Buy Date:" + FORMAT.format(buydate) + ITEM_SEP + "Description:" +
				desc + ITEM_SEP + "Image URI:" + image+ ITEM_SEP + "Genero:" + genero;
	}

}
