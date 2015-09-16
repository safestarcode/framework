/*
* Copyright (c)  2015, Newtouch
* All rights reserved. 
*
* $id: AllMyBatisTest.java 9552 2015年9月16日 上午11:38:54 WangLijun$
*/
package com.newtouch.lion; 

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Title: 
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * Company: Newtouch
 * </p>
 * 
 * @author WangLijun
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
@ContextConfiguration(locations = { "classpath*:applicationContext-mybatistest.xml" })
public abstract class AllMyBatisTest extends AbstractJUnit4SpringContextTests {
	/**日志*/
	protected final Logger logger = LoggerFactory.getLogger(super.getClass());
}

	