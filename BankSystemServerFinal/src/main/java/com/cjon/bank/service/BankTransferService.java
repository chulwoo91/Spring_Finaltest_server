package com.cjon.bank.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;

public class BankTransferService implements BankService {

	@Override
	public void execute(Model model) {
		HttpServletRequest request=(HttpServletRequest) model.asMap().get("request");
		String sender=request.getParameter("send");
		String receiver=request.getParameter("receive");
		String money=request.getParameter("money");
		DataSource dataSource=(DataSource) model.asMap().get("dataSource");
		Connection con=null;
		
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			
			BankDAO dao=new BankDAO(con);
			
			boolean result1= dao.updateWithdraw(sender, money);
			boolean result2=dao.updateDeposit(receiver, money);
			
			if(result1 && result2){
				con.commit();
			}else{
				con.rollback();
			}
			model.addAttribute("RESULT", result1 && result2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		} 
	}

}
