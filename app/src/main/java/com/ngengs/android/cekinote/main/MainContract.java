package com.ngengs.android.cekinote.main;

import android.os.Bundle;

import com.ngengs.android.cekinote.BasePresenter;
import com.ngengs.android.cekinote.BaseView;
import com.ngengs.android.cekinote.data.model.Game;

import java.util.List;

/**
 * Created by ngengs on 4/3/2017.
 */

interface MainContract {
    interface View extends BaseView<Presenter> {
        void changeHeaderLook(boolean forceChange);

        void changeHeaderTitle();

        void changeHeaderColor();

        void updateData(List<Game> data);

        void updateData(Game data, int position);

        void addSelected(int position);

        void removeSelected(int position);

        void clearOptionMenu();

        void startDetailGame(int position);
    }

    interface Presenter extends BasePresenter {
        boolean menuEndGame();

        int gameSize();

        int selectedSize();

        void updateDataGame();

        void generateFromSavedInstanceState(Bundle savedInstanceState);

        void refreshGameDao(Integer position);

        void backPressed();

        void itemClick(Integer position);

        List<Game> getGame();

        List<Integer> getSelected();

        boolean itemLongClick(Integer position);

        boolean isSelectedEmpty();

        String getGameId(Integer position);
    }
}
