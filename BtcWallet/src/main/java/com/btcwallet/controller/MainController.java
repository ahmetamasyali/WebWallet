package com.btcwallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btcwallet.exceptions.BitcoinNotEnoughException;
import com.btcwallet.model.Transaction;
import com.btcwallet.service.BitcoinService;
import com.btcwallet.service.TransactionService;
import com.btcwallet.service.UserService;


@Controller
public class MainController extends BaseController  {
	
	@Autowired
	private BitcoinService bitcoinService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping("/")
	String home(Model model) {
		
		if(isLoggedIn()) {
			model.addAttribute("isLoggedIn", true);
			model.addAttribute("user",getUser());
		}else {
			model.addAttribute("isLoggedIn", false);
		}
		return "home";
	}
	
	@RequestMapping(value = "/getTransactions", method = RequestMethod.GET)
	@ResponseBody
	public List<Transaction> getTransactions()  {
		return transactionService.findByUsername(getUser().getUsername());	
	}
	
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	@ResponseBody
	public String sendCoins(@RequestParam(value="address")String address,@RequestParam(value="amount")String amount)  {
		
		try {
			transactionService.sendCoins(getUser(), userService.findByBitcoinAddress(address), amount);
		}catch (BitcoinNotEnoughException e) {
			return e.getMessage();
		}
		
		return "SUCCESS";
	}
	
}
