VSkills
======

This is my first bukkit plugin that I have made to help me learn Java

http://dev.bukkit.org/bukkit-plugins/vskills/

VSkills is a plugin that provides skills that you can level up, all inside a master ranking system. VSkills also keeps track of your kills deaths and kd ratio. The Skills allow you to make money based on two factors 1: the xp given for the event 2: the players money multiplier. When players rank up they are awarded tokens. The tokens can currently only be used on increasing the money multiplier.

Skills:
- Acrobatics : Jumping and falling through the sky
- Archery : Killing mobs and players with a bow and arrow
- Axes : Killing mobs, players and chopping down trees
- Hoe : Using your hoe for crops and even killing
- Pickaxe : Mining and killing
- Shovel : digging and killing
- Sword : killing
- Unarmed : digging, mining, woodcutting, hunting, farming

Commands:
  - VGod:
    - description: Gives the specified player god mode
    - usage: /VGod <player>
  - VBoard:
    - description: Toggles the types of scoreboards
    - usage: /VBoard [XP, Level, Stats, Power]
  - VPower:
    - description: Changes the players current power and max power
    - usage: /VPower [refresh, set] <player> <power>
  - VReset:
    - description: Resets all of the players data
    - usage: /VReset <player>
  - VSkills:
    - description: Show the VSkills Help menu
    - usage: /VSkills
  - VSave:
    - description: Saves all the loaded player's data
    - usage: /VSave
  - VStats:
    - description: Shows different leaderboards
    - usage: /VStats [options]
  - VTokens:
    - description: Uses the players reward tokens
    - usage: /VTokens [money]
    
Permissions:
- VSkills.board
- VSkills.god
- VSkills.reset
- VSkills.power
- VSkills.save
- VSkills.stats
- VSkills.tokens
        
Dependencies:
- Vault
        
Things I hope to add to the plugin:
- More Skills
