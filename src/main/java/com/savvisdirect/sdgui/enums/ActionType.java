package com.savvisdirect.sdgui.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionType {

	SendKeys, Click;

	@JsonValue
	public Integer value() {
		return ordinal();
	}
}
