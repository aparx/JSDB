package com.github.mindcubr.jsdb.deserialize.siege;

import com.google.gson.annotations.SerializedName;

/**
 * This class represents the "stats" tree within the returning
 * JSON information about a certain user and player.
 * <p>The stats tree contains only statistical information about
 * the Game itself.
 * <p>This class is part of the deserialization process
 * of the returning http post requests that are being used
 * to get the statistics of players and users.
 *
 * @author mindcubr
 * @since 1.0-0.1
 */
public class SiegeStats {

    /**
     * The seasonal statistics of this user.
     */
    private SiegeStats.SeasonalData seasonal;

    /**
     * The general statistics of this user.
     * <p>So the actual <em>overall</em> statistics.
     */
    private SiegeStats.General general;

    /**
     * The progression statistics of this user, including
     * <em>Alphapacks</em>, <em>level</em> and <em>exp</em>.
     */
    private SiegeStats.Progression progression;

    /**
     * The general {@link Shared shared} general Ranked statistics.
     */
    private SiegeStats.Shared ranked;

    /**
     * The general {@link Shared shared} general Casual statistics.
     */
    private SiegeStats.Shared casual;

    public SeasonalData getSeasonal() {
        return seasonal;
    }

    /**
     * The general statistics of this user.
     * <p>So the actual <em>overall</em> statistics.
     */
    public General getGeneral() {
        return general;
    }

    /**
     * The progression statistics of this user, including
     * <em>Alphapacks</em>, <em>level</em> and <em>exp</em>.
     */
    public Progression getProgression() {
        return progression;
    }

    /**
     * The general {@link Shared shared} general Ranked statistics.
     */
    public Shared getRanked() {
        return ranked;
    }

    /**
     * The general {@link Shared shared} general Casual statistics.
     */
    public Shared getCasual() {
        return casual;
    }

    @Override
    public String toString() {
        return "Player{" +
                "seasonal=" + seasonal +
                ", general=" + general +
                ", progression=" + progression +
                ", ranked=" + ranked +
                ", casual=" + casual +
                '}';
    }

    /**
     * Seasonal package used for deserialization.
     * This class contains only seasonal statistics and data.
     */
    public static class SeasonalData {

        /**
         * Seasonal "Ranked" game mode statistics.
         */
        private Seasonal ranked;

        /**
         * Seasonal "Casual" game mode statistics.
         */
        private Seasonal casual;

        /**
         * Seasonal "Ranked" game mode statistics.
         */
        public Seasonal getRanked() {
            return ranked;
        }

        /**
         * Seasonal "Casual" game mode statistics.
         */
        public Seasonal getCasual() {
            return casual;
        }

        @Override
        public String toString() {
            return "SeasonalData{" +
                    "ranked=" + ranked +
                    ", casual=" + casual +
                    '}';
        }

    }

    /**
     * The progression class contains progression
     * statistics, so which can also be seen as a smaller
     * part of the {@link General} statistics.
     */
    public static class Progression {

        private int level;

        @SerializedName("lootbox_probability")
        private int chance;

        private int xp;

        /**
         * Returns the actual overall level of this player.
         *
         * @see #getXP()
         */
        public int getLevel() {
            return level;
        }

        /**
         * The chance to get an new Alphapack
         */
        public int getChance() {
            return chance;
        }

        /**
         * Returns the number of XP the player has.
         */
        public int getXP() {
            return xp;
        }

        @Override
        public String toString() {
            return "Progression{" +
                    "level=" + level +
                    ", chance=" + chance +
                    ", xp=" + xp +
                    '}';
        }

    }

    /**
     * The general class contains overall statistics of a user.
     */
    public static class General extends Shared {

        private int revives;

        private int melees;

        private int penetrations;

        private int assists;

        private int bulletshit;

        private int bulletsfired;

        private int headshots;

        /**
         * The distance travelled
         */
        @SerializedName("distancetravelled")
        private long travelled;

        /**
         * Barricades deployed
         */
        @SerializedName("barricadedeployed")
        private int barricades;

        /**
         * Reinforces deployed
         */
        @SerializedName("reinforcementdeploy")
        private int reinforces;

        /**
         * Suicides committed
         */
        @SerializedName("suicide")
        private int suicides;

        @SerializedName("dbno")
        private int knocks;

        @SerializedName("dbnoassists")
        private int knockAssists;

        @SerializedName("gadgetdestroy")
        private int gadgetsDestroyed;

        @SerializedName("blindkills")
        private int blindKills;

        /**
         * Returns the amounts of player this player has revived yet.
         */
        public int getRevives() {
            return revives;
        }

        /**
         * Returns the amount of meele kills this player has made.
         */
        public int getMelees() {
            return melees;
        }

        /**
         * Number of kills that were caused through penetrating bullets,
         * such as wall-bangs, even general objects where no clear
         * line of sight is given.
         */
        public int getPenetrations() {
            return penetrations;
        }

        /**
         * Returns the amount of assists this player has.
         */
        public int getAssists() {
            return assists;
        }

