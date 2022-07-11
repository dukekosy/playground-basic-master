package com.smile.common;

import java.text.DateFormat;
import java.util.Locale;

/**
 * SmileConstants class used to maintain constants for this project
 *
 * @author Kosala Amarasinghe
 * @version 1.0
 */
public class SmileConstants {

    public static final String nameRegex = "[a-zA-Z]+";
    public static final String invalidName = "Last name must be characters only";

    public static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("en", "US"));

    public static final String END_POINT = "Patient";
}
