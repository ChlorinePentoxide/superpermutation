package org.chlorinepentoxide.spt;

import java.util.Vector;

public class BatchGenerator {

    public static Vector<String> generate(int sptSlots, String options, String filePrefix, int batchSize) {
        PermutationEngine engine = new PermutationEngine(options, sptSlots);
        Vector<String> batches = new Vector<>(1,1);

        int currentBatch=1;
        int currentBatchSize=0;
        Vector<String> cache = new Vector<>(1,1);

        while(true) {
            if(currentBatchSize == batchSize) {
                String fn = filePrefix+"-"+currentBatch+".batch";
                DirectStreamWriter.write(cache, fn);
                batches.addElement(fn);
                currentBatchSize = 0;
                currentBatch++;
                cache = new Vector<>(1,1);
                System.gc();
            }
            String nextPermutation = engine.permuteNext();
            if(nextPermutation == null) {
                String fn = filePrefix+"-"+currentBatch+".batch";
                DirectStreamWriter.write(cache, fn);
                batches.addElement(fn);
                break;
            } else {
                cache.addElement(nextPermutation);
                currentBatchSize++;
            }
        }

        return batches;
    }

    public static Vector<String> generate(PermutationEngine ref, int batchSize) {
        Vector<String> vector = new Vector<>(1,1);
        for(int i=0;i<batchSize;i++) {
            String st = ref.permuteNext();
            if(st == null) return vector;
            vector.addElement(st);
        }
        return vector;
    }

}
