package com.cjon.bank.service;

import java.sql.Connection;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankDTO;


public class BankSelectAllMemberService implements BankService{

	@Override
	public void execute(Model model) {
		//실제 동작하는 코드를 작성해준다.
		//모든 사람에 대한 정보를 가져오는 logic을 수행해준다.
		DataSource dataSource= (DataSource) model.asMap().get("dataSource");
		Connection con;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			BankDAO dao=new BankDAO(con);
			ArrayList<BankDTO> list=dao.selectAll();
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
