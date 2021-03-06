package com.sks.learn.microservices.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sks.learn.microservices.currencyexchangeservice.dao.ExchangeRepository;
import com.sks.learn.microservices.currencyexchangeservice.model.ExchangeValue;

@RestController
public class CurrencyExchangeController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment env;

	@Autowired
	private ExchangeRepository exchangeRepo;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

		ExchangeValue ev = new ExchangeValue();
		ev.setFromCurrency(from);
		ev.setToCurrency(to);
		ExchangeValue evFound = exchangeRepo.findByFromCurrencyAndToCurrency(from, to);

		evFound.setPort(Integer.parseInt(env.getProperty("local.server.port")));

		logger.info("ExchangeValue = {}", evFound);
		return evFound;
	}
}
