package com.app.boxee.shopper.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.boxee.shopper.R;
import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.data.Env;
import com.app.boxee.shopper.data.request.EditProfileRequest;
import com.app.boxee.shopper.database.entity.ShopperData;
import com.app.boxee.shopper.services.FirebaseMessagingService;
import com.app.boxee.shopper.utils.EnvUtil;
import com.app.boxee.shopper.utils.Utils;
import com.app.boxee.shopper.view_models.ProfileViewModel;
import com.app.boxee.shopper.view_models.TicketViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.app.boxee.shopper.application.App.context;
import static com.app.boxee.shopper.utils.CameraUtils.Gallery_PERMISSION;
import static com.app.boxee.shopper.utils.CameraUtils.formatPickImage;
import static com.app.boxee.shopper.utils.CameraUtils.getPath;
import static com.app.boxee.shopper.utils.CameraUtils.hasPermission;

public class TicketFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.titleSpinner)
    Spinner titleSpinner;
    @BindView(R.id.messageEdit)
    EditText messageEdit;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.main_content)
    LinearLayout linearLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.message)
    TextView mess;
    @BindView(R.id.attachOne)
    RelativeLayout attachOne;
    @BindView(R.id.attachTwo)
    RelativeLayout attachTwo;
    @BindView(R.id.attachThree)
    RelativeLayout attachThree;



    @BindView(R.id.imageview)
    ImageView imageView;
    @BindView(R.id.imageview1)
    ImageView imageView1;
    @BindView(R.id.imageview2)
    ImageView imageView2;
    private int RESULT_LOAD_IMAGE = 1;
    private int RESULT_LOAD_IMAGE_TWO = 2;
    private int RESULT_LOAD_IMAGE_THREE = 3;
    File file, file1, file2;
    ArrayList<String> titles;

    @BindView(R.id.c)
    ImageButton c1;

    @BindView(R.id.c1)
    ImageButton c2;

    @BindView(R.id.c2)
    ImageButton c3;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private TicketViewModel viewModel;
    private ShopperData shopper;
    private List<File> imagesList = new ArrayList<File>();
    String consignmentId = "";
    private View lastView;
    private Env env;


    public TicketFragment() {
        // Required empty public constructor
    }

    public static TicketFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
        TicketFragment fragment = new TicketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        ButterKnife.bind(this, view);
        setStrings();
        setFonts();
        listeners();
        return view;
    }

    private void setFonts() {
        if (Utils.getCurrentLanguage().equals("ar")) {
            Typeface regular = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_regular.otf");
            Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "font/ge_ss_text_bold.otf");
            messageEdit.setTypeface(regular);
            submit.setTypeface(bold);

        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        env = EnvUtil.getEnv(getActivity());
        this.configureViewModel();
    }

