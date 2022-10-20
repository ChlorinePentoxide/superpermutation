package org.chlorinepentoxide.spt;

import java.util.Vector;

public class PermutationEngine {

    public static Vector<String> permute(String options, int slots) {
        Vector<String> permutations = new Vector<>(1,1);
        PermutationEngine pe = new PermutationEngine(options, slots);

        while(true) {
            String st = pe.permuteNext();
            if(st == null) break;
            permutations.addElement(st);
        }

        return permutations;
    }

    public static Vector<String> permuteValidation(String options, int slots) {
        Vector<String> permutations = new Vector<>(1,1);
        PermutationEngine pe = new PermutationEngine(options, slots);

        while(true) {
            String st = pe.permuteValidationNext();
            if(st == null) break;
            permutations.addElement(st);
        }

        return permutations;
    }

    public static class Slot {
        public char value;
        public String options;
        public int next = 0;

        public Slot(String opt) {
            this.options = opt;
            next();
        }

        public void next() {
            value = options.charAt(next++);
        }

        public void reset() {
            next = 0;
            next();
        }
    }

    public Slot[] values;
    public boolean block = false;
    public String opts;

    public PermutationEngine(String options, int slots) {
        values = new Slot[slots];
        for(int i=0;i<values.length;i++) values[i] = new Slot(options);
        opts = options;
    }

    public String permuteNext() {
        if(block) return null;
        String returnString = "";
        for(Slot slot:values) returnString += slot.value;
        boolean state = update(values.length - 1);
        if(!state) block = true;

        return returnString;
    }

    public String permuteValidationNext() {
        String st = permuteNext();
        if(st == null) return null;
        for(int i=0;i<opts.length();i++) if(countChar(st, opts.charAt(i)) >= 2) return permuteValidationNext();
        return st;
    }

    public boolean update(int beginIndex) {
        if(values[beginIndex].next == values[beginIndex].options.length()) {
            if(beginIndex == 0) return false;
            values[beginIndex].reset();
            return update(--beginIndex);
        } else {
            values[beginIndex].next();
        }
        return true;
    }

    public void reset() {
        for(Slot s:values) s.reset();
    }

    public int countChar(String str, char c)
    {
        int count = 0;

        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            count++;
        }

        return count;
    }

}
