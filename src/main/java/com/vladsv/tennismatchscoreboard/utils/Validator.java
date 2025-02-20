package com.vladsv.tennismatchscoreboard.utils;

import java.util.UUID;

public class Validator {

    public String getValidWinnerId(String id) {
        if (!id.matches("[0-9]+")) {
            throw new IllegalArgumentException("Invalid winner id");
        }
        return id;
    }

    public UUID getValidUuid(String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            throw new IllegalArgumentException("Missing UUID parameter");
        }
        return UUID.fromString(uuid);
    }

    public String getValidPageNumber(String page) {
        if (!page.matches("[0-9]+")) {
            throw new IllegalArgumentException("Invalid page number");
        }
        return page;
    }

    public String getValidPlayerName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Missing player name");
        }
        return name;
    }

    public String getValidFilterName(String filterByPlayerName) {
        if (filterByPlayerName == null) {
            throw new IllegalArgumentException("Null is not a valid filter by player");
        }
        return filterByPlayerName;
    }

    public void checkForUniqueNames(String firstPlayerName, String secondPlayerName) {
        if (firstPlayerName.equalsIgnoreCase(secondPlayerName)) {
            throw new IllegalArgumentException("Duplicate player names");
        }
    }

    public void verifyPageNumber(String page, int quantityOfPages) {
        if (page.equals("1") && quantityOfPages == 0) return;
        if (Integer.parseInt(page) > quantityOfPages) {
            throw new IllegalArgumentException("Invalid page number");
        }
    }
}
