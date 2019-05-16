package com.app.boxee.shopper.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.adapter.NavViewListAdapter;
import com.app.boxee.shopper.dailog.LoaderDialog;
import com.app.boxee.shopper.data.AppLanguage;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.NavViewListItem;
import com.app.boxee.shopper.database.entity.Ar;
import com.app.boxee.shopper.database.entity.En;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.app.boxee.shopper.fragments.ContactUsFragment;
import com.app.boxee.shopper.fragments.DashboardFragment;
import com.app.boxee.shopper.fragments.HelpFragment;
import com.app.boxee.shopper.fragments.HomeFragment;
import com.app.boxee.shopper.fragments.MyAddressesFragment;
import com.app.boxee.shopper.fragments.ProfileFragment;
import com.app.boxee.shopper.fragments.WebViewFragment;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.TinyDB;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.utils.Vals;
import com.app.boxee.shopper.view_models.MainViewModel;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    //FOR DESIGN
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    @BindView(R.id.language)
    TextView languageChange;
    @BindView(R.id.heading)
    TextView name;
    @BindView(R.id.notification_txt)
    TextView notificationTxt;
    @BindView(R.id.tv_nav_view_item_title)
    TextView signOut;
    @BindView(R.id.footer)
    LinearLayout signOutLayout;
    @BindView(R.id.location)
    Spinner locations;
    @BindView(R.id.add_address)
    ImageView addAddress;

    @BindView(R.id.notification)
    SwitchCompat switchnotification;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainViewModel viewModel;
    private List<NavViewListItem> navItems;
    private NavViewListAdapter adapter;
    @BindView(R.id.nav_view_list)
    ListView navListView;
    private FirebaseDatabase database;
    private Env env;
    private List<AppLanguage> languages;
    private TinyDB tinydb;
//    @BindView(R.id.app_bar_spinner)
//    AppBarLayout appBarSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        env = EnvUtil.getInstance(this);
        this.configureDagger();
        this.configureViewModel(savedInstanceState);
        this.setActivity();



        switchnotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                viewModel.enablenotification(MainActivity.this, b);
            }
        });
    }

    private void configureViewModel(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        Bundle extra = getIntent().getExtras();
        if (savedInstanceState == null) {
            viewModel.init(savedInstanceState, getSupportFragmentManager(), R.id.fragment_container, this, getIntent().getExtras());
            viewModel.metadata(this);
            viewModel.getMataData().observe(this, response -> setcities(response));
        }

    }

    private void setcities(MetadataData response) {
        if (response != null) {
            try {
                if (tinydb.getString(Vals.CURRENT_LANGUAGE).equals("ar")) {
                    List<Ar> location = response.getLocations().getAr();
                    List<String> list = new ArrayList<>();
                    list.add(env.getAppLocationText());
                    for (Ar loc : location) {
                        if (loc.getType().equals("city")){
                            if (loc.getType().equals("city"))
                                if(loc.getHasOperation().equalsIgnoreCase("1")){
                                    list.add(loc.getName());
                                }
                        }
                    }
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.sipinner_drop_down_list, list) {

                    };
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

                    locations.setAdapter(spinnerAdapter);

                } else {
                    List<En> location = response.getLocations().getEn();
                    List<String> list = new ArrayList<>();
                    list.add(env.getAppLocationText());
                    for (En loc : location) {
                        if (loc.getType().equals("city"))
                            if(loc.getHasOperation().equals("1")){
                                list.add(loc.getName());
                            }
                    }
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.sipinner_drop_down_list, list) {
                    };

                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    locations.setAdapter(spinnerAdapter);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    private void setActivity() {
        tinydb = TinyDB.getInstance();
        Utils.setLocale(this, tinydb.getString(Vals.CURRENT_LANGUAGE).equals("ar") ? "ar" : "en");
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationDrawerSetup();
    }

    public void setShopperName(String shopperName) {
        String[] nameStr = env.getAppSidemenuName().split("<");
        if (shopperName == null) {
            name.setText(env.getAppSidemenuName());
        } else if(shopperName.contains("null")) {
            name.setText(env.getAppSidemenuName() + shopperName.replace("null", ""));
        }else{
            name.setText(env.getAppSidemenuName() + shopperName.replace("null", ""));
        }
        if (Utils.getCurrentLanguage().equals("ar")) {
            Typeface bold = Typeface.createFromAsset(getAssets(), "font/ge_ss_text_bold.otf");
            name.setTypeface(bold);
        }
    }

    public void enableNotification(boolean t){
        switchnotification.setChecked(t);
    }

    private void navigationDrawerSetup() {

        notificationTxt.setText(env.getAppSidemenuNotifications());
        signOutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.logout(MainActivity.this);

            }
        });
        signOut.setText(env.getAppSidemenuSignOut());

        languages = env.getAppLanguages();
        languageChange.setText(Locale.getDefault().getLanguage().equals("en") ? languages.get(1).getName() : languages.get(0).getName());
        languageChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    drawer.closeDrawer(GravityCompat.START);
                }, 150);
                String locale = Locale.getDefault().getLanguage().equals("ar") ? "en" : "ar";
                Utils.setLocale(MainActivity.this, locale);
                TinyDB.getInstance().putString(Vals.CURRENT_LANGUAGE, Locale.getDefault().getLanguage());
                Intent refresh = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(refresh);
                finish();

            }
        });
        if (Utils.getCurrentLanguage().equals("ar")) {
//            Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_regular.otf");
            Typeface bold = Typeface.createFromAsset(getAssets(), "font/ge_ss_text_bold.otf");
            signOut.setTypeface(bold);
            notificationTxt.setTypeface(bold);
            languageChange.setTypeface(Typeface.createFromAsset(getAssets(), "font/worksans_semibold.ttf"));

        } else {
            languageChange.setTypeface(Typeface.createFromAsset(getAssets(), "font/ge_ss_text_bold.otf"));
        }
        navItems = Utils.getNavViewItems(this);
        adapter = new NavViewListAdapter(this, R.layout.nav_view_item, navItems);
        navListView.setAdapter(adapter);
        navListView.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            for (NavViewListItem item : navItems) {
                item.setSelected(false);
            }
            navItems.get(i).setSelected(true);
            adapter = new NavViewListAdapter(MainActivity.this, R.layout.nav_view_item, navItems);
            navListView.setAdapter(adapter);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                drawer.closeDrawer(GravityCompat.START);
            }, 150);
