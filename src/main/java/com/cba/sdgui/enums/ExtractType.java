package com.cba.sdgui.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExtractType {

    TEXT, SIZE, TABLE_DATA;
    
    @JsonValue
	public Integer value() {
		return ordinal();
	}

}
