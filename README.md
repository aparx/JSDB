# JSDB as a library
JStatsDB (Java StatsDB) is a Java Library that allows developers to easily use the StatsDB.net Developer-API.<br>
Unit tested, well documented and supported will JSDB help developers along the way.<br><br>
This readme is separated into following sections:
1. [StatsDB - The Ultimate Platform](https://github.com/mindcubr/JSDB#statsdb---the-ultimate-platform)
2. [Tutorial in 3 Steps](https://github.com/mindcubr/JSDB#example-code)
  - [First Step]()
  - [Second Step]()
  - [Third Step]()
3. [The Wiki](https://github.com/mindcubr/JSDB#the-wiki---a-new-way-of-tutorial)

## StatsDB - the ultimate platform
[StatsDB](https://statsdb.net/) describe themselves as the ultimate gaming statistic tracking platform,<br>
as they provide a lot of data that can be useful for improving, developing and beyond.<br>
It is recommended to use their API as it is simple, clean, intuitive and online at any time.<br>
By creating an account and application on their developer site, it is easy to use the required<br>
credentials to get a stream of statistics containing the information wanted, via JSDB.<br>

## Example code
The Wiki explains exactly, why and how every step is taken and necessary.<br>
This example just consists of a code example, without that much explanation.<br>
It is suggested to not just copy the library and examples, but explore it!<br><br>
In the following code snippets we've created a complete program within the wiki.<br>
At the end of every code you will find the specific Wiki-Links where you can read<br>
further about how and why to create those steps.<br><br>

**1.** We need to create a token. This token will be required as a header called<br>
"Authorization" within any post request to the StatsDB-Servers. Fortunately you don't<br>
have to do that by yourself. Without that authorization, an error code _401: Unauthorized_ is returned.<br>
In this case a **JSDBTokenInvalid** exception would be thrown, later when fetching a user.<br>
```java
DBToken token = DBToken.encrypt("App-UserID", "App-UserPassword");
```
_Wiki links:_
[How to setup a token](https://github.com/mindcubr/JSDB/wiki/Getting-Started#creating-an-application-for-statsdbnet) | [DBToken - what is this?](https://github.com/mindcubr/JSDB/wiki/The-Main-Components#dbtoken)

**2.** We need to create a [GlobalConfig](https://github.com/mindcubr/JSDB/wiki/The-Main-Components#globalconfig), that is used to store sensitive data,<br>
such as the token and for the [DBBridge](https://github.com/mindcubr/JSDB/wiki/The-Main-Components#dbbridge) necessary components and attributes.<br>
```java
DBToken token = DBToken.encrypt("App-UserID", "App-UserPassword");
GlobalConfig config = GlobalConfig.withToken(token);
DBBridge bridge = DBBridge.create(config);
```
The [DBBridge](https://github.com/mindcubr/JSDB/wiki/The-Main-Components#dbbridge) is a main component, which instance is required for<br>
further methods and actions you want to execute and take place at.<br>
_Wiki links:_
[GlobalConfig](https://github.com/mindcubr/JSDB/wiki/The-Main-Components#globalconfig) | [DBBridge - what is this?](https://github.com/mindcubr/JSDB/wiki/The-Main-Components#dbbridge)

**3.** Okay. We're pretty far. Now we need to implement the actual fetcher.<br>
This part is pretty hard to explain in short terms. That is why you should
consider reading the whole part [within the Wiki about Fetchers here](https://github.com/mindcubr/JSDB/wiki/The-Fetcher).<br>
In short terms: A DBFetcher class is a parent root class, that consists of abstract and pre-coded methods<br>
which will be used for later analytic and access onto the StatsDB Developer-API features and functions.<br>
The returning parts of a post request are formatted as a JSON format, that will be deserialized into<br>
virtual JVM-Instances that you will finally use as finished and formatted versions of **User**s.<br>
As this deserialization process can vary from game to game, every game has its own fetcher.<br>
For this example we want the statistics of the game Rainbow: Six Siege.<br>
```java
R6DBFetcher fetcher = R6DBFetcher.withBridge(bridge);
try {
  SiegePlayer player = fetcher.fetchPlayerByName(Platform.PC, "proelothrower");
  //The ID of the ubisoft user behind the player
  String userID = player.getID();

  //The deserialized statistics
  SiegeStats stats = player.getStats();

  //The level of the player
  int level = stats.getProgression().getLevel();

  //Accessing seasonal statistics
  SiegeStats.SeasonalData seasonal = stats.getSeasonal();
  int rankedMMR = seasonal.getRanked().getMMR();
  int casualMMR = seasonal.getCasual().getMMR();

  System.out.printf("ID: %s%n+ Level: %d%n+ Ranked: %d MMR%n+ Casual: %d MMR%n",
        userID, level, rankedMMR, casualMMR);
} catch (JSDBFetchingException | JSDBTokenInvalid e) {
  System.err.println("Sadly, something went wrong during the fetching.");
  e.printStackTrace();
} catch (JSDBUserDoesNotExist exc) {
  System.err.println("The user has changed its name or doesn't exist.");
}
```
Voilà, we're finished.

## The Wiki - a new way of tutorial
This GitHub-Project contains a complete library that gives you an idea of how<br>
to create an application on statsdb, giving you the information required to integrate<br>
and use the library and how to finish and deploy such an application.<br>
It is suggested to read the complete wiki!

[Begin reading the Wiki!](https://github.com/mindcubr/JSDB/wiki)
