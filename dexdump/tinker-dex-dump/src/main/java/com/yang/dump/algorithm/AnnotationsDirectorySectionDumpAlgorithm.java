package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.AnnotationsDirectory;
import com.tencent.tinker.android.dex.io.DexDataBuffer;

/**
 * Created by yangy on 2017/1/6.
 */
public class AnnotationsDirectorySectionDumpAlgorithm extends DexSectionDumpAlgorithm<AnnotationsDirectory> {
	public AnnotationsDirectorySectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		super(dexDataBuffer);
	}

	@Override
	public AnnotationsDirectory dumpItem(DexDataBuffer dexDataBuffer) {
		return dexDataBuffer.readAnnotationsDirectory();
	}


}
