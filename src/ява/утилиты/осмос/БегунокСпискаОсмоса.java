/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ява.утилиты.осмос;

import java.util.ListIterator;

/**
 *
 * @author mahairod
 * @param <Э>
 */
public interface БегунокСпискаОсмоса<Э> extends ListIterator<Э> {

    @Override
    public void add(Э e);

    @Override
    public void set(Э e);

    @Override
    public void remove();

    @Override
    public int previousIndex();

    @Override
    public int nextIndex();

    @Override
    public Э previous();

    @Override
    public boolean hasPrevious();

    @Override
    public Э next();

    @Override
    public boolean hasNext();

}
