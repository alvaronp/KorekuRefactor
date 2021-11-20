package es.unex.giiis.koreku.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
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

    Product(){
        id=0;
        consolename="";
        productname="";
        status="";
    }

    public String getConsolename(){
        return consolename;
    }
}

