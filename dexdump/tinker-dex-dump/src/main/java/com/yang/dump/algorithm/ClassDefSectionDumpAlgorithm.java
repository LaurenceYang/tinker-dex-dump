package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.ClassDef;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class ClassDefSectionDumpAlgorithm extends DexSectionDumpAlgorithm<ClassDef> {
	public ClassDefSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public ClassDef dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readClassDef();
	}
}
