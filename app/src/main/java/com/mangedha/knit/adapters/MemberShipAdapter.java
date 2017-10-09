package com.mangedha.knit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.activities.MembershipActivity;
import com.mangedha.knit.helpers.PayumoneyHelper;
import com.mangedha.knit.helpers.UserHelper;
import com.mangedha.knit.http.models.MemmberShipModel;
import com.mangedha.knit.http.models.SettingModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by bhavan on 10/2/17.
 */

public class MemberShipAdapter extends RecyclerView.Adapter<MemberShipAdapter.ViewHolder>{

    MemmberShipModel memmberShipModel;
    MembershipActivity membershipActivity;

    public MemberShipAdapter(MemmberShipModel memmberShipModel, MembershipActivity membershipActivity) {
        super();
        this.memmberShipModel = memmberShipModel;
        this.membershipActivity = membershipActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_ship_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final SettingModel.Membership.MemberShipDetails memberShipDetails = memmberShipModel.getMemberShipDetailsList().get(position);
        holder.member_ship_title.setText(memberShipDetails.getName());
        holder.member_ship_price.setText("Rs. " + memberShipDetails.getPrice());
        holder.member_ship_description.setText(memberShipDetails.getDesc());

        holder.buy_member_ship_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayumoneyHelper payumoneyHelper = PayumoneyHelper.getInstance(membershipActivity);
                payumoneyHelper.setPhone(UserHelper.getAppUserMobile());
                payumoneyHelper.setEmail(UserHelper.getEmail());
                payumoneyHelper.setProduct_info(memberShipDetails.getName());
                payumoneyHelper.setAmount(Double.parseDouble(memberShipDetails.getPrice()));
                payumoneyHelper.setPaymentType(PayumoneyHelper.MEMBERSHIP_PAYMENT);
                payumoneyHelper.setTypeValue(String.valueOf(memberShipDetails.getId()));
                payumoneyHelper.setFirst_name("Mangedha");
                payumoneyHelper.initiate();
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
