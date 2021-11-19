package es.unex.giiis.koreku.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("console-name")
    @Expose
    private String consolename;
    @SerializedName("product-name")
    @Expose
    private String productname;
}
