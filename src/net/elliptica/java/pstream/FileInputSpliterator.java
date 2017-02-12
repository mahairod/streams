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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Антон Астафьев <anton@astafiev.me> (Anton Astafiev)
 */
public class FileInputSpliterator implements Spliterator<String>{

	public FileInputSpliterator(File file) {
		this.file = file;
	}
	
	private static final long _8thBitsMask = 0x8080808080808080L * 0x100 + 0x80;

	private static long matches(long val){
		val = val | (val << 4);
		val = val | (val << 2);
		val = val | (val << 1);
		final long p = val & _8thBitsMask;
		final long q = p | (p << 7);
		final long r = q | (q << 14);
		final long s = r | (r << 28);
		return s & 0xFF00000000000000L;
	}
	
//	long[] omaskbuf = null;
//	long[] cmaskbuf = null;

	List<Integer> omaskbuf = null;
	List<Integer> cmaskbuf = null;
	
	int real_lbufsize(long[] maskbuf){
		int sz = 0;
		for (int i=0; i<maskbuf.length; i++){
			if (maskbuf[i] != 0) sz++;
		}
		return sz;
	}
	
	int getResetLastBit(long[] store){
		int pos = Long.numberOfTrailingZeros(store[0]);
		store[0] &= ~( 1L << pos );
		return pos;
	}

	@Override
	public boolean tryAdvance(Consumer<? super String> action) {
		try {
			init();
			preparebuf();
		} catch (IOException ex) {
			Logger.getLogger(FileInputSpliterator.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		if (!buffer.hasRemaining()){
			return false;
		}

		if (omaskbuf == null){
			long[] lbuf = new long[lbuffer.remaining()];
			lbuffer.get(lbuf);

			int compressedSize = lbuf.length / 8;
//			omaskbuf = new long[(lbuf.length + 7) / 8];
//			cmaskbuf = new long[omaskbuf.length];

			omaskbuf = new ArrayList<>( (int) (size / 280) );
			cmaskbuf = new ArrayList<>( (int) (size / 280) );

			long[] tmpStore = {0};
			for (int i=0; i<compressedSize; i++){
				long mask = 0;

				int offs = i*8;
				for (int j=0; j<8; j++){
					long val = lbuf[offs+j];
					// нас не интересует последний бит (ищем обе скобки)
					val &= 0xfefefefefefefefeL;
					mask = (mask >>> 8) | matches(val^0x2828282828282828L);
//					cmask = (cmask >> 8) | matches(val^0x29292929292929L);
				}
				mask = ~mask;
				
				while (mask != 0){
					tmpStore[0] = mask;
					int pos = getResetLastBit(tmpStore);
					mask = tmpStore[0];
					byte symb = (byte) (lbuf[offs + pos/8] >> (pos%8 * 8) );
					List<Integer> buf = 0x28 == symb ? omaskbuf : cmaskbuf;
					buf.add(pos);
				}

//				omaskbuf[i] = omask;
//				cmaskbuf[i] = cmask;
			}
			lbuffer = null;
			System.out.println("Offeset=" + offset + ", brackets: " + omaskbuf.size() + " / " + cmaskbuf.size());
			return true;
		}
		
/*
		int rest = buffer.remaining();
		int pos = buffer.position();
		int cur = pos;
		char ch = 0;
		while (rest>0 && ch != '\n'){
			ch = (char) buffer.get();
			cur++;
			rest--;
		}
		// назад к началу
		buffer.position(pos);

		byte[] dst = new byte[cur - pos];
		buffer.get(dst);
*/
//		String result = new String(dst, StandardCharsets.UTF_8);
		action.accept("");

		return false;
		
	}

	@Override
	public Spliterator<String> trySplit() {
		try {
			init();

			long size0 = size / 2;
			if (size0 / AVG_LINE_SIZE < 3){
				return null;
			}
			size0 = size0 - size0 % Long.BYTES;

			FileInputSpliterator prefixSpliterator = new FileInputSpliterator(file);
			prefixSpliterator.init();
			prefixSpliterator.offset = offset;
			prefixSpliterator.size = size0;

			offset += prefixSpliterator.size;
			size = size - size0;

			return prefixSpliterator;

		} catch (FileNotFoundException ex) {
			LOG.log(Level.SEVERE, null, ex);
			return null;
		}
	}

	@Override
	public long estimateSize() {
		try {
			init();
		} catch (FileNotFoundException ex) {
			LOG.log(Level.SEVERE, null, ex);
			return 0;
		}
		return size / AVG_LINE_SIZE;
	}

	@Override
	public int characteristics() {
		return CONCURRENT | IMMUTABLE | ORDERED;
	}
	
	private void preparebuf() throws FileNotFoundException, IOException{
		if (buffer == null){
			buffer = channel.map(FileChannel.MapMode.READ_ONLY, offset, size);
			lbuffer = buffer.asLongBuffer();
		}
	}
	private void init() throws FileNotFoundException{
		if (null == channel){
			size = file.length();
			channel = new RandomAccessFile(file, "r").getChannel();
			buffer = null;
		}
	}

	private final File file;

	private long size = 0;
	private long offset = 0;
	private FileChannel channel;
	private ByteBuffer buffer = null;
	private LongBuffer lbuffer = null;

	private final static long AVG_LINE_SIZE = 40;

	private final static Logger LOG = Logger.getLogger(FileInputSpliterator.class.getName());
}
