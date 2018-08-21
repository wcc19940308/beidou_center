package com.ctbt.beidou.notice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.dao.BdMsgNoticeMapper;
import com.ctbt.beidou.base.dao.BdShipMapper;
import com.ctbt.beidou.base.model.BdMsgNotice;
import com.ctbt.beidou.base.model.BdShip;
import com.ctbt.beidou.base.utils.DicUtil;
import com.ctbt.beidou.datapack.service.IBdDataPackService;
import com.google.gson.Gson;

@Transactional
@Service("noticeService")
public class BdNoticeServiceImpl implements IBdNoticeService {
	@Resource
	private BdMsgNoticeMapper bdMsgNoticeMapper;

	@Resource
	private IBdDataPackService datapackService;

	@Resource
	private BdShipMapper bdShipMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer msgId) {

		return this.bdMsgNoticeMapper.deleteByPrimaryKey(msgId);
	}

	@Override
	public int insert(BdMsgNotice record) {

		return this.bdMsgNoticeMapper.insert(record);
	}

	@Override
	public int insertSelective(BdMsgNotice record) {

		return this.bdMsgNoticeMapper.insertSelective(record);
	}

	@Override
	public BdMsgNotice selectByPrimaryKey(Integer msgId) {

		return this.bdMsgNoticeMapper.selectByPrimaryKey(msgId);
	}

	@Override
	public int updateByPrimaryKeySelective(BdMsgNotice record) {

		return this.bdMsgNoticeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BdMsgNotice record) {

		return this.updateByPrimaryKey(record);
	}

	@Override
	public List<BdMsgNotice> queryNoticeList(Map<String, Object> record) {

		return this.bdMsgNoticeMapper.selectByCondition(record);
	}

	@Override
	public int insertMsg(List<BdMsgNotice> list) {

		for (BdMsgNotice msg : list) {
			this.bdMsgNoticeMapper.insert(msg);
			datapackService.saveBdSendPackageForNotice(msg);
		}

		return list.size();
	}

	@Override
	public ResultView saveSendNotice(Map<String, Object> param) {
		String msg = (String)param.get("msg");
		String nodesStr = (String)param.get("nodes");
		Gson gson = new Gson();
		ArrayList list = gson.fromJson(nodesStr, ArrayList.class);
		
		if(list != null && list.size() > 0) {
			Date now = new Date();
			for(int k = 0; k < list.size(); k++) {
				Map nd = (Map)list.get(k);
				int id = ((Double)nd.get("id")).intValue();
				int level = ((Double)nd.get("level")).intValue();
				int pid = ((Double)nd.get("pid")).intValue();
				String type = (String)nd.get("type");
				
				BdMsgNotice notice = new BdMsgNotice();
				notice.setMsgTxt(msg);
				notice.setSendTime(now);
				notice.setMsgFrom(0);//中心平台发送
				
				if("BdShip".equals(type)) {
					//单发
					BdShip ship = bdShipMapper.selectByShipId(id);
					notice.setMsgTo(ship.getCardNo1());
				}else {
					//根据行政区划，通播
					Map<String,Object> regMap = DicUtil.getInstance().sysRegionMap.get(id);
					Integer publicId = (Integer)regMap.get("publicId");
					notice.setMsgTo(publicId);
				}
				
				bdMsgNoticeMapper.insert(notice);
				datapackService.saveBdSendPackageForNotice(notice);
			}
			
			System.out.println("");
		}
		
		return null;
	}
}
