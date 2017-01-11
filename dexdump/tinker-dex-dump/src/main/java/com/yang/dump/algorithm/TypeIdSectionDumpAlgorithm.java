package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class TypeIdSectionDumpAlgorithm extends DexSectionDumpAlgorithm<Integer> {
	public TypeIdSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public Integer dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readInt();
	}
}
