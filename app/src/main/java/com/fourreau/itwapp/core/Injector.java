package com.fourreau.itwapp.core;

import dagger.ObjectGraph;

/**
 * An instance which is capable of injecting dependencies.
 *
 * Created by Pierre on 22/04/2015.
 */
public interface Injector {
    /**
     * Inject to <code>object</code>
     * @param object
     */
    void inject(Object object);

    ObjectGraph getObjectGraph();
}