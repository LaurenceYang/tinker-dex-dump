/*
 * Tencent is pleased to support the open source community by making Tinker available.
 *
 * Copyright (C) 2016 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yang.dump.struct;

import com.tencent.tinker.android.dex.Annotation;
import com.tencent.tinker.android.dex.AnnotationSet;
import com.tencent.tinker.android.dex.AnnotationSetRefList;
import com.tencent.tinker.android.dex.AnnotationsDirectory;
import com.tencent.tinker.android.dex.ClassData;
import com.tencent.tinker.android.dex.ClassDef;
import com.tencent.tinker.android.dex.Code;
import com.tencent.tinker.android.dex.DebugInfoItem;
import com.tencent.tinker.android.dex.EncodedValue;
import com.tencent.tinker.android.dex.FieldId;
import com.tencent.tinker.android.dex.MethodId;
import com.tencent.tinker.android.dex.ProtoId;
import com.tencent.tinker.android.dex.SizeOf;
import com.tencent.tinker.android.dex.StringData;
import com.tencent.tinker.android.dex.TypeList;
import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.tencent.tinker.android.dex.util.CompareUtils;
import com.tencent.tinker.android.dex.util.FileUtils;
import com.tencent.tinker.android.dx.util.Hex;
import com.yang.dump.algorithm.AnnotationSectionDumpAlgorithm;
import com.yang.dump.algorithm.AnnotationSetRefListSectionDumpAlgorithm;
import com.yang.dump.algorithm.AnnotationSetSectionDumpAlgorithm;
import com.yang.dump.algorithm.AnnotationsDirectorySectionDumpAlgorithm;
import com.yang.dump.algorithm.ClassDataSectionDumpAlgorithm;
import com.yang.dump.algorithm.ClassDefSectionDumpAlgorithm;
import com.yang.dump.algorithm.CodeSectionDumpAlgorithm;
import com.yang.dump.algorithm.DebugInfoItemSectionDumpAlgorithm;
import com.yang.dump.algorithm.DexSectionDumpAlgorithm;
import com.yang.dump.algorithm.FieldIdSectionDumpAlgorithm;
import com.yang.dump.algorithm.MethodIdSectionDumpAlgorithm;
import com.yang.dump.algorithm.ProtoIdSectionDumpAlgorithm;
import com.yang.dump.algorithm.StaticValueSectionDumpAlgorithm;
import com.yang.dump.algorithm.StringDataSectionDumpAlgorithm;
import com.yang.dump.algorithm.TypeIdSectionDumpAlgorithm;
import com.yang.dump.algorithm.TypeListSectionDumpAlgorithm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public final class DexPatchFile {
    public static final byte[] MAGIC = {0x44, 0x58, 0x44, 0x49, 0x46, 0x46}; // DXDIFF
    public static final short CURRENT_VERSION = 0x0002;
    private final DexDataBuffer buffer;
    private short version;
    private int patchedDexSize;
    private int firstChunkOffset;
    private int patchedStringIdSectionOffset;
    private int patchedTypeIdSectionOffset;
    private int patchedProtoIdSectionOffset;
    private int patchedFieldIdSectionOffset;
    private int patchedMethodIdSectionOffset;
    private int patchedClassDefSectionOffset;
    private int patchedMapListSectionOffset;
    private int patchedTypeListSectionOffset;
    private int patchedAnnotationSetRefListSectionOffset;
    private int patchedAnnotationSetSectionOffset;
    private int patchedClassDataSectionOffset;
    private int patchedCodeSectionOffset;
    private int patchedStringDataSectionOffset;
    private int patchedDebugInfoSectionOffset;
    private int patchedAnnotationSectionOffset;
    private int patchedEncodedArraySectionOffset;
    private int patchedAnnotationsDirectorySectionOffset;
    private byte[] oldDexSignature;

    private DexSectionDumpAlgorithm<StringData> stringDataSectionDumpAlg;
    private DexSectionDumpAlgorithm<Integer> typeIdSectionDumpAlg;
    private DexSectionDumpAlgorithm<ProtoId> protoIdSectionDumpAlg;
    private DexSectionDumpAlgorithm<FieldId> fieldIdSectionDumpAlg;
    private DexSectionDumpAlgorithm<MethodId> methodIdSectionDumpAlg;
    private DexSectionDumpAlgorithm<ClassDef> classDefSectionDumpAlg;
    private DexSectionDumpAlgorithm<TypeList> typeListSectionDumpAlg;
    private DexSectionDumpAlgorithm<AnnotationSetRefList> annotationSetRefListSectionDumpAlg;
    private DexSectionDumpAlgorithm<AnnotationSet> annotationSetSectionDumpAlg;
    private DexSectionDumpAlgorithm<ClassData> classDataSectionDumpAlg;
    private DexSectionDumpAlgorithm<Code> codeSectionDumpAlg;
    private DexSectionDumpAlgorithm<DebugInfoItem> debugInfoSectionDumpAlg;
    private DexSectionDumpAlgorithm<Annotation> annotationSectionDumpAlg;
    private DexSectionDumpAlgorithm<EncodedValue> encodedArraySectionDumpAlg;
    private DexSectionDumpAlgorithm<AnnotationsDirectory> annotationsDirectorySectionDumpAlg;

    public enum SectionType {
        StringDataSection,
        TypeIdSection,
        ProtoIdSection,
        FieldIdSection,
        MethodIdSection,
        ClassDefSection,
        TypeListSection,
        AnnotationSetRefListSection,
        AnnotationSetSection,
        ClassDataSection,
        CodeSection,
        DebugInfoSection,
        AnnotationSection,
        StaticValueSection,
        AnnotationsDirectorySection
    };

    public DexPatchFile(File file) throws IOException {
        this.buffer = new DexDataBuffer(ByteBuffer.wrap(FileUtils.readFile(file)));
        init();
    }

    public DexPatchFile(InputStream is) throws IOException {
        this.buffer = new DexDataBuffer(ByteBuffer.wrap(FileUtils.readStream(is)));
        init();
    }

    private void init() {
        byte[] magic = this.buffer.readByteArray(MAGIC.length);
        if (CompareUtils.uArrCompare(magic, MAGIC) != 0) {
            throw new IllegalStateException("bad dex patch file magic: " + Arrays.toString(magic));
        }

        this.version = this.buffer.readShort();
        if (CompareUtils.uCompare(this.version, CURRENT_VERSION) != 0) {
            throw new IllegalStateException("bad dex patch file version: " + this.version + ", expected: " + CURRENT_VERSION);
        }

        this.patchedDexSize = this.buffer.readInt();
        this.firstChunkOffset = this.buffer.readInt();
        this.patchedStringIdSectionOffset = this.buffer.readInt();
        this.patchedTypeIdSectionOffset = this.buffer.readInt();
        this.patchedProtoIdSectionOffset = this.buffer.readInt();
        this.patchedFieldIdSectionOffset = this.buffer.readInt();
        this.patchedMethodIdSectionOffset = this.buffer.readInt();
        this.patchedClassDefSectionOffset = this.buffer.readInt();
        this.patchedMapListSectionOffset = this.buffer.readInt();
        this.patchedTypeListSectionOffset = this.buffer.readInt();
        this.patchedAnnotationSetRefListSectionOffset = this.buffer.readInt();
        this.patchedAnnotationSetSectionOffset = this.buffer.readInt();
        this.patchedClassDataSectionOffset = this.buffer.readInt();
        this.patchedCodeSectionOffset = this.buffer.readInt();
        this.patchedStringDataSectionOffset = this.buffer.readInt();
        this.patchedDebugInfoSectionOffset = this.buffer.readInt();
        this.patchedAnnotationSectionOffset = this.buffer.readInt();
        this.patchedEncodedArraySectionOffset = this.buffer.readInt();
        this.patchedAnnotationsDirectorySectionOffset = this.buffer.readInt();
        this.oldDexSignature = this.buffer.readByteArray(SizeOf.SIGNATURE);

        this.buffer.position(firstChunkOffset);

        dumpSection(buffer);
    }

    private void dumpSection(DexDataBuffer dexDataBuffer) {
        stringDataSectionDumpAlg = new StringDataSectionDumpAlgorithm(dexDataBuffer);
        typeIdSectionDumpAlg = new TypeIdSectionDumpAlgorithm(dexDataBuffer);
        protoIdSectionDumpAlg = new ProtoIdSectionDumpAlgorithm(dexDataBuffer);
        fieldIdSectionDumpAlg = new FieldIdSectionDumpAlgorithm(dexDataBuffer);
        methodIdSectionDumpAlg = new MethodIdSectionDumpAlgorithm(dexDataBuffer);
        classDefSectionDumpAlg = new ClassDefSectionDumpAlgorithm(dexDataBuffer);
        typeListSectionDumpAlg = new TypeListSectionDumpAlgorithm(dexDataBuffer);
        annotationSetRefListSectionDumpAlg = new AnnotationSetRefListSectionDumpAlgorithm(dexDataBuffer);
        annotationSetSectionDumpAlg = new AnnotationSetSectionDumpAlgorithm(dexDataBuffer);
        classDataSectionDumpAlg = new ClassDataSectionDumpAlgorithm(dexDataBuffer);
        codeSectionDumpAlg = new CodeSectionDumpAlgorithm(dexDataBuffer);
        debugInfoSectionDumpAlg = new DebugInfoItemSectionDumpAlgorithm(dexDataBuffer);
        annotationSectionDumpAlg = new AnnotationSectionDumpAlgorithm(dexDataBuffer);
        encodedArraySectionDumpAlg = new StaticValueSectionDumpAlgorithm(dexDataBuffer);
        annotationsDirectorySectionDumpAlg = new AnnotationsDirectorySectionDumpAlgorithm(dexDataBuffer);

        stringDataSectionDumpAlg.execute();
        typeIdSectionDumpAlg.execute();
        typeListSectionDumpAlg.execute();
        protoIdSectionDumpAlg.execute();
        Runtime.getRuntime().gc();
        fieldIdSectionDumpAlg.execute();
        methodIdSectionDumpAlg.execute();
        annotationSectionDumpAlg.execute();
        annotationSetSectionDumpAlg.execute();
        annotationSetRefListSectionDumpAlg.execute();
        Runtime.getRuntime().gc();
        annotationsDirectorySectionDumpAlg.execute();
        debugInfoSectionDumpAlg.execute();
        codeSectionDumpAlg.execute();
        classDataSectionDumpAlg.execute();
        encodedArraySectionDumpAlg.execute();
        classDefSectionDumpAlg.execute();
        Runtime.getRuntime().gc();
    }

    public void printStringDataSection() {
        print("printStringData start");
        List<PatchOperation<StringData>> patchOperationList = stringDataSectionDumpAlg.getPatchOperationList();
        for (int i = 0; i < patchOperationList.size(); i++) {
            PatchOperation<StringData> operation = patchOperationList.get(i);

            print(operation.op + "|" + operation.index + "|" + operation.newItem);
        }
        print("printStringData end");
    }

    public void printHeader() {
        print("");
        print("*************************************");
        print("************[Header area]************");
        print("MAGIC : " + new String(MAGIC));
        print("CURRENT_VERSION : " + CURRENT_VERSION);
        print("基准Dex大小 : " + patchedDexSize + "byte");
//        print("firstChunkOffset:" + firstChunkOffset);
        print("");
        print("[基准Dex中各Section的偏移]");
        print(offsetToHexString(patchedStringIdSectionOffset) + " [StringId Section]");
        print(offsetToHexString(patchedTypeIdSectionOffset) + " [TypeId Section]");
        print(offsetToHexString(patchedProtoIdSectionOffset) + " [ProtoId Section]");
        print(offsetToHexString(patchedFieldIdSectionOffset) + " [FieldId Section]");
        print(offsetToHexString(patchedMethodIdSectionOffset) + " [MethodId Section]");
        print(offsetToHexString(patchedClassDefSectionOffset) + " [ClassDef Section]");
        print(offsetToHexString(patchedMapListSectionOffset) + " [MapList Section]");
        print(offsetToHexString(patchedTypeListSectionOffset) + " [TypeList Section]");
        print(offsetToHexString(patchedAnnotationSetRefListSectionOffset) + " [AnnotationSetRefList Section]");
        print(offsetToHexString(patchedAnnotationSetSectionOffset) + " [AnnotationSet Section]");
        print(offsetToHexString(patchedClassDataSectionOffset) + " [ClassData Section]");
        print(offsetToHexString(patchedCodeSectionOffset) + " [Code Section]");
        print(offsetToHexString(patchedStringDataSectionOffset) + " [StringData Section]");
        print(offsetToHexString(patchedDebugInfoSectionOffset) + " [DebugInfo Section]");
        print(offsetToHexString(patchedAnnotationSectionOffset) + " [Annotation Section]");
        print(offsetToHexString(patchedEncodedArraySectionOffset) + " [EncodedArray Section]");
        print(offsetToHexString(patchedAnnotationsDirectorySectionOffset) + " [AnnotationsDirectory Section]");
        print("");

        print("基准Dex Signature:" + bytesToHexString(oldDexSignature));
    }

    public void printSection(SectionType sectionType) {
        print("");
        print("*************************************************");
        print("*******[" + sectionType.toString() + " operation list]*******");
        switch (sectionType) {
            case StringDataSection:
                stringDataSectionDumpAlg.printSection();
                break;
            case TypeIdSection:
                typeIdSectionDumpAlg.printSection();
                break;
            case ProtoIdSection:
                protoIdSectionDumpAlg.printSection();
                break;
            case FieldIdSection:
                fieldIdSectionDumpAlg.printSection();
                break;
            case MethodIdSection:
                methodIdSectionDumpAlg.printSection();
                break;
            case ClassDefSection:
                classDefSectionDumpAlg.printSection();
                break;
            case TypeListSection:
                typeListSectionDumpAlg.printSection();
                break;
            case AnnotationSetRefListSection:
                annotationSetRefListSectionDumpAlg.printSection();
                break;
            case AnnotationSetSection:
                annotationSetSectionDumpAlg.printSection();
                break;
            case ClassDataSection:
                classDataSectionDumpAlg.printSection();
                break;
            case CodeSection:
                codeSectionDumpAlg.printSection();
                break;
            case DebugInfoSection:
                debugInfoSectionDumpAlg.printSection();
                break;
            case AnnotationSection:
                annotationSectionDumpAlg.printSection();
                break;
            case StaticValueSection:
                encodedArraySectionDumpAlg.printSection();
                break;
            case AnnotationsDirectorySection:
                annotationsDirectorySectionDumpAlg.printSection();
                break;
        }
    }

    private void print(String str) {
        System.out.println(str);
    }

    private String offsetToHexString(int offset) {
        StringBuilder stringBuilder = new StringBuilder();
        String hexString = Integer.toHexString(offset);

        stringBuilder.append("0x");
        for (int i = 0; i < 8 - hexString.length(); i++) {
            stringBuilder.append("0");
        }
        stringBuilder.append(hexString);

        return stringBuilder.toString();
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder strBuilder = new StringBuilder(bytes.length << 1);
        for (byte b : bytes) {
            strBuilder.append(Hex.u1(b));
        }
        return strBuilder.toString();
    }
}
