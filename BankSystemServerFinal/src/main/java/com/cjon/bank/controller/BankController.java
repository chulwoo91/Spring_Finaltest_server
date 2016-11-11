package com.cjon.bank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.service.BankDepositService;
import com.cjon.bank.service.BankSelectAllMemberService;
import com.cjon.bank.service.BankSelectIDService;
import com.cjon.bank.service.BankService;
import com.cjon.bank.service.BankTransferService;
import com.cjon.bank.service.BankWithdrawService;

@Controller
public class BankController {

	private DataSource dataSource;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private BankService service;
	
	//view로 jsp를 이용하지 않기 때문에 return 타입은 string이 JSON방식에 사용하는 void로 return 해준다.
	//client로 부터 callback 값을 받아야지만 JSONP 방식으로 결과값을 전달한다.
	//출력이 JSP가 아니라 Stream을 열어서 JSON을 클라이언트에게 전송한다.
	@RequestMapping(value="/selectAllMember") //, method=RequestMethod.GET -> get방식으로 들어온 method만 처리하겠다는 것을 의미
	public void selectAllMember(HttpServletRequest request, HttpServletResponse response, Model model){
		
		//입력 처리
		String callback=request.getParameter("callback"); //""안의 값은 webstorm의 JSONP의 ""와 일치해야 한다.
		
		//로직 처리
		service=new BankSelectAllMemberService();
		model.addAttribute("dataSource", dataSource);
		service.execute(model);
		
		//결과 처리
		//model에서 결과값을 가져온다.
		ArrayList<BankDTO> list=(ArrayList<BankDTO>)model.asMap().get("RESULT");
		//ArrayList<BankDTO>를 JSON으로 바꿔 클라이언트에게 전송한다.
		
		String json=null;
		ObjectMapper om=new ObjectMapper();
		try {
			json=om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json + ")");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/selectID")
	public void selectID(HttpServletRequest request, HttpServletResponse response, Model model){
		String callback=request.getParameter("callback");
		
		service=new BankSelectIDService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		
		ArrayList<BankDTO> list=(ArrayList<BankDTO>) model.asMap().get("RESULT");
		
		String json=null;
		ObjectMapper om=new ObjectMapper();
		try {
			json=om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback+"("+json+")");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/deposit")
	public void deposit(HttpServletRequest request, HttpServletResponse response, Model model){
		String callback=request.getParameter("callback");
		
		service=new BankDepositService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		
		boolean result=(Boolean) model.asMap().get("RESULT");
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback+"("+result+")");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/withdraw")
	public void withdraw(HttpServletRequest request, HttpServletResponse response, Model model){
		String callback=request.getParameter("callback");
		
		service=new BankWithdrawService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		
		boolean result=(Boolean) model.asMap().get("RESULT");
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback+"("+result+")");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/transfer")
	public void transfer(HttpServletRequest request, HttpServletResponse response, Model model){
		String callback=request.getParameter("callback");
		
		service=new BankTransferService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		
		boolean result=(Boolean) model.asMap().get("RESULT");
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback+"("+result+")");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
