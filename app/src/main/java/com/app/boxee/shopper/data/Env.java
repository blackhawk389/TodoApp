
package com.app.boxee.shopper.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Env implements Serializable {

    @SerializedName("app.dashboard.welcome")
    @Expose
    private String appDashboardWelcome;
    @SerializedName("app.dashboard.signIn")
    @Expose
    private String appDashboardSignIn;
    @SerializedName("app.dashboard.trackOrder")
    @Expose
    private String appDashboardTrackOrder;
    @SerializedName("app.dashboard.clexLocations")
    @Expose
    private String appDashboardClexLocations;
    @SerializedName("app.dashboard.myCards")
    @Expose
    private String appDashboardMyCards;
    @SerializedName("app.dashboard.skip")
    @Expose
    private String appDashboardSkip;
    @SerializedName("app.login.signIn")
    @Expose
    private String appLoginSignIn;
    @SerializedName("app.login.btnLogin")
    @Expose
    private String appLoginBtnLogin;
    @SerializedName("app.login.loginTerms")
    @Expose
    private String appLoginLoginTerms;
    @SerializedName("app.sidemenu.name")
    @Expose
    private String appSidemenuName;
    @SerializedName("app.sidemenu.notifications")
    @Expose
    private String appSidemenuNotifications;
    @SerializedName("app.sidemenu")
    @Expose
    private List<AppSidemenu> appSidemenu = null;
    @SerializedName("app.languages")
    @Expose
    private List<AppLanguage> appLanguages = null;
    @SerializedName("app.home.title")
    @Expose
    private String appHomeTitle;
    @SerializedName("app.home.options")
    @Expose
    private List<AppHomeOption> appHomeOptions = null;
    @SerializedName("app.tracking.title")
    @Expose
    private String appTrackingTitle;
    @SerializedName("app.tracking.heading")
    @Expose
    private String appTrackingHeading;
    @SerializedName("app.tracking.etTrackingHint")
    @Expose
    private String appTrackingEtTrackingHint;
    @SerializedName("app.tracking.btnSubmit")
    @Expose
    private String appTrackingBtnSubmit;
    @SerializedName("app.orderStatus.title")
    @Expose
    private String appOrderStatusTitle;
    @SerializedName("app.orderStatus.noShipmentFound")
    @Expose
    private String appOrderStatusNoShipmentFound;
    @SerializedName("app.locations.title")
    @Expose
    private String appLocationsTitle;
    @SerializedName("app.locations.options")
    @Expose
    private List<AppLocationsOption> appLocationsOptions = null;
    @SerializedName("app.cards.title")
    @Expose
    private String appCardsTitle;
    @SerializedName("app.sidemenu.signOut")
    @Expose
    private String appSidemenuSignOut;
    @SerializedName("app.number.verification.title")
    @Expose
    private String appNumberVerificationTitle;
    @SerializedName("app.number.verification.heading")
    @Expose
    private String appNumberVerificationHeading;
    @SerializedName("app.number.verification.btnSubmit")
    @Expose
    private String appNumberVerificationBtnSubmit;
    @SerializedName("app.login.numberRequiredValidation")
    @Expose
    private String appLoginNumberRequiredValidation;
    @SerializedName("app.login.incorrectNumberValidation")
    @Expose
    private String appLoginIncorrectNumberValidation;
    @SerializedName("app.number.verification.incorrectValidation")
    @Expose
    private String appNumberVerificationIncorrectValidation;
    @SerializedName("app.home.options.incoming")
    @Expose
    private List<AppLocationsOption> appHomeOptionsIncoming = null;
    @SerializedName("app.home.options.outgoing")
    @Expose
    private List<AppLocationsOption> appHomeOptionsOutgoing = null;

    @SerializedName("app.address.default.dialog.delete.ok")
    @Expose
    private String appAddressDefaultDeleteDialogOk;

    public String getAppAddressarea() {
        return appAddressarea;
    }

    public void setAppAddressarea(String appAddressarea) {
        this.appAddressarea = appAddressarea;
    }

    @SerializedName("app.address.areaNeighbourhood")
    @Expose
    private String appAddressarea;

    public String getSaudiaarab() {
        return saudiaarab;
    }

    public void setSaudiaarab(String saudiaarab) {
        this.saudiaarab = saudiaarab;
    }

    @SerializedName("app.saudiaArabia")
    @Expose
    private String saudiaarab;

    public String getSaudiaarabrateservice() {
        return saudiaarabrateservice;
    }

    public void setSaudiaarabrateservice(String saudiaarabrateservice) {
        this.saudiaarabrateservice = saudiaarabrateservice;
    }

    @SerializedName("app.message.enter.resend.rate.service")
    @Expose
    private String saudiaarabrateservice;

    public String getSaudiaarabrateserviceentertitle() {
        return saudiaarabrateserviceentertitle;
    }

    public void setSaudiaarabrateserviceentertitle(String saudiaarabrateserviceentertitle) {
        this.saudiaarabrateserviceentertitle = saudiaarabrateserviceentertitle;
    }

    @SerializedName("app.generateTicket.titlePlaceHolder")
    @Expose
    private String saudiaarabrateserviceentertitle;

    public String getAreamsge() {
        return areamsge;
    }

    public void setAreamsge(String areamsge) {
        this.areamsge = areamsge;
    }

    @SerializedName("app.address.areaMsg")
    @Expose
    private String areamsge;

    public String getSaudiaarabrateserviceentertitleEnterArea() {
        return saudiaarabrateserviceentertitleEnterArea;
    }

    public void setSaudiaarabrateserviceentertitleEnterArea(String saudiaarabrateserviceentertitleEnterArea) {
        this.saudiaarabrateserviceentertitleEnterArea = saudiaarabrateserviceentertitleEnterArea;
    }

    @SerializedName("app.message,enter.resend.rate.service.enter.area")
    @Expose
    private String saudiaarabrateserviceentertitleEnterArea;


    public List<AppLocationsOption> getAppHomeOptionsIncoming() {
        return appHomeOptionsIncoming;
    }

    public void setAppHomeOptionsIncoming(List<AppLocationsOption> appHomeOptionsIncoming) {
        this.appHomeOptionsIncoming = appHomeOptionsIncoming;
    }

    public List<AppLocationsOption> getAppHomeOptionsOutgoing() {
        return appHomeOptionsOutgoing;
    }

    public void setAppHomeOptionsOutgoing(List<AppLocationsOption> appHomeOptionsOutgoing) {
        this.appHomeOptionsOutgoing = appHomeOptionsOutgoing;
    }

    public String getAppNumberVerificationIncorrectValidation() {
        return appNumberVerificationIncorrectValidation;
    }

    public void setAppNumberVerificationIncorrectValidation(String appNumberVerificationIncorrectValidation) {
        this.appNumberVerificationIncorrectValidation = appNumberVerificationIncorrectValidation;
    }

    public String getAppLoginNumberRequiredValidation() {
        return appLoginNumberRequiredValidation;
    }

    public void setAppLoginNumberRequiredValidation(String appLoginNumberRequiredValidation) {
        this.appLoginNumberRequiredValidation = appLoginNumberRequiredValidation;
    }

    public String getAppLoginIncorrectNumberValidation() {
        return appLoginIncorrectNumberValidation;
    }

    public void setAppLoginIncorrectNumberValidation(String appLoginIncorrectNumberValidation) {
        this.appLoginIncorrectNumberValidation = appLoginIncorrectNumberValidation;
    }
    public String getAppNumberVerificationTitle() {
        return appNumberVerificationTitle;
    }

    public void setAppNumberVerificationTitle(String appNumberVerificationTitle) {
        this.appNumberVerificationTitle = appNumberVerificationTitle;
    }

    public String getAppNumberVerificationHeading() {
        return appNumberVerificationHeading;
    }

    public void setAppNumberVerificationHeading(String appNumberVerificationHeading) {
        this.appNumberVerificationHeading = appNumberVerificationHeading;
    }

    public String getAppNumberVerificationBtnSubmit() {
        return appNumberVerificationBtnSubmit;
    }

    public void setAppNumberVerificationBtnSubmit(String appNumberVerificationBtnSubmit) {
        this.appNumberVerificationBtnSubmit = appNumberVerificationBtnSubmit;
    }


    public String getAppSidemenuSignOut() {
        return appSidemenuSignOut;
    }

    public void setAppSidemenuSignOut(String appSidemenuSignOut) {
        this.appSidemenuSignOut = appSidemenuSignOut;
    }
    public String getAppDashboardWelcome() {
        return appDashboardWelcome;
    }

    public void setAppDashboardWelcome(String appDashboardWelcome) {
        this.appDashboardWelcome = appDashboardWelcome;
    }

    public String getAppDashboardSignIn() {
        return appDashboardSignIn;
    }

    public void setAppDashboardSignIn(String appDashboardSignIn) {
        this.appDashboardSignIn = appDashboardSignIn;
    }

    public String getAppDashboardTrackOrder() {
        return appDashboardTrackOrder;
    }

    public void setAppDashboardTrackOrder(String appDashboardTrackOrder) {
        this.appDashboardTrackOrder = appDashboardTrackOrder;
    }

    public String getAppDashboardClexLocations() {
        return appDashboardClexLocations;
    }

    public void setAppDashboardClexLocations(String appDashboardClexLocations) {
        this.appDashboardClexLocations = appDashboardClexLocations;
    }

    public String getAppDashboardMyCards() {
        return appDashboardMyCards;
    }

    public void setAppDashboardMyCards(String appDashboardMyCards) {
        this.appDashboardMyCards = appDashboardMyCards;
    }

    public String getAppDashboardSkip() {
        return appDashboardSkip;
    }

    public void setAppDashboardSkip(String appDashboardSkip) {
        this.appDashboardSkip = appDashboardSkip;
    }

    public String getAppLoginSignIn() {
        return appLoginSignIn;
    }

    public void setAppLoginSignIn(String appLoginSignIn) {
        this.appLoginSignIn = appLoginSignIn;
    }

    public String getAppLoginBtnLogin() {
        return appLoginBtnLogin;
    }

    public void setAppLoginBtnLogin(String appLoginBtnLogin) {
        this.appLoginBtnLogin = appLoginBtnLogin;
    }

    public String getAppLoginLoginTerms() {
        return appLoginLoginTerms;
    }

    public void setAppLoginLoginTerms(String appLoginLoginTerms) {
        this.appLoginLoginTerms = appLoginLoginTerms;
    }

    public String getAppSidemenuName() {
        return appSidemenuName;
    }

    public void setAppSidemenuName(String appSidemenuName) {
        this.appSidemenuName = appSidemenuName;
    }

    public String getAppSidemenuNotifications() {
        return appSidemenuNotifications;
    }

    public void setAppSidemenuNotifications(String appSidemenuNotifications) {
        this.appSidemenuNotifications = appSidemenuNotifications;
    }

    public List<AppSidemenu> getAppSidemenu() {
        return appSidemenu;
    }

    public void setAppSidemenu(List<AppSidemenu> appSidemenu) {
        this.appSidemenu = appSidemenu;
    }

    public List<AppLanguage> getAppLanguages() {
        return appLanguages;
    }

    public void setAppLanguages(List<AppLanguage> appLanguages) {
        this.appLanguages = appLanguages;
    }

    public String getAppHomeTitle() {
        return appHomeTitle;
    }

    public void setAppHomeTitle(String appHomeTitle) {
        this.appHomeTitle = appHomeTitle;
    }

    public List<AppHomeOption> getAppHomeOptions() {
        return appHomeOptions;
    }

    public void setAppHomeOptions(List<AppHomeOption> appHomeOptions) {
        this.appHomeOptions = appHomeOptions;
    }

    public String getAppTrackingTitle() {
        return appTrackingTitle;
    }

    public void setAppTrackingTitle(String appTrackingTitle) {
        this.appTrackingTitle = appTrackingTitle;
    }

    public String getAppTrackingHeading() {
        return appTrackingHeading;
    }

    public void setAppTrackingHeading(String appTrackingHeading) {
        this.appTrackingHeading = appTrackingHeading;
    }

    public String getAppTrackingEtTrackingHint() {
        return appTrackingEtTrackingHint;
    }

    public void setAppTrackingEtTrackingHint(String appTrackingEtTrackingHint) {
        this.appTrackingEtTrackingHint = appTrackingEtTrackingHint;
    }

    public String getAppTrackingBtnSubmit() {
        return appTrackingBtnSubmit;
    }

    public void setAppTrackingBtnSubmit(String appTrackingBtnSubmit) {
        this.appTrackingBtnSubmit = appTrackingBtnSubmit;
    }

    public String getAppOrderStatusTitle() {
        return appOrderStatusTitle;
    }

    public void setAppOrderStatusTitle(String appOrderStatusTitle) {
        this.appOrderStatusTitle = appOrderStatusTitle;
    }

    public String getAppOrderStatusNoShipmentFound() {
        return appOrderStatusNoShipmentFound;
    }

    public void setAppOrderStatusNoShipmentFound(String appOrderStatusNoShipmentFound) {
        this.appOrderStatusNoShipmentFound = appOrderStatusNoShipmentFound;
    }

    public String getAppLocationsTitle() {
        return appLocationsTitle;
    }

    public void setAppLocationsTitle(String appLocationsTitle) {
        this.appLocationsTitle = appLocationsTitle;
    }

    public List<AppLocationsOption> getAppLocationsOptions() {
        return appLocationsOptions;
    }

    public void setAppLocationsOptions(List<AppLocationsOption> appLocationsOptions) {
        this.appLocationsOptions = appLocationsOptions;
    }

    public String getAppCardsTitle() {
        return appCardsTitle;
    }

    public void setAppCardsTitle(String appCardsTitle) {
        this.appCardsTitle = appCardsTitle;
    }
    @SerializedName("app.orderStatus.deliveryDate")
    @Expose
    private String appOrderStatusDeliveryDate;
    @SerializedName("app.orderStatus.status")
    @Expose
    private String appOrderStatusStatus;
    @SerializedName("app.orderStatus.webshop")
    @Expose
    private String appOrderStatusWebshop;
    @SerializedName("app.orderDetails.title")
    @Expose
    private String appOrderDetailsTitle;
    @SerializedName("app.orderDetails.options")
    @Expose
    private List<AppHomeOption> appOrderDetailsOptions = null;
    @SerializedName("app.shipmentDetails.from")
    @Expose
    private String appShipmentDetailsFrom;
    @SerializedName("app.shipmentDetails.to")
    @Expose
    private String appShipmentDetailsTo;
    @SerializedName("app.shipmentDetails.deliveryDate")
    @Expose
    private String appShipmentDetailsDeliveryDate;
    @SerializedName("app.shipmentDetails.deliveryTime")
    @Expose
    private String appShipmentDetailsDeliveryTime;
    @SerializedName("app.shipmentDetails.status")
    @Expose
    private String appShipmentDetailsStatus;
    @SerializedName("app.shipmentDetails.amount")
    @Expose
    private String appShipmentDetailsAmount;
    @SerializedName("app.billingType")
    @Expose
    private List<AppHomeOption> appBillingType = null;
    @SerializedName("app.shipmentDetails.btnChangeDeliveryDate")
    @Expose
    private String appShipmentDetailsBtnChangeDeliveryDate;
//    @SerializedName("app.shipmentDetails.btnChangeDeliveryLocations")
//    @Expose
//    private String appShipmentDetailsBtnChangeDeliveryLocations;
    @SerializedName("app.shipmentDetails.btnchangePaymentMethod")
    @Expose
    private String appShipmentDetailsBtnchangePaymentMethod;
    @SerializedName("app.profile.title")
    @Expose
    private String appProfileTitle;
    @SerializedName("app.profile.firstNameHint")
    @Expose
    private String appProfileFirstNameHint;
    @SerializedName("app.profile.lastNameHint")
    @Expose
    private String appProfileLastNameHint;
    @SerializedName("app.profile.emailHint")
    @Expose
    private String appProfileEmailHint;
    @SerializedName("app.profile.btnSubmit")
    @Expose
    private String appProfileBtnSubmit;
    @SerializedName("app.profile.btnCancel")
    @Expose
    private String appProfileBtnCancel;

    public String getAppOrderStatusDeliveryDate() {
        return appOrderStatusDeliveryDate;
    }

    public void setAppOrderStatusDeliveryDate(String appOrderStatusDeliveryDate) {
        this.appOrderStatusDeliveryDate = appOrderStatusDeliveryDate;
    }

    public String getAppOrderStatusStatus() {
        return appOrderStatusStatus;
    }

    public void setAppOrderStatusStatus(String appOrderStatusStatus) {
        this.appOrderStatusStatus = appOrderStatusStatus;
    }

    public String getAppOrderStatusWebshop() {
        return appOrderStatusWebshop;
    }

    public void setAppOrderStatusWebshop(String appOrderStatusWebshop) {
        this.appOrderStatusWebshop = appOrderStatusWebshop;
    }

    public String getAppOrderDetailsTitle() {
        return appOrderDetailsTitle;
    }

    public void setAppOrderDetailsTitle(String appOrderDetailsTitle) {
        this.appOrderDetailsTitle = appOrderDetailsTitle;
    }

    public List<AppHomeOption> getAppOrderDetailsOptions() {
        return appOrderDetailsOptions;
    }

    public void setAppOrderDetailsOptions(List<AppHomeOption> appOrderDetailsOptions) {
        this.appOrderDetailsOptions = appOrderDetailsOptions;
    }

    public String getAppShipmentDetailsFrom() {
        return appShipmentDetailsFrom;
    }

    public void setAppShipmentDetailsFrom(String appShipmentDetailsFrom) {
        this.appShipmentDetailsFrom = appShipmentDetailsFrom;
    }

    public String getAppShipmentDetailsTo() {
        return appShipmentDetailsTo;
    }

    public void setAppShipmentDetailsTo(String appShipmentDetailsTo) {
        this.appShipmentDetailsTo = appShipmentDetailsTo;
    }

    public String getAppShipmentDetailsDeliveryDate() {
        return appShipmentDetailsDeliveryDate;
    }

    public void setAppShipmentDetailsDeliveryDate(String appShipmentDetailsDeliveryDate) {
        this.appShipmentDetailsDeliveryDate = appShipmentDetailsDeliveryDate;
    }

    public String getAppShipmentDetailsDeliveryTime() {
        return appShipmentDetailsDeliveryTime;
    }

    public void setAppShipmentDetailsDeliveryTime(String appShipmentDetailsDeliveryTime) {
        this.appShipmentDetailsDeliveryTime = appShipmentDetailsDeliveryTime;
    }

    public String getAppShipmentDetailsStatus() {
        return appShipmentDetailsStatus;
    }

    public void setAppShipmentDetailsStatus(String appShipmentDetailsStatus) {
        this.appShipmentDetailsStatus = appShipmentDetailsStatus;
    }

    public String getAppShipmentDetailsAmount() {
        return appShipmentDetailsAmount;
    }

    public void setAppShipmentDetailsAmount(String appShipmentDetailsAmount) {
        this.appShipmentDetailsAmount = appShipmentDetailsAmount;
    }

    public List<AppHomeOption> getAppBillingType() {
        return appBillingType;
    }

    public void setAppBillingType(List<AppHomeOption> appBillingType) {
        this.appBillingType = appBillingType;
    }

    public String getAppShipmentDetailsBtnChangeDeliveryDate() {
        return appShipmentDetailsBtnChangeDeliveryDate;
    }

    public void setAppShipmentDetailsBtnChangeDeliveryDate(String appShipmentDetailsBtnChangeDeliveryDate) {
        this.appShipmentDetailsBtnChangeDeliveryDate = appShipmentDetailsBtnChangeDeliveryDate;
    }

//    public String getAppShipmentDetailsBtnChangeDeliveryLocations() {
//        return appShipmentDetailsBtnChangeDeliveryLocations;
//    }
//
//    public void setAppShipmentDetailsBtnChangeDeliveryLocations(String appShipmentDetailsBtnChangeDeliveryLocations) {
//        this.appShipmentDetailsBtnChangeDeliveryLocations = appShipmentDetailsBtnChangeDeliveryLocations;
//    }

    public String getAppShipmentDetailsBtnchangePaymentMethod() {
        return appShipmentDetailsBtnchangePaymentMethod;
    }

    public void setAppShipmentDetailsBtnchangePaymentMethod(String appShipmentDetailsBtnchangePaymentMethod) {
        this.appShipmentDetailsBtnchangePaymentMethod = appShipmentDetailsBtnchangePaymentMethod;
    }

    public String getAppProfileTitle() {
        return appProfileTitle;
    }

    public void setAppProfileTitle(String appProfileTitle) {
        this.appProfileTitle = appProfileTitle;
    }

    public String getAppProfileFirstNameHint() {
        return appProfileFirstNameHint;
    }

    public void setAppProfileFirstNameHint(String appProfileFirstNameHint) {
        this.appProfileFirstNameHint = appProfileFirstNameHint;
    }

    public String getAppProfileLastNameHint() {
        return appProfileLastNameHint;
    }

    public void setAppProfileLastNameHint(String appProfileLastNameHint) {
        this.appProfileLastNameHint = appProfileLastNameHint;
    }

    public String getAppProfileEmailHint() {
        return appProfileEmailHint;
    }

    public void setAppProfileEmailHint(String appProfileEmailHint) {
        this.appProfileEmailHint = appProfileEmailHint;
    }

    public String getAppProfileBtnSubmit() {
        return appProfileBtnSubmit;
    }

    public String getAppAddressDefaultDeleteDialogOk() {
        return appAddressDefaultDeleteDialogOk;
    }

    public void setAppAddressDefaultDeleteDialogOk(String appAddressDefaultDeleteDialogOk) {
        this.appAddressDefaultDeleteDialogOk = appAddressDefaultDeleteDialogOk;
    }

    public void setAppProfileBtnSubmit(String appProfileBtnSubmit) {

        this.appProfileBtnSubmit = appProfileBtnSubmit;
    }

    public String getAppProfileBtnCancel() {
        return appProfileBtnCancel;
    }

    public void setAppProfileBtnCancel(String appProfileBtnCancel) {
        this.appProfileBtnCancel = appProfileBtnCancel;
    }
    @SerializedName("app.help.chat")
    @Expose
    private String appHelpChat;
    @SerializedName("app.help.call")
    @Expose
    private String appHelpCall;
    @SerializedName("app.help.feedback")
    @Expose
    private String appHelpFeedback;
    @SerializedName("app.help.Complaints")
    @Expose
    private String appHelpComplaints;
    @SerializedName("app.help.title")
    @Expose
    private String appHelpTitle;
    @SerializedName("app.dashboard.help")
    @Expose
    private String appDashboardHelp;

    public String getAppdashboardtitle() {
        return appdashboardtitle;
    }

    public void setAppdashboardtitle(String appdashboardtitle) {
        this.appdashboardtitle = appdashboardtitle;
    }

    @SerializedName("app.shipmentStatus.Title")
    @Expose
    private String appdashboardtitle;

    public String getAppHelpChat() {
        return appHelpChat;
    }

    public void setAppHelpChat(String appHelpChat) {
        this.appHelpChat = appHelpChat;
    }

    public String getAppHelpCall() {
        return appHelpCall;
    }

    public void setAppHelpCall(String appHelpCall) {
        this.appHelpCall = appHelpCall;
    }

    public String getAppHelpFeedback() {
        return appHelpFeedback;
    }

    public void setAppHelpFeedback(String appHelpFeedback) {
        this.appHelpFeedback = appHelpFeedback;
    }

    public String getAppHelpComplaints() {
        return appHelpComplaints;
    }

    public void setAppHelpComplaints(String appHelpComplaints) {
        this.appHelpComplaints = appHelpComplaints;
    }

    public String getAppHelpTitle() {
        return appHelpTitle;
    }

    public void setAppHelpTitle(String appHelpTitle) {
        this.appHelpTitle = appHelpTitle;
    }

    public String getAppDashboardHelp() {
        return appDashboardHelp;
    }

    public void setAppDashboardHelp(String appDashboardHelp) {
        this.appDashboardHelp = appDashboardHelp;
    }
    @SerializedName("app.location.filter")
    @Expose
    private String appLocationText;

    public String getAppLocationText() {
        return appLocationText;
    }

    public void setAppLocationText(String appLocationText) {
        this.appLocationText = appLocationText;
    }

    @SerializedName("app.address.default.dialog.delete")
    @Expose
    private String appAddressDefaultDeleteDialog;

    public String getAppAddressDefaultDeleteDialog() {
        return appAddressDefaultDeleteDialog;
    }

    public void setAppAddressDefaultDeleteDialog(String appAddressDefaultDeleteDialog) {
        this.appAddressDefaultDeleteDialog = appAddressDefaultDeleteDialog;
    }

    @SerializedName("app.sidemenu.About_Us")
    @Expose
    private String appSideMenuAboutUS;

    public String getAppSideMenuAboutUS() {
        return appSideMenuAboutUS;
    }

    public void setAppSideMenuAboutUS(String appSideMenuAboutUS) {
        this.appSideMenuAboutUS = appSideMenuAboutUS;
    }

    @SerializedName("app.sidemenu.my_addresses")
    @Expose
    private String appmyaddresstitle;

    public String getAppmyaddressnotfound() {
        return appmyaddresstitle;
    }

    public void setAppmyaddressnotfound(String appmyaddressnotfound) {
        this.appmyaddresstitle = appmyaddressnotfound;
    }

    @SerializedName("app.address.no.found")
    @Expose
    private String appmyaddressnotfound;

    public String getAppmyaddresstitle() {
        return appmyaddressnotfound;
    }

    public void setAppmyaddresstitle(String appmyaddresstitle) {
        this.appmyaddressnotfound = appmyaddresstitle;
    }

    public String getAppdashboardservice() {
        return appdashboardservice;
    }

    public void setAppdashboardservice(String appdashboardservice) {
        this.appdashboardservice = appdashboardservice;
    }

    @SerializedName("app.dashboard.services")
    @Expose
    private String appdashboardservice;

    @SerializedName("app.delete.default.address")
    @Expose
    private String appdeletedefaultaddress;

    public String getAppdeletedefaultaddress() {
        return appdeletedefaultaddress;
    }

    public void setAppdeletedefaultaddress(String appdeletedefaultaddress) {
        this.appdeletedefaultaddress = appdeletedefaultaddress;
    }

    @SerializedName("app.ok")
    @Expose
    private String appOk;

    public String getAppOk() {
        return appOk;
    }

    public void setAppOk(String appOk) {
        this.appOk = appOk;
    }

    @SerializedName("app.address.deleteAddress")
    @Expose
    private String appaddressdeleteaddress;

    public String getAppaddressdeleteaddress() {
        return appaddressdeleteaddress;
    }

    public void setAppaddressdeleteaddress(String appaddressdeleteaddress) {
        this.appaddressdeleteaddress = appaddressdeleteaddress;
    }

    @SerializedName("app.yes")
    @Expose
    private String appYes;

    public String getAppresendcode() {
        return appresendcode;
    }

    public void setAppresendcode(String appresendcode) {
        this.appresendcode = appresendcode;
    }

    @SerializedName("app.message.enter.resend")
    @Expose
    private String appresendcode;

    public String getAppmsgenter() {
        return appmsgenter;
    }

    public void setAppmsgenter(String appmsgenter) {
        this.appmsgenter = appmsgenter;
    }

    @SerializedName("app.message.enter")
    @Expose
    private String appmsgenter;

    public String getAppYes() {
        return appYes;
    }

    public void setAppYes(String appYes) {
        this.appYes = appYes;
    }

    @SerializedName("app.no")
    @Expose
    private String appNo;

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getAppAddressAdd() {
        return appAddressAdd;
    }

    public void setAppAddressAdd(String appAddressAdd) {
        this.appAddressAdd = appAddressAdd;
    }

    @SerializedName("app.address.addAddress")
    @Expose
    private String appAddressAdd;

    public String getAppEditAddress() {
        return appEditAddress;
    }

    public void setAppEditAddress(String appEditAddress) {
        this.appEditAddress = appEditAddress;
    }

    @SerializedName("app.address.updateAddress")
    @Expose
    private String appEditAddress;

    @SerializedName("app.profile.homeLabel")
    @Expose
    private String appHomeLable;

    public String getAppHomeLable() {
        return appHomeLable;
    }

    public void setAppHomeLable(String appHomeLable) {
        this.appHomeLable = appHomeLable;
    }

    public String getAppWork() {
        return appWork;
    }

    public void setAppWork(String appWork) {
        this.appWork = appWork;
    }

    @SerializedName("app.profile.workLabel")
    @Expose
    private String appWork;

    public String getAppAddAddressCountry() {
        return appAddAddressCountry;
    }

    public void setAppAddAddressCountry(String appAddAddressCountry) {
        this.appAddAddressCountry = appAddAddressCountry;
    }

    @SerializedName("app.add.address.country")
    @Expose
    private String appAddAddressCountry;

    @SerializedName("app.address.city")
    @Expose
    private String appAddAddressCity;

    public String getAppAddAddressCity() {
        return appAddAddressCity;
    }

    public void setAppAddAddressCity(String appAddAddressCity) {
        this.appAddAddressCity = appAddAddressCity;
    }

    public String getAppAddnesarestlandmark() {
        return appAddnesarestlandmark;
    }

    public void setAppAddnesarestlandmark(String appAddnesarestlandmark) {
        this.appAddnesarestlandmark = appAddnesarestlandmark;
    }

    @SerializedName("app.address.nearestLandmark")
    @Expose
    private String appAddnesarestlandmark;

    public String getAppAddstreetname() {
        return appAddstreetname;
    }

    public void setAppAddstreetname(String appAddstreetname) {
        this.appAddstreetname = appAddstreetname;
    }

    @SerializedName("app.address.streetName")
    @Expose
    private String appAddstreetname;

    public String getAppAddbuildingname() {
        return appAddbuildingname;
    }

    public void setAppAddbuildingname(String appAddbuildingname) {
        this.appAddbuildingname = appAddbuildingname;
    }

    @SerializedName("app.address.buildingName")
    @Expose
    private String appAddbuildingname;

    public String getAppAddapartment() {
        return appAddapartment;
    }

    public void setAppAddapartment(String appAddapartment) {
        this.appAddapartment = appAddapartment;
    }

    @SerializedName("app.address.appartment")
    @Expose
    private String appAddapartment;

    public String getApppinlocation() {
        return apppinlocation;
    }

    public void setApppinlocation(String apppinlocation) {
        this.apppinlocation = apppinlocation;
    }

    @SerializedName("app.address.pinLocation")
    @Expose
    private String apppinlocation;



    public String getApppinlocationmakedefault() {
        return apppinlocationmakedefault;
    }

    public void setApppinlocationmakedefault(String apppinlocationmakedefault) {
        this.apppinlocationmakedefault = apppinlocationmakedefault;
    }

    @SerializedName("app.profile.btnMakeDefault")
    @Expose
    private String apppinlocationmakedefault;

    public String getAppdefaultaddrss() {
        return appdefaultaddrss;
    }

    public void setAppdefaultaddrss(String appdefaultaddrss) {
        this.appdefaultaddrss = appdefaultaddrss;
    }

    @SerializedName("app.profile.defaultAddress")
    @Expose
    private String appdefaultaddrss;

    public String getAppcontactus() {
        return appcontactus;
    }

    public void setAppcontactus(String appcontactus) {
        this.appcontactus = appcontactus;
    }

    @SerializedName("app.sidemenu.contact_Us")
    @Expose
    private String appcontactus;

    public String getAppcontactussuport() {
        return appcontactussuport;
    }

    public void setAppcontactussuport(String appcontactussuport) {
        this.appcontactussuport = appcontactussuport;
    }

    @SerializedName("app.email.support")
    @Expose
    private String appcontactussuport;

    public String getAppcontactussuportphone() {
        return appcontactussuportphone;
    }

    public void setAppcontactussuportphone(String appcontactussuportphone) {
        this.appcontactussuportphone = appcontactussuportphone;
    }

    @SerializedName("app.email.phone")
    @Expose
    private String appcontactussuportphone;

    public String getAppeemailnnoshipment() {
        return appeemailnnoshipment;
    }

    public void setAppeemailnnoshipment(String appeemailnnoshipment) {
        this.appeemailnnoshipment = appeemailnnoshipment;
    }

    @SerializedName("app.email.no.shipment")
    @Expose
    private String appeemailnnoshipment;

    public String getApppickupdate() {
        return apppickupdate;
    }

    public void setApppickupdate(String apppickupdate) {
        this.apppickupdate = apppickupdate;
    }

    @SerializedName("app.orderStatus.pickUpDate")
    @Expose
    private String apppickupdate;

    public String getApporderstatus() {
        return apporderstatus;
    }

    public void setApporderstatus(String apporderstatus) {
        this.apporderstatus = apporderstatus;
    }

    @SerializedName("app.orderStatus.ReturnDate")
    @Expose
    private String apporderstatus;

    public String getAppprepaid() {
        return appprepaid;
    }

    public void setAppprepaid(String appprepaid) {
        this.appprepaid = appprepaid;
    }

    @SerializedName("app.paid.pre")
    @Expose
    private String appprepaid;

    public String getApppaid() {
        return apppaid;
    }

    public void setApppaid(String apppaid) {
        this.apppaid = apppaid;
    }

    @SerializedName("app.paid")
    @Expose
    private String apppaid;

    public String getAppbtnpickuplcation() {
        return appbtnpickuplcation;
    }

    public void setAppbtnpickuplcation(String appbtnpickuplcation) {
        this.appbtnpickuplcation = appbtnpickuplcation;
    }

    @SerializedName("app.shipmentDetails.btnChangeDeliveryLocations")
    @Expose
    private String appbtnpickuplcation;

    public String getAppbtnpickuplcationpickup() {
        return appbtnpickuplcationpickup;
    }

    public void setAppbtnpickuplcationpickup(String appbtnpickuplcationpickup) {
        this.appbtnpickuplcationpickup = appbtnpickuplcationpickup;
    }

    @SerializedName("app.shipmentDetails.btnChangPickupLocation")
    @Expose
    private String appbtnpickuplcationpickup;

    public String getAppbtnpickuplcationdate() {
        return appbtnpickuplcationdate;
    }

    public void setAppbtnpickuplcationdate(String appbtnpickuplcationdate) {
        this.appbtnpickuplcationdate = appbtnpickuplcationdate;
    }

    @SerializedName("app.shipmentDetails.btnChangPickupDate")
    @Expose
    private String appbtnpickuplcationdate;

    public String getAppbtnpickuplcationdatetitle() {
        return appbtnpickuplcationdatetitle;
    }

    public void setAppbtnpickuplcationdatetitle(String appbtnpickuplcationdatetitle) {
        this.appbtnpickuplcationdatetitle = appbtnpickuplcationdatetitle;
    }

    @SerializedName("app.generateTicket.labelTitle")
    @Expose
    private String appbtnpickuplcationdatetitle;

    public String getAppbtnpickupcontact() {
        return appbtnpickupcontact;
    }

    public void setAppbtnpickupcontact(String appbtnpickupcontact) {
        this.appbtnpickupcontact = appbtnpickupcontact;
    }

    @SerializedName("app.shipmentDetails.pleaseContactShipper")
    @Expose
    private String appbtnpickupcontact;

    public String getAppenerateticket() {
        return appenerateticket;
    }

    public void setAppenerateticket(String appenerateticket) {
        this.appenerateticket = appenerateticket;
    }

    @SerializedName("app.shipmentDetails.btnGenerateTicket")
    @Expose
    private String appenerateticket;

    public String getAppenerateticketToast() {
        return appenerateticketToast;
    }

    public void setAppenerateticketToast(String appenerateticketToast) {
        this.appenerateticketToast = appenerateticketToast;
    }

    @SerializedName("app.shipmentDetails.inValidDate.Toast")
    @Expose
    private String appenerateticketToast;

    public String getAppenerateticketToastresend() {
        return appenerateticketToastresend;
    }

    public void setAppenerateticketToastresend(String appenerateticketToastresend) {
        this.appenerateticketToastresend = appenerateticketToastresend;
    }

    @SerializedName("app.message,enter.resend")
    @Expose
    private String appenerateticketToastresend;

    public String getAppenerateticketenterdetail() {
        return appenerateticketenterdetail;
    }

    public void setAppenerateticketenterdetail(String appenerateticketenterdetail) {
        this.appenerateticketenterdetail = appenerateticketenterdetail;
    }

    @SerializedName("app.profile.enterDetails")
    @Expose
    private String appenerateticketenterdetail;

    public String getAppenerateticketToastmesLable() {
        return appenerateticketToastmesLable;
    }

    public void setAppenerateticketToastmesLable(String appenerateticketToastmesLable) {
        this.appenerateticketToastmesLable = appenerateticketToastmesLable;
    }

    @SerializedName("app.generateTicket.labelMessage")
    @Expose
    private String appenerateticketToastmesLable;

    public String getAppmessageEnter() {
        return appmessageEnter;
    }

    public void setAppmessageEnter(String appmessageEnter) {
        this.appmessageEnter = appmessageEnter;
    }

    @SerializedName("app.message,enter")
    @Expose
    private String appmessageEnter;

    public String getAppaddaddress() {
        return appaddaddress;
    }

    public void setAppaddaddress(String appaddaddress) {
        this.appaddaddress = appaddaddress;
    }

    @SerializedName("app.address.select.city")
    @Expose

    private String appaddaddress;

    public String getAppprofilesecondarynumber() {
        return appprofilesecondarynumber;
    }

    public void setAppprofilesecondarynumber(String appprofilesecondarynumber) {
        this.appprofilesecondarynumber = appprofilesecondarynumber;
    }

    @SerializedName("app.profile.secondary.number")
    @Expose
    private String appprofilesecondarynumber;

    public String getAppTrackingEnter() {
        return appTrackingEnter;
    }

    public void setAppTrackingEnter(String appTrackingEnter) {
        this.appTrackingEnter = appTrackingEnter;
    }

    @SerializedName("app.tracking.enter")
    @Expose
    private String appTrackingEnter;

    public String getAppReturnHistory() {
        return appReturnHistory;
    }

    public void setAppReturnHistory(String appReturnHistory) {
        this.appReturnHistory = appReturnHistory;
    }

    @SerializedName("app.home.options.outgoing.Return_History")
    @Expose
    private String appReturnHistory;

    public String getApp99() {
        return app99;
    }

    public void setApp99(String app99) {
        this.app99 = app99;
    }

    @SerializedName("app.numbr")
    @Expose
    private String app99;


}
