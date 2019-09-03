/**
 *
 *
 * http://www.xkygame.com
 *
 * 版权所有，侵权必究！
 */

package io.renren;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApiApplication  extends SpringBootServletInitializer {
	private static Logger logger = LoggerFactory.getLogger(ApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		logger.info("============= SpringBoot-Api Start Success =============");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApiApplication.class);
	}
}
