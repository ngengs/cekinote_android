package com.ngengs.android.cekinote.detailgame.history;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngengs.android.cekinote.R;
import com.ngengs.android.cekinote.data.model.Score;
import com.ngengs.android.cekinote.utils.ScoreHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryGameFragment extends Fragment {
    private static final String ARG_PARAM1 = "GAME_DATA_PLAYER_NAME1";
    private static final String ARG_PARAM2 = "GAME_DATA_PLAYER_NAME2";
    private static final String ARG_PARAM3 = "GAME_DATA_PLAYER_NAME3";
    private static final String ARG_PARAM4 = "GAME_DATA_PLAYER_NAME4";
    private static final String ARG_PARAM5 = "GAME_DATA_PLAYER_SCORE1";
    private static final String ARG_PARAM6 = "GAME_DATA_PLAYER_SCORE2";
    private static final String ARG_PARAM7 = "GAME_DATA_PLAYER_SCORE3";
    private static final String ARG_PARAM8 = "GAME_DATA_PLAYER_SCORE4";
    @BindView(R.id.name_game_history_player1)
    TextView nameGameHistoryPlayer1;
    @BindView(R.id.name_game_history_player2)
    TextView nameGameHistoryPlayer2;
    @BindView(R.id.name_game_history_player3)
    TextView nameGameHistoryPlayer3;
    @BindView(R.id.name_game_history_player4)
    TextView nameGameHistoryPlayer4;
    @BindView(R.id.score_game_history_player1)
    TextView scoreGameHistoryPlayer1;
    @BindView(R.id.score_game_history_player2)
    TextView scoreGameHistoryPlayer2;
    @BindView(R.id.score_game_history_player3)
    TextView scoreGameHistoryPlayer3;
    @BindView(R.id.score_game_history_player4)
    TextView scoreGameHistoryPlayer4;
    @BindView(R.id.history_game_list)
    RecyclerView historyRecyclerView;

    private String playerName1;
    private String playerName2;
    private String playerName3;
    private String playerName4;
    private List<Score> scorePlayer1;
    private List<Score> scorePlayer2;
    private List<Score> scorePlayer3;
    private List<Score> scorePlayer4;


    private Unbinder unbinder;
    private GameHistoryAdapter historyAdapter;


    public HistoryGameFragment() {
        // Required empty public constructor
    }

    public static HistoryGameFragment newInstance(String name1, String name2, String name3, String name4, List<Score> score1, List<Score> score2, List<Score> score3, List<Score> score4) {
        HistoryGameFragment fragment = new HistoryGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name1);
        args.putString(ARG_PARAM2, name2);
        args.putString(ARG_PARAM3, name3);
        args.putString(ARG_PARAM4, name4);
        args.putSerializable(ARG_PARAM5, new ArrayList<>(score1));
        args.putSerializable(ARG_PARAM6, new ArrayList<>(score2));
        args.putSerializable(ARG_PARAM7, new ArrayList<>(score3));
        args.putSerializable(ARG_PARAM8, new ArrayList<>(score4));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scorePlayer1 = new ArrayList<>();
        scorePlayer2 = new ArrayList<>();
        scorePlayer3 = new ArrayList<>();
        scorePlayer4 = new ArrayList<>();
        if (getArguments() != null) {
            generateFromArguments(getArguments());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_game, container, false);
        unbinder = ButterKnife.bind(this, view);
        historyAdapter = new GameHistoryAdapter(ScoreHelper.joinScore(scorePlayer1, scorePlayer2, scorePlayer3, scorePlayer4));
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.setLayoutManager(layoutManager);
        historyRecyclerView.setAdapter(historyAdapter);
        nameGameHistoryPlayer1.setText(playerName1);
        nameGameHistoryPlayer2.setText(playerName2);
        nameGameHistoryPlayer3.setText(playerName3);
        nameGameHistoryPlayer4.setText(playerName4);
        int[] score = ScoreHelper.countScore(scorePlayer1, scorePlayer2, scorePlayer3, scorePlayer4);
        if (score != null) {
            updateTotalScore(score);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    private void generateFromArguments(Bundle argument) {
        playerName1 = argument.getString(ARG_PARAM1);
        playerName2 = argument.getString(ARG_PARAM2);
        playerName3 = argument.getString(ARG_PARAM3);
        playerName4 = argument.getString(ARG_PARAM4);
        scorePlayer1.addAll((List) argument.getSerializable(ARG_PARAM5));
        scorePlayer2.addAll((List) argument.getSerializable(ARG_PARAM6));
        scorePlayer3.addAll((List) argument.getSerializable(ARG_PARAM7));
        scorePlayer4.addAll((List) argument.getSerializable(ARG_PARAM8));
    }

    public void updateScore(Score score1, Score score2, Score score3, Score score4) {
        if (scorePlayer1 != null && scorePlayer2 != null && scorePlayer3 != null && scorePlayer4 != null) {
            this.scorePlayer1.add(score1);
            this.scorePlayer2.add(score2);
            this.scorePlayer3.add(score3);
            this.scorePlayer4.add(score4);
            int[] score = new int[]{score1.getScore(), score2.getScore(), score3.getScore(), score4.getScore()};
            historyAdapter.addScore(score);
            updateTotalScore(score);
        }
    }

    private void updateTotalScore(int[] score) {
        if (score.length == 4) {
            int oldScore1 = (!scoreGameHistoryPlayer1.getText().toString().equals("")) ? Integer.parseInt(scoreGameHistoryPlayer1.getText().toString()) : 0;
            int oldScore2 = (!scoreGameHistoryPlayer2.getText().toString().equals("")) ? Integer.parseInt(scoreGameHistoryPlayer2.getText().toString()) : 0;
            int oldScore3 = (!scoreGameHistoryPlayer3.getText().toString().equals("")) ? Integer.parseInt(scoreGameHistoryPlayer3.getText().toString()) : 0;
            int oldScore4 = (!scoreGameHistoryPlayer4.getText().toString().equals("")) ? Integer.parseInt(scoreGameHistoryPlayer4.getText().toString()) : 0;
            scoreGameHistoryPlayer1.setText(String.valueOf(oldScore1 + score[0]));
            scoreGameHistoryPlayer2.setText(String.valueOf(oldScore2 + score[1]));
            scoreGameHistoryPlayer3.setText(String.valueOf(oldScore3 + score[2]));
            scoreGameHistoryPlayer4.setText(String.valueOf(oldScore4 + score[3]));
        }
    }

}
