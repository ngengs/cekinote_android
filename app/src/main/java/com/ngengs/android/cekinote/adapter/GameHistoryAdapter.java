package com.ngengs.android.cekinote.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngengs.android.cekinote.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ngengs on 12/20/2016.
 */

public class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryAdapter.ViewHolder> {
    private List<int[]> scoreData;

    public GameHistoryAdapter(List<int[]> scoreData) {
        this.scoreData = new ArrayList<>();
        if (scoreData != null) {
            this.scoreData.addAll(scoreData);
        }
    }

    @Override
    public GameHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_game_list_item, parent, false);

        return new GameHistoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GameHistoryAdapter.ViewHolder holder, int position) {
        int[] score = scoreData.get(position);
        holder.scoreGameHistoryNumber.setText(String.valueOf(position + 1));
        holder.scoreGameHistoryPlayer1.setText(String.valueOf(score[0]));
        holder.scoreGameHistoryPlayer2.setText(String.valueOf(score[1]));
        holder.scoreGameHistoryPlayer3.setText(String.valueOf(score[2]));
        holder.scoreGameHistoryPlayer4.setText(String.valueOf(score[3]));
    }

    @Override
    public int getItemCount() {
        return scoreData.size();
    }

    public void addScore(int[] data) {
        addScore(data, getItemCount());
    }

    public void addScore(int[] data, int position) {
        if (data.length == 4) {
            scoreData.add(position, data);
            notifyDataSetChanged();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.score_game_history_number)
        TextView scoreGameHistoryNumber;
        @BindView(R.id.score_game_history_player1)
        TextView scoreGameHistoryPlayer1;
        @BindView(R.id.score_game_history_player2)
        TextView scoreGameHistoryPlayer2;
        @BindView(R.id.score_game_history_player3)
        TextView scoreGameHistoryPlayer3;
        @BindView(R.id.score_game_history_player4)
        TextView scoreGameHistoryPlayer4;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
