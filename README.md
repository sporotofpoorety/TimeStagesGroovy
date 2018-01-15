# Time Stages #

## About ##
Allows stages to be unlocked/locked with a timer.

## License ##
* Time Stages is licensed under the MIT License
  - (c) 2018 Mrbysco
  - [![License](https://img.shields.io/badge/License-MIT-red.svg?style=flat)](http://opensource.org/licenses/MIT)
  
## Downloads ##
Downloads are https://minecraft.curseforge.com/projects/time-stages

## Example

```
// Makes you gain a stage 30 seconds after getting the needed stage.
mods.TimeStages.addTimer("neededStage" ,"UnlockedStage" ,30 ,"seconds");

// Removes a stage after 20 minutes.
mods.TimeStages.removalTimer("removedStage" ,20 ,"minutes");
```