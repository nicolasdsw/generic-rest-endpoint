package com.example;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Transaction {

	private String date;
	private String description;
	private Double amount;
}
