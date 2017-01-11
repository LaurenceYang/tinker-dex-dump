package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.AnnotationSetRefList;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class AnnotationSetRefListSectionDumpAlgorithm extends DexSectionDumpAlgorithm<AnnotationSetRefList> {
	public AnnotationSetRefListSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public AnnotationSetRefList dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readAnnotationSetRefList();
	}
}
