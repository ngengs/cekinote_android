package com.ngengs.android.cekinote.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ngengs.android.cekinote.DetailGameActivity;
import com.ngengs.android.cekinote.R;

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
    private static final String ARG_PARAM1 = "PLAYER_NAME1";
    private static final String ARG_PARAM2 = "PLAYER_NAME2";
    private static final String ARG_PARAM3 = "PLAYER_NAME3";
    private static final String ARG_PARAM4 = "PLAYER_NAME4";
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
    private String defaultScore;
    private Context context;

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
        defaultScore = getString(R.string.text_value_abstract);
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
        nameManagePlayer1.setText(String.valueOf(namePlayer1));
        nameManagePlayer2.setText(String.valueOf(namePlayer2));
        nameManagePlayer3.setText(String.valueOf(namePlayer3));
        nameManagePlayer4.setText(String.valueOf(namePlayer4));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.layout_manage_score_player1)
    protected void onClickPlayer1() {
        createModal(1, namePlayer1);
    }

    @OnClick(R.id.layout_manage_score_player2)
    protected void onClickPlayer2() {
        createModal(2, namePlayer2);
    }

    @OnClick(R.id.layout_manage_score_player3)
    protected void onClickPlayer3() {
        createModal(3, namePlayer3);
    }

    @OnClick(R.id.layout_manage_score_player4)
    protected void onClickPlayer4() {
        createModal(4, namePlayer4);
    }

    private void createModal(final int position, final String name) {
        new MaterialDialog.Builder(context)
                .inputType(InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_SIGNED)
                .positiveColorRes(R.color.colorPrimary)
                .input(String.format(getString(R.string.title_score_format), name), null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        switch (position) {
                            case 1:
                                scoreManagePlayer1.setText(input);
                                break;
                            case 2:
                                scoreManagePlayer2.setText(input);
                                break;
                            case 3:
                                scoreManagePlayer3.setText(input);
                                break;
                            case 4:
                                scoreManagePlayer4.setText(input);
                                break;
                        }
                    }
                })
                .show();
    }

    @OnClick(R.id.score_manage_add)
    protected void onClickAddScore() {
        if (scoreManagePlayer1.getText().toString().equals(defaultScore) || scoreManagePlayer2.getText().toString().equals(defaultScore) || scoreManagePlayer3.getText().toString().equals(defaultScore) || scoreManagePlayer4.getText().toString().equals(defaultScore)) {
            Snackbar.make(scoreManageAdd, R.string.natice_complete_score_player, Snackbar.LENGTH_SHORT).show();
        } else {
            int score1 = Integer.parseInt(scoreManagePlayer1.getText().toString());
            int score2 = Integer.parseInt(scoreManagePlayer2.getText().toString());
            int score3 = Integer.parseInt(scoreManagePlayer3.getText().toString());
            int score4 = Integer.parseInt(scoreManagePlayer4.getText().toString());
            activity.addScore(score1, score2, score3, score4);
            scoreManagePlayer1.setText(defaultScore);
            scoreManagePlayer2.setText(defaultScore);
            scoreManagePlayer3.setText(defaultScore);
            scoreManagePlayer4.setText(defaultScore);
        }
    }
}
