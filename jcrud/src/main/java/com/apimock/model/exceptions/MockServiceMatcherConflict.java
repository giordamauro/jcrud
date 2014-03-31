package com.apimock.model.exceptions;

public class MockServiceMatcherConflict extends Exception {

	private static final long serialVersionUID = -8767255369555599701L;

	private final String conflictMockData1;
	private final String conflictMockData2;

	public MockServiceMatcherConflict(String conflictMockData1, String conflictMockData2) {
		this.conflictMockData1 = conflictMockData1;
		this.conflictMockData2 = conflictMockData2;
	}

	public String getConflictMockData1() {
		return conflictMockData1;
	}

	public String getConflictMockData2() {
		return conflictMockData2;
	}
}
