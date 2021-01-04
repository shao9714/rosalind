import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.*;

public class BetterBWMatching {
    ArrayList<String> patterns = new ArrayList<>();
    public static void main(String args[]) {
        
        BetterBWMatching r = new BetterBWMatching();
        List<Integer> list = new ArrayList<>();

        StringBuffer b = new StringBuffer();
        try {
            r.readFile(b);
        } catch (Exception e) {
            System.out.println("Encountered file reading error: " + e);
        }

        for (int i=0; i<r.patterns.size(); i++) {
            //System.out.println(r.patterns.get(i));
            list.add(r.BMW(b.toString(), r.patterns.get(i)));
        }

        for (int i=0; i<list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        
    }

    public int BMW(String L, String p) {
        int top = 0;
        int bottom = L.length()-1;
        StringBuffer pattern = new StringBuffer();
        pattern.append(p);
        char c;

        while (top <= bottom) {
            if (pattern.length() != 0) {
                c = pattern.charAt(pattern.length()-1);
                pattern.deleteCharAt(pattern.length()-1);
                if (containsSymbol(top, bottom, L, c)) {
                    top = firstOccur(L, c) + count(top, L, c);
                    bottom = firstOccur(L, c) + count(bottom+1, L, c) - 1;
                } else {
                    return 0;
                }
            } else {
                return bottom - top +1;
            }
        }

        return -1;
    }

    public int count(int top, String L, char c) {
        int res = 0;
        for (int i=0; i<top; i++) {
            if (L.charAt(i) == c) {
                res++;
            }
        }
        return res;
    }

    public int firstOccur(String L, char c) {
        char[] LArray = new char[L.length()];
        for (int i=0; i<L.length(); i++) {
            LArray[i] = L.charAt(i);
        }
        Arrays.sort(LArray);

        for (int i=0; i<L.length(); i++) {
            if (LArray[i] == c) {
                return i;
            }
        }
        return 0;
    }

    public boolean containsSymbol(int top, int bottom, String L, char c) {
        for (int i=top; i<=bottom; i++) {
            if (L.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

    public void readFile(StringBuffer b) throws Exception {

        File file = new File("/Users/annielynn/Desktop/rosalind_ba9m.txt");
        Scanner sc = new Scanner(file);

        b.append(sc.nextLine());
        while (sc.hasNext()) {
            patterns.add(sc.next());
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

    public void mergeSort(String[] s, int l, int r) {
        if (l < r) {
            int m = (l+r)/2;

            mergeSort(s, l, m);
            mergeSort(s, m+1, r);

            merge(s, l, m, r);
        }
    }

    public void merge(String[] s, int l, int m, int r) {
        int n = m-l+1;
        int k = r-m;

        String[] nArray = new String[n];
        String[] kArray = new String[k];

        for (int i=0; i<n; i++) {
            nArray[i] = s[l+i];
        }
        
        for (int i=0; i<k; i++) {
            kArray[i] = s[m+1+i];
        }

        int i = 0, j = 0;
        int q = l;
        while (i<n && j<k) {
            if (nArray[i].compareTo(kArray[j]) <= 0) {
                s[q] = nArray[i];
                i++;
            } else {
                s[q] = kArray[j];
                j++;
            }
            q++;
        }

        while (i < n) {
            s[q] = nArray[i];
            i++;
            q++;
        }

        while (j < k) {
            s[q] = kArray[j];
            j++;
            q++;
        }
    }
}