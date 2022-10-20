package org.chlorinepentoxide.spt;

import java.util.Vector;

public class SuperPermutationUtils {

    public static int generateSPTUpperBound(int slots, String opts) {
        int len = 0;
        PermutationEngine pe = new PermutationEngine(opts, slots);
        while(true) {
            String next = pe.permuteNext();
            if(next == null) break;
            len += next.length();
        }
        return len;
    }

    public static boolean validateSPT(Vector<String> permutations, String sptTarget) {
        for(String p:permutations) {
            if(!sptTarget.contains(p)) return false;

        }
        return true;
    }

}
