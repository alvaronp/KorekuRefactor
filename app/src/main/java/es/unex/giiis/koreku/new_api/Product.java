package es.unex.giiis.koreku.new_api;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "product", indices = {@Index(value = {"id"}, unique = true)})
public class Product {
    @SerializedName("id")
    @PrimaryKey
    @Expose
    public int id;
    @SerializedName("console-name")
    @Expose
    public String consolename;
    @SerializedName("product-name")
    @Expose
    public String productname;
    @SerializedName("status")
    @Expose
    public String status;

    public Product(){
        id=0;
        consolename="";
        productname="";
        status="";
    }

    public String getConsolename(){
        return consolename;
    }

    public String getProductname() {
        return productname;
    }

}

