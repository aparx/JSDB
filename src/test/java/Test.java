import de.mindcubr.jsdb.Platform;
import de.mindcubr.jsdb.bridge.DBBridge;
import de.mindcubr.jsdb.bridge.DBToken;
import de.mindcubr.jsdb.bridge.config.GlobalConfig;
import de.mindcubr.jsdb.deserialize.siege.SiegePlayer;
import de.mindcubr.jsdb.deserialize.siege.SiegeStats;
import de.mindcubr.jsdb.exception.JSDBFetchingException;
import de.mindcubr.jsdb.fetch.R6DBFetcher;

/**
 * Small test application to test the functionalities
 * of the given JSDB-framework.
 *
 * @author mindcubr
 * @since 1.0-0.1
 */
public class Test {

    public static void main(String[] args) {
        DBToken token = DBToken.of("MTcwMzEzMjQxMTI3OTk3ODo4Y2NkNGZjYWUyOTAyMTEwMzE3OGExMTg4MDIzMDA0ZA==");
        GlobalConfig config = GlobalConfig.withToken(token);
        DBBridge bridge = DBBridge.create(config);
        R6DBFetcher fetcher = R6DBFetcher.withBridge(bridge);
        try {
            SiegePlayer player = fetcher.fetchPlayerByName(Platform.PC, "Spoit.GODSENT");
            SiegeStats stats = player.getStats();
            System.out.println("STATISTICS OF " + player.getName().toUpperCase());

            //The general statistics part
            SiegeStats.General general = stats.getGeneral();
            System.out.println("+ Time played: " + (general.getTimePlayed() / 3600.0) + "min");

            //The seasonal statistics part
            SiegeStats.SeasonalData seasonal = stats.getSeasonal();
            SiegeStats.Seasonal ranked = seasonal.getRanked();
            SiegeStats.Seasonal casual = seasonal.getCasual();
            System.out.println("+ Level: " + stats.getProgression().getLevel());

            System.out.println("Seasonal");

            //Display some seasonal and overall ranked statistics
            System.out.println("+ Ranked K/D: " + ranked.getKD());
            System.out.println("+ Ranked: " + ranked.getMMR() + "/" + ranked.getMaxMMR());

            //Display some seasonal and overall casual statistics
            System.out.println("+ Casual K/D: " + casual.getKD());
            System.out.println("+ Casual: " + casual.getMMR() + "/" + casual.getMaxMMR());
        } catch (JSDBFetchingException exc) {
            if (exc.isUserNotExisting()) {
                System.out.println("Spoit's name potentially changed!");
            }
        }
    }

}
