package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.ProtoId;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class ProtoIdSectionDumpAlgorithm extends DexSectionDumpAlgorithm<ProtoId> {
	public ProtoIdSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public ProtoId dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readProtoId();
	}
}
