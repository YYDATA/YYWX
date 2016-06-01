package com.yy.control;

import java.util.Date;

import com.yy.common.utils.Common;

public class Test {
	public static void main(String[] args) {
		
		System.out.println(new StringBuilder("BA"+Common.getFormatDate(new Date(), "yyyyMMdd")+Common.randomCode(6)).toString());
	}

}
