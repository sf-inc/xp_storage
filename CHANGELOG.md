# Versions Changelog

Currently, every **stable** mod versions are available on **1.16.X** Minecraft versions.

* **X versions** are major update with lots of new content. Very rare
* **x.X versions** are either updates that add content or major bug fixes
* **x.x.X versions** are either small content update (language translation, new textures, ...) or bug fixes

## v1.3

* Server crash (added in v1.2) fix because of initializing something client only in server side

## v1.2
* You can brew the experience of your XP Book into XP Bottles
  * 100 experiences is taken for each bottles
  * 80-130% of the amount is effectively on the bottle
  * you brew it into mundane potions
* XP Bottles can have a nbt tag for a specific amount (see previous line)
* XP Bottles display a tooltip to tell the player if it's a normal one or not. If not, it also displays the XP amount

## v1.1.1
* Added russian language, thanks to *@Romz24*

## v1.1
* **IMPORTANT!** Invert the damage system: now a book not damaged is empty, while a full damaged book is full of 
  experience. Be aware of that if you update the mod in your world! Actually, I made this to avoid any issues about 
  crafting XP Books. Indeed, some mods can cause compatibility issue by crafting a not damaged book (so it was full of 
  xp, which was broken)
* Tooltips are now translated in Chinese
* Bug fix of experience duplication added in the 1.0 version

## v1.0
* Added two XP Books! You can now upgrade your XP Book so that it can store more experience
* New crafting recipes: the normal XP Book is now less expensive in order to be symmetrical with the upgraded versions
* Crafting system renew: now you can directly see in the crafting table the experience stored in your book. That means 
  for a first level one, it will appear empty. But for other levels, it will keep the experience already stored in your 
  previous XP Book
* Added tooltips: the book will tell you the maximum number of level you can store into it

## v0.4.1
* Updated the versioning system so that **ModUpdater** can check the newer version in CurseForge
* Added icons and contact, *that you can see with **ModMenu***
* Removed some files for better code

## v0.4
* You were able to take experience from tools if you had xp book on offhand, and could repair them

## v0.3: 
* The experience stored wasn't the current player experience but its score

## v0.2.3:
* Fix tentative of the xp duplication bug by rewriting important parts of the code

## v0.2.2:
* Plays the xp sound only for effective uses

## v0.2.1:
* Added Chinese language, thakns to *@Samekichi*
* Added French language
