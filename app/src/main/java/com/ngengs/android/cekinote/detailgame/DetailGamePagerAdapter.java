package com.ngengs.android.cekinote.detailgame;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ngengs.android.cekinote.data.model.Game;
import com.ngengs.android.cekinote.detailgame.history.HistoryGameFragment;
import com.ngengs.android.cekinote.detailgame.managescore.ManageScoreFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngengs on 12/20/2016.
 */

class DetailGamePagerAdapter extends FragmentPagerAdapter {
    private String[] tabTitles;
    private ManageScoreFragment manageScoreFragment;
    private HistoryGameFragment historyGameFragment;

    DetailGamePagerAdapter(FragmentManager fm, String[] title, Game gameData, List<Integer> scorePlayer1, List<Integer> scorePlayer2, List<Integer> scorePlayer3, List<Integer> scorePlayer4) {
        super(fm);
        if (title.length == 2) {
            manageScoreFragment = ManageScoreFragment.newInstance(gameData.getPlayer1().getName(), gameData.getPlayer2().getName(), gameData.getPlayer3().getName(), gameData.getPlayer4().getName());
        }
        List<Integer> scorePlayer11 = new ArrayList<>();
        List<Integer> scorePlayer21 = new ArrayList<>();
        List<Integer> scorePlayer31 = new ArrayList<>();
        List<Integer> scorePlayer41 = new ArrayList<>();
        scorePlayer11.addAll(scorePlayer1);
        scorePlayer21.addAll(scorePlayer2);
        scorePlayer31.addAll(scorePlayer3);
        scorePlayer41.addAll(scorePlayer4);
        this.tabTitles = title;
        historyGameFragment = HistoryGameFragment.newInstance(gameData.getPlayer1().getName(), gameData.getPlayer2().getName(), gameData.getPlayer3().getName(), gameData.getPlayer4().getName(), scorePlayer11, scorePlayer21, scorePlayer31, scorePlayer41);
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

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    void updateDataScore(int scorePlayer1, int scorePlayer2, int scorePlayer3, int scorePlayer4) {
        if (historyGameFragment != null) {
            historyGameFragment.updateScore(scorePlayer1, scorePlayer2, scorePlayer3, scorePlayer4);
        }
    }
}
