package com.mangedha.knit.http.models.http_interface;

import com.mangedha.knit.http.models.PaymentsModel;

/**
 * Created by bhavan on 5/12/17.
 */

public interface PaymentsInterface {

    void onPaymentSuccess(PaymentsModel paymentsModel);

    void onPaymentFailur(Throwable t);

}
