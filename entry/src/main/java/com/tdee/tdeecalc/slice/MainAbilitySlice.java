package com.tdee.tdeecalc.slice;

import com.tdee.tdeecalc.ResourceTable;
import com.tdee.tdeecalc.utils.Utils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;

import java.util.List;

public class MainAbilitySlice extends AbilitySlice {

    private PageSlider pageSlider;
    private List<Component> pageviews;
    private int gender = 0;
    private int exerciseRateIndex = 0;
    private double bmr = 0.0;
    private double tdee = 0.0;
    private int eatTimesIndex = 0;
    private int targetType = 0;
    private double targetTdee = 0.0;
    private double userWeight = 0.0;
    private double userAge = 0.0;
    private double userHeight = 0.0;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        Text bmrRet = (Text)findComponentById(ResourceTable.Id_bmrRet);
        bmrRet.setText("您的BMR为:");

        RadioContainer genderRadio = (RadioContainer)findComponentById(ResourceTable.Id_genderRadio);
        genderRadio.mark(gender);
        genderRadio.setMarkChangedListener(new RadioContainer.CheckedStateChangedListener() {
            @Override
            public void onCheckedChanged(RadioContainer radioContainer, int i) {
                gender = i;
            }
        });

        TextField ageTextField = (TextField)findComponentById(ResourceTable.Id_user_age);
        TextField heightTextField = (TextField)findComponentById(ResourceTable.Id_user_height);
        TextField weightTextField = (TextField)findComponentById(ResourceTable.Id_user_weight);

        ShapeElement errorElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field_error);
        ShapeElement normalElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field);

        // 计算bmr
        Button bmrBtn = (Button)findComponentById(ResourceTable.Id_calc_bmr);
        bmrBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                try {
                    userAge = Float.parseFloat(ageTextField.getText());
                    if (userAge < 20 || userAge > 100) {
                        ageTextField.setBackground(errorElement);
                        ageTextField.setText("");
                        ageTextField.setHint("请输入20到100之间的年龄");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ageTextField.setHint("请输入20到100之间的年龄");
                    ageTextField.setBackground(errorElement);
                    return;
                }
                ageTextField.setBackground(normalElement);

                try {
                    userHeight = Float.parseFloat(heightTextField.getText());
                    if (userHeight <100 || userHeight > 230) {
                        heightTextField.setBackground(errorElement);
                        heightTextField.setText("");
                        heightTextField.setHint("请输入100到230厘米的身高");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    heightTextField.setBackground(errorElement);
                    heightTextField.setHint("请输入100到230厘米的身高");
                    return;
                }
                heightTextField.setBackground(normalElement);

                try {
                    userWeight = Float.parseFloat(weightTextField.getText());
                    if (userWeight <20 || userWeight > 200) {
                        weightTextField.setBackground(errorElement);
                        weightTextField.setText("");
                        weightTextField.setHint("请输入20到200公斤的体重");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    weightTextField.setBackground(errorElement);
                    weightTextField.setHint("请输入20到200公斤的体重");
                    return;
                }
                weightTextField.setBackground(normalElement);

                if (gender == 0) {
                    bmr = 88.4 + 13.4 * userWeight + 4.8 * userHeight - 5.68 * userAge;
                } else {
                    bmr = 447.6 + 9.25 * userWeight + 3.1 * userHeight - 4.33 * userAge;
                }

                bmrRet.setText("您的TDEE为:" + String.valueOf(Utils.converDouble(bmr)) + "千卡(kCal)");
            }
        });

        Picker picker = (Picker)findComponentById(ResourceTable.Id_exerciseRate);
        picker.setValue(exerciseRateIndex);
        picker.setMinValue(0);
        picker.setMaxValue(4);
        picker.setDisplayedData(new String[]{
                "久坐/无运动习惯 - (Little/no exercise)",
                "每周运动1-3天 - (Light exercise)",
                "每周运动3-5天 - (Moderate exercise)",
                "每周运动6-7天 - (Very active)",
                "烈运动者/体力活工作 - (Extra active)"
        });
        picker.setValueChangedListener((picker1, oldVal, newVal) -> {
            exerciseRateIndex = newVal;
        });

        Text tdeeRet = (Text)findComponentById(ResourceTable.Id_tdeeRet);
        tdeeRet.setText("您的TDEE为:");

        Button tdeeBtn = (Button)findComponentById(ResourceTable.Id_calc_tdee);
        tdeeBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                double rate = 1.2;
                switch(exerciseRateIndex) {
                    case 0:
                        rate = 1.2;
                        break;
                    case 1:
                        rate = 1.375;
                        break;
                    case 2:
                        rate = 1.55;
                        break;
                    case 3:
                        rate = 1.725;
                        break;
                    case 4:
                        rate = 1.9;
                        break;
                    default:
                        break;
                }
                tdee = bmr * rate;

                tdeeRet.setText("您的TDEE为:" + String.valueOf(Utils.converDouble(tdee)) + "千卡(kCal)");
            }
        });

        RadioContainer targetRadio = (RadioContainer)findComponentById(ResourceTable.Id_target_type);
        targetRadio.mark(targetType);
        targetRadio.setMarkChangedListener(new RadioContainer.CheckedStateChangedListener() {
            @Override
            public void onCheckedChanged(RadioContainer radioContainer, int i) {
                targetType = i;
            }
        });

        Picker eatTimesPicker = (Picker)findComponentById(ResourceTable.Id_eatTimes);
        eatTimesPicker.setValue(eatTimesIndex);
        eatTimesPicker.setMinValue(0);
        eatTimesPicker.setMaxValue(3);
        eatTimesPicker.setDisplayedData(new String[]{
                "每日3次",
                "每日4次",
                "每日5次",
                "每日6次"
        });
        eatTimesPicker.setValueChangedListener((picker1, oldVal, newVal) -> {
            eatTimesIndex = newVal;
        });

        Text targetTdeeRet = (Text)findComponentById(ResourceTable.Id_targetTdeeRet);
        targetTdeeRet.setText("您的目标TDEE为:");

        Button calcTargetTdeeBtn = (Button)findComponentById(ResourceTable.Id_calc_target_tdee);
        calcTargetTdeeBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                switch(targetType) {
                    case 0:
                        targetTdee = tdee - tdee * 0.1;
                        break;
                    case 1:
                        targetTdee = tdee;
                    case 2:
                        targetTdee = tdee + tdee * 0.1;
                        break;
                    default:
                        break;
                }
                targetTdeeRet.setText("您的目标TDEE为:" + String.valueOf(Utils.converDouble(targetTdee)) + "千卡(kCal)");
            }
        });

        Button toNormalTargetBtn = (Button)findComponentById(ResourceTable.Id_to_normal_target);
        toNormalTargetBtn.setClickedListener(listener -> present(new NormalTargetSlice(targetTdee, userWeight, eatTimesIndex + 3, targetType), new Intent()));

        Button toCustomTargetBtn = (Button)findComponentById(ResourceTable.Id_to_custom_target);
        toCustomTargetBtn.setClickedListener(listener -> present(new CustomTargetPage(targetTdee, userWeight, eatTimesIndex + 3, targetType), new Intent()));
    }

    private void calculateBMR() {

    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
