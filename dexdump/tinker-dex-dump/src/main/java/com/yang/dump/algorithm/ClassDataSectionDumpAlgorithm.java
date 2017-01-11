package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.ClassData;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class ClassDataSectionDumpAlgorithm extends DexSectionDumpAlgorithm<ClassData> {
	public ClassDataSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public ClassData dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readClassData();
	}
}
