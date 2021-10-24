package com.nikitacouponproject;

import com.nikitacouponproject.job.CouponExpirationDailyJob;
import com.nikitacouponproject.service.CategoryFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NikitaCouponProjectApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(NikitaCouponProjectApplication.class, args);
		CouponExpirationDailyJob jobBean = run.getBean(CouponExpirationDailyJob.class);
		CategoryFacade categoryFacade = run.getBean(CategoryFacade.class);

		categoryFacade.addCategories();

		Thread thread = new Thread(jobBean);
		thread.setDaemon(true);
		thread.start();


	}

}
