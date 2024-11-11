package com.marcelo.contatos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
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

        holder.contact_id_txt.setText(Utils.getInitials(String.valueOf(contact_name.get(position)).toUpperCase()));
        holder.contact_name_txt.setText(String.valueOf(contact_name.get(position)));
        holder.contact_phone_txt.setText(String.valueOf(contact_phone.get(position)));
        holder.contact_birthday_txt.setText(String.valueOf(contact_birthday.get(position)));

        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, ShowActivity.class);
            intent.putExtra("id", String.valueOf(contact_id.get(position)));
            intent.putExtra("name", String.valueOf(contact_name.get(position)));
            intent.putExtra("phone", String.valueOf(contact_phone.get(position)));
            intent.putExtra("birthday", String.valueOf(contact_birthday.get(position)));
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contact_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

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

    public void attachSwipeHandler(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback swipeCallback =

                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

                    private final Drawable deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete);
                    private final Drawable editIcon = ContextCompat.getDrawable(context, R.drawable.ic_edit);
                    private final ColorDrawable background = new ColorDrawable();
                    private final Paint textPaint = new Paint();
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Intent intent;

                        if (direction == ItemTouchHelper.RIGHT) {
                            intent = new Intent(context, UpdateActivity.class);
                        } else {
                            intent = new Intent(context, DeleteActivity.class);
                        }

                        intent.putExtra("id", String.valueOf(contact_id.get(position)));
                        intent.putExtra("name", String.valueOf(contact_name.get(position)));
                        intent.putExtra("phone", String.valueOf(contact_phone.get(position)));
                        intent.putExtra("birthday", String.valueOf(contact_birthday.get(position)));

                        if (direction == ItemTouchHelper.RIGHT) {
                            activity.startActivityForResult(intent, 1);
                        } else {
                            activity.startActivityForResult(intent, 2);
                        }
                        notifyItemChanged(position); // Reset the item's position

                    }
                    @Override
                    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                            int actionState, boolean isCurrentlyActive) {

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                        View itemView = viewHolder.itemView;
                        int backgroundCornerOffset = 20;

                        if (dX > 0) { // Swiping to the right
                            background.setColor(Color.parseColor("#388E3C"));
                            background.setBounds(itemView.getLeft(), itemView.getTop(),
                                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
                            background.draw(c);

                            if (editIcon != null) {
                                int iconMargin = (itemView.getHeight() - editIcon.getIntrinsicHeight()) / 2;
                                int iconTop = itemView.getTop() + (itemView.getHeight() - editIcon.getIntrinsicHeight()) / 2;
                                int iconBottom = iconTop + editIcon.getIntrinsicHeight();
                                int iconLeft = itemView.getLeft() + iconMargin;
                                int iconRight = iconLeft + editIcon.getIntrinsicWidth();

                                editIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                                editIcon.draw(c);
                            }

                        } else if (dX < 0) { // Swiping to the left
                            background.setColor(Color.parseColor("#D32F2F"));
                            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
                            background.draw(c);

                            if (deleteIcon != null) {
                                int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                                int iconTop = itemView.getTop() + (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                                int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();
                                int iconRight = itemView.getRight() - iconMargin;
                                int iconLeft = iconRight - deleteIcon.getIntrinsicWidth();

                                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                                deleteIcon.draw(c);
                            }
                        }
                    }


                };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}
