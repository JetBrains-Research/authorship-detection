package cn.abovesky.shopping.util;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by snow on 2014/4/24.
 */
public class IdStatusSplitUtils {
    public static Integer[] split2Id(String[] idsAndStatuses) {
        Integer[] ids = new Integer[idsAndStatuses.length];
        int index = 0;
        Iterator<String> iterator = Arrays.asList(idsAndStatuses).iterator();
        while (iterator.hasNext()) {
            ids[index++] = Integer.valueOf(iterator.next().split("_")[0]);
        }
        return ids;
    }

    public static boolean isFormatSecret(String[] idsAndStatuses, String compareValue) {
        Iterator<String> iterator = Arrays.asList(idsAndStatuses).iterator();
        while (iterator.hasNext()) {
            if (!compareValue.equals(iterator.next().split("_")[1])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isFormatSecret(String[] idsAndStatuses, String compareValue1, String compareValue2) {
        Iterator<String> iterator = Arrays.asList(idsAndStatuses).iterator();
        while (iterator.hasNext()) {
            String status = iterator.next().split("_")[1];
            if (!compareValue1.equals(status) && !compareValue2.equals(status)) {
                return false;
            }
        }
        return true;
    }
}
