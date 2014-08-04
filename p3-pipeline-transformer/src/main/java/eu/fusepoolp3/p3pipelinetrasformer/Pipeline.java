/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.fusepoolp3.p3pipelinetrasformer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gabor
 */
public class Pipeline {

    private Map<Integer, Transformer> transformers;
    private static Pipeline instance = null;

    private Pipeline() {
        transformers = new HashMap<>();
    }

    public static Pipeline getInstance() {
        if (instance == null) {
            instance = new Pipeline();
        }
        return instance;
    }

    public void AddTransformer(Transformer transformer) {
        int order = transformers.size() + 1;
        transformers.put(order, transformer);
    }

    public void Run() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