//            if (navItems.get(i).title.equalsIgnoreCase(env.getAppSidemenu().get(0).getValue())) {
//
//                if (viewModel.getShopper() != null) {
//
//                    Utils.replaceFragment(getSupportFragmentManager(), HomeFragment.newInstance(viewModel.getShopper()), R.id.fragment_container, false, true);
//
//                } else {
//                    Utils.replaceFragment(getSupportFragmentManager(), DashboardFragment.newInstance(), R.id.fragment_container, false, true);
//                }
//            }
            if (navItems.get(i).title.equalsIgnoreCase(env.getAppSidemenu().get(0).getValue())) {
                Utils.replaceFragment(getSupportFragmentManager(), ProfileFragment.newInstance(), R.id.fragment_container, true, true);
            } else if (navItems.get(i).title.equalsIgnoreCase(env.getAppSidemenu().get(1).getValue())) {
//                Utils.replaceFragment(getSupportFragmentManager(), OrderHistoryFragment.newInstance(), R.id.fragment_container, false, true);
            } else if (navItems.get(i).title.equalsIgnoreCase(env.getAppSidemenu().get(2).getValue())) {
                Utils.replaceFragment(getSupportFragmentManager(), MyAddressesFragment.newInstance(null), R.id.fragment_container, true, true);

            } else if (navItems.get(i).title.equalsIgnoreCase(env.getAppSidemenu().get(3).getValue())){
                Utils.replaceFragment(getSupportFragmentManager(), HelpFragment.newInstance(true), R.id.fragment_container, true, true);

            } else if (navItems.get(i).title.equalsIgnoreCase(env.getAppSidemenu().get(4).getValue())){
                Utils.replaceFragment(getSupportFragmentManager(), new ContactUsFragment(), R.id.fragment_container, true, true);
            }else if (navItems.get(i).title.equalsIgnoreCase(env.getAppSidemenu().get(5).getValue())){
                Utils.replaceFragment(getSupportFragmentManager(), WebViewFragment.newInstance(Vals.GET_BASE_URL()+"cms/about_us/", true, false), R.id.fragment_container, true, true);
            }
            else if (navItems.get(i).title.equalsIgnoreCase(env.getAppSidemenu().get(7).getValue())){
                Utils.replaceFragment(getSupportFragmentManager(), WebViewFragment.newInstance( Vals
                        .GET_BASE_URL()+"cms/services/", true, true), R.id.fragment_container, true, true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        LoaderDialog.hideLoader(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
        Utils.hideKeyboard(this);
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackCount >= 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }


    private void configureDagger() {
        AndroidInjection.inject(this);
    }

//    public void setSpinner(boolean a) {
//        if(a){
//            appBarSpinner.setVisibility(View.VISIBLE);
//        }else{
//            appBarSpinner.setVisibility(View.GONE);
//        }
//
//
//    }

    public void setBack(boolean b) {
        Utils.hideKeyboard(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (b) {
            toggle.setDrawerIndicatorEnabled(false);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toggle.setToolbarNavigationClickListener(v -> onBackPressed());

        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.setToolbarNavigationClickListener(null);

        }
        Fragment f = ((Fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container));
        if (f instanceof MyAddressesFragment) {
            addAddress.setVisibility(View.VISIBLE);
        } else {
            addAddress.setVisibility(View.GONE);
        }

    }

    public void hideAppBar(boolean b) {
        if (b) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }
    }

    public void changeTitle(String title, boolean showTitle) {
        if (showTitle) {
            getSupportActionBar().setDisplayShowTitleEnabled(showTitle);
            getSupportActionBar().setTitle(title);
            setSpinner(!showTitle);


        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(showTitle);
            setSpinner(!showTitle);
        }
    }

    public void setSpinner(Boolean showSpinner) {
        locations.setVisibility(showSpinner ? View.VISIBLE : View.GONE);
    }
}
