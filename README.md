# RayosX2 - Temporary X-Ray Vision Plugin

**RayosX2** is a lightweight and customizable Minecraft plugin that grants players temporary X-Ray vision when consuming a specific item (e.g., a golden apple). It allows players to momentarily see valuable ores and hidden blocks by removing non-valuable blocks around them for a brief period.

## 🔧 Features

- 🔍 Temporarily reveals valuable ores by removing surrounding blocks.
- ⏱️ Fully configurable radius, duration, and cooldown.
- 🛡️ Includes permission checks to control usage.
- 🌐 Multi-language support (Spanish and English).
- 🧪 Reload configuration and message files without restarting the server.
- 🚨 Built-in update checker with console and player notifications.

## 📦 Installation

1. Download the latest `.jar` file from the [Releases](https://github.com/yourusername/RayosX2/releases) section or [SpigotMC](https://www.spigotmc.org/resources/).
2. Place the `.jar` in your server’s `plugins` folder.
3. Start the server.
4. Configuration and message files will be automatically generated in `/plugins/RayosX2/`.

## 🛠 Configuration

**`config.yml`**
```yaml
radius: 10
duration-seconds: 5
cooldown-seconds: 30
trigger-item: GOLDEN_APPLE
Options:

radius: The range around the player where non-valuable blocks will be temporarily removed.

duration-seconds: How long the effect lasts (in seconds).

cooldown-seconds: Delay before a player can use the effect again.

trigger-item: The item a player must consume to activate the ability.

🌐 Language Support
Language files:

messages_es.yml (Spanish)

messages_en.yml (English)

Add more languages by duplicating and translating existing files.

🎮 Permissions
Permission	Description
rayosx2.use	Allows a player to use the X-Ray ability
rayosx2.reload	Allows reloading the plugin config/messages
rayosx2.notify	Receives update notifications on join
rayosx2.update	Permission to see update alerts

💬 Commands
Command	Description
/rayosx2reload	Reloads the config and messages

✅ Compatibility
Minecraft: 1.16+ (tested on latest versions)

Server types: Paper, Spigot, Folia

🚀 Update Checker
RayosX2 checks for updates automatically and notifies operators or players with permission on login.

📜 License
MIT License - See the LICENSE file for more information.

👤 Author
Developed with ❤️ by TheGhostor
If you enjoy the plugin, consider ⭐ starring the repo!

📥 Support & Feedback
GitHub Issues for bug reports and suggestions.

Discord (if available) or SpigotMC for community help.