//    private void configureViewModel() {
//        updateUI();
//    }

    private void updateUI(ShopperData shopper) {


        if (Locale.getDefault().getLanguage().equals("")) {
            titles = (ArrayList<String>) shopper.getTicketList().getEnTitles();
            titles.add(0, env.getSaudiaarabrateserviceentertitle());
        } else {
            titles = (ArrayList<String>) shopper.getTicketList().getArTitles();
            titles.add(0, env.getSaudiaarabrateserviceentertitle());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.sipinner_drop_down_list_black, titles) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
//                ((TextView) v).setTextSize(15);
//                v.setPadding(90, 0, 90, 15);
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                v.setPadding(20, 0, 20, 20);
                return v;
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        titleSpinner.setAdapter(spinnerAdapter);
    }

    private void updateUIOnComplete(ShopperData shopper) {
        getFragmentManager().popBackStack();
    }

    private void listeners() {
        attachOne.setOnClickListener(this::onClick);
        attachTwo.setOnClickListener(this::onClick);
        attachThree.setOnClickListener(this::onClick);
        submit.setOnClickListener(this::onClick);

        c1.setOnClickListener(this::onClick);
        c2.setOnClickListener(this::onClick);
        c3.setOnClickListener(this::onClick);
    }

    private void setStrings() {

        Env env = EnvUtil.getInstance(getActivity());
        title.setText(env.getAppbtnpickuplcationdatetitle());
        mess.setText(env.getAppenerateticketToastmesLable());
        messageEdit.setHint(env.getAppmsgenter());
        submit.setText(env.getAppenerateticket());
        ((MainActivity) getActivity()).hideAppBar(false);
        ((MainActivity) getActivity()).setBack(true);
        ((MainActivity) getActivity()).changeTitle(env.getAppenerateticket(), true);

        if (getArguments() != null && getArguments().getBoolean("parentDashboard")) {
            ((MainActivity) getActivity()).hideAppBar(false);
            ((MainActivity) getActivity()).setBack(true);
            ((MainActivity) getActivity()).changeTitle(env.getAppenerateticket(), true);

        } else {
            Configuration config = getActivity().getResources().getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                    //in Right To Left layout
//                selectModeRv.setRotationY(180);
                   // linearLayout.setRotationY(180);

                }
            }
        }
        submit.setText(env.getAppTrackingBtnSubmit());
        consignmentId = getArguments().getString("id") + "";

    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TicketViewModel.class);
        shopper = viewModel.init();
        updateUI(shopper);
    }

    @Override
    public void onClick(View view) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        lastView = view;
        switch (view.getId()) {
            case R.id.attachOne:
                if (!hasPermission(this))
                    return;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
                break;
            case R.id.attachTwo:
                if (!hasPermission(this))
                    return;
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Select Picture"), RESULT_LOAD_IMAGE_TWO);
                break;
            case R.id.attachThree:
                if (!hasPermission(this))
                    return;
                Intent intent2 = new Intent();
                intent2.setType("image/*");
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent2, "Select Picture"), RESULT_LOAD_IMAGE_THREE);
                break;
            case R.id.submit:
                if ( !titleSpinner.getSelectedItem().toString().equalsIgnoreCase(titles.get(0))) {
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    builder.setType(MultipartBody.FORM);
                    builder.addFormDataPart("title", titleSpinner.getSelectedItem().toString());
                    builder.addFormDataPart("message", messageEdit.getText().toString());
                    builder.addFormDataPart("consignment_id", consignmentId);
                    if (file != null) {
                        imagesList.add(file);
                    }
                     if (file1 != null) {
                        imagesList.add(file1);
                    }
                     if (file2 != null) {
                        imagesList.add(file2);
                    }

                    for (File filePath : imagesList) {
                        builder.addFormDataPart("attachment_image[]", filePath.getName(),
                                RequestBody.create(MediaType.parse("image/*"), filePath));
                    }
                    viewModel.generateTicket(getActivity(), this, builder.build(), shopper);
                    viewModel.getShopper().observe(this, response -> updateUIOnComplete(shopper));
                } else {
                    if (titleSpinner.getSelectedItem().toString().equalsIgnoreCase(titles.get(0))) {
                        Toast.makeText(getActivity(), env.getSaudiaarabrateserviceentertitle(), Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getActivity(), "Please enter message", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.c:
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    attachTwo.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dotted_border) );
                } else {
                    attachOne.setBackground(ContextCompat.getDrawable(context, R.drawable.dotted_border));
                }
                imageView.setVisibility(View.VISIBLE);
                c1.setVisibility(View.GONE);
                file = null;
                break;
            case R.id.c1:
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    attachTwo.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dotted_border) );
                } else {
                    attachTwo.setBackground(ContextCompat.getDrawable(context, R.drawable.dotted_border));
                }

                imageView1.setVisibility(View.VISIBLE);
                c2.setVisibility(View.GONE);
                file1 = null;
                break;
            case R.id.c2:
                imageView2.setVisibility(View.VISIBLE);

                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    attachThree.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dotted_border) );
                } else {
                    attachThree.setBackground(ContextCompat.getDrawable(context, R.drawable.dotted_border));
                }
                c3.setVisibility(View.GONE);
                file2 = null;
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Gallery_PERMISSION) {
            for (int status : grantResults) {
                if (status != PERMISSION_GRANTED) {
                    return;
                }

            }
        }
        onClick(lastView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            //   Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            file = new File(formatPickImage(getActivity(), getPath(getActivity(), data.getData())));
            //InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImage);
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            if (file.length() > 300000) {
                Toast.makeText(getActivity(), "File Too Large", Toast.LENGTH_SHORT).show();
            } else {
                //  imagesList.add(file);
                Drawable dr = new BitmapDrawable(bitmap);
                imageView.setVisibility(View.GONE);
                attachOne.setBackground(dr);
                c1.setVisibility(View.VISIBLE);
            }


        } else if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            //Uri.parse(selectedImage)

            Bitmap bitmap = null;
            file1 = new File(formatPickImage(getActivity(), getPath(getActivity(), data.getData())));
            //InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImage);
            bitmap = BitmapFactory.decodeFile(file1.getAbsolutePath());
            if (file1.length() > 300000) {
                Toast.makeText(getActivity(), "File Too Large", Toast.LENGTH_SHORT).show();
            } else {
                //  imagesList.add(file1);
                Drawable dr = new BitmapDrawable(bitmap);
                imageView1.setVisibility(View.GONE);
                attachTwo.setBackground(dr);
                c2.setVisibility(View.VISIBLE);
            }


        } else if (requestCode == 3 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            file2 = new File(formatPickImage(getActivity(), getPath(getActivity(), data.getData())));
            //   InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImage);
           bitmap = BitmapFactory.decodeFile(file2.getAbsolutePath());


            if (file2.length() > 300000) {
                Toast.makeText(getActivity(), "File Too Large", Toast.LENGTH_SHORT).show();
            } else {
                //  imagesList.add(file2);
                Drawable dr = new BitmapDrawable(bitmap);
                imageView2.setVisibility(View.GONE);
                attachThree.setBackground(dr);
                c3.setVisibility(View.VISIBLE);
            }


        }
    }

}
