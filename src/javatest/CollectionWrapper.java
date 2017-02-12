/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatest;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;
import ява.утилиты.осмос.КоллекцияОсмоса;

/**
 *
 * @author mahairod
 */
public class CollectionWrapper<Э> {
    КоллекцияОсмоса<Э> колл;

    public Stream<Э> parallelStream() {
        return колл.parallelStream();
    }

    public Stream<Э> stream() {
        return колл.stream();
    }

    public void clear() {
        колл.clear();
    }

    public boolean retainAll(Collection<?> c) {
        return колл.retainAll(c);
    }

    public boolean removeIf(Predicate<? super Э> filter) {
        return колл.removeIf(filter);
    }

    public boolean removeAll(Collection<?> c) {
        return колл.removeAll(c);
    }

    public boolean addAll(Collection<? extends Э> c) {
        return колл.addAll(c);
    }

    public boolean containsAll(Collection<?> c) {
        return колл.containsAll(c);
    }

    public boolean remove(Object o) {
        return колл.remove(o);
    }

    public boolean add(Э e) {
        return колл.add(e);
    }

    public <T> T[] toArray(T[] a) {
        return колл.toArray(a);
    }

    public Object[] toArray() {
        return колл.toArray();
    }

    public boolean contains(Object o) {
        return колл.contains(o);
    }

    public boolean isEmpty() {
        return колл.isEmpty();
    }

    public int size() {
        return колл.size();
    }

}
