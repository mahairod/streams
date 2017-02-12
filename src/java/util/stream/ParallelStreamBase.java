/*
 * Авторское право принадлежит Антону Александровичу Астафьеву <anton@astafiev.me> (Anton Astafiev) ѱ.
 * Все права защищены и охраняются законом.
 * Copyright (c) 2016 Антон Александрович Астафьев <anton@astafiev.me> (Anton Astafiev). All rights reserved.
 * 
 *  Собственная лицензия Астафьева
 * Данный программный код является собственностью Астафьева Антона Александровича
 * и может быть использован только с его личного разрешения
 */

package java.util.stream;

import java.util.Spliterator;
import java.util.function.Supplier;

/**
 *
 * @author Антон Астафьев <anton@astafiev.me> (Anton Astafiev)
 */
public abstract class ParallelStreamBase<Type> extends ReferencePipeline.Head<Type, Type> {

	public ParallelStreamBase(Spliterator<?> source, int sourceFlags, boolean parallel) {
		super(source, sourceFlags, parallel);
	}

	public ParallelStreamBase(Supplier<? extends Spliterator<?>> source, int sourceFlags, boolean parallel) {
		super(source, sourceFlags, parallel);
	}

}
