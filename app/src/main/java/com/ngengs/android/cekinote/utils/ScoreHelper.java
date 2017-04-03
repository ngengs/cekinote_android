package com.ngengs.android.cekinote.utils;

import com.ngengs.android.cekinote.data.model.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngengs on 12/20/2016.
 */

public class ScoreHelper {

    public static int[] countScore(List<Score> scores1, List<Score> scores2, List<Score> scores3, List<Score> scores4) {
        if (scores1.size() == scores2.size() && scores1.size() == scores3.size() && scores1.size() == scores4.size()) {
            int score1 = 0;
            int score2 = 0;
            int score3 = 0;
            int score4 = 0;
            for (int i = 0; i < scores1.size(); i++) {
                score1 += scores1.get(i).getScore();
                score2 += scores2.get(i).getScore();
                score3 += scores3.get(i).getScore();
                score4 += scores4.get(i).getScore();
            }
            return new int[]{score1, score2, score3, score4};
        } else {
            return null;
        }
    }

    public static List<int[]> joinScore(List<Score> scores1, List<Score> scores2, List<Score> scores3, List<Score> scores4) {
        if (scores1.size() == scores2.size() && scores1.size() == scores3.size() && scores1.size() == scores4.size()) {
            List<int[]> score = new ArrayList<>();
            for (int i = 0; i < scores1.size(); i++) {
                score.add(new int[]{scores1.get(i).getScore(), scores2.get(i).getScore(), scores3.get(i).getScore(), scores4.get(i).getScore()});
            }
            return score;
        } else {
            return null;
        }
    }
}
