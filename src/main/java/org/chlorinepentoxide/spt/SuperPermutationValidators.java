package org.chlorinepentoxide.spt;

import java.util.Vector;
import java.util.concurrent.Callable;

public class SuperPermutationValidators implements Callable<Void> {

    public static Vector<String> permutations;
    public static Vector<String> output = new Vector<>(1,1);

    private final Vector<String> currentBatch;
    private final int batchID;
    private static int cid = 0;

    public static String smallest = null;

    public static boolean interruptFlag = false;

    public int currentSlots;

    public SuperPermutationValidators(String batch) {
        currentBatch = DirectStreamReader.read(batch);
        batchID = cid++;
    }

    public SuperPermutationValidators(Vector<String> data) {
        currentBatch = data;
        batchID = cid++;
    }

    @Override
    public Void call() throws Exception {
        for(String permutation:currentBatch) {
            boolean state = SuperPermutationUtils.validateSPT(permutations, permutation);
            if(state) {
                output.addElement(permutation);
                interruptFlag = true;
            }
        }
        System.out.println("["+currentSlots+"] Finished batch "+batchID+" ("+currentBatch.size()+" permutations).");
        return null;
    }

}
