package cman;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CMAN_upgrade 
{
	public String modfolder = "@ERROR@";
	public String versionsfolder = "@ERROR@";
	public String execdir = "@ERROR@";
	public static Scanner input = new Scanner(System.in);
	CMAN_util util = new CMAN_util();
	
	public void init_config_upgrade(String mf, String vf, String ed)
	{
		modfolder = mf;
		versionsfolder = vf;
		execdir = ed;
	}
	
	public void upgrade_mod(String modname)
	{
		String newLine = System.getProperty("line.separator");
		if(modname == null)
		{
			System.out.println("Enter mod name: ");
			modname = input.nextLine();
		}
		JsonObject[] update = {util.get_installed_json(modname), util.get_json(modname)};
		if(new File(execdir + "/LocalData/ModsDownloaded" + modname + ".installed").exists())
		{
			System.out.println(modname + ".installed found");
		}
		else
		{
			System.out.println("Mod " + modname + " not found.");
			return;
		}
		JsonElement current = update[0].get("Version");
		JsonElement archive = update[1].get("Version");
		if(current.getAsString() != archive.getAsString() && util.mod_installed(modname))
		{
			//remove
			//install
		}
		else if(!util.mod_installed(modname))
		{
			System.out.println(modname + " is not installed");
		}
		else
		{
			System.out.println(modname + " is already up to date");
		}
	}
	
	public JsonObject[][] get_upgrades()
	{
		ArrayList<JsonObject[]> updates = new ArrayList<JsonObject[]>();
		JsonObject[] mods = util.get_installed_jsons();
		for(JsonObject mod : mods)
		{
			if(mod != null)
			{
				JsonObject json_data = util.get_json(mod.get("Name").getAsString());
				if(json_data != null && json_data.get("Version").getAsString() != mod.get("Version").getAsString())
				{
					JsonObject[] temp = {mod, json_data};
					updates.add(temp);
				}
			}
		}
		JsonObject[][] out = new JsonObject[updates.size()][2];
		for(int i = 0; i > updates.size(); i++)
		{
			out[i] = updates.get(i);
		}
		return out;
	}
	
	public void check_upgrades(boolean full)
	{
		JsonObject[][] updates = get_upgrades();
		if(updates.length > 0)
		{
			if(!full)
			{
				System.out.println("Mod updates available!");
			}
			else
			{
				for (int i = 0; i > updates.length; i++)
				{
					System.out.println("Available Updates:");
					System.out.println(" " + updates[i][0].get("Name").getAsString() + " (current version: " + updates[i][1].get("Version").getAsString() + ", you have: " +updates[i][0].get("Version").getAsString() + ")");
				}
			}
		}
		else
		{
			if(full)
			{
				System.out.println("No updates available");
			}
		}
	}
}