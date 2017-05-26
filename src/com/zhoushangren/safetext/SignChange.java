//告示牌事件
package com.zhoushangren.safetext;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChange implements Listener{
	SafeText plugin;
	
	public SignChange(SafeText instance){
		plugin=instance;
	}
	
	@EventHandler
	public void PublicSignChangeEvent(SignChangeEvent event){
		if(!plugin.getConfig().getBoolean("enable")){
			return;
		}
		List<String> textarray=plugin.getConfig().getStringList("safetext");
		String msg="";
		for(int i=0;i<event.getLines().length;i++){
			if(i==event.getLines().length-1){
				msg=msg+event.getLines()[i];
			}else{
				msg=msg+event.getLines()[i]+"\r\n";
			}
		}
		for(int x=0;x<textarray.size();x++){
			if(msg.indexOf(textarray.get(x))!=-1){
				if(plugin.getConfig().getBoolean("block")){
					//拦截模式
					event.setCancelled(true);
					event.getPlayer().sendMessage(plugin.getConfig().getString("block_alert_color")+plugin.getConfig().getString("block_alert"));
					if(plugin.getConfig().getBoolean("block_damage")){
						//给予玩家伤害
						if(plugin.getConfig().getInt("block_damage_limit")>0 && event.getPlayer().getHealth()<plugin.getConfig().getInt("block_damage_limit")){
							//玩家生命过低，不进行伤害
							return;
						}else{
							event.getPlayer().damage(plugin.getConfig().getInt("block_damage_amount"));
							return;
						}
					}
				}else{
					//过滤模式
					msg=msg.replace(textarray.get(x), plugin.getConfig().getString("replace"));
				}
			}
		}
		String[] msgarr=msg.split("\r\n");
		for(int y=0;y<msgarr.length;y++){
			event.setLine(y, msgarr[y]);
		}
		return;
	}
}
