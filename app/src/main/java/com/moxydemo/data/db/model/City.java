package com.moxydemo.data.db.model;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Vyacheslav on 01.06.2017.
 */
@Entity
public class City implements SearchSuggestion {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city1 = (City) o;

        if (_id != null ? !_id.equals(city1._id) : city1._id != null) return false;
        return city != null ? city.equals(city1.city) : city1.city == null;

    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                "_id=" + _id +
                ", city='" + city + '\'' +
                ", ll='" + ll + '\'' +
                ", favourited=" + favourited +
                '}';
    }

    @Override
    public String getBody() {
        return city;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this._id);
        dest.writeString(this.city);
        dest.writeString(this.ll);
        dest.writeByte(this.favourited ? (byte) 1 : (byte) 0);
    }

    public boolean getFavourited() {
        return this.favourited;
    }

    protected City(Parcel in) {
        this._id = (Long) in.readValue(Long.class.getClassLoader());
        this.city = in.readString();
        this.ll = in.readString();
        this.favourited = in.readByte() != 0;
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
