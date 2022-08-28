package com.civildeal.civildeal.api;

import com.civildeal.civildeal.api.ModelResponse.AddLead_Response;
import com.civildeal.civildeal.api.ModelResponse.AddToCartResponse;
import com.civildeal.civildeal.api.ModelResponse.BidDetailsResultResponse;
import com.civildeal.civildeal.api.ModelResponse.BidderBannerImagesResultResponse;
import com.civildeal.civildeal.api.ModelResponse.CheckBidResultResponse;
import com.civildeal.civildeal.api.ModelResponse.CheckVendorListResultResponse;
import com.civildeal.civildeal.api.ModelResponse.CityListResultResponse;
import com.civildeal.civildeal.api.ModelResponse.CityResponse;
import com.civildeal.civildeal.api.ModelResponse.ContactUsResponse;
import com.civildeal.civildeal.api.ModelResponse.DeleteCartResponse;
import com.civildeal.civildeal.api.ModelResponse.DirectLeadResultResponse;
import com.civildeal.civildeal.api.ModelResponse.EditProfileResultResponse;
import com.civildeal.civildeal.api.ModelResponse.FreeListingResultResponse;
import com.civildeal.civildeal.api.ModelResponse.GetvendorProfilePicResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ItemAddToCartResultResponse;
import com.civildeal.civildeal.api.ModelResponse.LoginResultResponse;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceLeadByPromoterResultResponse;
import com.civildeal.civildeal.api.ModelResponse.MaintenanceResultResponse;
import com.civildeal.civildeal.api.ModelResponse.OrderResultResponse;
import com.civildeal.civildeal.api.ModelResponse.OtpResponse;
import com.civildeal.civildeal.api.ModelResponse.PostEnquiryResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductEnquiryResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductLeadByPromoterResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductLeadResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProductResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProfileResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProjectExpireResponse;
import com.civildeal.civildeal.api.ModelResponse.ProjectPostBannerImagesResponse;
import com.civildeal.civildeal.api.ModelResponse.ProjectPostBannerImagesResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProjectPostListResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProjectPostResponse;
import com.civildeal.civildeal.api.ModelResponse.PromoterMyLeadResultResponse;
import com.civildeal.civildeal.api.ModelResponse.PromoterProfileResultResponse;
import com.civildeal.civildeal.api.ModelResponse.PromoterUpdateProfileResponse;
import com.civildeal.civildeal.api.ModelResponse.RegisterResultResponse;
import com.civildeal.civildeal.api.ModelResponse.SaveOrderInfoResponse;
import com.civildeal.civildeal.api.ModelResponse.SavePlaceBidResponse;
import com.civildeal.civildeal.api.ModelResponse.SaveVendorEnquiryResultResponse;
import com.civildeal.civildeal.api.ModelResponse.SearchBidResultResponse;
import com.civildeal.civildeal.api.ModelResponse.SearchLeadResultResponse;
import com.civildeal.civildeal.api.ModelResponse.SearchResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceLeadByPromoterResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceLeadResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ServiceResultResponse;
import com.civildeal.civildeal.api.ModelResponse.SwitchPromoterResponse;
import com.civildeal.civildeal.api.ModelResponse.TestResultResponse;
import com.civildeal.civildeal.api.ModelResponse.UpdateLeadByPromoterResponse;
import com.civildeal.civildeal.api.ModelResponse.UpdatePasswordResponse;
import com.civildeal.civildeal.api.ModelResponse.UpdateProfilePicResponse;
import com.civildeal.civildeal.api.ModelResponse.VendorCheckoutResponse;
import com.civildeal.civildeal.api.ModelResponse.WalletResultResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @GET("getCity")
    Call<CityResponse> getCityList();

    @GET("getCityByLeads")
    Call<CityResponse> getCityLeadList();

    @FormUrlEncoded
    @POST("user_register")
    Call<RegisterResultResponse> getRegister(

            @Field("name") String username,
            @Field("mobile") String mobile,
            @Field("password") String password,
            @Field("city_id") int city_id,
            @Field("vendor_type") String vendor_type,
            @Field("service_name") String service_name,
            @Field("product_name") String product_name


    );


    @FormUrlEncoded
    @POST("loginPassword")
    Call<LoginResultResponse> getLogin(
            @Field("mobile") String mobile,
            @Field("password") String password
    );


    @GET("getServices1")
    Call<ServiceResultResponse> getServicesList();

    @GET("getServices1")
    Call<TestResultResponse> getTestList();

    @GET("getProduct")
    Call<ProductResultResponse> getProductList();

    @GET("getMaintenance")
    Call<MaintenanceResultResponse> getMaintenanceList();

    @FormUrlEncoded
    @POST("getServiceLead")
    Call<ServiceLeadResultResponse> getServiceLeadList(

            @Field("id") String id,
            @Field("city_id") String city_id
//            @Field("location") String location,
//            @Field("leadprice") String leadprice,
//            @Field("servicename") String servicename,
//            @Field("serviceImage") String serviceImage
    );

    @FormUrlEncoded
    @POST("getProductLead")
    Call<ProductLeadResultResponse> getProductLeadList(

            @Field("id") String id,
            @Field("city_id") String city_id

    );

    @FormUrlEncoded
    @POST("saveGeneralEnquery")
    Call<PostEnquiryResultResponse> savePost(

            @Field("name") String name,
//            @Field("email") String email,
            @Field("phone") String phone,
            @Field("query") String query,
            @Field("city_id") String city_id,
            @Field("leadtype") String leadtype,
            @Field("service_name") String service_name,
            @Field("product_name") String product_name

    );

    @FormUrlEncoded
    @POST("addToCart")
    Call<AddToCartResponse> addCart(
            @Field("type") String type,
            @Field("id") String id,
            @Field("lead_id") String lead_id
    );

    @FormUrlEncoded
    @POST("getVendorProfile")
    Call<ProfileResultResponse> getProfileDetails(
            @Field("vendor_id") String vendor_id
    );

    @FormUrlEncoded
    @POST("updateProfile")
    Call<EditProfileResultResponse> editProfile(
            @Field("vendor_id") String vendor_id,
            @Field("name") String name,
            @Field("lastname") String lastname,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("pan") String pan,
            @Field("short_description") String short_description,
            @Field("company_name") String company_name,
            @Field("service_amount") String service_amount,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("getOrder")
    Call<OrderResultResponse> getOrderList(
            @Field("vendor_id") String vendor_id
//            @Field("name") String name,
//            @Field("query") String query
    );

    @FormUrlEncoded
    @POST("getOrder")
    Call<OrderResultResponse> getOrderListDetails(
            @Field("vendor_id") int vendor_id
//            @Field("order_id") String order_id,
//            @Field("query") String query,
//            @Field("vendor_type") String vendor_type,
//            @Field("amount") String amount,
//            @Field("location") String location,
//            @Field("payment_status") String payment_status
    );

    @FormUrlEncoded
    @POST("saveDirectVendorEnquery")
    Call<SaveVendorEnquiryResultResponse> getenquiry(
            @Field("name") String name,
            @Field("email") String email,
            @Field("query") String query,
            @Field("citi_id") int citi_id,
            @Field("phone") String phone,
            @Field("vendor_id") String vendor_id,
            @Field("service_id") String service_id,
            @Field("product_id") String product_id,
            @Field("leadtype") String leadtype
    );

    @FormUrlEncoded
    @POST("getLeadInCart")
    Call<ItemAddToCartResultResponse> getCartList(
            @Field("vendor_id") String vendor_id,
            @Field("leadtype") String leadtype

    );

    @FormUrlEncoded
    @POST("deleteLeadInCart")
    Call<DeleteCartResponse> deleteCart(
            @Field("vendor_id") String vendor_id,
            @Field("cart_id") String cart_id

    );

    @FormUrlEncoded
    @POST("saveFreeListing")
    Call<FreeListingResultResponse> saveListing(
            @Field("name") String name,
            @Field("location_id") String location_id,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("description") String description,
            @Field("vendor_type") String vendor_type,
            @Field("product_name") String product_name,
            @Field("service_name") String service_name,
            @Field("maintenance_name ") String maintenance_name

    );

    @FormUrlEncoded
    @POST("searchvendor")
    Call<SearchResultResponse> getVendorList(
            @Field("citi_id") String citi_id,
            @Field("vendor_type") String vendor_type,
            @Field("keywords") String keywords

    );

    @FormUrlEncoded
    @POST("vendorlist")
    Call<ProfileResultResponse> vendorlist(
            @Field("vendor_type") String vendor_type,
            @Field("id") String id,
            @Field("city_id") String city_id

    );

    @FormUrlEncoded
    @POST("leadmanager")
    Call<SearchLeadResultResponse> leadlist(
            @Field("vendor_type") String vendor_type,
            @Field("citi_id") String citi_id,
            @Field("keyword") String keyword,
            @Field("vendor_id") String vendor_id
    );


    @FormUrlEncoded
    @POST("getWalletData")
    Call<WalletResultResponse> getWallet(
            @Field("user_id") String user_id


    );

    @FormUrlEncoded
    @POST("vendor_Checkout")
    Call<VendorCheckoutResponse> walletPurchase(
            @Field("vendor_id") String vendor_id,
            @Field("vendor_type") String vendor_type,
            @Field("vendor_type_id") String vendor_type_id,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("checkout_type") String checkout_type,
            @Field("lead_id") String lead_id


    );


    @FormUrlEncoded
    @POST("saveOrderInfo")
    Call<SaveOrderInfoResponse> saveOrderinfo(
            @Field("vendor_id") String vendor_id,
            @Field("vendor_type") String vendor_type,
            @Field("vendor_type_id") String vendor_type_id,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("lead_id") int lead_id,
            @Field("razorpay_order_id") String razorpay_order_id,
            @Field("checkout_type") String checkout_type


    );

    @FormUrlEncoded
    @POST("saveOrderInfo")
    Call<SaveOrderInfoResponse> savecartOrderinfo(
            @Field("vendor_id") String vendor_id,
            @Field("vendor_type") String vendor_type,
            @Field("vendor_type_id") String vendor_type_id,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("razorpay_order_id") String razorpay_order_id,
            @Field("checkout_type") String checkout_type


    );


    @FormUrlEncoded
    @POST("getProductDetails")
    Call<ProductEnquiryResultResponse> getProductDetails(
            @Field("product_id") String product_id


    );

    @FormUrlEncoded
    @POST("getDirectLead")
    Call<DirectLeadResultResponse> getDirectLeadList(
            @Field("vendor_id") String vendor_id,
            @Field("vendor_type") String vendor_type


    );

    @FormUrlEncoded
    @POST("forgotLogin")
    Call<OtpResponse> getOtp(
            @Field("mobile") String mobile

    );

    @FormUrlEncoded
    @POST("updatePassword")
    Call<UpdatePasswordResponse> updatePassword(
            @Field("mobile") String mobile,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("feedback")
    Call<ContactUsResponse> sendFeedback(
            @Field("vendor_id") String vendor_id,
            @Field("title") String title,
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("location") String location

    );

    @FormUrlEncoded
    @POST("updateImage")
    Call<UpdateProfilePicResponse> updateImage(
            @Field("vendor_id") String vendor_id,
            @Field("image") String image

    );

    @FormUrlEncoded
    @POST("getUserProfileImage")
    Call<GetvendorProfilePicResultResponse> getVendorImage(
            @Field("vendor_id") String vendor_id
    );

    @FormUrlEncoded
    @POST("saveProjectPost")
    Call<ProjectPostResponse> projectPost(
            @Field("vendor_id") String vendor_id,
            @Field("title") String title,
            @Field("name") String name,
            @Field("email") String email,
            @Field("location") String location,
            @Field("other_location") String other_location,
            @Field("min_budget") String min_budget,
            @Field("max_budget") String max_budget,
            @Field("description") String description,
            @Field("select_size") String select_size,
            @Field("size") String size,
            @Field("image[]") ArrayList<String> image

    );


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("saveProjectPost")
    Call<ProjectPostResponse> saveProjectPost1 (@Body JsonObject jsonObj);

//    @GET("getProjectPostList")
//    Call<ProjectPostListResultResponse> getProjectPostList();

    @FormUrlEncoded
    @POST("getProjectPostList")
    Call<ProjectPostListResultResponse> getProjectPostList(
            @Field("user_id") String user_id,
            @Field("city") String city
            );

    @FormUrlEncoded
    @POST("savePlaceBids")
    Call<SavePlaceBidResponse> saveBid(
            @Field("vendor_id") String vendor_id,
            @Field("post_id") String post_id,
            @Field("bid_amount") String bid_amount,
            @Field("estimate_time") String estimate_time,
            @Field("description") String description,
            @Field("image") String image

    );

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("savePlaceBids")
    Call<SavePlaceBidResponse> saveBid (@Body JsonObject jsonObj);

    @GET("otherCity")
    Call<CityListResultResponse> getCityLists();

    @FormUrlEncoded
    @POST("UserProjectPost")
    Call<CheckBidResultResponse> userProjectList(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("getVendorBidsList")
    Call<CheckVendorListResultResponse> getBidsVendorList(
            @Field("post_id") String post_id
    );

    @FormUrlEncoded
    @POST("projectPostDetails")
    Call<BidDetailsResultResponse> getBidDetails(
            @Field("user_id") String user_id,
            @Field("post_id") String post_id
    );

    @FormUrlEncoded
    @POST("searchProjectPost")
    Call<SearchBidResultResponse> getSearchBidList(
            @Field("city") String city,
            @Field("keyword") String keyword,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("savePromoter")
    Call<SwitchPromoterResponse> registerPromoter(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("addLeadByPromoter")
    Call<AddLead_Response> getaddLeadList(
            @Field("user_id") String userid,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("city") String city,
            @Field("lead_type") String type,
            @Field("lead_sub_type") String subtype,
            @Field("enquiry") String enquiry,
            @Field("price") String price,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("serviceLeadListByPromoter")
    Call<ServiceLeadByPromoterResultResponse> getServiceAddList(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("productLeadListByPromoter")
    Call<ProductLeadByPromoterResultResponse> getProductAddList(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("maintenanceLeadListByPromoter")
    Call<MaintenanceLeadByPromoterResultResponse> getMaintenanceAddList(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("updateLead")
    Call<UpdateLeadByPromoterResponse> updateLead(
            @Field("user_id") String userid,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("city") String city,
            @Field("lead_type") String type,
            @Field("lead_sub_type") String subtype,
            @Field("enquiry") String enquiry,
            @Field("price") String price
    );

    @FormUrlEncoded
    @POST("getUserPromoterDetails")
    Call<PromoterProfileResultResponse> getpromoterprofileDetails(
            @Field("promoter_id") String promoter_id
    );

    @FormUrlEncoded
    @POST("allLeadAddedByPromoter")
    Call<PromoterMyLeadResultResponse> getMyLead(
            @Field("promoter_id") String promoter_id,
            @Field("type") String type,
            @Field("keywords") String keywords
    );


    @FormUrlEncoded
    @POST("updateProfilePromoter")
    Call<PromoterUpdateProfileResponse> updatePromoterProfile(
            @Field("promoter_id") String promoter_id,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("dob") String dob,
            @Field("gender") String gender,
            @Field("location") String location,
            @Field("pan_card") String pan_card,
            @Field("aadhar_card") String aadhar_card,
            @Field("bank_name") String bank_name,
            @Field("bank_holder_name") String bank_holder_name,
            @Field("bank_ifsc_code") String bank_ifsc_code,
            @Field("bank_account_no") String bank_account_no,
            @Field("age") String age
    );

    @FormUrlEncoded
    @POST("projectPostExpire")
    Call<ProjectExpireResponse> expirePost(
            @Field("post_id") String post_id
    );

    @FormUrlEncoded
    @POST("projectPostBannerImages")
    Call<ProjectPostBannerImagesResultResponse> bannerImages(
            @Field("post_id") String post_id
    );

    @FormUrlEncoded
    @POST("projectPostBannerImagesByBidder")
    Call<BidderBannerImagesResultResponse> bidderBannerImages(
            @Field("post_id") String post_id,
            @Field("vendor_id") String vendor_id
    );
}
