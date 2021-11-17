package com.yura.yoj.status;

public class MemberStatus {

	public enum JoinStatus {
		SUCCESS, DUPLICATEDID
	}

	public enum LoginStatus {
		MANAGER, MEMBER, GUEST, FAILED
	}

	public enum ModifyStatus {
		SUCCESS, INCORRECTPW
	}

	public enum ChangePwStatus {
		SUCCESS, INCORRECTPW
	}
	public enum DelMemberStatus{
		SUCCESS, INCORRECTPW
	}
}
