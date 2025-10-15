# Time Stages #

## About ##
Allows stages to be unlocked/locked with a timer, now no longer depends on CraftTweaker, and allows control over what, if any, messages timers come with.

## License ##
* Time Stages is licensed under the MIT License
  - (c) 2020 Mrbysco
  - [![License](https://img.shields.io/badge/License-MIT-red.svg?style=flat)](http://opensource.org/licenses/MIT)
  
## Downloads ##
Download for the original is at https://minecraft.curseforge.com/projects/time-stages

Download for my fork is currently at https://drive.google.com/file/d/1FPYNS4tNoS5Xd--_ouLrt2kSTn95dss7/view?usp=sharing

I will probably upload to CurseForge at some point, though.

## How to Add Timers ##
Use the GroovyScript mod (https://www.curseforge.com/minecraft/mc-mods/groovyscript), launch the game with it at least once to generate the groovy/postInit folder in your Minecraft instance, then go it, make a new .groovy file, and follow the instructions below:


```
import com.mrbysco.timestages.TimeStages

TimeStages.addTimerInfo(uniqueID, stage, nextStage, time, amount, removal, removeOld, timerAddMessage, timerRemovalMessage)
```

uniqueID - A string of text giving the timer a unique ID

stage - The name of the stage required to begin the timer of the new stage

nextStage - The new stage the timer will grant once done

time - Amount of time needed for new stage

amount - Unit of time, can be "seconds", "min", "hour", "day"

removal - Whether to ONLY remove the required stage and NOT add the new stage

removeOld - Whether to remove required stage AND add new stage

timerAddMessage - What message if any to display if removal is false (Set to an empty "" for none)

timerRemovalMessage - What message if any to display if removal is true (Set to an empty "" for none)

## Example ##

For example:

```
import com.mrbysco.timestages.TimeStages




//Adds the 4 seasons as cyclical timed stages with
//90 hours inbetween, each season removes the previous and doesn't come with any message
TimeStages.addTimerInfo("SpringToSummer", "spring", "summer", 90, "hour", false, true, "", "")
TimeStages.addTimerInfo("SummerToAutumn", "summer", "autumn", 90, "hour", false, true, "", "")
TimeStages.addTimerInfo("AutumnToWinter", "autumn", "winter", 90, "hour", false, true, "", "")
TimeStages.addTimerInfo("WinterToSpring", "winter", "spring", 90, "hour", false, true, "", "")
```

To add an initial stage when the player first logs in you can do:

```
import java.util.UUID

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound

import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent




//Return compound at compound, 
//and if it doesn't exist, create it
def compoundInCompound(compoundKey, atCompound) {
//Check if the compound already has that key
    if(atCompound.hasKey(compoundKey)) {
//If it does, return nested compound with that key
        return atCompound.getCompoundTag(compoundKey)
    } else {
//If not, create a new compound
        def newCompound = new NBTTagCompound()
//Then assign it to the parameter key
        atCompound.setTag(compoundKey, newCompound)
        return atCompound.getCompoundTag(compoundKey)
    }
}




// When player logins
event_manager.listen(EventPriority.LOWEST) { PlayerLoggedInEvent event -> 
    def player = event.player
//Get NBT in portable format
    def playerNBT = player.getEntityData()

// Get persistent NBT
    def forgeData = compoundInCompound("ForgeData", playerNBT)
    def playerPersisted = compoundInCompound("PlayerPersisted", forgeData)
    def logCount = playerPersisted.getInteger("logCount")

//Get player login count
    def newLogCount = (logCount == null) ? 1 : (logCount + 1)

// First login ever, assign initial gamestage
    if (newLogCount == 1) {
        GameStageHelper.addStage(player, "spring")  
    }

//Set logCount to new log count
    playerPersisted.setInteger("logCount", newLogCount)
}
```
