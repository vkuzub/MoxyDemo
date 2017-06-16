package com.moxydemo.data.db.model;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Vyacheslav on 12.06.2017.
 */

@Entity
public class CitySuggestion implements SearchSuggestion {

    @Id(autoincrement = true)
    private Long _id;
    @Unique
    private String query;
    @Generated(hash = 1331202733)
    public CitySuggestion(Long _id, String query) {
        this._id = _id;
        this.query = query;
    }
    @Generated(hash = 289178131)
    public CitySuggestion() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getQuery() {
        return this.query;
    }
    public void setQuery(String query) {
        this.query = query;
    }


    @Override
    public String getBody() {
        return query;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this._id);
        dest.writeString(this.query);
    }

    protected CitySuggestion(Parcel in) {
        this._id = (Long) in.readValue(Long.class.getClassLoader());
        this.query = in.readString();
    }

    public static final Creator<CitySuggestion> CREATOR = new Creator<CitySuggestion>() {
        @Override
        public CitySuggestion createFromParcel(Parcel source) {
            return new CitySuggestion(source);
        }

        @Override
        public CitySuggestion[] newArray(int size) {
            return new CitySuggestion[size];
        }
    };
}
