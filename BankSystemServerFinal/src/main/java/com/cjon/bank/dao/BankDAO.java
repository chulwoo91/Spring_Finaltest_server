package com.cjon.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.cjon.bank.dto.BankDTO;

public class BankDAO {

	private Connection con;
	public BankDAO(Connection con) {
		this.con=con;
	}

	public ArrayList<BankDTO> selectAll(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		ArrayList<BankDTO> list=new ArrayList<BankDTO>();
		
		try {

			String sql="select * from bank_member_tb";
			pstmt=con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				BankDTO dto=new BankDTO();
				dto.setMemberId(rs.getString("member_id"));
				dto.setMemberName(rs.getString("member_name"));
				dto.setMemberAccount(rs.getString("member_account"));
				dto.setMemberBalance(rs.getInt("member_balance"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public boolean updateDeposit(String memberID, String memberBalance) {
		PreparedStatement pstmt=null;
		boolean result=false;
		
		String sql="update bank_member_tb set member_balance=member_balance+? where member_id=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(memberBalance));
			pstmt.setString(2,  memberID);
			int count=pstmt.executeUpdate();
			if(count==1){
				result=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				pstmt.close();
			} catch (Exception e2) {
			}
		}
		return result;
	}

	public boolean updateWithdraw(String memberID, String memberBalance) {
		PreparedStatement pstmt=null;
		boolean result=false;
		BankDTO dto=new BankDTO();
		
		String sql="update bank_member_tb set member_balance=member_balance-? where member_id=?";
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,  Integer.parseInt(memberBalance));
			pstmt.setString(2,  memberID);
			int count=pstmt.executeUpdate();
			if(count==1){
				String sql1="select member_id, member_balance from bank_member_tb where member_id=?";
				PreparedStatement pstmt1=con.prepareStatement(sql1);
				pstmt1.setString(1,  memberID);
				ResultSet rs=pstmt1.executeQuery();
				if(rs.next()){	
					dto.setMemberBalance(rs.getInt("member_balance"));
				}
				if(dto.getMemberBalance()<0){
					System.out.println("잔액 부족으로 인한 출금 실패");
					con.rollback();
				}else{
					con.commit();		
				}
				result=true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				pstmt.close();
			} catch (Exception e2) {
			}
		}
		return result;
	}

	public ArrayList<BankDTO> selectID(String keyword) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		ArrayList<BankDTO> list=new ArrayList<BankDTO>();
		
		try {

			String sql="select * from bank_member_tb where member_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,  keyword);
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				BankDTO dto=new BankDTO();
				dto.setMemberId(rs.getString("member_id"));
				dto.setMemberName(rs.getString("member_name"));
				dto.setMemberAccount(rs.getString("member_account"));
				dto.setMemberBalance(rs.getInt("member_balance"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
		
	}

}
