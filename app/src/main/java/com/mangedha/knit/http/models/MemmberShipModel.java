package com.mangedha.knit.http.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.http.RestAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bhavan on 10/2/17.
 */

public class MemmberShipModel extends MangedhaModel {

    @SerializedName("list")
    @Expose
    List<SettingModel.Membership.MemberShipDetails> memberShipDetailsList;

    @SerializedName("membership")
    @Expose
    SettingModel.Membership membership;

    public List<SettingModel.Membership.MemberShipDetails> getMemberShipDetailsList() {
        return memberShipDetailsList;
    }

    public void setMemberShipDetailsList(List<SettingModel.Membership.MemberShipDetails> memberShipDetailsList) {
        this.memberShipDetailsList = memberShipDetailsList;
    }

    public SettingModel.Membership getMembership() {
        return membership;
    }

    public void setMembership(SettingModel.Membership membership) {
        this.membership = membership;
    }

    public static void getMembershipList(final MemberShipInterface memberShipInterface){
        RestAdapter.get().getMembership().enqueue(new Callback<MemmberShipModel>() {

            @Override
            public void onResponse(Call<MemmberShipModel> call, Response<MemmberShipModel> response) {
                if(response.body() != null){
                    memberShipInterface.onSuccess(response.body());
                }else{
                    memberShipInterface.onFail("Please try again.");
                }
            }

            @Override
            public void onFailure(Call<MemmberShipModel> call, Throwable t) {
                memberShipInterface.onFail(t.getMessage());
            }
        });
    }

    public interface MemberShipInterface{

        void onSuccess(MemmberShipModel memmberShipModel);
        void onFail(String error);

    }


}
