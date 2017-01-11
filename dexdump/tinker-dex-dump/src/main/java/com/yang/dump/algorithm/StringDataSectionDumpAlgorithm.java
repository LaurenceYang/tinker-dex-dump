package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.StringData;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class StringDataSectionDumpAlgorithm extends DexSectionDumpAlgorithm<StringData> {
	public StringDataSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public StringData dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readStringData();
	}

}
