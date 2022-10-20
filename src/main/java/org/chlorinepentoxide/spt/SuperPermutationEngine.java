package org.chlorinepentoxide.spt;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SuperPermutationEngine {

    public Vector<String> permutations;
    public int upperBound;
    public int slots;
    public String opts;
    public int threads;
    public int batchSize;

    private ExecutorService executor;

    public SuperPermutationEngine(int slots, String opts) {
        System.out.println("Initializing SuperPermutationEngine for "+ slots +"... ");
        System.out.println("Generating permutations ... ");
        permutations = PermutationEngine.permuteValidation(opts, slots);
        SuperPermutationValidators.permutations = permutations;
        System.out.println("Generating upper bound ... ");
        upperBound = SuperPermutationUtils.generateSPTUpperBound(slots, opts);
        System.out.println("SuperPermutation Upper Bound: "+upperBound);
        this.slots = slots;
        this.opts = opts;
    }

    public void start() {
        executor = Executors.newFixedThreadPool(threads);
        execute(new PermutationEngine(opts, 1), 1);
        executor.shutdown();
    }

    public void newStart(int s) {
        if(s == upperBound) return;
        System.out.println("Testing SPTs with length "+s+" ... ");
        PermutationEngine pe = new PermutationEngine(opts, s);
        while(true) {
            String st = pe.permuteNext();
            if(st == null) newStart(s+1);
            boolean flag = SuperPermutationUtils.validateSPT(permutations,st);
            if(flag) {
                System.out.println("-------------------------------------------------");
                System.out.println("Shortest SPT found: "+st+" (length: "+st.length()+")");
                System.out.println("-------------------------------------------------");
                System.exit(0);
            }
        }
    }

    public void execute(PermutationEngine engine, int slots) {
        System.gc();
        if(SuperPermutationValidators.interruptFlag) {
            finish();
            return;
        }
        if(engine.permuteNext() == null) {
            if(slots == upperBound) {
                finish();
                return;
            }
            execute(new PermutationEngine(engine.opts, slots+1), slots+1);
        } else {
            List<SuperPermutationValidators> validators = new ArrayList<>();
            for(int i=0;i<threads;i++) {
                Vector<String> data = BatchGenerator.generate(engine, batchSize);
                SuperPermutationValidators v = new SuperPermutationValidators(data);
                v.currentSlots = slots;
                validators.add(v);
                if(engine.permuteNext() == null) break;
            }
            try {
                executor.invokeAll(validators);
            } catch (InterruptedException e) {
                System.out.println("Execution interrupted...");
                throw new RuntimeException(e);
            }
            execute(engine, slots);
        }
    }

    public void finish() {
        System.out.println("Execution Finished...");
        System.out.println("---------------------------------------------------------");
        String SPT = getSPT();
        System.out.println("Shortest SPT found: "+SPT+" ("+SPT.length()+").");
        System.out.println("----------------------------------------------------------");
    }

    public String getSPT() {
        String current = SuperPermutationValidators.output.elementAt(0);
        for(String st:SuperPermutationValidators.output) {
            if(st.length() < current.length()) current = st;
        }
        return current;
    }

}
