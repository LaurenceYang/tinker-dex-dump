package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.AnnotationSet;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class AnnotationSetSectionDumpAlgorithm extends DexSectionDumpAlgorithm<AnnotationSet> {
	public AnnotationSetSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public AnnotationSet dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readAnnotationSet();
	}
}
