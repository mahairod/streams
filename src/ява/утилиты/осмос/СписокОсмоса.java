/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ява.утилиты.осмос;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.UnaryOperator;

/**
 *
 * @author Антон Астафьев
 * @param <Тип>
 */
public interface СписокОсмоса<Тип> extends List<Тип> {

    @Override
    public Spliterator<Тип> spliterator();

    @Override
    public List<Тип> subList(int fromIndex, int toIndex);

    @Override
    public ListIterator<Тип> listIterator(int index);

    @Override
    public ListIterator<Тип> listIterator();

    @Override
    public int lastIndexOf(Object o);

    @Override
    public int indexOf(Object o);

    @Override
    public Тип remove(int index);

    @Override
    public void add(int index, Тип element);

    @Override
    public Тип set(int index, Тип element);

    @Override
    public Тип get(int index);

    @Override
    public void clear();

    @Override
    public void sort(Comparator<? super Тип> c);

    @Override
    public void replaceAll(UnaryOperator<Тип> operator);

    @Override
    public boolean retainAll(Collection<?> c);

    @Override
    public boolean removeAll(Collection<?> c);

    @Override
    public boolean addAll(int index, Collection<? extends Тип> c);

    @Override
    public boolean addAll(Collection<? extends Тип> c);

    @Override
    public boolean containsAll(Collection<?> c);

    @Override
    public boolean remove(Object o);

    @Override
    public boolean add(Тип e);

    @Override
    public <T> T[] toArray(T[] a);

    @Override
    public Object[] toArray();

    @Override
    public Iterator<Тип> iterator();

    @Override
    public boolean contains(Object o);

    @Override
    public boolean isEmpty();

    @Override
    public int size();

}
