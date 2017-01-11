package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.FieldId;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class FieldIdSectionDumpAlgorithm extends DexSectionDumpAlgorithm<FieldId> {
	public FieldIdSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public FieldId dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readFieldId();
	}
}
