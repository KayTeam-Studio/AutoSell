/*
 * Copyright 2020
 *
 * User: LNonymous
 * Project: Horizon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 *
 */

package de.xnonymous.autosell.structure;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Registry<T> {

    @Getter
    protected final List<T> objects;

    /**
     * Constructor to init the object list.
     */
    public Registry() {
        this.objects = new ArrayList<>();
    }

    /**
     * Register a object by adding it
     * to the list.
     *
     * @param object
     */

    @SafeVarargs
    public final void register(T... object) {
        this.objects.addAll(Arrays.asList(object));
    }

    /**
     * Unregister a object by removing
     * it from the list.
     *
     * @param objects
     */
    @SafeVarargs
    public final void unregister(T... objects) {
        this.objects.removeAll(Arrays.asList(objects));
    }

    /**
     * Get a object by a class
     * by searching in the list.
     *
     * @param c
     * @return
     */
    public final T getByClass(Class<? extends T> c) {
        return this.objects.stream().filter(object -> object.getClass().equals(c)).findFirst().orElse(null);
    }

}
