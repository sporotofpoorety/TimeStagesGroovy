package com.mrbysco.timestages;

import com.mrbysco.timestages.proxy.CommonProxy;
import com.mrbysco.timestages.util.TimeHelper;
import net.darkhax.bookshelf.lib.LoggingHelper;
import net.darkhax.bookshelf.util.PlayerUtils;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class TimeStages {
	
	@Instance(Reference.MOD_ID)
	public static TimeStages instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static final LoggingHelper LOGGER = new LoggingHelper(Reference.MOD_NAME);
		
	public static HashMap<String, StageInfo> timers = new HashMap<String, StageInfo>();

	public static void addTimerInfo(String uniqueID, String stage, String nextStage, int time, String amount, boolean removal, boolean removeOld)
	{
		// Check if the info doesn't already exist
		StageInfo timer_info = new StageInfo(uniqueID, stage, nextStage, time, amount, removal, removeOld);
		if(timers.containsValue(timer_info) || timers.containsKey(uniqueID) ) {
			return;
		} else {
			timers.put(uniqueID, timer_info);
		}
	}

    @EventHandler
    public void Preinit(FMLPreInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event)
    {
		if(event.phase == TickEvent.Phase.END)
			return;

		final EntityPlayer player = (EntityPlayer) event.player;
		if(!player.world.isRemote) {
			if (player.world.getWorldTime() % 20 == 0) {
				if (PlayerUtils.isPlayerReal(event.player)) {
					for (HashMap.Entry<String, StageInfo> entry : timers.entrySet()) {
						StageInfo info = entry.getValue();
						if (info.getStage().isEmpty())
							return;

						final boolean removal = info.isRemoval();

						final String stage = info.getStage();
						final String nextStage = info.getNextStage();
						final int time = TimeHelper.getProperTime(info.getTime(), info.getAmount());
						final boolean removeOld = info.isRemoveOld();
						final String uniqueID = info.getUniqueID();

						if (removal)
						{
							if(GameStageHelper.hasStage(player, stage))
							{
								if(getEntityTimeData(player, uniqueID) != info.timer)
								{
									info.timer = getEntityTimeData(player, uniqueID);
								}

								if(info.timer >= time)
								{
									info.timer = 0;
									setEntityTimeData(player, uniqueID, 0);

									GameStageHelper.removeStage(player, stage);
									player.sendMessage(new TextComponentTranslation("stage.removal.message", new Object[] {stage}));
								}
								else
								{
									++info.timer;
									setEntityTimeData(player, uniqueID, info.timer);
								}
							}
							else
							{
								if (info.timer != 0)
								{
									info.timer = 0;
									setEntityTimeData(player, uniqueID, 0);
								}
							}
						}
						else
						{

							if (GameStageHelper.hasStage(player, stage) && !GameStageHelper.hasStage(player, nextStage))
							{
								if(info.getAmount().contains("day")) {
									long worldAge = player.world.getWorldTime() / 24000;
									if((int)worldAge >= time) {
										setEntityTimeData(player, uniqueID, 0);
										if(removeOld)
										{
											GameStageHelper.addStage(player, nextStage);
											GameStageHelper.removeStage(player, stage);
										}
										else
										{
											GameStageHelper.addStage(player, nextStage);
										}
										player.sendMessage(new TextComponentTranslation("stage.add.message", new Object[] {nextStage}));
									}
								} else {
									if(getEntityTimeData(player, uniqueID) != info.timer)
									{
										info.timer = getEntityTimeData(player, uniqueID);
									}

									if(info.timer >= time)
									{
										info.timer = 0;
										setEntityTimeData(player, uniqueID, 0);

										if(removeOld)
										{
											GameStageHelper.addStage(player, nextStage);
											GameStageHelper.removeStage(player, stage);
										}
										else
										{
											GameStageHelper.addStage(player, nextStage);
										}
										player.sendMessage(new TextComponentTranslation("stage.add.message", new Object[] {nextStage}));
									}
									else
									{
										++info.timer;
										setEntityTimeData(player, uniqueID, info.timer);
									}
								}
							}
							else
							{
								if (info.timer != 0)
								{
									info.timer = 0;
									setEntityTimeData(player, uniqueID, 0);
								}
							}
						}
					}
				}
			}
		}
    }
    
    public static void setEntityTimeData(EntityPlayer player, String valueTag, int time)
    {
    	NBTTagCompound playerData = player.getEntityData();
    	NBTTagCompound data = getTag(playerData, EntityPlayer.PERSISTED_NBT_TAG);
    	
    	data.setInteger(valueTag, time);
    	playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
    }
    
    public static int getEntityTimeData(EntityPlayer player, String valueTag)
    {
    	NBTTagCompound playerData = player.getEntityData();
    	NBTTagCompound data = getTag(playerData, EntityPlayer.PERSISTED_NBT_TAG);
    	return data.getInteger(valueTag);
    }
    
    public static NBTTagCompound getTag(NBTTagCompound tag, String key) {
		if(tag == null || !tag.hasKey(key)) {
			return new NBTTagCompound();
		}
		return tag.getCompoundTag(key);
    }
    
    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
