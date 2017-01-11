package com.yang.dump.algorithm;

import com.tencent.tinker.android.dex.io.DexDataBuffer;
import com.yang.dump.struct.PatchOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangy on 2017/1/6.
 */
public abstract class DexSectionDumpAlgorithm<T extends Comparable<T>> {
	protected DexDataBuffer dexDataBuffer;
	private List<PatchOperation<T>> patchOperationList = new ArrayList<>();

	public DexSectionDumpAlgorithm(DexDataBuffer dexDataBuffer) {
		this.dexDataBuffer = dexDataBuffer;
	}

	public List<PatchOperation<T>> getPatchOperationList() {
		return this.patchOperationList;
	}

	public void execute() {
		List<Integer> addOpIndexList = new ArrayList<>();
		List<Integer> replaceOpIndexList = new ArrayList<>();

		int delOpIndexListSize = dexDataBuffer.readUleb128();
		int previousIndex = 0;
		for (int i = 0; i < delOpIndexListSize; i++) {
			int delOpIndex = dexDataBuffer.readSleb128() + previousIndex;
			previousIndex = delOpIndex;

			patchOperationList.add(new PatchOperation<>(PatchOperation.OP_DEL, delOpIndex));
		}

		int addOpIndexListSize = dexDataBuffer.readUleb128();
		previousIndex = 0;
		for (int i = 0; i < addOpIndexListSize; i++) {
			int addOpIndex = dexDataBuffer.readSleb128() + previousIndex;
			previousIndex = addOpIndex;

			addOpIndexList.add(new Integer(addOpIndex));
		}

		int replaceOpIndexListSize = dexDataBuffer.readUleb128();
		previousIndex = 0;
		for (int i = 0; i < replaceOpIndexListSize; i++) {
			int replaceOpIndex = dexDataBuffer.readSleb128() + previousIndex;
			previousIndex = replaceOpIndex;

			replaceOpIndexList.add(new Integer(replaceOpIndex));
		}

		for (int i = 0; i < addOpIndexListSize; i++) {
			patchOperationList.add(new PatchOperation<>(
					PatchOperation.OP_ADD,
					addOpIndexList.get(i),
					dumpItem(dexDataBuffer)));
		}

		for (int i = 0; i < replaceOpIndexListSize; i++) {
			patchOperationList.add(new PatchOperation<T>(
					PatchOperation.OP_REPLACE,
					replaceOpIndexList.get(i),
					dumpItem(dexDataBuffer)));
		}
	}

	public void printSection() {
		for (int i = 0; i < patchOperationList.size(); i++) {
			if (i == 0) {
				print("[operation]\t|\t[index in section]\t|\t[item]");
			}

			PatchOperation<T> operation = patchOperationList.get(i);

			T item = operation.newItem;

			if (item == null) {
				print(PatchOperation.translateOpToString(operation.op) +
						"\t|\t" +
						offsetToHexString(operation.index) +
						"\t\t|\t");
			} else {
				print(PatchOperation.translateOpToString(operation.op) +
						"\t|\t" +
						offsetToHexString(operation.index) +
						"\t\t|\t" + item.toString());
			}
		}
	}

	public abstract T dumpItem(DexDataBuffer dexDataBuffer);


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
}
