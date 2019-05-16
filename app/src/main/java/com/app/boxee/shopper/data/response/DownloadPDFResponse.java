package com.app.boxee.shopper.data.response;

import com.app.boxee.shopper.database.entity.ShopperIncommingShipment;
import com.app.boxee.shopper.database.entity.ShopperOutgoingShipment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadPDFResponse {
    @SerializedName("download_pdf")
    @Expose
    private DownloadPdf downloadPdf;

    public DownloadPdf getDownloadPdf() {
        return downloadPdf;
    }

    public void setDownloadPdf(DownloadPdf downloadPdf) {
        this.downloadPdf = downloadPdf;
    }

}
