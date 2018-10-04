package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class UtilMethods {

    public boolean isComplete(List<String> listKOs, String kosToSend) {

        return getMinusList(listKOs, getKosToCompareList(kosToSend)).size()>0?false:true;
    }

    public List<String> getMinusList(List<String> listOne, List<String> listTwo) {
        List<String> added = new ArrayList<String>();
        added.addAll(listOne);
        added.removeAll(listTwo);
        return added;
    }

    public List<String> getKosToCompareList(String kosToSend) {
        List kosToSendList = Arrays.asList(kosToSend.replaceAll("ko:", "").split("\n"));
        kosToSendList.sort(Comparator.naturalOrder());
        return kosToSendList;
    }
}
