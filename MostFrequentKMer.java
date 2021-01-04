import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.*;

public class MostFrequentKMer {

    public static void main(String args[]) {
        
        MostFrequentKMer r = new MostFrequentKMer();

        StringBuffer b = new StringBuffer();
        try {
            r.readFile(b);
        } catch (Exception e) {
            System.out.println("Encountered file reading error: " + e);
        }
        
        String[] s = b.toString().split(";");
        String text = s[0];
        int kmerLength = Integer.valueOf(s[1]);

        String result = r.mostKMer(text, kmerLength);
        
        System.out.println(result);
    }

    public String mostKMer(String text, int kmerLength) {
        Map<String, Integer> map = new HashMap<>();
        String s;
        int max = -1;

        for (int i=0; i<text.length() - kmerLength + 1; i++) {
            s = text.substring(i, i + kmerLength);
            if (map.containsKey(s)) {
                map.replace(s, map.get(s) + 1);
            } else {
                map.put(s, patternCount(text, s));
            }
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
            }
        }

        StringBuffer kmer = new StringBuffer();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == max) {
                kmer.append(entry.getKey() + " ");
            }
        }

        return kmer.toString();
    }

    public int patternCount(String text, String pattern) {
        int count = 0;

        for (int i=0; i<text.length() - pattern.length() + 1; i++) {
            if (text.substring(i, i + pattern.length()).equals(pattern)) {
                count++;
            }
        }

        return count;
    }

    public void readFile(StringBuffer b) throws Exception {

        File file = new File("/Users/annielynn/Desktop/rosalind_ba1b.txt");
        Scanner sc = new Scanner(file);
        int count = 0;

        while (sc.hasNextLine()) {
            if (count > 0) {
                b.append(";");
            }
            b.append(sc.nextLine());
            count++;
        }

        sc.close();
    }
}