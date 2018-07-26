package com.ctbt.beidou.alarm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.dao.BdMsgAlarmMapper;
import com.ctbt.beidou.base.model.BdMsgAlarm;

@Transactional
@Service("alarmService")
public class BdAlarmService implements IBdAlarmService {
	@Resource
	private BdMsgAlarmMapper bdMsgAlarmMapper;

	@Override
	public String saveBdMsgAlarmEdit(BdMsgAlarm record) {

		try {
			record.setSendTime(new Date());
			int i = bdMsgAlarmMapper.insertSelective(record);
			System.out.println(i);
			return "success";
		} catch (Exception E) {
			return "false";
		}
	}

	@Override
	public List<BdMsgAlarm> queryAlarmList(Map<String, Object> record) {
		List<BdMsgAlarm> list = bdMsgAlarmMapper.selectByCondition(record);
		return list;
	}

	// 查询消息，构造树形结构
	@Override
	public List<Map<String, Object>> findAll(HttpServletRequest request) {

		String text = request.getParameter("text");
		String str = request.getParameter("str");
		List<Map<String, Object>> list = null;
		// 如果搜索框的内容和to中的内容都为空，构造整棵树
		if ((text == null || text == "") && (str == null || str == "")) {
			list = bdMsgAlarmMapper.findAll();
		}
		// 如果搜索框内容不为空，则按搜索框内容进行搜索
		else if (text != null || text == "") {
			text = "%" + text + "%";
			list = bdMsgAlarmMapper.searchInfo(text);
		}
		// 6
		System.out.println("------------------------" + list.size());
		
		//返回的List数据
				List<Map<String, Object>> returnListInfo = new LinkedList<>();	
				while(list.size() > 0){
					//返回的Map数据
					Map<String,Object> returnMapInfo = new HashMap<>();
					//获得每项明细
					int detailShip_id = (int) list.get(0).get("ship_id");
					String detailShip_name = (String) list.get(0).get("ship_name");
					String detailCard_no1 = (String) list.get(0).get("card_no1");
					//船的具体信息
					returnMapInfo.put("ship_id", detailShip_id);
					returnMapInfo.put("ship_name", detailShip_name);
					returnMapInfo.put("card_no1", detailCard_no1);
					returnMapInfo.put("text", detailShip_name+"("+detailCard_no1+")");
					returnMapInfo.put("icon", request.getScheme() +"://" + request.getServerName()  + 
							":" +request.getServerPort() +request.getContextPath()+"/images/icons/ship22.png");			
					list.remove(0);
					returnListInfo.add(returnMapInfo);
				}
				return returnListInfo;
			}

	@Override
	public int insertMsg(List<BdMsgAlarm> list) {

		return this.bdMsgAlarmMapper.toInsertMsg(list);
	}

	@Override
	public BdMsgAlarm selectByPrimaryKey(Integer msgId) {
		// TODO Auto-generated method stub
		return bdMsgAlarmMapper.selectByPrimaryKey(msgId);
	}

	@Override
	public  List<BdMsgAlarm> selectSendToData(BdMsgAlarm sendTime) {
		// TODO Auto-generated method stub
		return bdMsgAlarmMapper.selectSendToData(sendTime);
	}
}
