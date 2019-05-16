package com.app.boxee.shopper.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.app.boxee.shopper.database.entity.User;
import com.app.boxee.shopper.repositories.ShopperRepository;

import javax.inject.Inject;

public class UserProfileViewModel extends ViewModel {

    private LiveData<User> user;
    private ShopperRepository userRepo;

    @Inject
    public UserProfileViewModel(ShopperRepository userRepo) {
        this.userRepo = userRepo;
    }

    // ----

    public void init(String userId) {
        if (this.user != null) {
            return;
        }
        user = userRepo.getUser(userId);
    }

    public LiveData<User> getUser() {
        return this.user;
    }
}
