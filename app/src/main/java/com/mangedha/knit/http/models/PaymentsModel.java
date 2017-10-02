package com.mangedha.knit.http.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.helpers.DateHelper;
import com.mangedha.knit.http.models.http_interface.PaymentsInterface;

import java.util.List;

/**
 * Created by bhavan on 5/12/17.
 */

public class PaymentsModel extends MangedhaModel {

    @SerializedName("subscription")
    @Expose
    Subscription subscription;

    @SerializedName("welfair")
    @Expose
    Subscription welfair;

    @SerializedName("status")
    @Expose
    int status;

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Subscription getWelfair() {
        return welfair;
    }

    public void setWelfair(Subscription welfair) {
        this.welfair = welfair;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public class Subscription{

        @SerializedName("pending_from")
        @Expose
        String pending_from;

        @SerializedName("pending_to")
        @Expose
        String pending_to;

        @SerializedName("amount")
        @Expose
        int amount;

        @SerializedName("total_amount")
        @Expose
        int total_amount;

        @SerializedName("log")
        @Expose
        List<PaymentLog> paymentLog;

        @SerializedName("number_count")
        @Expose
        int number_count;

        public String getPending_from() {
            return pending_from;
        }

        public void setPending_from(String pending_from) {
            this.pending_from = pending_from;
        }

        public String getPending_to() {
            return pending_to;
        }

        public void setPending_to(String pending_to) {
            this.pending_to = pending_to;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(int total_amount) {
            this.total_amount = total_amount;
        }

        public List<PaymentLog> getPaymentLog() {
            return paymentLog;
        }

        public void setPaymentLog(List<PaymentLog> paymentLog) {
            this.paymentLog = paymentLog;
        }

        public String getFromToString() {
            String from = DateHelper.formateDateFromstring("yyyy-MM-dd" , "MMM dd, yyyy", getPending_from());
            String to = DateHelper.formateDateFromstring("yyyy-MM-dd" , "MMM dd, yyyy", getPending_to());
            return from + " - " + to;
        }

        public int getNumber_count() {
            return number_count;
        }

        public void setNumber_count(int number_count) {
            this.number_count = number_count;
        }

        public class PaymentLog{

            @SerializedName("id")
            @Expose
            int id;

            @SerializedName("user_id")
            @Expose
            int user_id;

            @SerializedName("payment_type")
            @Expose
            int payment_type;

            @SerializedName("subscription_id")
            @Expose
            int subscription_id;

            @SerializedName("status")
            @Expose
            int status;

            @SerializedName("amount")
            @Expose
            int amount;

            @SerializedName("created_at")
            @Expose
            String created_at;

            @SerializedName("updated_at")
            @Expose
            String updated_at;

            @SerializedName("log")
            @Expose
            List<Log> logs;

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

            public int getPayment_type() {
                return payment_type;
            }

            public void setPayment_type(int payment_type) {
                this.payment_type = payment_type;
            }

            public int getSubscription_id() {
                return subscription_id;
            }

            public void setSubscription_id(int subscription_id) {
                this.subscription_id = subscription_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public float getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
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

            public List<Log> getLogs() {
                return logs;
            }

            public void setLogs(List<Log> logs) {
                this.logs = logs;
            }

            public String getFromToString() {
                String fromTo = "";
                if(getLogs() != null && getLogs().size() > 0){
                    String from = getLogs().get(0).getDate();
                    String to = getLogs().get(getLogs().size() - 1).getDate();
                    fromTo = DateHelper.formateDateFromstring("yyyy-MM-dd", "MMM dd, yyyy", from) + " - " + DateHelper.formateDateFromstring("yyyy-MM-dd", "MMM dd, yyyy", to);
                }
                return fromTo;
            }

            public class Log{

                @SerializedName("id")
                @Expose
                int id;

                @SerializedName("payment_id")
                @Expose
                int payment_id;

                @SerializedName("date")
                @Expose
                String date;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getPayment_id() {
                    return payment_id;
                }

                public void setPayment_id(int payment_id) {
                    this.payment_id = payment_id;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }
            }

        }

    }

    public static void getPayments(final PaymentsInterface paymentsInterface){

    }

}
