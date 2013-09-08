VSkills
======

This is my first bukkit plugin that I have made to help me learn Java

http://dev.bukkit.org/bukkit-plugins/vskills/

VSkills is a plugin that provides jobs and skills that you can level up, all inside a master ranking system that will
be more developed in later releases. VSkills also keeps track of your kills deaths and kd ratio. The Jobs allow you to make
money based on two factors 1: the xp given for the event 2: the players money multiplier. When players rank up then are
awarded tokens. The tokens can currently only be used on increasing the money multiplier.

Jobs:
- Digger : Digging
- Miner : Mining
- Woodcutter : Chooping down trees
- Builder : Placing blocks
- Hunter : Killing players, mobs, and animals
- Farmer : Planting seeds and using a hoe

Skills:
- Archery : Killing mobs and players with a bow and arrow
- Axes : Killing mobs, players and chopping down trees
- Hoe : Using your hoe for crops and even killing
- Pickaxe : Mining and killing
- Shovel : digging and killing
- Sword : killing
- Unarmed : digging, mining, woodcutting, hunting, farming

Commands:
- VStats:
    - description: Displays the Players Kills, Deaths, and K/D Ratio
    - usage: /VStats [jobs, skills, stats]
- VTokens:
    - description: Allows you to spend your tokens gained on rank ups
    - usage: /VTokens <options>
- VReset:
    - description: Sets the given player's Exp, Level, Rank, and Stats
    - usage: /VReset <player>
- VGod:
    - description: Gives the specified player god mode
    - usage: /VGod <player>
    
Permissions:
- VSkills.stats
- VSkills.reset
- VSkills.tokens
- VSkills.god
        
        
Things I hope to add to the plugin:
- Special Abilities for Skills
- More Skills
