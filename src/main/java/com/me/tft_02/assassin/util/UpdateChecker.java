package com.me.tft_02.assassin.util;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;

import com.me.tft_02.assassin.Assassin;
import com.me.tft_02.assassin.config.Config;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UpdateChecker {
    private UpdateChecker() {}

    public static boolean updateAvailable() throws Exception {
        String checkType = Config.getInstance().getPreferBeta() ? "latest" : "release";
        String version = Assassin.p.getDescription().getVersion();
        InputStreamReader isr;

        try {
            isr = new InputStreamReader(new URL("http://api.bukget.org/api2/bukkit/plugin/assassin/" + checkType).openStream());
        }
        catch (UnknownHostException e) {
            return false;
        }

        try {
            Object o = new JSONParser().parse(isr);

            if (!(o instanceof JSONObject)) {
                return false;
            }

            JSONObject versions = (JSONObject) ((JSONObject) o).get("versions");
            String newVersion = (String) versions.get("version");

            String[] oldTokens = version.replaceAll("(?i)(-)(.+?)(-)", "-").split("[.]|-b");
            String[] newTokens = newVersion.replaceAll("(?i)(-)(.+?)(-)", "-").split("[.]|-b");

            for (int i = 0; i < 4; i++) {
                Integer newVer = Integer.parseInt(newTokens[i]);
                Integer oldVer;

                try {
                    oldVer = Integer.parseInt(oldTokens[i]);
                }
                catch (NumberFormatException e) {
                    Assassin.p.getLogger().warning("Could not get information about this Assassin version; perhaps you are running a custom one?");
                    return false;
                }

                if (oldVer < newVer) {
                    return true;
                }
            }

            return false;
        }
        catch (ParseException e) {
            return false;
        }
        finally {
            isr.close();
        }
    }
}
