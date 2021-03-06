/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tencent.tinker.android.dex;

import com.tencent.tinker.android.dex.TableOfContents.Section.Item;

import java.io.UTFDataFormatException;

/**
 * *** This file is NOT a part of AOSP. ***
 *
 * Structure of StringData element in Dex file.
 */
public class StringData extends Item<StringData> {
    public String value;

    public StringData(int offset, String value) {
        super(offset);
        this.value = value;
    }

    @Override
    public int compareTo(StringData other) {
        return value.compareTo(other.value);
    }

    @Override
    public int byteCountInDex() {
        try {
            return Leb128.unsignedLeb128Size(value.length()) + (int) Mutf8.countBytes(value, true) + SizeOf.UBYTE;
        } catch (UTFDataFormatException e) {
            throw new DexException(e);
        }
    }

    @Override
    public String toString() {
        return "StringData{" +
                "value='" + value + '\'' +
                '}';
    }
}
