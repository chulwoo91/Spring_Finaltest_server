package com.cjon.bank.service;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankDTO;

public class BankSelectIDService implements BankService {

	@Override
	public void execute(Model model) {

		HttpServletRequest request=(HttpServletRequest) model.asMap().get("request");
		String keyword=request.getParameter("keyword");
		DataSource dataSource=(DataSource) model.asMap().get("dataSource");
		Connection con=null;
		
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			BankDAO dao=new BankDAO(con);
			ArrayList<BankDTO> list=dao.selectID(keyword);
			if(list !=null){
				con.commit();
			}else{
				con.rollback();
			}
			model.addAttribute("RESULT", list);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
