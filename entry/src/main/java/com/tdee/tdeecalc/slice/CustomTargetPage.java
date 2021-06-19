package com.tdee.tdeecalc.slice;

import com.tdee.tdeecalc.ResourceTable;
import com.tdee.tdeecalc.utils.Utils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.TextField;
import ohos.agp.components.element.ShapeElement;

public class CustomTargetPage extends AbilitySlice {

    private double customProtein = 1.5;
    private double customFat = 20;
    private double targetTdee;
    private double weight;
    private int eatTimes;
    private int targetType;
    private double protein = 0.0;
    private double fat = 0.0;
    private double carb = 0.0;

    public CustomTargetPage(double targetTdee, double weight, int eatTimes, int targetType) {
        super();
        this.targetTdee = targetTdee;
        this.weight = weight;
        this.eatTimes = eatTimes;
        this.targetType = targetType;
    }

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_custom_target_page);

        TextField customProteinSet = (TextField)findComponentById(ResourceTable.Id_custom_protein_set);
        customProteinSet.setText(String.valueOf(customProtein));

        TextField customFatSet = (TextField)findComponentById(ResourceTable.Id_custom_fat_set);
        customFatSet.setText(String.valueOf(customFat));

        ShapeElement errorElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field_error);
        ShapeElement normalElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field);

        Button calcBtn = (Button)findComponentById(ResourceTable.Id_calc_custom_goal);
        calcBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {

                try {
                    customProtein = Float.parseFloat(customProteinSet.getText());
                    if (customProtein < 1.5 || customProtein > 2.2) {
                        customProteinSet.setBackground(errorElement);
                        customProteinSet.setText("");
                        customProteinSet.setHint("请输入1.5到2.2之间的数字");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    customProteinSet.setHint("请输入1.5到2.2之间的数字");
                    customProteinSet.setBackground(errorElement);
                    return;
                }

                try {
                    customFat = Float.parseFloat(customFatSet.getText());
                    if (customFat < 0 || customFat > 100) {
                        customFatSet.setBackground(errorElement);
                        customFatSet.setText("");
                        customFatSet.setHint("请输入0到100之间的数字");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    customFatSet.setHint("请输入0到100之间的数字");
                    customFatSet.setBackground(errorElement);
                    return;
                }

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
                }
            }
        });
    }

    private void calcFatLoss() {
        TextField customProteinTf = (TextField)findComponentById(ResourceTable.Id_custom_protein);
        TextField customFatTf = (TextField)findComponentById(ResourceTable.Id_custom_fat);
        TextField customCarbTf = (TextField)findComponentById(ResourceTable.Id_custom_carb);

        protein = (weight * 1) / eatTimes;
        fat = (targetTdee * customFat) / 100 / 9 / eatTimes;
        carb = (targetTdee - protein * 4 - (targetTdee * customFat) / 100) / 4 / eatTimes;

        customProteinTf.setText(String.valueOf(Utils.converDouble(protein)) + "克");
        customFatTf.setText(String.valueOf(Utils.converDouble(fat)) + "克");
        customCarbTf.setText(String.valueOf(Utils.converDouble(carb)) + "克");
    }

    private void calcMaintenance() {
        TextField customProteinTf = (TextField)findComponentById(ResourceTable.Id_custom_protein);
        TextField customFatTf = (TextField)findComponentById(ResourceTable.Id_custom_fat);
        TextField customCarbTf = (TextField)findComponentById(ResourceTable.Id_custom_carb);

        protein = (weight * 1) / eatTimes;
        fat = (targetTdee * customFat) / 100 / 9 / eatTimes;
        carb = (targetTdee - protein * 4 - (targetTdee * customFat) / 100) / 4 / eatTimes;

        customProteinTf.setText(String.valueOf(Utils.converDouble(protein)) + "克");
        customFatTf.setText(String.valueOf(Utils.converDouble(fat)) + "克");
        customCarbTf.setText(String.valueOf(Utils.converDouble(carb)) + "克");
    }

    private void calcMuscleGainz() {
        TextField customProteinTf = (TextField)findComponentById(ResourceTable.Id_custom_protein);
        TextField customFatTf = (TextField)findComponentById(ResourceTable.Id_custom_fat);
        TextField customCarbTf = (TextField)findComponentById(ResourceTable.Id_custom_carb);

        protein = (weight * customProtein) / eatTimes;
        fat = (targetTdee * customFat) / 100 / 9 / eatTimes;
        carb = (targetTdee - protein * 4 - (targetTdee * customFat) / 100) / 4 / eatTimes;

        customProteinTf.setText(String.valueOf(Utils.converDouble(protein)) + "克");
        customFatTf.setText(String.valueOf(Utils.converDouble(fat)) + "克");
        customCarbTf.setText(String.valueOf(Utils.converDouble(carb)) + "克");
    }
}
