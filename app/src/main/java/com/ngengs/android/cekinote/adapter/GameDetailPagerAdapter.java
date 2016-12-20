package com.ngengs.android.cekinote.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ngengs.android.cekinote.fragment.HistoryGameFragment;
import com.ngengs.android.cekinote.fragment.ManageScoreFragment;
import com.ngengs.android.cekinote.model.Game;
import com.ngengs.android.cekinote.model.Score;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ngengs on 12/20/2016.
 */

public class GameDetailPagerAdapter extends FragmentPagerAdapter {
    private String[] tabTitles;
    private String[] tabChecker;
    private ManageScoreFragment manageScoreFragment;
    private HistoryGameFragment historyGameFragment;
    List<Score> scorePlayer1;
    List<Score> scorePlayer2;
    List<Score> scorePlayer3;
    List<Score> scorePlayer4;

    public GameDetailPagerAdapter(FragmentManager fm, String[] title, String[] checker, Game gameData, List<Score> scorePlayer1, List<Score> scorePlayer2, List<Score> scorePlayer3, List<Score> scorePlayer4) {
        super(fm);
        if (title.length == checker.length) {
            manageScoreFragment = ManageScoreFragment.newInstance(gameData.getPlayer1().getName(), gameData.getPlayer2().getName(), gameData.getPlayer3().getName(), gameData.getPlayer4().getName());
        }
        this.scorePlayer1 = new ArrayList<>();
        this.scorePlayer2 = new ArrayList<>();
        this.scorePlayer3 = new ArrayList<>();
        this.scorePlayer4 = new ArrayList<>();
        this.scorePlayer1.addAll(scorePlayer1);
        this.scorePlayer2.addAll(scorePlayer2);
        this.scorePlayer3.addAll(scorePlayer3);
        this.scorePlayer4.addAll(scorePlayer4);
        this.tabTitles = title;
        this.tabChecker = checker;
        historyGameFragment = HistoryGameFragment.newInstance(gameData.getPlayer1().getName(), gameData.getPlayer2().getName(), gameData.getPlayer3().getName(), gameData.getPlayer4().getName(), this.scorePlayer1, this.scorePlayer2, this.scorePlayer3, this.scorePlayer4);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        if (tabTitles[position].equals(tabChecker[0])) {
            return manageScoreFragment;
        } else if (tabTitles[position].equals(tabChecker[1])) {
            return historyGameFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    public void updateDataGame(Game game) {

    }

    public void updateDataScore(Score scorePlayer1, Score scorePlayer2, Score scorePlayer3, Score scorePlayer4) {
        if (historyGameFragment != null) {
            historyGameFragment.updateScore(scorePlayer1, scorePlayer2, scorePlayer3, scorePlayer4);
        }
    }

    public void changeToEndGame(){
        List<String> dummy = new ArrayList<>();
        dummy.addAll(Arrays.asList(tabTitles));
        dummy.remove(0);
        tabTitles = dummy.toArray(new String[dummy.size()]);
        if(manageScoreFragment != null){
            manageScoreFragment.onDestroy();
            manageScoreFragment = null;
        }
        notifyDataSetChanged();
    }
}
