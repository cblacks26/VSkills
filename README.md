Skills
======

This is my first bukkit plugin that I have made to help me learn Java

Skills adds Skills like Mining and Building that you can level up with. Every time you level up you get closer to ranking up. Currently Ranks are shown in the player prefix's.
Rank names can be changed to whatever is suitable for your server. The skills are configurable to allow you to decide how players can gain xp. When players gain xp they also earn
money based off of the BasePay times the xp gained. Skills are not the only thing that is included in this plugin. There is a player stats system that keeps track of the number
of Kills, Deaths, and the players K/D ratio. Currently this plugin only supports SQLite but I hope to add MySQL in the future. This plugin does require Vault, so if chat,
permissions, or economy doesn't work you either don't have vault or don't have a plugin that is supported by Vault.

Skills:
- Digger : Digging with a Shovel
- Miner : Mining with a Pickaxe
- Logger : Chooping down trees with an Axe
- Builder : Placing blocks
- Hunter : Killing players, mobs, and animals
- Farmer : Planting seeds and using a hoe

Commands:
- MOTD:
    - description: Displays the message of the day
    - usage: /Motd  
- Leaderboards:
    - description: Displays the top 8 Player's Kills, Deaths, and K/D Ratio, Rank, Levels
    - usage: /Leaderboards <options>   
- EXP:
    - description: Displays the Players Rank, Skill Level, and Exp to next Level
    - usage: /Exp [player]
- Stats:
    - description: Displays the Players Kills, Deaths, and K/D Ratio
    - usage: /Stats [player]
    
Permissions:
- skills.basic:
    - description: Gives the basic commands
    - children:
        - skills.exp
        - skills.motd
        - skills.stats
        - skills.leaderboards
- skills.mod
    - description: Gives commands for moderators
    - children:
        - skills.exp.others
        - skills.stats.others
- skills.exp.others:
    - description: Displays the player designated's Rank, Levels, and Exp to next Level
    - default: op
    - children:
        - skills.exp
- skills.stats.others:
    - description: Displays the Player designated's Kills, Deaths, and K/D Ratio
    - default: op
    - children:
        - skills.exp
- skills.leaderboards:
    - description: Displays the top 8 Player's Kills, Deaths, and K/D Ratio, Rank, Levels
    - default: true
- skills.exp:
    - description: Displays the player's Rank, Levels, and Exp to next Level
    - default: true
- skills.stats:
    - description: Displays the Player's Kills, Deaths, and K/D Ratio
    - default: true
- skills.motd:
    - description: Displays the message of the day
    - default: true
        
        
Things I hope to add to the plugin:
- MySQL support
- Special Abilities for Skills
- Token system for Ranking up
- More Skills
- Better system for Skills