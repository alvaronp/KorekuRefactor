package es.unex.giiis.koreku.ui.games;

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
			"yyyy-MM-dd", Locale.US);
	@Ignore
	public final static String BUGS = "bugs";
	@Ignore
	public final static String GENERO = "genero";
	@Ignore
	public final static String CONSOLE = "console";
	//Atributos de la clase
	@ColumnInfo(name = "game_id")
	@PrimaryKey (autoGenerate = true) //Clave primaria autogenerada para la bd
	private long id;
	@ColumnInfo(name = "title") //Nombre que tendrá el atributo en nuestra bd
	private String title = new String();
	@ColumnInfo(name = "status")
	@TypeConverters(StatusConverter.class)
	private Status status = Status.NOTFINISHED;
	@ColumnInfo(name = "date")
	@TypeConverters(DateConverter.class) //Le indica a Room que para convertir a timestamp debe usar esa clase
	private Date buydate = new Date();
	@ColumnInfo(name = "desc") //Descripcion del juego
	private String desc = new String();
	@ColumnInfo(name = "image") //URI de la portada del juego
	private String image = new String();
	@ColumnInfo(name = "bugs") //Errores del juego
	private String bugs = new String();
	@ColumnInfo(name = "genero") //URI de la portada del juego
	private String genero = new String();
	@ColumnInfo(name = "consola") //URI de la portada del juego
	private String console = new String();


	@Ignore //Room no tiene porque saber los constructores de la clase
	 Games(String title, Status status, Date buydate, String desc, String image, String genero, String bugs, String console) {
		this.title = title;
		this.status = status;
		this.buydate = buydate;
		this.desc = desc;
		this.image = image;
		this.bugs = bugs;
		this.genero = genero;
		this.console = console;
	}

	@Ignore
	public Games(){
		this.id =0;
		this.title = "";
		this.status = null;
		this.buydate = null;
		this.desc = "";
		this.image = "";
		this.bugs = "";
		this.genero = "";
		this.console = "";
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
		bugs = intent.getStringExtra(Games.BUGS);
		genero = intent.getStringExtra(Games.GENERO);
		console = intent.getStringExtra(Games.CONSOLE);
	}

	public Games(long id, String title, Status status, Date buydate, String desc, String image, String bugs, String genero, String console){
		this.id =id;
		this.title =title;
		this.status =status;
		this.buydate = buydate;
		this.desc = desc;
		this.image = image;
		this.bugs = bugs;
		this.genero = genero;
		this.console = console;
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

	public String getBugs() {
		return bugs;
	}

	public void setBugs(String bugs) {
		this.bugs = bugs;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}


	// Take a set of String data values and 
	// package them for transport in an Intent

	public static void packageIntent(Intent intent, String title,
									 Status status, String buydate, String desc, String image, String genero, String bugs, String console) {

		intent.putExtra(Games.TITLE, title);
		intent.putExtra(Games.STATUS, status.toString());
		intent.putExtra(Games.BUYDATE, buydate);
		intent.putExtra(Games.DESC, desc);
		intent.putExtra(Games.IMAGE, image);
		intent.putExtra(Games.GENERO, genero);
		intent.putExtra(Games.BUGS, bugs);
		intent.putExtra(Games.CONSOLE, console);
	}

	public String toString() {
		return id + ITEM_SEP + title + ITEM_SEP +  status + ITEM_SEP +
				FORMAT.format(buydate) + desc + ITEM_SEP + image + ITEM_SEP + genero + ITEM_SEP + bugs
				+ ITEM_SEP + console;
	}

	public String toLog() {
		return "ID: " + id + ITEM_SEP + "Title:" + title + ITEM_SEP + "Status:" + status +
				ITEM_SEP + "Buy Date:" + FORMAT.format(buydate) + ITEM_SEP + "Description:" +
				desc + ITEM_SEP + "Image URI:" + image+ ITEM_SEP + "Genero:" + genero + ITEM_SEP + "Bugs:" + bugs
				+ ITEM_SEP + "Console" + console ;
	}

}
