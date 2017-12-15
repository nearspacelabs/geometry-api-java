/*
 Copyright 1995-2015 Esri

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 For additional information, contact:
 Environmental Systems Research Institute, Inc.
 Attn: Contracts Dept
 380 New York Street
 Redlands, California, USA 92373

 email: contracts@esri.com
 */

package com.esri.core.geometry;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleByteBufferCursor extends ByteBufferCursor {

    ArrayDeque<ByteBuffer> m_byteBufferDeque;
    int m_index = -1;

    public SimpleByteBufferCursor(ByteBuffer byteBuffer) {
        m_byteBufferDeque = new ArrayDeque<>();
        m_byteBufferDeque.add(byteBuffer);
    }

    public SimpleByteBufferCursor(ByteBuffer[] byteBufferArray) {
        m_byteBufferDeque = Arrays.stream(byteBufferArray).collect(Collectors.toCollection(ArrayDeque::new));
    }

    @Deprecated
    public SimpleByteBufferCursor(List<ByteBuffer> byteBufferArray) {
        m_byteBufferDeque = new ArrayDeque<>(byteBufferArray);
    }

    public SimpleByteBufferCursor(ArrayDeque<ByteBuffer> byteBufferArrayDeque) {
        m_byteBufferDeque = byteBufferArrayDeque;
    }

    @Override
    public boolean hasNext() {
        return m_byteBufferDeque.size() > 0;
    }

    @Override
    public int getByteBufferID() {
        return m_index;
    }

    @Override
    public ByteBuffer next() {
        if (!m_byteBufferDeque.isEmpty()) {
            m_index++;
            return m_byteBufferDeque.pop();
        }

        return null;
    }

}
