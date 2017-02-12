/*
 * Авторское право принадлежит Антону Александровичу Астафьеву <anton@astafiev.me> (Anton Astafiev) ѱ.
 * Все права защищены и охраняются законом.
 * Copyright (c) 2016 Антон Александрович Астафьев <anton@astafiev.me> (Anton Astafiev). All rights reserved.
 * 
 *  Собственная лицензия Астафьева
 * Данный программный код является собственностью Астафьева Антона Александровича
 * и может быть использован только с его личного разрешения
 */

package net.elliptica.java.pstream;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.ParallelStreamBase;
import java.util.stream.Stream;

/**
 *
 * @author Антон Астафьев <anton@astafiev.me> (Anton Astafiev)
 */
public class InputParallelStream extends ParallelStreamBase<String> implements Stream<String> {

	public InputParallelStream(Spliterator<?> source, int sourceFlags, boolean parallel) {
		super(source, sourceFlags, parallel);
	}

	public InputParallelStream(Supplier<? extends Spliterator<?>> source, int sourceFlags, boolean parallel) {
		super(source, sourceFlags, parallel);
	}

	@Override
	public void forEachOrdered(Consumer<? super String> action) {
		super.forEachOrdered(action);
	}

	@Override
	public void forEach(Consumer<? super String> action) {
		super.forEach(action);
	}

	@Override
	public Stream<String> unordered() {
		return super.unordered();
	}

	@Override
	public Spliterator<String> spliterator() {
		return super.spliterator();
	}

	@Override
	public Stream<String> onClose(Runnable closeHandler) {
		return super.onClose(closeHandler);
	}

	@Override
	public void close() {
		super.close();
	}

}
