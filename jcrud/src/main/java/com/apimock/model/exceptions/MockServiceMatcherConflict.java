package com.apimock.model.exceptions;

public class MockServiceMatcherConflict extends Exception {

	private static final long serialVersionUID = -8767255369555599701L;

	private final long conflictMockData1;
	private final long conflictMockData2;

	public MockServiceMatcherConflict(long conflictMockData1, long conflictMockData2) {
		this.conflictMockData1 = conflictMockData1;
		this.conflictMockData2 = conflictMockData2;
	}

	public long getConflictMockData1() {
		return conflictMockData1;
	}

	public long getConflictMockData2() {
		return conflictMockData2;
	}
}
