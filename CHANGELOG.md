# Versions Changelog

* **X versions** are major update with lots of new content. Very rare
* **x.X versions** are either updates that add content or major bug fixes
* **x.x.X versions** are either small content update (language translation, new textures, ...) or bug fixes

## v1.5.10
* Fix crash on startup when number of xp book lowered

## v1.5.9
* Fix recipes issues (introduced in 1.21 port)

## v1.5.8
* Add Italian translation

## v1.5.7
* Fix compatibility issue with LevelZ (#24)
* Add Japanese translation

## v1.5.6
* Fix compatibility issue with LevelZ (#24)

## v1.5.6
* Port to 1.20.2

## v1.5.5
* Fix XP sound playing even though adding 0 experience, as discovering a recipe
  do (#23 & #25)

## v1.5.4
* Fix XP duplication glitch when brewing potion
  * The duplication appears if potions are quick moved into the brewing stand,
    they stack while they shouldn't, and XP is duplicated
  * The issue is within minecraft and the fix is not trivial when modding, so
    the workaround was to ignore slots with more than one potion

## v1.5.3
* Update mod API to let other mods know XP Books information (needed for XP Storage - Trinkets)
* Recipes are now discoverable via ingredients

## v1.5.2
* Port to 1.20.1
* Fix XP duplication glitch introduced in 1.5 and 1.5.1 (#22)
* Redesigned XP Books texture to match their ingredient
* XP Books now need Crystallized Lapis in their recipe instead of Lapis Lazuli
  * Crystallized Lapis is made of Lapis Lazuli and Amethyst Shard
  * This comes from XP Storage - Trinkets mod

## v1.5.1
* XP Books can't stack (again)

## v1.5
* Port to 1.19.3
* XP Books use CC api to store levels and experience (not damage anymore, incompatible with older versions)
* XP Bottles brew per level increment depending on XP Book level, giving possibility to have an exact amount
* XP Bottles can store up to a maximum capacity of level
* Fix vanilla experience adding (mojang bug)
* Readd books' parameters (fireproof and rarity)

## v1.4.3
* Reworked configs in tabs
* You can (de)activate features such as the number of XP Books wanted and the brewing feature
* You can now choose the percentage of xp a book gives you back per tier
* Same with bottles parameters: xp taken, and random bounds
* Added cosmetic configs for client
  * item bar color for each book
  * when books start to glint (percentage of xp)
  * book and bottles tooltips

## v1.4.2
* Fixed crash introduced in last update

## v1.4.1
* Using an XP Book now spawns experience orbs on the player instead of adding xp directly. By doing so, it's nicer and 
  the retrieved experience can repair your armor, etc
* Now XP Book can't get Mending or Unbreaking with anvil (they were able before). Even though it would be stupid to do 
  so, it's better that we can't do it at all

## v1.4
* You can now change the mod configs:
  * The capacity of each XP Books *(needs restart)*
  * The experience retrieved for XP Books use
  * The experience taken and retrieved for XP Bottles
  * You can change the configs directly in the mod menu

## v1.3.2
* The XP Book now retrieve only 90% of the experience by using it

## v1.3.1
* Fix a small graphic bug: sometimes when filling the XP Book, it seems you still have one experience on your xp bar 
while you don't have experience anymore

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

## v0.2.3
* Fix tentative of the xp duplication bug by rewriting important parts of the code

## v0.2.2
* Plays the xp sound only for effective uses

## v0.2.1
* Added Chinese language, thakns to *@Samekichi*
* Added French language
