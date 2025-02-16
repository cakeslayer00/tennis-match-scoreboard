package com.vladsv.tennismatchscoreboard.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

public class Validator {

    public String getValidWinnerId(String id) {
        if (!id.matches("[0-9]")) {
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

    public void validatePlayerNames(HttpServletRequest req, HttpServletResponse resp, String firstPlayerName, String secondPlayerName) throws ServletException, IOException {
        if (firstPlayerName.equals(secondPlayerName)) {
            req.setAttribute("error", "Player names cannot be the same!");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }

}
