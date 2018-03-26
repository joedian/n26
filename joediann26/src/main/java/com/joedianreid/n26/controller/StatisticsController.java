package com.joedianreid.n26.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joedianreid.n26.models.Statistic;
import com.joedianreid.n26.services.StatisticService;

@RestController
public class StatisticsController {
	
	@Autowired
	private StatisticService statisticService;
	
	
	/**
	 * This endpoint provides o(1) constant response as it pulls an already generated statistics and doesnt actually calulate it
	 * @return
	 */
	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public Statistic getStatistics(){	
		return statisticService.getCurrentStatistic();
	}

}
