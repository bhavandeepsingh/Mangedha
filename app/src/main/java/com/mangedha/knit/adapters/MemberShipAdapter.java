package com.mangedha.knit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.activities.MembershipActivity;
import com.mangedha.knit.http.models.MemmberShipModel;
import com.mangedha.knit.http.models.SettingModel;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by bhavan on 10/2/17.
 */

public class MemberShipAdapter extends RecyclerView.Adapter<MemberShipAdapter.ViewHolder>{

    MemmberShipModel memmberShipModel;
    Context context;

    public MemberShipAdapter(MemmberShipModel memmberShipModel, Context context) {
        super();
        this.memmberShipModel = memmberShipModel;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_ship_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SettingModel.Membership.MemberShipDetails memberShipDetails = memmberShipModel.getMemberShipDetailsList().get(position);
        holder.member_ship_title.setText(memberShipDetails.getName());
        holder.member_ship_price.setText("Rs. " + memberShipDetails.getPrice());
        holder.member_ship_description.setText(memberShipDetails.getDesc());

        holder.buy_member_ship_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(MemberShipAdapter.this.context, ProductsActivity.class);
                MemberShipAdapter.this.context.startActivity(intent);*/

                PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
                builder.setAmount(12.2)
                        .setTxnId("asdasdjkjf")
                        .setPhone("98918289")
                        .setProductName("Buy membership")
                        .setFirstName("Boparai")
                        .setMerchantId("4934580")
                        .setKey("rjQUPktU")
                        .setEmail("ghudani1@gmail.com")
                        .setsUrl("http://localhost/test.com")
                        .setfUrl("http://localhost/test.com");

                String hashSequence = "rjQUPktU|asdasdjkjf|12.2|Buy membership|Boparai|ghudani1@gmail.com|e5iIg1jwi8";
                String serverCalculatedHash= hashCal("SHA-512", hashSequence);

                PayUmoneySdkInitializer.PaymentParam paymentParam = builder.build();
                paymentParam.setMerchantHash(serverCalculatedHash);

                PayUmoneyFlowManager.startPayUMoneyFlow(
                        paymentParam,
                        (MembershipActivity) MemberShipAdapter.this.context,
                        R.style.AppTheme,
                        false
                );


            }
        });

    }

    @Override
    public int getItemCount() {
        if(memmberShipModel != null){
            return memmberShipModel.getMemberShipDetailsList().size();
        }
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView member_ship_title, member_ship_price, member_ship_description, buy_member_ship_button;

        public ViewHolder(View itemView)
        {
            super(itemView);
            member_ship_title = (TextView) itemView.findViewById(R.id.member_ship_title);
            member_ship_price = (TextView) itemView.findViewById(R.id.member_ship_price);
            member_ship_description = (TextView) itemView.findViewById(R.id.member_ship_description);
            buy_member_ship_button = (TextView) itemView.findViewById(R.id.buy_member_ship_button);
        }
    }

    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }
}
