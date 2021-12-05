package es.unex.giiis.koreku.new_api;

import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultQuery {
    @SerializedName("products")
    @Expose
    private List<Product> productList;

    @SerializedName("status")
    @Expose
    private String status;

    public ResultQuery(){
        productList = null;
        status="";
    }

    public List<Product> getProductList() {
        return productList;
    }

    public String getStatus() {
        return status;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
