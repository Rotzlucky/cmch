package services;

/**
 * Created by marcelsteffen on 27.08.16.
 */
public class LinkUtil {

    public static String getCharacterImagePath() {
        return "/files/images/characters/";
    }

    public static String getCharacterUploadPath() {
        return baseUploadPath() + "images/characters/";
    }

    public static String baseUploadPath() {
        return "/var/www/files/";
    }

    public static String getIssueImagePath() {
        return "/files/images/issues/";
    }
}
