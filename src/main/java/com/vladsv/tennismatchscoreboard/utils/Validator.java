package com.vladsv.tennismatchscoreboard.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Validator {

    public String getValidatedWinnerId(String id) {
        if (!id.matches("[0-9]")) {
            throw new IllegalArgumentException("Invalid winner id");
        }
        return id;
    }

    public void validatePlayerNames(HttpServletRequest req, HttpServletResponse resp, String firstPlayerName, String secondPlayerName) throws ServletException, IOException {
        if (firstPlayerName.equals(secondPlayerName)) {
            req.setAttribute("error", "Player names cannot be the same!");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
}
