package dev.excl.mc.fivecore.database;

import java.util.UUID;

public class PlayerData {

    private UUID uuid;
    private String username;
    //Other playerData that should be tracked.

    public PlayerData(UUID uudi, String username) {

        this.uuid = uuid;
        this.username = username;

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
