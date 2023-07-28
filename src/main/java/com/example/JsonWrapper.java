package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;

@Data
public class JsonWrapper<T> {

	@JsonProperty("__type")
	private String type;
	@JsonUnwrapped
	private T inner;
}
