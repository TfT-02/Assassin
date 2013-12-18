package com.me.tft_02.assassin.database;

import com.me.tft_02.assassin.Assassin;
import com.me.tft_02.assassin.config.Config;
import com.me.tft_02.assassin.datatypes.database.DatabaseType;

public class DatabaseManagerFactory {
    private static Class<? extends DatabaseManager> customManager = null;

    public static DatabaseManager getDatabaseManager() {
        if (customManager != null) {
            try {
                return createCustomDatabaseManager(customManager);
            }
            catch (Exception e) {
                Assassin.p.debug("Could not create custom database manager");
                e.printStackTrace();
            }
            catch (Throwable e) {
                Assassin.p.debug("Failed to create custom database manager");
                e.printStackTrace();
            }
            Assassin.p.debug("Falling back on " + (Config.getInstance().getUseMySQL() ? "SQL" : "Flatfile") + " database");
        }

        return Config.getInstance().getUseMySQL() ? new SQLDatabaseManager() : new FlatfileDatabaseManager();
    }

    /**
     * Sets the custom DatabaseManager class for mcMMO to use. This should be
     * called prior to mcMMO enabling.
     * <p/>
     * The provided class must have an empty constructor, which is the one
     * that will be used.
     * <p/>
     * This method is intended for API use, but it should not be considered
     * stable. This method is subject to change and/or removal in future
     * versions.
     *
     * @param clazz the DatabaseManager class to use
     *
     * @throws IllegalArgumentException if the provided class does not have
     *                                  an empty constructor
     */
    public static void setCustomDatabaseManagerClass(Class<? extends DatabaseManager> clazz) {
        try {
            clazz.getConstructor((Class<?>) null);
            customManager = clazz;
        }
        catch (Throwable e) {
            throw new IllegalArgumentException("Provided database manager class must have an empty constructor", e);
        }
    }

    public static Class<? extends DatabaseManager> getCustomDatabaseManagerClass() {
        return customManager;
    }

    public static DatabaseManager createDatabaseManager(DatabaseType type) {
        switch (type) {
            case FLATFILE:
                return new FlatfileDatabaseManager();

            case SQL:
                return new SQLDatabaseManager();

            default:
                return null;
        }
    }

    //TODO: Why is clazz never used here?
    public static DatabaseManager createCustomDatabaseManager(Class<? extends DatabaseManager> clazz) throws Throwable {
        return customManager.getConstructor((Class<?>) clazz).newInstance((Object[]) null);
    }
}
