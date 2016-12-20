package com.ngengs.android.cekinote.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ngengs.android.cekinote.R;
import com.ngengs.android.cekinote.model.Game;
import com.ngengs.android.cekinote.utils.ResourceHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by ngengs on 12/15/2016.
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    private static GameItemClickListener clickListener;
    private List<Game> data;
    private List<Integer> selected;
    private Context context;

    public GameListAdapter(Context context, List<Game> data) {
        this.context = context;
        this.data = new ArrayList<>();
        this.selected = new ArrayList<>();
        if (data != null) this.data.addAll(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Game game = data.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd.hh:mm");
        String datePlaying = dateFormat.format(game.getDateStart());
        if (game.getDateFinish() == null) {
            holder.indicatorStatusGame.setBackgroundColor(ResourceHelper.getColor(context, R.color.colorPrimary));
        } else {
            holder.indicatorStatusGame.setBackgroundColor(ResourceHelper.getColor(context, R.color.colorAccent));
            datePlaying += "-" + dateFormat.format(game.getDateFinish());
        }
        if (!selected.isEmpty()) {
            if (selected.contains(position)) {
                holder.indicatorStatusGame.setBackgroundColor(ResourceHelper.getColor(context, R.color.colorAccentLight));
                holder.indicatorSelectedGame.setVisibility(View.VISIBLE);
            } else {
                holder.indicatorSelectedGame.setVisibility(View.GONE);
            }
        } else {
            holder.indicatorSelectedGame.setVisibility(View.GONE);
        }
        holder.gameNumber.setText(String.format(context.getString(R.string.text_number_game_format), (data.size() - position)));
        holder.gameDate.setText(datePlaying);
        if (game.getLocation() == null) {
            holder.gameLocation.setVisibility(View.GONE);
        } else {
            holder.gameLocation.setVisibility(View.VISIBLE);
            holder.gameLocation.setText(String.format(context.getString(R.string.text_location_game_format), game.getLocation()));
        }
        holder.player1.setText(String.format(context.getString(R.string.player_number_format), 1, game.getPlayer1().getName()));
        holder.player2.setText(String.format(context.getString(R.string.player_number_format), 2, game.getPlayer2().getName()));
        holder.player3.setText(String.format(context.getString(R.string.player_number_format), 3, game.getPlayer3().getName()));
        holder.player4.setText(String.format(context.getString(R.string.player_number_format), 4, game.getPlayer4().getName()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(Game data, int position) {
        this.data.add(position, data);
        notifyItemInserted(position);
    }

    public void updateAllData(List<Game> data) {
        notifyItemRangeRemoved(0, this.data.size());
        this.data.clear();
        this.data.addAll(data);
        notifyItemInserted(0);
    }

    public void updateData(Game data, int position) {
        this.data.remove(position);
        this.data.add(position, data);
        notifyItemChanged(position);
    }

    public void addSelected(int position) {
        if (selected != null) {
            if (!selected.contains(position)) {
                selected.add(position);
                notifyItemChanged(position);
            }
        }
    }

    public void removeSelected(int position) {
        if (selected != null) {
            if (selected.contains(position)) {
                selected.remove(selected.indexOf(position));
                notifyItemChanged(position);
            }
        }
    }

    public void removeSelected() {
        if (selected != null) {
            selected.clear();
            notifyItemRangeChanged(0, data.size());
        }
    }

    public void setClickListener(GameItemClickListener listener) {
        clickListener = listener;
    }

    public interface GameItemClickListener {
        void onItemClick(int position, View view);

        boolean onItemLongClick(int position, View view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.indicator_status_game)
        View indicatorStatusGame;
        @BindView(R.id.game_number)
        TextView gameNumber;
        @BindView(R.id.game_date)
        TextView gameDate;
        @BindView(R.id.game_location)
        TextView gameLocation;
        @BindView(R.id.player1)
        TextView player1;
        @BindView(R.id.player2)
        TextView player2;
        @BindView(R.id.player3)
        TextView player3;
        @BindView(R.id.player4)
        TextView player4;
        @BindView(R.id.game_card)
        CardView gameCard;
        @BindView(R.id.indicator_selected_game)
        RelativeLayout indicatorSelectedGame;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.game_card)
        protected void GameCardClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @OnLongClick(R.id.game_card)
        protected boolean GameCardLongClick(View view) {
            return clickListener.onItemLongClick(getAdapterPosition(), view);
        }
    }
}
