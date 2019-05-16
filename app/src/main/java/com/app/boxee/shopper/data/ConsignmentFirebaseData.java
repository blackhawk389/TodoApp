package com.app.boxee.shopper.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.app.boxee.shopper.database.entity.En;
import com.app.boxee.shopper.database.entity.Locations;
import com.app.boxee.shopper.database.entity.MetadataData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ConsignmentFirebaseData implements Parcelable {
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("billing_type")
    @Expose
    private String billing_type;
    @SerializedName("booking_date")
    @Expose
    private String booking_date;
    @SerializedName("collect_amount")
    @Expose
    private Double collect_amount;


    protected ConsignmentFirebaseData(Parcel in) {
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readDouble();
        }
        billing_type = in.readString();
        booking_date = in.readString();
        if (in.readByte() == 0) {
            collect_amount = null;
        } else {
            collect_amount = in.readDouble();
        }
        is_paid = in.readInt();
        consignee = in.readString();
        destination_area = in.readString();
        consignor_address_house_appartment = in.readString();
        consignor_address_landmark = in.readString();
        consignor_address_two = in.readString();
        origin_area = in.readString();
        consignee_address_house_appartment = in.readString();
        consignee_address_landmark = in.readString();
        consignee_address_two = in.readString();
        consignee_address_one = in.readString();
        consignee_email = in.readString();
        consignee_phone = in.readString();
        consignment_id = in.readLong();
        consignment_no = in.readLong();
        parent_consignment_id = in.readLong();
        primary_key = in.readLong();
        consignor = in.readString();
        consignor_address_one = in.readString();
        consignor_email = in.readString();
        consignor_phone = in.readString();
        created_at = in.readString();
        if (in.readByte() == 0) {
            created_by = null;
        } else {
            created_by = in.readInt();
        }
        currency_code = in.readString();
        if (in.readByte() == 0) {
            currency_conversion_rate = null;
        } else {
            currency_conversion_rate = in.readDouble();
        }
        currency_name = in.readString();
        if (in.readByte() == 0) {
            deleted_by = null;
        } else {
            deleted_by = in.readInt();
        }
        if (in.readByte() == 0) {
            delivery_attempt = null;
        } else {
            delivery_attempt = in.readDouble();
        }
        if (in.readByte() == 0) {
            depth = null;
        } else {
            depth = in.readDouble();
        }
        destination_city = in.readString();
        if (in.readByte() == 0) {
            fk_consignment_activity = null;
        } else {
            fk_consignment_activity = in.readInt();
        }
        if (in.readByte() == 0) {
            fk_currency = null;
        } else {
            fk_currency = in.readInt();
        }
        if (in.readByte() == 0) {
            fk_runsheet_active = null;
        } else {
            fk_runsheet_active = in.readInt();
        }
        if (in.readByte() == 0) {
            fk_service_primary = null;
        } else {
            fk_service_primary = in.readInt();
        }
        fk_service_secondary = in.readString();
        if (in.readByte() == 0) {
            fk_shopper = null;
        } else {
            fk_shopper = in.readInt();
        }
        if (in.readByte() == 0) {
            fk_status = null;
        } else {
            fk_status = in.readInt();
        }
        if (in.readByte() == 0) {
            fk_webshop = null;
        } else {
            fk_webshop = in.readInt();
        }
        has_active_runsheet = in.readString();
        if (in.readByte() == 0) {
            height = null;
        } else {
            height = in.readDouble();
        }
        if (in.readByte() == 0) {
            is_return = null;
        } else {
            is_return = in.readInt();
        }
        scanned = in.readByte() != 0;
        if (in.readByte() == 0) {
            item_value = null;
        } else {
            item_value = in.readDouble();
        }
        if (in.readByte() == 0) {
            number_attemps = null;
        } else {
            number_attemps = in.readInt();
        }
        order_date = in.readString();
        order_id = in.readString();
        origin_city = in.readString();
        if (in.readByte() == 0) {
            pieces = null;
        } else {
            pieces = in.readInt();
        }
        if (in.readByte() == 0) {
            reference_cn = null;
        } else {
            reference_cn = in.readInt();
        }
        seq_no = in.readString();
        type = in.readString();
        currency_symbol_left = in.readString();
        updated_at = in.readString();
        if (in.readByte() == 0) {
            updated_by = null;
        } else {
            updated_by = in.readInt();
        }
        if (in.readByte() == 0) {
            fk_reason_unsuccessful = null;
        } else {
            fk_reason_unsuccessful = in.readInt();
        }
        if (in.readByte() == 0) {
            weight_actual = null;
        } else {
            weight_actual = in.readDouble();
        }
        if (in.readByte() == 0) {
            weight_applied = null;
        } else {
            weight_applied = in.readDouble();
        }
        weight_volumetric = in.readString();
        if (in.readByte() == 0) {
            width = null;
        } else {
            width = in.readDouble();
        }
        fk_status_name = in.readString();
        fk_webshop_name = in.readString();
        pic = in.readString();
        file_id = in.readInt();
        found = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(amount);
        }
        dest.writeString(billing_type);
        dest.writeString(booking_date);
        if (collect_amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(collect_amount);
        }
        dest.writeInt(is_paid);
        dest.writeString(consignee);
        dest.writeString(destination_area);
        dest.writeString(consignor_address_house_appartment);
        dest.writeString(consignor_address_landmark);
        dest.writeString(consignor_address_two);
        dest.writeString(origin_area);
        dest.writeString(consignee_address_house_appartment);
        dest.writeString(consignee_address_landmark);
        dest.writeString(consignee_address_two);
        dest.writeString(consignee_address_one);
        dest.writeString(consignee_email);
        dest.writeString(consignee_phone);
        dest.writeLong(consignment_id);
        dest.writeLong(consignment_no);
        dest.writeLong(parent_consignment_id);
        dest.writeLong(primary_key);
        dest.writeString(consignor);
        dest.writeString(consignor_address_one);
        dest.writeString(consignor_email);
        dest.writeString(consignor_phone);
        dest.writeString(created_at);
        if (created_by == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(created_by);
        }
        dest.writeString(currency_code);
        if (currency_conversion_rate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(currency_conversion_rate);
        }
        dest.writeString(currency_name);
        if (deleted_by == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(deleted_by);
        }
        if (delivery_attempt == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(delivery_attempt);
        }
        if (depth == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(depth);
        }
        dest.writeString(destination_city);
        if (fk_consignment_activity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fk_consignment_activity);
        }
        if (fk_currency == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fk_currency);
        }
        if (fk_runsheet_active == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fk_runsheet_active);
        }
        if (fk_service_primary == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fk_service_primary);
        }
        dest.writeString(fk_service_secondary);
        if (fk_shopper == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fk_shopper);
        }
        if (fk_status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fk_status);
        }
        if (fk_webshop == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fk_webshop);
        }
        dest.writeString(has_active_runsheet);
        if (height == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(height);
        }
        if (is_return == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(is_return);
        }
        dest.writeByte((byte) (scanned ? 1 : 0));
        if (item_value == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(item_value);
        }
        if (number_attemps == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(number_attemps);
        }
        dest.writeString(order_date);
        dest.writeString(order_id);
        dest.writeString(origin_city);
        if (pieces == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pieces);
        }
        if (reference_cn == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(reference_cn);
        }
        dest.writeString(seq_no);
        dest.writeString(type);
        dest.writeString(currency_symbol_left);
        dest.writeString(updated_at);
        if (updated_by == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(updated_by);
        }
        if (fk_reason_unsuccessful == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fk_reason_unsuccessful);
        }
        if (weight_actual == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(weight_actual);
        }
        if (weight_applied == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(weight_applied);
        }
        dest.writeString(weight_volumetric);
        if (width == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(width);
        }
        dest.writeString(fk_status_name);
        dest.writeString(fk_webshop_name);
        dest.writeString(pic);
        dest.writeInt(file_id);
        dest.writeByte((byte) (found ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ConsignmentFirebaseData> CREATOR = new Creator<ConsignmentFirebaseData>() {
        @Override
        public ConsignmentFirebaseData createFromParcel(Parcel in) {
            return new ConsignmentFirebaseData(in);
        }

        @Override
        public ConsignmentFirebaseData[] newArray(int size) {
            return new ConsignmentFirebaseData[size];
        }
    };

    public int getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(int is_paid) {
        this.is_paid = is_paid;
    }

    @SerializedName("is_paid")
    @Expose
    private int is_paid;
    @SerializedName("consignee")
    @Expose
    private String consignee;

    @SerializedName("destination_area")
    @Expose
    private String destination_area;


    public String getAddress(MetadataData realm) {
        String apartment = this.getConsignee_address_house_appartment();
        String adrressTwo = this.getConsignee_address_two();
        String adressOne = this.getConsignee_address_one();
        String landMark = this.getConsignee_address_landmark();
        String destArea = findInMeta(this.getDestination_area(), realm);
        String destCity = findInMeta(this.getDestination_city(), realm);
        return ((destCity != null && !destCity.isEmpty()) ? destCity + ", ": "") +
                ((destArea != null && !destArea.isEmpty()) ? destArea + ", " : "") +
                ((adressOne != null && !adressOne.isEmpty()) ? adressOne + ", " : "") +
                ((landMark != null && !landMark.isEmpty()) ? landMark + ", " : "") +
                ((adrressTwo != null && !adrressTwo.isEmpty()) ? adrressTwo + ", " : "") +
                ((apartment != null && !apartment.isEmpty()) ? apartment : "");
//                ((destArea != null && !destArea.isEmpty()) ? destArea + ", " : "") +
    }

    public String getConsignor_address_house_appartment() {
        return consignor_address_house_appartment;
    }

    public void setConsignor_address_house_appartment(String consignor_address_house_appartment) {
        this.consignor_address_house_appartment = consignor_address_house_appartment;
    }

    public String getConsignor_address_landmark() {
        return consignor_address_landmark;
    }

    public void setConsignor_address_landmark(String consignor_address_landmark) {
        this.consignor_address_landmark = consignor_address_landmark;
    }

    public String getConsignor_address_two() {
        return consignor_address_two;
    }

    public void setConsignor_address_two(String consignor_address_two) {
        this.consignor_address_two = consignor_address_two;
    }

    public Integer getFk_reason_unsuccessful() {
        return fk_reason_unsuccessful;
    }

    public void setFk_reason_unsuccessful(Integer fk_reason_unsuccessful) {
        this.fk_reason_unsuccessful = fk_reason_unsuccessful;
    }

    @SerializedName("consignor_address_house_appartment")
    @Expose
    private String consignor_address_house_appartment;
    @SerializedName("consignor_address_landmark")
    @Expose
    private String consignor_address_landmark;
    @SerializedName("consignor_address_two")
    @Expose
    private String consignor_address_two;

    public String getOrigin_area() {
        return origin_area;
    }

    public void setOrigin_area(String origin_area) {
        this.origin_area = origin_area;
    }

    @SerializedName("origin_area")
    @Expose
    private String origin_area;

//    public String getAddress() {
//        String apartment = this.getConsignee_address_house_appartment();
//        String adrressTwo = this.getConsignee_address_two();
//        String adressOne = this.getConsignee_address_one();
//        String landMark = this.getConsignee_address_landmark();
//        return ((adressOne != null && !adressOne.isEmpty()) ? adressOne + ", " : "") +
//                ((landMark != null && !landMark.isEmpty()) ? landMark + ", " : "") +
//                ((adrressTwo != null && !adrressTwo.isEmpty()) ? adrressTwo + ", " : "") +
//                ((apartment != null && !apartment.isEmpty()) ? apartment : "");
////                ((destArea != null && !destArea.isEmpty()) ? destArea + ", " : "") +
//    }
//
//    public String getAddressCons() {
//        String appartment = this.getConsignor_address_house_appartment();
//        String address_two = this.getConsignor_address_two();
//        String address_one = this.getConsignor_address_one();
//        String landmark = this.getConsignor_address_landmark();
//        return ((address_one != null && !address_one.isEmpty()) ? address_one + ", " : "") +
//                ((landmark != null && !landmark.isEmpty()) ? landmark + ", " : "") +
//                ((address_two != null && !address_two.isEmpty()) ? address_two + ", " : "") +
//                ((appartment != null && !appartment.isEmpty()) ? appartment : "");
//    }
    public String getAddressCons(MetadataData realm) {
        String appartment = this.getConsignor_address_house_appartment();
        String address_two = this.getConsignor_address_two();
        String address_one = this.getConsignor_address_one();
        String Origin_area = findInMeta(this.getOrigin_area(), realm);
        String Origin_city = findInMeta(this.getOrigin_city(), realm);
        String landmark = this.getConsignor_address_landmark();
//        return (appartment == null || appartment.isEmpty() ? "" : appartment + ", ") +
//                (address_two == null || address_two.isEmpty() ? "" : address_two + ", ") +
//                (address_one == null || address_one.isEmpty() ? "" : address_one + ", ")
//                + (Origin_area == null || Origin_area.isEmpty() ? "" : Origin_area + ", ") +
//                (landmark == null || landmark.isEmpty() ? "" : landmark + ", ") +
//                (Origin_city == null || Origin_city.isEmpty() ? "" : Origin_city);
        return ((Origin_city != null && !Origin_city.isEmpty()) ? Origin_city + ", ": "") +
                ((Origin_area != null && !Origin_area.isEmpty()) ? Origin_area + ", " : "") +
                ((address_one != null && !address_one.isEmpty()) ? address_one + ", " : "") +
                ((landmark != null && !landmark.isEmpty()) ? landmark + ", " : "") +
                ((address_two != null && !address_two.isEmpty()) ? address_two + ", " : "") +
                ((appartment != null && !appartment.isEmpty()) ? appartment : "");
    }
//
    private String findInMeta(String destination_area, MetadataData metadata) {
        String result = "";
        Locations locations = metadata.getLocations();
        List<En> enLocations = locations.getEn();
        for (int i = 0; i < enLocations.size(); i++) {
            if (destination_area != null && enLocations.get(i).getLocationId() == Integer.parseInt(destination_area)) {
                result = enLocations.get(i).getName();
                return result == null ? "" : result;
            }
        }
        return result;
    }


    public String getDestination_area() {
        return destination_area;
    }

    public void setDestination_area(String destination_area) {
        this.destination_area = destination_area;
    }


    public String getConsignee_address_house_appartment() {
        return consignee_address_house_appartment;
    }

    public void setConsignee_address_house_appartment(String consignee_address_house_appartment) {
        this.consignee_address_house_appartment = consignee_address_house_appartment;
    }

    public String getConsignee_address_landmark() {
        return consignee_address_landmark;
    }

    public void setConsignee_address_landmark(String consignee_address_landmark) {
        this.consignee_address_landmark = consignee_address_landmark;
    }

    public String getConsignee_address_two() {
        return consignee_address_two;
    }

    public void setConsignee_address_two(String consignee_address_two) {
        this.consignee_address_two = consignee_address_two;
    }

    @SerializedName("consignee_address_house_appartment")
    @Expose
    private String consignee_address_house_appartment;
    @SerializedName("consignee_address_landmark")
    @Expose
    private String consignee_address_landmark;
    @SerializedName("consignee_address_two")
    @Expose
    private String consignee_address_two;
    @SerializedName("consignee_address_one")
    @Expose
    private String consignee_address_one;
    @SerializedName("consignee_email")
    @Expose
    private String consignee_email;
    @SerializedName("consignee_phone")
    @Expose
    private String consignee_phone;
    @SerializedName("consignment_id")
    @Expose
    private long consignment_id;
    @SerializedName("consignment_no")
    @Expose
    private long consignment_no;

    @SerializedName("parent_consignment_id")
    @Expose
    private long parent_consignment_id;
    @SerializedName("primary_key")
    @Expose
    private long primary_key;
    @SerializedName("consignor")
    @Expose
    private String consignor;
    @SerializedName("consignor_address_one")
    @Expose
    private String consignor_address_one;
    @SerializedName("consignor_email")
    @Expose
    private String consignor_email;
    @SerializedName("consignor_phone")
    @Expose
    private String consignor_phone;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("created_by")
    @Expose
    private Integer created_by;
    @SerializedName("currency_code")
    @Expose
    private String currency_code;
    @SerializedName("currency_conversion_rate")
    @Expose
    private Double currency_conversion_rate;
    @SerializedName("currency_name")
    @Expose
    private String currency_name;
    @SerializedName("deleted_by")
    @Expose
    private Integer deleted_by;
    @SerializedName("delivery_attempt")
    @Expose
    private Double delivery_attempt;
    @SerializedName("depth")
    @Expose
    private Double depth;
    @SerializedName("destination_city")
    @Expose
    private String destination_city;
    @SerializedName("fk_consignment_activity")
    @Expose
    private Integer fk_consignment_activity;
    @SerializedName("fk_currency")
    @Expose
    private Integer fk_currency;
    @SerializedName("fk_runsheet_active")
    @Expose
    private Integer fk_runsheet_active;
    @SerializedName("fk_service_primary")
    @Expose
    private Integer fk_service_primary;
    @SerializedName("fk_service_secondary")
    @Expose
    private String fk_service_secondary;
    @SerializedName("fk_shopper")
    @Expose
    private Integer fk_shopper;
    @SerializedName("fk_status")
    @Expose
    private Integer fk_status;
    @SerializedName("fk_webshop")
    @Expose
    private Integer fk_webshop;
    @SerializedName("has_active_runsheet")
    @Expose
    private String has_active_runsheet;
    @SerializedName("height")
    @Expose
    private Double height;
    @SerializedName("is_return")
    @Expose
    private Integer is_return;
    @SerializedName("scanned")
    @Expose
    private boolean scanned;
    @SerializedName("item_value")
    @Expose
    private Double item_value;
    @SerializedName("number_attemps")
    @Expose
    private Integer number_attemps;
    @SerializedName("order_date")
    @Expose
    private String order_date;
    @SerializedName("order_id")
    @Expose
    private String order_id;
    @SerializedName("origin_city")
    @Expose
    private String origin_city;

    @SerializedName("pieces")
    @Expose
    private Integer pieces;

    @SerializedName("reference_cn")
    @Expose
    private Integer reference_cn;
    @SerializedName("seq_no")
    @Expose
    private String seq_no;
    @SerializedName("type")
    @Expose
    private String type;


    @SerializedName("currency_symbol_left")
    @Expose
    private String currency_symbol_left;


    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    @SerializedName("updated_by")
    @Expose
    private Integer updated_by;

    @SerializedName("fk_reason_unsuccessful")
    @Expose
    private Integer fk_reason_unsuccessful;


    @SerializedName("weight_actual")
    @Expose
    private Double weight_actual;
    @SerializedName("weight_applied")
    @Expose
    private Double weight_applied;
    @SerializedName("weight_volumetric")
    @Expose
    private String weight_volumetric;
    @SerializedName("width")
    @Expose
    private Double width;


    public String getFk_status_name() {
        return fk_status_name;
    }

    public void setFk_status_name(String fk_status_name) {
        this.fk_status_name = fk_status_name;
    }

    @SerializedName("fk_status_name")
    @Expose
    private String fk_status_name;

    public String getFk_webshop_name() {
        return fk_webshop_name;
    }

    public void setFk_webshop_name(String fk_webshop_name) {
        this.fk_webshop_name = fk_webshop_name;
    }

    @SerializedName("fk_webshop_name")
    @Expose
    private String fk_webshop_name;


    public boolean isScanned() {
        return scanned;
    }

    public void setScanned(boolean scanned) {
        this.scanned = scanned;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    @SerializedName("pic")
    @Expose

    private String pic = null;
    @SerializedName("file_id")
    @Expose
    private int file_id;

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    @SerializedName("found")
    @Expose
    private boolean found;
    public ConsignmentFirebaseData() {

    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBilling_type() {
        return billing_type;
    }

    public void setBilling_type(String billing_type) {
        this.billing_type = billing_type;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public Double getCollect_amount() {
        return collect_amount;
    }

    public void setCollect_amount(Double collect_amount) {
        this.collect_amount = collect_amount;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee_address_one() {
        return consignee_address_one;
    }

    public void setConsignee_address_one(String consignee_address_one) {
        this.consignee_address_one = consignee_address_one;
    }

    public String getConsignee_email() {
        return consignee_email;
    }

    public void setConsignee_email(String consignee_email) {
        this.consignee_email = consignee_email;
    }

    public String getConsignee_phone() {
        return consignee_phone;
    }

    public void setConsignee_phone(String consignee_phone) {
        this.consignee_phone = consignee_phone;
    }

    public long getConsignment_id() {
        return consignment_id;
    }

    public void setConsignment_id(long consignment_id) {
        this.consignment_id = consignment_id;
    }

    public long getConsignment_no() {
        return consignment_no;
    }

    public void setConsignment_no(long consignment_no) {
        this.consignment_no = consignment_no;
    }

    public String getConsignor() {
        return consignor;
    }

    public void setConsignor(String consignor) {
        this.consignor = consignor;
    }

    public String getConsignor_address_one() {
        return consignor_address_one;
    }

    public void setConsignor_address_one(String consignor_address_one) {
        this.consignor_address_one = consignor_address_one;
    }

    public String getConsignor_email() {
        return consignor_email;
    }

    public void setConsignor_email(String consignor_email) {
        this.consignor_email = consignor_email;
    }

    public String getConsignor_phone() {
        return consignor_phone;
    }

    public void setConsignor_phone(String consignor_phone) {
        this.consignor_phone = consignor_phone;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Integer created_by) {
        this.created_by = created_by;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public Double getCurrency_conversion_rate() {
        return currency_conversion_rate;
    }

    public void setCurrency_conversion_rate(Double currency_conversion_rate) {
        this.currency_conversion_rate = currency_conversion_rate;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public Integer getDeleted_by() {
        return deleted_by;
    }

    public void setDeleted_by(Integer deleted_by) {
        this.deleted_by = deleted_by;
    }

    public Double getDelivery_attempt() {
        return delivery_attempt;
    }

    public void setDelivery_attempt(Double delivery_attempt) {
        this.delivery_attempt = delivery_attempt;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public String getDestination_city() {
        return destination_city;
    }

    public void setDestination_city(String destination_city) {
        this.destination_city = destination_city;
    }

    public Integer getFk_consignment_activity() {
        return fk_consignment_activity;
    }

    public void setFk_consignment_activity(Integer fk_consignment_activity) {
        this.fk_consignment_activity = fk_consignment_activity;
    }

    public Integer getFk_currency() {
        return fk_currency;
    }

    public void setFk_currency(Integer fk_currency) {
        this.fk_currency = fk_currency;
    }

    public Integer getFk_runsheet_active() {
        return fk_runsheet_active;
    }

    public void setFk_runsheet_active(Integer fk_runsheet_active) {
        this.fk_runsheet_active = fk_runsheet_active;
    }

    public Integer getFk_service_primary() {
        return fk_service_primary;
    }

    public void setFk_service_primary(Integer fk_service_primary) {
        this.fk_service_primary = fk_service_primary;
    }

    public String getFk_service_secondary() {
        return fk_service_secondary;
    }

    public void setFk_service_secondary(String fk_service_secondary) {
        this.fk_service_secondary = fk_service_secondary;
    }

    public Integer getFk_shopper() {
        return fk_shopper;
    }

    public void setFk_shopper(Integer fk_shopper) {
        this.fk_shopper = fk_shopper;
    }

    public Integer getFk_status() {
        return fk_status;
    }

    public void setFk_status(Integer fk_status) {
        this.fk_status = fk_status;
    }

    public Integer getFk_webshop() {
        return fk_webshop;
    }

    public void setFk_webshop(Integer fk_webshop) {
        this.fk_webshop = fk_webshop;
    }

    public String getHas_active_runsheet() {
        return has_active_runsheet;
    }

    public void setHas_active_runsheet(String has_active_runsheet) {
        this.has_active_runsheet = has_active_runsheet;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getIs_return() {
        return is_return;
    }

    public void setIs_return(Integer is_return) {
        this.is_return = is_return;
    }

    public Double getItem_value() {
        return item_value;
    }

    public void setItem_value(Double item_value) {
        this.item_value = item_value;
    }

    public Integer getNumber_attemps() {
        return number_attemps;
    }

    public void setNumber_attemps(Integer number_attemps) {
        this.number_attemps = number_attemps;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrigin_city() {
        return origin_city;
    }

    public void setOrigin_city(String origin_city) {
        this.origin_city = origin_city;
    }

    public long getParent_consignment_id() {
        return parent_consignment_id;
    }

    public void setParent_consignment_id(long parent_consignment_id) {
        this.parent_consignment_id = parent_consignment_id;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public long getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(long primary_key) {
        this.primary_key = primary_key;
    }

    public Integer getReference_cn() {
        return reference_cn;
    }

    public void setReference_cn(Integer reference_cn) {
        this.reference_cn = reference_cn;
    }

    public String getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(String seq_no) {
        this.seq_no = seq_no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(Integer updated_by) {
        this.updated_by = updated_by;
    }

    public Double getWeight_actual() {
        return weight_actual;
    }

    public void setWeight_actual(Double weight_actual) {
        this.weight_actual = weight_actual;
    }

    public Double getWeight_applied() {
        return weight_applied;
    }

    public void setWeight_applied(Double weight_applied) {
        this.weight_applied = weight_applied;
    }

    public String getWeight_volumetric() {
        return weight_volumetric;
    }

    public void setWeight_volumetric(String weight_volumetric) {
        this.weight_volumetric = weight_volumetric;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }


}