package com.picosoft.picosoft.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CountResponse {
	private String name;
	private int value;

}
