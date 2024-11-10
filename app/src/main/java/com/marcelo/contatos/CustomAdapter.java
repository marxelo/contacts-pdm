package com.marcelo.contatos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.marcelo.contatos.utils.Utils;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private final Context context;
    private final Activity activity;
    private final ArrayList<String> contact_id;
    private final ArrayList<String> contact_name;
    private final ArrayList<String> contact_phone;
    private final ArrayList<String> contact_birthday;

    CustomAdapter(Activity activity,
                  Context context,
                  ArrayList<String> contact_id,
                  ArrayList<String> contact_name,
                  ArrayList<String> contact_phone,
                  ArrayList<String> contact_birthday) {
        this.activity = activity;
        this.context = context;
        this.contact_id = contact_id;
        this.contact_name = contact_name;
        this.contact_phone = contact_phone;
        this.contact_birthday = contact_birthday;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.contact_id_txt.setText(Utils.getInitials(String.valueOf(contact_name.get(position))));
        holder.contact_name_txt.setText(String.valueOf(contact_name.get(position)));
        holder.contact_phone_txt.setText(String.valueOf(contact_phone.get(position)));
        holder.contact_birthday_txt.setText(String.valueOf(contact_birthday.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(contact_id.get(position)));
            intent.putExtra("name", String.valueOf(contact_name.get(position)));
            intent.putExtra("phone", String.valueOf(contact_phone.get(position)));
            intent.putExtra("birthday", String.valueOf(contact_birthday.get(position)));
            activity.startActivityForResult(intent, 1);
        });


    }

    @Override
    public int getItemCount() {
        return contact_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView contact_id_txt, contact_name_txt, contact_phone_txt, contact_birthday_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contact_id_txt = itemView.findViewById(R.id.contact_id_txt);
            contact_name_txt = itemView.findViewById(R.id.contact_name_txt);
            contact_phone_txt = itemView.findViewById(R.id.contact_phone_txt);
            contact_birthday_txt = itemView.findViewById(R.id.contact_birthday_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}
