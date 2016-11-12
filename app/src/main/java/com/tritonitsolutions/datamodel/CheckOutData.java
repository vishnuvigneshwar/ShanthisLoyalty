package com.tritonitsolutions.datamodel;

/**
 * Created by TritonDev on 15/10/2015.
 */
public class CheckOutData {
    String pro_img;
    String pro_name;
    String pro_size;
    String pro_price;
    String pro_count;
    String pro_total;
    String pro_coup;
    String pro_id;

    public CheckOutData(){

    }
    public CheckOutData(String pro_img,String pro_name,String pro_size,String pro_price,String pro_count,String pro_total,String pro_coup,String pro_id){
        this.pro_img=pro_img;
        this.pro_name=pro_name;
        this.pro_size=pro_size;
        this.pro_price=pro_price;
        this.pro_count=pro_count;
        this.pro_total=pro_total;
        this.pro_coup=pro_coup;
        this.pro_id=pro_id;

    }

    public String getPro_img() {
        return pro_img;
    }

    public void setPro_img(String pro_img) {
        this.pro_img = pro_img;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_size() {
        return pro_size;
    }

    public void setPro_size(String pro_size) {
        this.pro_size = pro_size;
    }

    public String getPro_price() {
        return pro_price;
    }

    public void setPro_price(String pro_price) {
        this.pro_price = pro_price;
    }

    public String getPro_count() {
        return pro_count;
    }

    public void setPro_count(String pro_count) {
        this.pro_count = pro_count;
    }

    public String getPro_total() {
        return pro_total;
    }

    public void setPro_total(String pro_total) {
        this.pro_total = pro_total;
    }
    public String getPro_coup() {
        return pro_coup;
    }

    public void setPro_coup(String pro_coup) {
        this.pro_coup = pro_coup;
    }
    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

}
