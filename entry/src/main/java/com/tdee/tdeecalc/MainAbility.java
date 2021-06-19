package com.tdee.tdeecalc;

import com.tdee.tdeecalc.slice.CustomTargetPage;
import com.tdee.tdeecalc.slice.MainAbilitySlice;
import com.tdee.tdeecalc.slice.NormalTargetSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());

        addActionRoute("action.normal_target_page", NormalTargetSlice.class.getName());
        addActionRoute("action.custom_target_page", CustomTargetPage.class.getName());
    }
}
