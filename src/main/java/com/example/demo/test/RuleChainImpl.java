package com.example.demo.test;

import java.util.List;

public class RuleChainImpl implements RuleChain{

	private List<Rule> ruleList;
	private int currentPosition;
	private int size;
	@Override
	public void handler(Monitor monitor) {
		if (currentPosition == size){

		}
		ruleList.get(currentPosition - 1);
	}
}
