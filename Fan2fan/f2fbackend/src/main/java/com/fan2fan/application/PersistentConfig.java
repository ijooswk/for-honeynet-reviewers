package com.fan2fan.application;

import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * persistent bean settings
 * Created by huangsz on 2014/5/20.
 */
@EnableTransactionManagement
public class PersistentConfig extends DataSourceTransactionManagerAutoConfiguration {

}
