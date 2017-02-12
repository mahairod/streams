/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ява.утилиты.осмос;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 *
 * @author mahairod
 */
public interface Итерируемое<Э> extends Iterable<Э>{

    @Override
    public Spliterator<Э> spliterator();

    @Override
    public void forEach(Consumer<? super Э> action);

    @Override
    public Iterator<Э> iterator();
    
}
