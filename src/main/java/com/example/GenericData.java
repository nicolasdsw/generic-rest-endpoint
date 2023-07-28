package com.example;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericData<T, P> {

	private T data;
	private P other;

	public GenericData(T data, P other) {
		super();
		this.data = data;
		this.other = other;
	}

}
