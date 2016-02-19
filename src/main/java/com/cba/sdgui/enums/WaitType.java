package com.cba.sdgui.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WaitType {

    NONE, UNTIL_CLICKABLE;
    
    @JsonValue
	public Integer value() {
		return ordinal();
	}

}
