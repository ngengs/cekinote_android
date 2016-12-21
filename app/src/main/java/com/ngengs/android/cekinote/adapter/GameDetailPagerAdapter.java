package com.ngengs.android.cekinote.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ngengs.android.cekinote.fragment.HistoryGameFragment;
import com.ngengs.android.cekinote.fragment.ManageScoreFragment;
import com.ngengs.android.cekinote.model.Game;
import com.ngengs.android.cekinote.model.Score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ngengs on 12/20/2016.
 */

public class GameDetailPagerAdapter extends FragmentPagerAdapter {
    private String[] tabTitles;
    private ManageScoreFragment manageScoreFragment;
    private HistoryGameFragment historyGameFragment;

    public GameDetailPagerAdapter(FragmentManager fm, String[] title, Game gameData, List<Score> scorePlayer1, List<Score> scorePlayer2, List<Score> scorePlayer3, List<Score> scorePlayer4) {
        super(fm);
        if (title.length == 2) {
            manageScoreFragment = ManageScoreFragment.newInstance(gameData.getPlayer1().getName(), gameData.getPlayer2().getName(), gameData.getPlayer3().getName(), gameData.getPlayer4().getName());
        }
        List<Score> scorePlayer11 = new ArrayList<>();
        List<Score> scorePlayer21 = new ArrayList<>();
        List<Score> scorePlayer31 = new ArrayList<>();
        List<Score> scorePlayer41 = new ArrayList<>();
        scorePlayer11.addAll(scorePlayer1);
        scorePlayer21.addAll(scorePlayer2);
        scorePlayer31.addAll(scorePlayer3);
        scorePlayer41.addAll(scorePlayer4);
        this.tabTitles = title;
        historyGameFragment = HistoryGameFragment.newInstance(gameData.getPlayer1().getName(), gameData.getPlayer2().getName(), gameData.getPlayer3().getName(), gameData.getPlayer4().getName(), scorePlayer11, scorePlayer21, scorePlayer31, scorePlayer41);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        if (getCount() == 2) {
            switch (position) {
                case 0:
                    return manageScoreFragment;
                case 1:
                    return historyGameFragment;
            }
        } else {
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

    public void changeToEndGame() {
        List<String> dummy = new ArrayList<>();
        dummy.addAll(Arrays.asList(tabTitles));
        dummy.remove(0);
        tabTitles = dummy.toArray(new String[dummy.size()]);
        if (manageScoreFragment != null) {
            manageScoreFragment.onDestroy();
            manageScoreFragment = null;
        }
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        if (getCount() == 2) {
            switch (position) {
                case 0:
                    manageScoreFragment = (ManageScoreFragment) fragment;
                    break;
                case 1:
                    historyGameFragment = (HistoryGameFragment) fragment;
                    break;
            }
        } else {
            historyGameFragment = (HistoryGameFragment) fragment;
        }
        return fragment;
    }
}
