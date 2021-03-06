package com.gmail.mikeundead;

import java.io.IOException;
import java.util.logging.Logger;

import com.gmail.mikeundead.mcstats.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class PvPTitles extends JavaPlugin
{
    public Logger log;
    private RankCommand rankCommand;
	private DatabaseHandler databaseHandler;
	private Ranks ranks;
	private HandlePlayerPrefix handlePlayerPrefix;
	private LeaderBoardCommand ladder;
	
	public void onEnable()
	{			
		this.log = getLogger();
		this.databaseHandler = new DatabaseHandler(this);
		this.ranks = new Ranks(this.databaseHandler, this);
		this.rankCommand = new RankCommand(this.databaseHandler, this.ranks);
		this.handlePlayerPrefix = new HandlePlayerPrefix(this.databaseHandler, this.ranks, this);
		this.ladder = new LeaderBoardCommand(this);
		getServer().getPluginManager().registerEvents(handlePlayerPrefix, this);

		getCommand("rank").setExecutor(this.rankCommand);
		getCommand("ladder").setExecutor(this.ladder);
		
		this.log.info("PvPTitles has been enabled!");

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            this.log.info("MCStats load failed!");
        }
	}
	 
	public void onDisable()
	{ 
		this.log.info("PvPTitles has been disabled.");
	}
}