        /**
         * The amount of bullets that actually hit someone.
         */
        public int getBulletsHit() {
            return bulletshit;
        }

        /**
         * The amount of bullets fired.
         */
        public int getBulletsfired() {
            return bulletsfired;
        }

        /**
         * Returns the amount of bullets that were instantaneous headshots.
         */
        public int getHeadshots() {
            return headshots;
        }

        /**
         * Returns the distance travelled in metres.
         */
        public long getTravelled() {
            return travelled;
        }

        /**
         * Returns the number of barricades the player has deployed.
         */
        public int getBarricades() {
            return barricades;
        }

        /**
         * Returns the number of reinforces the player has deployed.
         */
        public int getReinforces() {
            return reinforces;
        }

        /**
         * Returns the number of suicides the player committed.
         */
        public int getSuicides() {
            return suicides;
        }

        /**
         * Returns how many players this player has knocked so far.
         * <p>An alias word for this is <em>"dbno"</em>.
         */
        public int getKnocks() {
            return knocks;
        }

        /**
         * Returns the amount of assists that resulted in an knockout.
         */
        public int getKnockAssists() {
            return knockAssists;
        }

        /**
         * Returns the amount of gadgets that this player destroyed.
         */
        public int getGadgetsDestroyed() {
            return gadgetsDestroyed;
        }

        /**
         * Returns the amount of kills where the player was blinded
         * or not able to see the enemy in general.
         * <p>Do not to be confused with {@link #getPenetrations()}.
         * Whereas this is mainly meant to being flashed and <em>not</em>
         * something like wall-bangs.
         */
        public int getBlindKills() {
            return blindKills;
        }

        @Override
        public String toString() {
            return "General{" +
                    "revives=" + revives +
                    ", melees=" + melees +
                    ", penetrations=" + penetrations +
                    ", assists=" + assists +
                    ", bulletshit=" + bulletshit +
                    ", bulletsfired=" + bulletsfired +
                    ", headshots=" + headshots +
                    ", travelled=" + travelled +
                    ", barricades=" + barricades +
                    ", reinforces=" + reinforces +
                    ", suicides=" + suicides +
                    ", knocks=" + knocks +
                    ", knockAssists=" + knockAssists +
                    ", gadgetsDestroyed=" + gadgetsDestroyed +
                    ", blindKills=" + blindKills +
                    '}';
        }

    }

    /**
     * The shared statistics are statistics that are contained
     * in overall and seasonal data.
     */
    public static class Shared {

        private int kills;

        private int deaths;

        private int wins;

        private int losses;

        @SerializedName("matchesplayed")
        private int matches;


        @SerializedName("timeplayed")
        private long timePlayed;

        /**
         * Returns the kills of this player in this game mode.
         *
         * @apiNote Note, as this is a {@link Shared} instance,
         * the kills are different between the <em>seasonal</em>
         * and <em>general (overall)</em> statistics and values.
         */
        public int getKills() {
            return kills;
        }

        /**
         * Returns the deaths of this player in this game mode.
         *
         * @apiNote Note, as this is a {@link Shared} instance,
         * the deaths are different between the <em>seasonal</em>
         * and <em>general (overall)</em> statistics and values.
         */
        public int getDeaths() {
            return deaths;
        }

        /**
         * A new measured Kills Deaths Ratio per call,
         * that is not saved as an attribute but calculated
         * when invoked.
         * <p>The Kills-To-Death-Ratio (K/D) describes the potential and
         * skill a player has to make more or less kills than deaths.
         * <p>The formula for the calculation of the so called <em>K/D</em>
         * is equal to the following:
         * <pre><code>
         *     Kills / Deaths
         * </code></pre>
         * <p><em>{@link #getKills() Kills} divided by {@link #getDeaths() Deaths}</em>
         *
         * @see #getKills()
         * @see #getDeaths()
         */
        public double getKD() {
            double kills = getKills();
            double deaths = getDeaths();

            //To avoid NAN as an output value
            if (kills < 1)
                return 0;

            //To avoid Infinity as an output value
            if (deaths < 1)
                return kills;

            //Return the actual kills-to-deaths ratio
            return kills / deaths;
        }

        /**
         * Returns the wins of this player in this game mode.
         *
         * @apiNote Note, as this is a {@link Shared} instance,
         * the wins are different between the <em>seasonal</em>
         * and <em>general (overall)</em> statistics and values.
         */
        public int getWins() {
            return wins;
        }

        /**
         * Returns the losses of this player in this game mode.
         *
         * @apiNote Note, as this is a {@link Shared} instance,
         * the losses are different between the <em>seasonal</em>
         * and <em>general (overall)</em> statistics and values.
         */
        public int getLosses() {
            return losses;
        }

        /**
         * Returns the matches of this player in this game mode.
         *
         * @apiNote Note, as this is a {@link Shared} instance,
         * the matches are different between the <em>seasonal</em>
         * and <em>general (overall)</em> statistics and values.
         */
        public int getMatches() {
            return matches;
        }

