/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ява.утилиты.осмос;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author mahairod
 */
public interface КоллекцияОсмоса<Э> extends Collection<Э> {

    @Override
    public Stream<Э> parallelStream();

    @Override
    public Stream<Э> stream();

    @Override
    public void clear();

    @Override
    public boolean retainAll(Collection<?> c);

    @Override
    public boolean removeIf(Predicate<? super Э> filter);

    @Override
    public boolean removeAll(Collection<?> c);

    @Override
    public boolean addAll(Collection<? extends Э> c);

    @Override
    public boolean containsAll(Collection<?> c);

    @Override
    public boolean remove(Object o);

    @Override
    public boolean add(Э e);

    @Override
    public <T> T[] toArray(T[] a);

    @Override
    public Object[] toArray();

    @Override
    public boolean contains(Object o);

    @Override
    public boolean isEmpty();

    @Override
    public int size();
    
}
