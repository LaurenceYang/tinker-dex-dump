package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.Code;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class CodeSectionDumpAlgorithm extends DexSectionDumpAlgorithm<Code> {
	public CodeSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public Code dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readCode();
	}
}
