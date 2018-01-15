package com.Mrbysco.TimeStages;

import java.util.ArrayList;

import com.Mrbysco.TimeStages.proxy.CommonProxy;
import com.Mrbysco.TimeStages.util.TimeHelper;

import net.darkhax.bookshelf.lib.LoggingHelper;
import net.darkhax.bookshelf.util.PlayerUtils;
import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.darkhax.gamestages.capabilities.PlayerDataHandler.IStageData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:crafttweaker;;required-after:gamestages@[1.0.71,);")
public class TimeStages {
	
	@Instance(Reference.MOD_ID)
	public static TimeStages instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static final LoggingHelper LOG = new LoggingHelper("Time Stages");
		
	public static ArrayList<StageInfo> timers = new ArrayList<>();
	
	private static StageInfo timer_info;
	
	public static void addTimerInfo(String stage, String nextStage, int time, String amount, boolean removal)
	{
		// Check if the info doesn't already exist
		timer_info = new StageInfo(stage, nextStage, time, amount, removal);
		if(timers.contains(timer_info))
			return;
		else
			timers.add(timer_info);
	}
	
    @EventHandler
    public void Preinit(FMLPreInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void livingUpdate(TickEvent.PlayerTickEvent event)
    {   
    	if (event.phase.equals(TickEvent.Phase.START) && event.side.isServer())
		{
    		if (PlayerUtils.isPlayerReal(event.player)) {
                final EntityPlayer player = (EntityPlayer) event.player;
                
                if (player.isCreative())
                    return;
                
                for (StageInfo info : this.timers) {
                	if (info.getStage().isEmpty())
                		return;
                	
                	final boolean removal = info.isRemoval();
                	final IStageData stageData = PlayerDataHandler.getStageData(player);

                	final String stage = info.getStage();
                	final String nextStage = info.getNextStage();
                	final int time = TimeHelper.getProperTime(info.getTime(), info.getAmount());
                	final String amount = info.getAmount();
                	
                	if (removal)
                	{
                		if(stageData.hasUnlockedStage(stage))
                		{
                			int theTimer = info.timer++;
                    		System.out.println("Timer going strong" + theTimer);
                			if(theTimer == time)
        			        {
                        		theTimer = 0;
                        		info.timer = 0;
                        		
                    			stageData.lockStage(stage);
                    			player.sendMessage(new TextComponentString("Stage " + stage + " has been locked."));;
        			        }
                		}
                		else
                		{
                   			if (info.timer > 0)
                   			info.timer = 0;
                		}
                	}
                	else
                	{
                		if (stageData.hasUnlockedStage(stage) && !stageData.hasUnlockedStage(nextStage))
                    	{
                			int theTimer = info.timer++;
                    		System.out.println("Timer going strong" + theTimer);
                    		if(theTimer == time)
        			        {
                        		theTimer = 0;
                        		info.timer = 0;
                        		
                    			stageData.unlockStage(nextStage);
                    			player.sendMessage(new TextComponentString("You unlocked stage " + nextStage + "!"));;
        			        }
                    	}
                		else
                		{
                			if (info.timer > 0)
                			info.timer = 0;
                		}
                	}
                }
        	}
    	}
    }
}
