package com.mangedha.knit.http.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.helpers.DateHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by bhavan on 10/2/17.
 */

public class SettingModel extends MangedhaModel {

    @SerializedName("membership")
    @Expose
    Membership membership;

    public class Membership{

        @SerializedName("id")
        @Expose
        int id;

        @SerializedName("is_valid")
        @Expose
        boolean is_valid;

        @SerializedName("member_ship_id")
        @Expose
        int member_ship_id;

        @SerializedName("user_id")
        @Expose
        int user_id;

        @SerializedName("payment_id")
        @Expose
        int payment_id;

        @SerializedName("created_at")
        @Expose
        String created_at;

        @SerializedName("updated_at")
        @Expose
        String updated_at;

        @SerializedName("membership")
        @Expose
        MemberShipDetails memberShipDetails;

        public class MemberShipDetails{

            @SerializedName("id")
            @Expose
            int id;

            @SerializedName("name")
            @Expose
            String name;

            @SerializedName("desc")
            @Expose
            String desc;

            @SerializedName("price")
            @Expose
            String price;

            @SerializedName("value")
            @Expose
            int value;

            @SerializedName("status")
            @Expose
            int status;

            @SerializedName("created_at")
            @Expose
            String created_at;

            @SerializedName("updated_at")
            @Expose
            String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getExpiryDate(){
                Date payment_date = new Date(Long.parseLong(getCreated_at()) * 1000);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(payment_date);
                calendar.add(Calendar.MONTH, getValue());
                return DateHelper.formatMangedha(calendar.getTime().getTime());
            }

        }

        @SerializedName("payments")
        @Expose
        PaymentsDetals paymentsDetals;

        public class PaymentsDetals{

            @SerializedName("id")
            @Expose
            int id;

            @SerializedName("user_id")
            @Expose
            int user_id;

            @SerializedName("txn_id")
            @Expose
            String txn_id;

            @SerializedName("amount")
            @Expose
            String amount;

            @SerializedName("payment_response")
            @Expose
            String payment_response;

            @SerializedName("status")
            @Expose
            int status;

            @SerializedName("created_at")
            @Expose
            String created_at;

            @SerializedName("updated_at")
            @Expose
            String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getTxn_id() {
                return txn_id;
            }

            public void setTxn_id(String txn_id) {
                this.txn_id = txn_id;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getPayment_response() {
                return payment_response;
            }

            public void setPayment_response(String payment_response) {
                this.payment_response = payment_response;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMember_ship_id() {
            return member_ship_id;
        }

        public void setMember_ship_id(int member_ship_id) {
            this.member_ship_id = member_ship_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getPayment_id() {
            return payment_id;
        }

        public void setPayment_id(int payment_id) {
            this.payment_id = payment_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public MemberShipDetails getMemberShipDetails() {
            return memberShipDetails;
        }

        public void setMemberShipDetails(MemberShipDetails memberShipDetails) {
            this.memberShipDetails = memberShipDetails;
        }

        public PaymentsDetals getPaymentsDetals() {
            return paymentsDetals;
        }

        public void setPaymentsDetals(PaymentsDetals paymentsDetals) {
            this.paymentsDetals = paymentsDetals;
        }

        public boolean is_valid() {
            return is_valid;
        }

        public void setIs_valid(boolean is_valid) {
            this.is_valid = is_valid;
        }
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }
}
