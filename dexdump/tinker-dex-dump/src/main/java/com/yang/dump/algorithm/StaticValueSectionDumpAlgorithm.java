package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.EncodedValue;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class StaticValueSectionDumpAlgorithm extends DexSectionDumpAlgorithm<EncodedValue> {
	public StaticValueSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public EncodedValue dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readEncodedArray();
	}
}
