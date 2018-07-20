package com.ctbt.beidou.sysdic.service;

import java.util.List;

import com.ctbt.beidou.base.bo.BdUserBo;
import com.ctbt.beidou.base.model.SysDic;

public interface ISysDicService {
    List<SysDic> selectAll();
    List<SysDic> selectAllByItem(SysDic sysDic);
}
