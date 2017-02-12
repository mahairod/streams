/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ява.утилиты.осмос;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 *
 * @author mahairod
 * @param <Э>
 */
public interface БегунокОсмоса<Э> extends Iterator<Э> {

    @Override
    public void forEachRemaining(Consumer<? super Э> action);

    @Override
    public void remove();

    @Override
    public Э next();

    @Override
    public boolean hasNext();

}
