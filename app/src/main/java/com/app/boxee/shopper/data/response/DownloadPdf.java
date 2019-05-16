
package com.app.boxee.shopper.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadPdf {

    @SerializedName("awb_pdf_url")
    @Expose
    private String awbPdfUrl;

    public String getAwbPdfUrl() {
        return awbPdfUrl;
    }

    public void setAwbPdfUrl(String awbPdfUrl) {
        this.awbPdfUrl = awbPdfUrl;
    }

}
