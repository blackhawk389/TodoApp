package com.app.boxee.shopper.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.database.entity.User;

import java.util.Date;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface ShopperDao {

    @Insert(onConflict = REPLACE)
    void save(User user);

    @Query("SELECT * FROM user WHERE login = :userLogin")
    LiveData<User> load(String userLogin);

    @Query("SELECT * FROM user WHERE login = :userLogin AND lastRefresh > :lastRefreshMax LIMIT 1")
    User hasUser(String userLogin, Date lastRefreshMax);
    @Query("SELECT * FROM metadata")
    LiveData<MetadataData> loadMataData();
    @Query("SELECT * FROM metadata")
    MetadataData hasMetaData();
    @Insert(onConflict = REPLACE)
    void addMetaData(MetadataData metadataData);
    @Query("SELECT * FROM ShopperData")
    ShopperData hasShopper();
    @Insert(onConflict = REPLACE)
    void addShopper(ShopperData shopperData);
    @Update(onConflict = REPLACE)
    void updateShopper(ShopperData shopperData);
    @Query("SELECT * FROM ShopperData")
    LiveData<ShopperData> loadShopper();
    @Query("DELETE FROM ShopperData")
    void logout();

}