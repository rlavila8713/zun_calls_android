package com.xkoders.zuncallandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xkoders.zuncallandroid.R;
import com.xkoders.zuncallandroid.activities.DetailCallActivity;
import com.xkoders.zuncallandroid.interfaces.OnItemClickListener;
import com.xkoders.zuncallandroid.models.Call;

import java.util.List;



public class CallsAdapter extends RecyclerView.Adapter<CallsAdapter.CallsViewHolder> implements OnItemClickListener {

    private final Context context;
    private List<Call> items;
    private int lastPosition = -1;

    public CallsAdapter(List<Call> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @Override
    public void onViewDetachedFromWindow(CallsViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    @Override
    public void onItemClick(Call item) {

    }

    @Override
    public void onItemClick(View view, int position) {
        DetailCallActivity.createInstance(
                (Activity) context, items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public CallsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.calls_card_layout, parent, false);
        return new CallsViewHolder(v, this);
    }


    @Override
    public void onBindViewHolder(CallsAdapter.CallsViewHolder holder, int position) {
        Call currentItem = items.get(position);
        holder.contact_name.setText(currentItem.getName());
        holder.contact_call_cost.setText("$ " + currentItem.getCost());
        holder.contact_phone_number.setText(currentItem.getDigitos());
        holder.contact_date.setText(currentItem.getFecha());
        holder.contact_call_duration.setText(currentItem.getDuration()+" min");
        Glide.with(holder.contact_avatar.getContext())
                .load(currentItem.getIdImagen())
                .centerCrop()
                .into(holder.contact_avatar);
        setSlideInLeft(holder.itemView, position);
    }

    private void setSlideInLeft(View view, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            view.startAnimation(animation);
            lastPosition = position;
        }
    }


    public class CallsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView contact_name;
        public TextView contact_phone_number;
        public TextView contact_call_cost;
        public TextView contact_call_duration;
        public ImageView contact_avatar;
        public TextView contact_date;

        public OnItemClickListener listener;
        public View rootView;

        public CallsViewHolder(View v, OnItemClickListener listener) {
            super(v);
            contact_name = (TextView) v.findViewById(R.id.contact_name);
            contact_phone_number = (TextView) v.findViewById(R.id.contact_phone_number);
            contact_avatar = (ImageView) v.findViewById(R.id.contact_avatar);
            contact_call_cost = (TextView) v.findViewById(R.id.contact_call_cost);
            contact_call_duration = (TextView) v.findViewById(R.id.contact_call_duration);
            contact_date = (TextView) v.findViewById(R.id.contact_call_date);

            this.listener = listener;
            v.setOnClickListener(this);
            rootView = v;
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }

        public void clearAnimation() {
            rootView.clearAnimation();
        }
    }

}