package com.ctbt.beidou.fish.service;

import java.util.Date;
import java.util.List;

import com.ctbt.beidou.base.model.BdFishRecord;
import com.ctbt.beidou.base.model.BdFishRecordDetail;

public interface IFishService {
	public int insertFishRecord(BdFishRecord bdFishRecord);
	public int insertFishRecordDetail(BdFishRecordDetail bdFishRecordDetail);
	public List getFishRecord(BdFishRecord bdFishRecord, BdFishRecordDetail bdFishRecordDetail, Date sendTimeStart, Date sendTimeEnd);
	public List getFishRecordDetail(Integer fishRecordId);
	public List getFishLogListByKey(Integer recordId);

}
