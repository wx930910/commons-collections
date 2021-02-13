/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4.bloomfilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.apache.commons.collections4.bloomfilter.hasher.Hasher;
import org.apache.commons.collections4.bloomfilter.hasher.Shape;

/**
 * A Hasher implementation to return fixed indexes. Duplicates are allowed. The
 * shape is ignored when generating the indexes.
 *
 * <p>
 * <strong>This is not a real hasher and is used for testing only</strong>.
 */
class FixedIndexesTestHasher {
	public static Hasher mockHasher1(final Shape shape, final int... indexes) {
		Shape mockFieldVariableShape;
		int[] mockFieldVariableIndexes;
		Hasher mockInstance = mock(Hasher.class);
		mockFieldVariableShape = shape;
		mockFieldVariableIndexes = indexes;
		when(mockInstance.getHashFunctionIdentity()).thenAnswer((stubInvo) -> {
			return mockFieldVariableShape.getHashFunctionIdentity();
		});
		when(mockInstance.iterator(any())).thenAnswer((stubInvo) -> {
			Shape shapeMockVariable = stubInvo.getArgument(0);
			if (!mockFieldVariableShape.equals(shapeMockVariable)) {
				throw new IllegalArgumentException(String.format("shape (%s) does not match internal shape (%s)",
						shapeMockVariable, mockFieldVariableShape));
			}
			return Arrays.stream(mockFieldVariableIndexes).iterator();
		});
		return mockInstance;
	}
}
