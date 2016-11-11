package com.cjon.bank.dto;

public class BankDTO {

	private String memberId;	//java에서는 _을 사용하지 않고, 붙여서 사용한다. 문법적으로 오류가 있는 것은 아니다.
	private String memberName;
	private String memberAccount;
	private int memberBalance;
	
	public BankDTO() {
		
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public int getMemberBalance() {
		return memberBalance;
	}

	public void setMemberBalance(int memberBalance) {
		this.memberBalance = memberBalance;
	}
	
	
}
