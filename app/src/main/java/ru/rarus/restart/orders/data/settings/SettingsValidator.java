package ru.rarus.restart.orders.data.settings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SettingsValidator {

    private static final String URL_REGEX = "\\b(https?|ftp|file)://[-A-Z0-9+&@#/%?=~_|!:,.;]*[-A-Z0-9+&@#/%=~_|]";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    private static final int CURRENCY_LENGTH_MINUS_ONE = 3;
    private static final String CURRENCY_REGEX = "^[^0-9]*$";
    private static final Pattern CURRENCY_PATTERN = Pattern.compile(CURRENCY_REGEX);


    public static boolean isValidUrl(String url) {
        try {
            Matcher matcher = URL_PATTERN.matcher(url);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public static boolean isValidCurrency(String currency) {
        Matcher matcher = CURRENCY_PATTERN.matcher(currency);
        return currency.length() <= CURRENCY_LENGTH_MINUS_ONE && matcher.matches();
    }
}
