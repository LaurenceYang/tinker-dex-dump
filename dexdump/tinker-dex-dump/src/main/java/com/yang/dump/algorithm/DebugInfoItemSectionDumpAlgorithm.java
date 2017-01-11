package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.DebugInfoItem;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class DebugInfoItemSectionDumpAlgorithm extends DexSectionDumpAlgorithm<DebugInfoItem> {
	public DebugInfoItemSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public DebugInfoItem dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readDebugInfoItem();
	}
}
