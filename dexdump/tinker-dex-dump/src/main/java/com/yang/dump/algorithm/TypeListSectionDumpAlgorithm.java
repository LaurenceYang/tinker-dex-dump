package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.TypeList;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class TypeListSectionDumpAlgorithm extends DexSectionDumpAlgorithm<TypeList> {
	public TypeListSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public TypeList dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readTypeList();
	}
}