        /**
         * Returns the time played in seconds.
         *
         * @apiNote Note, as this is a {@link Shared} instance,
         * there is no variable for the time played in the seasonal
         * statistics, that is why you should not use this method
         * when you are calling the seasonal statistics, as the number
         * will always be <em>0</em>.
         */
        public long getTimePlayed() {
            return timePlayed;
        }

        @Override
        public String toString() {
            return "Shared{" +
                    "kills=" + kills +
                    ", deaths=" + deaths +
                    ", wins=" + wins +
                    ", losses=" + losses +
                    ", matches=" + matches +
                    ", timePlayed=" + timePlayed +
                    '}';
        }

    }

    /**
     * Seasonal stats that are deeply detailed to provide a
     * big amount of data for the end-user.
     * <p>This statistics only be valid until a new season
     * comes out, then they will be reset and the statistics
     * will begin from the ground up again.
     */
    public static class Seasonal extends Shared {

        private int mmr;

        private int season;

        private double skill_stdev;

        @SerializedName("max_rank")
        private int maxRank;

        @SerializedName("max_mmr")
        private int maxMMR;

        private int abandons;

        @SerializedName("last_match_mmr_change")
        private int lastChangeOfMMR;

        @SerializedName("top_rank_position")
        private int topRankPosition;

        @SerializedName("last_match_result")
        private int lastMatchResult;

        @SerializedName("next_rank_mmr")
        private int nextRankMMR;

        @SerializedName("update_time")
        private long updateTime;

        private boolean noMatchesPlayed;

        private boolean banned;

        /**
         * Returns the current MMR of the player.
         */
        public int getMMR() {
            return mmr;
        }

        /**
         * Returns the number of Season this statistics
         * is measured in.
         */
        public int getSeason() {
            return season;
        }

        //TODO: what is this?

        /**
         * Returns the uncertainty of the matchmaking system sigma (σ).
         * <p>The estimation of a skill is probabilistic. In general,
         * the more games are played, the more information the matchmaking
         * has, and the more confident it is about this estimation.
         * This confidence is represented by the uncertainty value sigma (σ).
         * The lower the uncertainty, the higher the confidence.
         * This uncertainty is returned as a <em>skill_stdev</em> value.
         *
         * @see <a href="https://www.ubisoft.com/en-gb/game/rainbow-six/siege/news-updates/4hShcX2HZTG2ttIi3IIN9Y/matchmaking-rating">
         * (Source) Read Ubisoft's Blogpost about the Matchmaking Rating System
         * </a>
         */
        public double getMatchmakingSigma() {
            return skill_stdev;
        }

        /**
         * The max rank as an integer.
         */
        public int getMaxRank() {
            return maxRank;
        }

        /**
         * The maximum MMR of this player in this mode.
         *
         * @apiNote Note that the Casual game mode might not have
         * a maximum MMR set, so in context with a casual game mode,
         * zero is returned. There are some cases, when the max
         * MMR is defined (at least in the future) and sometimes they don't
         * display it.
         */
        public int getMaxMMR() {
            return maxMMR;
        }

        /**
         * The abandons the player has.
         * <p>Abandons are matches, that the player left and
         * not returned to.
         *
         * @apiNote Cancellations seem to not count.
         */
        public int getAbandons() {
            return abandons;
        }

        /**
         * The last MMR difference.
         */
        public int getLastChangeOfMMR() {
            return lastChangeOfMMR;
        }

        /**
         * The top position of the player.
         *
         * @apiNote especially useful for Champion ranked players.
         */
        public int getTopRankPosition() {
            return topRankPosition;
        }

        /**
         * Returns the last match result, whether it is won or not.
         */
        public int getLastMatchResult() {
            return lastMatchResult;
        }

        /**
         * Returns the next required MMR in order to rank up.
         */
        public int getNextRankMMR() {
            return nextRankMMR;
        }

        /**
         * The time this statistics were updated the last time.
         */
        public long getUpdateTime() {
            return updateTime;
        }

        /**
         * Whether the player has no matches played.
         *
         * @apiNote Seems like this feature is not useful,
         * as it is just for a simpler way of rendering and
         * checking for matches on websites.
         */
        public boolean isNoMatchesPlayed() {
            return noMatchesPlayed;
        }

        /**
         * Whether the player is banned from this game mode.
         */
        public boolean isBanned() {
            return banned;
        }

        @Override
        public String toString() {
            return "Seasonal{" +
                    "mmr=" + mmr +
                    ", season=" + season +
                    ", skill_stdev=" + skill_stdev +
                    ", maxRank=" + maxRank +
                    ", maxMMR=" + maxMMR +
                    ", abandons=" + abandons +
                    ", lastChangeOfMMR=" + lastChangeOfMMR +
                    ", topRankPosition=" + topRankPosition +
                    ", lastMatchResult=" + lastMatchResult +
                    ", nextRankMMR=" + nextRankMMR +
                    ", updateTime=" + updateTime +
                    ", noMatchesPlayed=" + noMatchesPlayed +
                    ", banned=" + banned +
                    '}';
        }

    }

}
