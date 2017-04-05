package com.ngengs.android.cekinote.detailgame.managescore;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ngengs.android.cekinote.R;
import com.ngengs.android.cekinote.data.Tag;
import com.ngengs.android.cekinote.detailgame.DetailGameActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageScoreFragment extends Fragment {
    private static final String TAG = "ManageScoreFragment";

    private static final String ARG_PARAM1 = "PLAYER_NAME1";
    private static final String ARG_PARAM2 = "PLAYER_NAME2";
    private static final String ARG_PARAM3 = "PLAYER_NAME3";
    private static final String ARG_PARAM4 = "PLAYER_NAME4";
    private final int defaultScoreInt = -999999999;
    @BindView(R.id.name_manage_score_player1)
    TextView nameManagePlayer1;
    @BindView(R.id.score_manage_score_player1)
    TextView scoreManagePlayer1;
    @BindView(R.id.layout_manage_score_player1)
    LinearLayout layoutManagePlayer1;
    @BindView(R.id.name_manage_score_player2)
    TextView nameManagePlayer2;
    @BindView(R.id.score_manage_score_player2)
    TextView scoreManagePlayer2;
    @BindView(R.id.layout_manage_score_player2)
    LinearLayout layoutManagePlayer2;
    @BindView(R.id.name_manage_score_player3)
    TextView nameManagePlayer3;
    @BindView(R.id.score_manage_score_player3)
    TextView scoreManagePlayer3;
    @BindView(R.id.layout_manage_score_player3)
    LinearLayout layoutManagePlayer3;
    @BindView(R.id.name_manage_score_player4)
    TextView nameManagePlayer4;
    @BindView(R.id.score_manage_score_player4)
    TextView scoreManagePlayer4;
    @BindView(R.id.layout_manage_score_player4)
    LinearLayout layoutManagePlayer4;
    @BindView(R.id.score_manage_add)
    FloatingActionButton scoreManageAdd;
    private String namePlayer1;
    private String namePlayer2;
    private String namePlayer3;
    private String namePlayer4;
    private String defaultScoreString;
    private Context context;
    private int scorePlayer1;
    private int scorePlayer2;
    private int scorePlayer3;
    private int scorePlayer4;
    private DetailGameActivity activity;
    private Unbinder unbinder;


    public ManageScoreFragment() {
        // Required empty public constructor
    }

    public static ManageScoreFragment newInstance(String name1, String name2, String name3, String name4) {
        ManageScoreFragment fragment = new ManageScoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name1);
        args.putString(ARG_PARAM2, name2);
        args.putString(ARG_PARAM3, name3);
        args.putString(ARG_PARAM4, name4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            namePlayer1 = getArguments().getString(ARG_PARAM1);
            namePlayer2 = getArguments().getString(ARG_PARAM2);
            namePlayer3 = getArguments().getString(ARG_PARAM3);
            namePlayer4 = getArguments().getString(ARG_PARAM4);
        }
        if (savedInstanceState != null) {
            scorePlayer1 = savedInstanceState.getInt(Tag.PLAYER_SCORE1);
            scorePlayer2 = savedInstanceState.getInt(Tag.PLAYER_SCORE2);
            scorePlayer3 = savedInstanceState.getInt(Tag.PLAYER_SCORE3);
            scorePlayer4 = savedInstanceState.getInt(Tag.PLAYER_SCORE4);
        } else {
            scorePlayer1 = defaultScoreInt;
            scorePlayer2 = defaultScoreInt;
            scorePlayer3 = defaultScoreInt;
            scorePlayer4 = defaultScoreInt;
        }
        defaultScoreString = getString(R.string.text_value_abstract);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_score, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getActivity() != null) {
            activity = (DetailGameActivity) getActivity();
        }
        context = view.getContext();
        nameManagePlayer1.setText(String.format(getString(R.string.player_number_format), 1, namePlayer1));
        nameManagePlayer2.setText(String.format(getString(R.string.player_number_format), 2, namePlayer2));
        nameManagePlayer3.setText(String.format(getString(R.string.player_number_format), 3, namePlayer3));
        nameManagePlayer4.setText(String.format(getString(R.string.player_number_format), 4, namePlayer4));
        scoreToText(1, String.valueOf(scorePlayer1));
        scoreToText(2, String.valueOf(scorePlayer2));
        scoreToText(3, String.valueOf(scorePlayer3));
        scoreToText(4, String.valueOf(scorePlayer4));
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(Tag.PLAYER_SCORE1, scorePlayer1);
        outState.putInt(Tag.PLAYER_SCORE2, scorePlayer2);
        outState.putInt(Tag.PLAYER_SCORE3, scorePlayer3);
        outState.putInt(Tag.PLAYER_SCORE4, scorePlayer4);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.layout_manage_score_player1, R.id.layout_manage_score_player2, R.id.layout_manage_score_player3, R.id.layout_manage_score_player4})
    protected void onClickPlayer(View view) {
        switch (view.getId()) {
            case R.id.layout_manage_score_player1:
                createModal(1, namePlayer1);
                break;
            case R.id.layout_manage_score_player2:
                createModal(2, namePlayer2);
                break;
            case R.id.layout_manage_score_player3:
                createModal(3, namePlayer3);
                break;
            case R.id.layout_manage_score_player4:
                createModal(4, namePlayer4);
                break;
        }
    }

    private void createModal(final int position, final String name) {
        new MaterialDialog.Builder(context)
                .inputType(InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_SIGNED)
                .positiveColorRes(R.color.colorPrimary)
                .input(String.format(getString(R.string.title_score_format), name), null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        scoreToText(position, input.toString());
                    }
                })
                .show();
    }

    @OnClick(R.id.score_manage_add)
    protected void onClickAddScore() {
        if (scorePlayer1 == defaultScoreInt || scorePlayer2 == defaultScoreInt || scorePlayer3 == defaultScoreInt || scorePlayer4 == defaultScoreInt) {
            Snackbar.make(scoreManageAdd, R.string.natice_complete_score_player, Snackbar.LENGTH_SHORT).show();
        } else {
            activity.addScore(scorePlayer1, scorePlayer2, scorePlayer3, scorePlayer4);
            scoreToText(1, defaultScoreString);
            scoreToText(2, defaultScoreString);
            scoreToText(3, defaultScoreString);
            scoreToText(4, defaultScoreString);
        }
    }

    private void scoreToText(int position, String scoreInString) {
        try {
            int scoreInNumber;
            if (scoreInString.equals(defaultScoreString)) scoreInNumber = defaultScoreInt;
            else if (scoreInString.equals(String.valueOf(defaultScoreInt))) {
                scoreInString = defaultScoreString;
                scoreInNumber = defaultScoreInt;
            } else scoreInNumber = Integer.parseInt(scoreInString);

            switch (position) {
                case 1:
                    scorePlayer1 = scoreInNumber;
                    scoreManagePlayer1.setText(scoreInString);
                    break;
                case 2:
                    scorePlayer2 = scoreInNumber;
                    scoreManagePlayer2.setText(scoreInString);
                    break;
                case 3:
                    scorePlayer3 = scoreInNumber;
                    scoreManagePlayer3.setText(scoreInString);
                    break;
                case 4:
                    scorePlayer4 = scoreInNumber;
                    scoreManagePlayer4.setText(scoreInString);
                    break;
            }
        } catch (NumberFormatException e) {
            Log.e(TAG, "scoreToText: ", e);
        }
    }
}
