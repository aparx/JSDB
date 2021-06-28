# JSDB as a library
JStatsDB (Java StatsDB) is a Java Library that allows developers to easily use the StatsDB.net Developer-API.<br>
Unit tested, well documented and supported will JSDB help developers along the way.<br>

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
"Authorization" within any post request to the StatsDB-Servers. Without that authorization,
an error code _401: Unauthorized_ is returned.
```java
DBToken token = DBToken.encrypt("1703132411279978", "8ccd4fcae29021103178a1188023004d");
```
[How to setup a token](https://github.com/mindcubr/JSDB/wiki/Getting-Started#creating-an-application-for-statsdbnet) | [DBToken - was is this?](https://github.com/mindcubr/JSDB/wiki/The-Main-Components#dbtoken]

## The Wiki - a new way of tutorial
This GitHub-Project contains a complete library that gives you an idea of how<br>
to create an application on statsdb, giving you the information required to integrate<br>
and use the library and how to finish and deploy such an application.<br>
It is suggested to read the complete wiki!

[Begin reading the Wiki!](https://github.com/mindcubr/JSDB/wiki)
