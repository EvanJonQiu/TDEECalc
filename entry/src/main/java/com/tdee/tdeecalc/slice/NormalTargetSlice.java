package com.tdee.tdeecalc.slice;

import com.tdee.tdeecalc.utils.Utils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import com.tdee.tdeecalc.ResourceTable;
import ohos.agp.components.TextField;

public class NormalTargetSlice extends AbilitySlice {

    private double targetTdee;
    private double weight;
    private int eatTimes;
    private int targetType;
    private double protein = 0.0;
    private double fat = 0.0;
    private double carb = 0.0;

    public NormalTargetSlice(double targetTdee, double weight, int eatTimes, int targetType) {
        super();
        this.targetTdee = targetTdee;
        this.weight = weight;
        this.eatTimes = eatTimes;
        this.targetType = targetType;
    }

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);

        super.setUIContent(ResourceTable.Layout_ability_normal_target_page);
        if (targetTdee != 0.0 && weight != 0.0) {

            switch (targetType) {
                case 0:
                    calcFatLoss();
                    break;
                case 1:
                    calcMaintenance();
                    break;
                case 2:
                    calcMuscleGainz();
                    break;
                default:
                    break;
            }

            TextField proteinText = (TextField) findComponentById(ResourceTable.Id_normal_protein);
            proteinText.setText(String.valueOf(Utils.converDouble(protein)) + "克");

            TextField fatText = (TextField) findComponentById(ResourceTable.Id_normal_fat);
            fatText.setText(String.valueOf(Utils.converDouble(fat)) + "克");

            TextField carbText = (TextField) findComponentById(ResourceTable.Id_normal_carb);
            carbText.setText(String.valueOf(Utils.converDouble(carb)) + "克");
        }
    }

    private void calcFatLoss() {
        protein = (weight * 2) / eatTimes;
        fat = (targetTdee * 0.25) / 9 / eatTimes;
        carb = (targetTdee - protein * 4 - targetTdee * 0.2) / 4 / eatTimes;
    }

    private void calcMaintenance() {
        protein = (weight * 1) / eatTimes;
        fat = (targetTdee * 0.2) / 9 / eatTimes;
        carb = (targetTdee - protein * 4 - targetTdee * 0.2) / 4 / eatTimes;
    }

    private void calcMuscleGainz() {
        protein = (weight * 1.5) / eatTimes;
        fat = (targetTdee * 0.2) / 9 / eatTimes;
        carb = (targetTdee - protein * 4 - targetTdee * 0.2) / 4 / eatTimes;
    }
}
