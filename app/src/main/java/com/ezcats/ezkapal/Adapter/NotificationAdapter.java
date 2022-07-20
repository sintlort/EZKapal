package com.ezcats.ezkapal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcats.ezkapal.Fragment.CurrentNotificationFragment;
import com.ezcats.ezkapal.Model.NotificationModel;
import com.ezcats.ezkapal.R;

import org.w3c.dom.Text;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    List<NotificationModel> notificationModels;
    OnNotificationListener onClickNotification;
    OnOpenNotification onOpenNotification;
    Context context;

    public NotificationAdapter(Context context, List<NotificationModel> notificationModels, OnNotificationListener onNotificationListener, OnOpenNotification onOpenNotification) {
        this.notificationModels = notificationModels;
        this.onOpenNotification = onOpenNotification;
        this.onClickNotification = onNotificationListener;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification, parent, false);
        return new NotificationViewHolder(view, onClickNotification, onOpenNotification);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationModel notificationModel = notificationModels.get(position);
        holder.notificationTitle.setText(notificationModel.getTitle());
        holder.notificationText.setText(notificationModel.getBody());
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }


    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageButton notificationMore;
        TextView notificationTitle, notificationText;
        CardView notificationCard;

        public NotificationViewHolder(@NonNull View itemView, OnNotificationListener onNotificationListener, OnOpenNotification onOpenNotification) {
            super(itemView);
            notificationCard = itemView.findViewById(R.id.card_view_notification);
            notificationMore = itemView.findViewById(R.id.notification_more);
            notificationTitle = itemView.findViewById(R.id.notification_title);
            notificationText = itemView.findViewById(R.id.notification_text);
            notificationMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(context, notificationMore);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            NotificationModel notificationModel = notificationModels.get(getAdapterPosition());
                            switch (menuItem.getItemId()){
                                case R.id.menu_save:
                                    onNotificationListener.OnClickNotification(notificationModel.getId(), true);
                                    return true;
                                case R.id.menu_delete:
                                    onNotificationListener.OnClickNotification(notificationModel.getId(), false);
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.inflate(R.menu.notification_menu);
                    popup.show();
                }
            });

            notificationCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NotificationModel notificationModel = notificationModels.get(getAdapterPosition());
                    onOpenNotification.OnSelectNotification(notificationModel.getClick_action());
                }
            });
        }
    }

    /**
     * boolean action
     * true = archieve
     * false = delete
     *
     */

    public interface OnNotificationListener{
        void OnClickNotification(int id, boolean action);
    }

    public interface OnOpenNotification{
        void OnSelectNotification(String click_action);
    }
}
