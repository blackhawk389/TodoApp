package com.app.boxee.shopper.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.app.boxee.shopper.database.converter.DateConverter;
import com.app.boxee.shopper.database.converter.ListConverter;
import com.app.boxee.shopper.database.dao.ShopperDao;
import com.app.boxee.shopper.database.entity.Ar;
import com.app.boxee.shopper.database.entity.En;
import com.app.boxee.shopper.database.entity.Locations;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.database.entity.Shopper;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.database.entity.ShopperIncommingShipment;
import com.app.boxee.shopper.database.entity.ShopperOutgoingShipment;
import com.app.boxee.shopper.database.entity.TitlesTickets;
import com.app.boxee.shopper.database.entity.User;
import com.app.boxee.shopper.database.entity.Warehouse;

@Database(entities = {User.class, MetadataData.class, ShopperData.class}, version = 6,exportSchema = false)
@TypeConverters({ListConverter.class, DateConverter.class, Warehouse.class, Locations.class, En.class, Ar.class, Shopper.class, ShopperIncommingShipment.class, ShopperOutgoingShipment.class,TitlesTickets.class})
public abstract class ShopperDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile ShopperDatabase INSTANCE;

    // --- DAO ---
    public abstract ShopperDao userDao();
}
