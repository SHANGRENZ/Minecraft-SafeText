package com.zhoushangren.safetext;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import org.bukkit.Bukkit;
//import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class SafeText extends JavaPlugin{
	@Override
	public void onEnable(){
		//注册监听
		Bukkit.getPluginManager().registerEvents(new AsyncPlayerChat(this), this);
		Bukkit.getPluginManager().registerEvents(new SignChange(this), this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args){
		if(cmd.getName().equalsIgnoreCase("safetext")){
			switch(args[0]){
			case "add":
				if(args[1].equals("")){
					sender.sendMessage("不能添加空的关键字\\:");
					return true;
				}
				List<String> safetext_add=this.getConfig().getStringList("safetext");
				for(int x=0;x<safetext_add.size();x++){
					if(safetext_add.get(x).equals(args[1])){
						sender.sendMessage("关键字列表中已经有当前关键字了");
						return true;
					}
				}
				safetext_add.add(args[1]);
				this.getConfig().set("safetext", safetext_add);
				this.saveConfig();
				sender.sendMessage("成功添加关键字[ "+args[1]+" ]");
				break;
			case "del":
				if(args[1].equals("")){
					sender.sendMessage("不能删除空的关键字\\:");
					return true;
				}
				List<String> safetext_del=this.getConfig().getStringList("safetext");
				for(int i=0;i<safetext_del.size();i++){
					if(safetext_del.get(i).equals(args[1])){
						safetext_del.remove(i);
						this.getConfig().set("safetext", safetext_del);
						this.saveConfig();
						sender.sendMessage("成功删除关键字[ "+args[1]+" ]");
						return true;
					}
				}
				sender.sendMessage("列表中没有你要删除的关键字\\:");
				break;
			case "list":
				List<String> safetext_list=this.getConfig().getStringList("safetext");
				String list="关键字列表:[  ";
				for(int y=0;y<safetext_list.size();y++){
					list=list+safetext_list.get(y)+"  ";
				}
				list=list+"]";
				sender.sendMessage(list);
				break;
			case "help":
				sender.sendMessage("如何使用?\r\n/safetext add/del/list");
				break;
			default:
				sender.sendMessage("您输入的指令有误。");
			}
			return true;
		}else{
			return false;
		}
	}
}
