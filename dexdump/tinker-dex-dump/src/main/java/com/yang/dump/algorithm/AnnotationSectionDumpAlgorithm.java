package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.Annotation;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class AnnotationSectionDumpAlgorithm extends DexSectionDumpAlgorithm<Annotation> {
	public AnnotationSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public Annotation dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readAnnotation();
	}
}
