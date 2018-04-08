package com.Mrbysco.TimeStages.compat.ct;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.TimeStages")
public class StageTimerCT {

	@ZenMethod
    public static void addTimer(String stage, String nextStage, int time, String amount, boolean removeOld) {
        CraftTweakerAPI.apply(new ActionAddTimer(stage, nextStage, time, amount, false, removeOld));
	}
	
	@ZenMethod
	public static void addTimer(String stage, String nextStage, int time, String amount) {
		CraftTweakerAPI.apply(new ActionAddTimer(stage, nextStage, time, amount, false, false));
	}
	
	@ZenMethod
	public static void removalTimer(String stage, int time, String amount) {
		CraftTweakerAPI.apply(new ActionAddTimer(stage, null, time, amount, true, false));
	}
}
