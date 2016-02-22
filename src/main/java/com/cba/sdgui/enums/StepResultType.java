package com.cba.sdgui.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StepResultType {

	SUCCESS, FAILURE, IN_PROGRESS, SKIPPED;

	@JsonValue
	public Integer value() {
		return ordinal();
	}

}
