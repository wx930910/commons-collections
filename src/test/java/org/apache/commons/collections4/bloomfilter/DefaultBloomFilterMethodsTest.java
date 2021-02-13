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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.util.BitSet;
import java.util.function.IntConsumer;

import org.apache.commons.collections4.bloomfilter.hasher.Hasher;
import org.apache.commons.collections4.bloomfilter.hasher.Shape;
import org.apache.commons.collections4.bloomfilter.hasher.StaticHasher;
import org.mockito.Mockito;

/**
 * Test all the default implementations of the BloomFilter in
 * {@link AbstractBloomFilter}.
 */
public class DefaultBloomFilterMethodsTest extends AbstractBloomFilterTest {

	public static AbstractBloomFilter mockAbstractBloomFilter2(final Hasher hasher, final Shape shape) {
		BitSet mockFieldVariableBitSet;
		AbstractBloomFilter mockInstance = mock(AbstractBloomFilter.class,
				withSettings().useConstructor(shape).defaultAnswer(Mockito.CALLS_REAL_METHODS));
		mockFieldVariableBitSet = new BitSet();
		mockInstance.verifyHasher(hasher);
		hasher.iterator(shape).forEachRemaining((IntConsumer) mockFieldVariableBitSet::set);
		doAnswer((stubInvo) -> {
			BloomFilter other = stubInvo.getArgument(0);
			mockInstance.verifyShape(other);
			mockFieldVariableBitSet.or(BitSet.valueOf(other.getBits()));
			return true;
		}).when(mockInstance).merge(any(BloomFilter.class));
		doAnswer((stubInvo) -> {
			Hasher hasherMockVariable = stubInvo.getArgument(0);
			mockInstance.verifyHasher(hasherMockVariable);
			hasherMockVariable.iterator(mockInstance.getShape())
					.forEachRemaining((IntConsumer) mockFieldVariableBitSet::set);
			return true;
		}).when(mockInstance).merge(any(Hasher.class));
		doAnswer((stubInvo) -> {
			return new StaticHasher(mockFieldVariableBitSet.stream().iterator(), mockInstance.getShape());
		}).when(mockInstance).getHasher();
		doAnswer((stubInvo) -> {
			return mockFieldVariableBitSet.toLongArray();
		}).when(mockInstance).getBits();
		return mockInstance;
	}

	public static AbstractBloomFilter mockAbstractBloomFilter1(final Shape shape) {
		BitSet mockFieldVariableBitSet;
		AbstractBloomFilter mockInstance = mock(AbstractBloomFilter.class,
				withSettings().useConstructor(shape).defaultAnswer(Mockito.CALLS_REAL_METHODS));
		mockFieldVariableBitSet = new BitSet();
		doAnswer((stubInvo) -> {
			BloomFilter other = stubInvo.getArgument(0);
			mockInstance.verifyShape(other);
			mockFieldVariableBitSet.or(BitSet.valueOf(other.getBits()));
			return true;
		}).when(mockInstance).merge(any(BloomFilter.class));
		doAnswer((stubInvo) -> {
			Hasher hasher = stubInvo.getArgument(0);
			mockInstance.verifyHasher(hasher);
			hasher.iterator(mockInstance.getShape()).forEachRemaining((IntConsumer) mockFieldVariableBitSet::set);
			return true;
		}).when(mockInstance).merge(any(Hasher.class));
		doAnswer((stubInvo) -> {
			return new StaticHasher(mockFieldVariableBitSet.stream().iterator(), mockInstance.getShape());
		}).when(mockInstance).getHasher();
		doAnswer((stubInvo) -> {
			return mockFieldVariableBitSet.toLongArray();
		}).when(mockInstance).getBits();
		return mockInstance;
	}

	@Override
	protected AbstractBloomFilter createEmptyFilter(final Shape shape) {
		return DefaultBloomFilterMethodsTest.mockAbstractBloomFilter1(shape);
	}

	@Override
	protected AbstractBloomFilter createFilter(final Hasher hasher, final Shape shape) {
		return DefaultBloomFilterMethodsTest.mockAbstractBloomFilter2(hasher, shape);
	}
}
