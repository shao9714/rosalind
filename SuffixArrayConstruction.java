import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.*;

public class SuffixArrayConstruction {
    int[] index;
    String[] suffix;

    public static void main(String args[]) {
        
        SuffixArrayConstruction r = new SuffixArrayConstruction();

        StringBuffer b = new StringBuffer();
        try {
            r.readFile(b);
        } catch (Exception e) {
            System.out.println("Encountered file reading error: " + e);
        }

        r.suffix(b.toString());

        r.mergeSort(0, b.length()-1);

        for (int i=0; i<b.length();i++) {
            System.out.print(r.index[i] + ", ");
            //System.out.println(r.suffix[i] + ", ");
        }
    }

    public void suffix(String text) {
        //Map<String, Integer> map = new TreeMap<>();
        index = new int[text.length()];
        suffix = new String[text.length()];
        int j = text.length() - 1;

        for (int i=text.length()-1; i>=0; i--) {
            index[j] = j;
            suffix[j] = mySubstring(text, i, text.length());
            j--;
        }
    }

    public void readFile(StringBuffer b) throws Exception {

        File file = new File("/Users/annielynn/Desktop/school/CMSC423/Suffix Array Construction/test.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            b.append(sc.nextLine().trim());
        }
        
        sc.close();
    }

    public String mySubstring(String text, int start, int end) {
        StringBuffer res = new StringBuffer();

        for (int i=start; i<end; i++) {
            res.append(text.charAt(i));
        }

        return res.toString();
    }

    public void mergeSort(int l, int r) {
        if (l < r) {
            int m = (l+r)/2;

            mergeSort(l, m);
            mergeSort(m+1, r);

            merge(l, m, r);
        }
    }

    public void merge(int l, int m, int r) {
        int n = m-l+1;
        int k = r-m;

        String[] nArray = new String[n];
        String[] kArray = new String[k];
        int[] nIndex = new int[n];
        int[] kIndex = new int[k];

        for (int i=0; i<n; i++) {
            nArray[i] = suffix[l+i];
            nIndex[i] = index[l+i];
        }
        
        for (int i=0; i<k; i++) {
            kArray[i] = suffix[m+1+i];
            kIndex[i] = index[m+1+i];
        }

        int i = 0, j = 0;
        int q = l;
        while (i<n && j<k) {
            if (nArray[i].compareTo(kArray[j]) <= 0) {
                suffix[q] = nArray[i];
                index[q] = nIndex[i];
                i++;
            } else {
                suffix[q] = kArray[j];
                index[q] = kIndex[j];
                j++;
            }
            q++;
        }

        while (i < n) {
            suffix[q] = nArray[i];
            index[q] = nIndex[i];
            i++;
            q++;
        }

        while (j < k) {
            suffix[q] = kArray[j];
            index[q] = kIndex[j];
            j++;
            q++;
        }
    }
}