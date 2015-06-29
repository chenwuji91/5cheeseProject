import java.util.ArrayList;  
import java.util.Collections;  
import java.util.Comparator;  
import java.util.HashMap;  
import java.util.LinkedHashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.TreeMap;  
  
public class CountAccurrenceOfWord {  
    public static void main(String[] args) {  
        String text = "Have a good day. Have a good class. Have a good visit. Have fun";   
        Map<String, Integer> hashMap = new HashMap<String, Integer>();  
        String[] words = text.split("[ .!?]");  
        for (int i = 0; i < words.length; i++) {  
            if (words[i].length() > 1) {  
                if (hashMap.get(words[i]) != null) {  
                    int value = hashMap.get(words[i]).intValue();  
                    value++;  
                    hashMap.put(words[i], value);  
                } else  
                    hashMap.put(words[i], 1);  
            }  
        }  
        Map<String, Integer> treeMap = new TreeMap<String, Integer>(hashMap);  
        System.out  
                .println("Dispaly words and their count in ascending order of the word");  
        System.out.println(treeMap);  
          
        List arrayList = new ArrayList(hashMap.entrySet());  
        Collections.sort(arrayList, new Comparator(){  
            public int compare(Object o1, Object o2) {  
                Map.Entry obj1 = (Map.Entry) o1;  
                Map.Entry obj2 = (Map.Entry) o2;  
                return ((Integer) obj2.getValue()).compareTo((Integer)obj1.getValue());  
                }  
        });        
        System.out.println(arrayList);  
    }  
}  