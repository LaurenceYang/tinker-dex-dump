package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.MethodId;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class MethodIdSectionDumpAlgorithm extends DexSectionDumpAlgorithm<MethodId> {
	public MethodIdSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public MethodId dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readMethodId();
	}
}
