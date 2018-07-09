package com.ctbt.beidou.base.bo;

import com.ctbt.beidou.base.model.BdUser;

public class BdUserBo extends BdUser{
private String roleName;

public String getRoleName() {
	return roleName;
}

public void setRoleName(String roleName) {
	this.roleName = roleName;
}
}
