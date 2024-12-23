package xcmg.global.cockpit.backend.util;

public class StringUtil {
    public static boolean isNullEmpTrim(String str) {
        if (str == null || str.length() == 0 || str.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
