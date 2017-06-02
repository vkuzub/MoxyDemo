package com.moxydemo.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Vyacheslav on 01.06.2017.
 */
@Entity
public class City {

    @Id(autoincrement = true)
    private Long _id;

    private String city;
    private String ll;
    private boolean favourited;

    @Generated(hash = 70737194)
    public City(Long _id, String city, String ll, boolean favourited) {
        this._id = _id;
        this.city = city;
        this.ll = ll;
        this.favourited = favourited;
    }

    @Generated(hash = 750791287)
    public City() {
    }

    public boolean isFavourited() {
        return favourited;
    }

    public void setFavourited(boolean favourited) {
        this.favourited = favourited;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLl() {
        return ll;
    }

    public void setLl(String ll) {
        this.ll = ll;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public boolean getFavourited() {
        return this.favourited;
    }
}
