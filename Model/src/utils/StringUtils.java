package utils;

/**
 * Util class for operations on type String.
 */
public final class StringUtils {

    /**
     * Default constructor.
     */
    private StringUtils() {
    }

    /**
     * Checks whether passed String can be converted to type Double.
     * @param str - passed String.
     * @return true if String can be converted to type Double, false - otherwise.
     */
    public static boolean isDouble(String str) {
        try {
            double d = Double.valueOf(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
