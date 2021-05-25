package com.example.demo.test;

public interface Rule {

	default void init(){}

	void handler(Monitor monitor, RuleChain ruleChain);

	default void destroy(){};
}
