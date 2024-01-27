# FoxAddition
An anti-cheat plugin designed to prevent specific types of hacks. It can be used in conjunction with other anti-cheat plugins, enhancing hack detection on your server with additional checks.

### FEATURES
- **Support for most versions:** FoxAddition aims to be designed to support Spigot servers (and forks) from older versions like 1.7.2 to the latest versions of Minecraft. Compatibility with FOLIA is unavailable for the moment; this can be added in a future.
- **Extensive and customizable configuration:** We strive to give users the ability to create their configuration freely, allowing them to edit possible values. Users can even disable checks in specific worlds. You can review the documentation section for a preview of the files.
- **Support for Bedrock players:** The plugin is designed to be compatible with and detect Bedrock players (if your server uses GeyserMC). This functions properly when Floodgate is detected on your server, allowing you to enable or disable checks for specific editions. If Floodgate is not in use, the player in Bedrock will be treated if it were Java, and the plugin will continue to function and detect accordingly. In versions 1.1.5+ of FoxAddition you can configure a prefix option to determine a bedrock player, this can be perfect in case you configured Floodgate in a Bungeecord.
- **Logging system:** Log all alerts received when a player fails any checks; this is stored in the 'logs' folder within the plugin folder with a txt file. This is a feature that can be activated or deactivated in the configuration. You can check 'Some information.' below for more details.
- **Compatibility with various plugins:** The plugin is compatible with other plugins to provide fixes for false positives or enhance your experience with FoxAddition. This is automatically detected upon plugin initiation, and it can be deactivated in the configuration in case of any issues. Below, you will find a list of plugins currently compatible with FoxAddition and their respective uses.
  - **ExecutableItems** - Used to fix some false flags in some checks in certain types of attributes/enchant/items. [Check this plugin here](https://www.spigotmc.org/resources/77578/)
  - **DiscordSRV** - Used to send alerts in a specify discord channel with your bot in this plugin, you can change the messages in the configuration file. [Check this plugin here](https://www.spigotmc.org/resources/18494/)
  - **PlaceholderAPI** - Used to allow to the plugin in use placeholders in any part, like in the list of commands to execute in certain vls or in the messages. [Check this plugin here](https://www.spigotmc.org/resources/6245/)
  - **ProtocolLib** - Used to allow the usage of packets to use in checks that only works with this plugin and add some additional checks. *(Probably in a future, this can be a requeriment to improve the stability and performance of the anti-cheat in using packets.)* [Check this plugin here](https://www.spigotmc.org/resources/1997/)
  - **VeinMiner** - Used to fix some false flags in some checks when the mode of this plugin is enabled. [Check this plugin here](https://www.spigotmc.org/resources/12038/)

**Visit in**:
- [SpigotMC.org](https://www.spigotmc.org/resources/111260/)
- [Modrinth.com](https://modrinth.com/plugin/foxaddition)
