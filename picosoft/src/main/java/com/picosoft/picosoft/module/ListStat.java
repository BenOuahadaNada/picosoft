package com.picosoft.picosoft.module;

import lombok.Data;

@Data
public class ListStat {
	private String name ;
	private Double value;
	
	ListStat(){
		
	}

	ListStat(String name , Double value){
		this.name=name;
		this.value=value;
	}
}
