package com.ctbt.beidou.chat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.print.DocFlavor.STRING;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.bo.ResultView;
import com.ctbt.beidou.base.dao.BdMsgChatMapper;
import com.ctbt.beidou.base.model.BdMsgChat;
import com.ctbt.beidou.base.model.BdMsgChatDTO;
import com.ctbt.beidou.base.model.BdRole;

@Transactional
@Service("chatService")
public class BdChatServiceImpl implements IBdChatService{
	
	@Resource
	private BdMsgChatMapper bdMsgChatMapper;
	

	@Override
	public int deleteByPrimaryKey(Integer msgId) {
		
		return this.bdMsgChatMapper.deleteByPrimaryKey(msgId);
	}

	@Override
	public int insert(BdMsgChat record) {
		
		return  this.bdMsgChatMapper.insert(record);
	}

	@Override
	public int insertSelective(BdMsgChat record) {
		
		return this.bdMsgChatMapper.insertSelective(record);
	}

	@Override
	public BdMsgChat selectByPrimaryKey(Integer msgId) {
		
		return this.bdMsgChatMapper.selectByPrimaryKey(msgId);
	}

	@Override
	public int updateByPrimaryKeySelective(BdMsgChat record) {
		
		return this.bdMsgChatMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BdMsgChat record) {
		
		return this.updateByPrimaryKey(record);
	}

	@Override
	public List<BdMsgChat> queryChatList(BdMsgChat record) {
		
		return this.bdMsgChatMapper.selectByCondition(record);
	}

	@Override
	public List<BdMsgChat> showChatList() {
		
		List<BdMsgChat> list = bdMsgChatMapper.selectAll();
		for(BdMsgChat b : list) {
			if(b.getMsgType().equals("3")) {
				String base64Msg = bdMsgChatMapper.findVoice(b.getMsgId());
				b.setMsgTxt(base64Msg);
			}		
		}
		return list;
	}

	//找到相应的数据拼接并返回
	@Override
	public List<Map<String, Object>> findAll() {
		
		List<Map<String, Object>> list = bdMsgChatMapper.findAll();
		//返回的List数据
		List<Map<String, Object>> returnListInfo = new ArrayList<>();	
		for(int i=0; i<=list.size(); i++) {
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
			returnMapInfo.put("text", "船名:"+detailShip_name+", IC卡号:"+detailCard_no1);
			//用户具体信息
			List<Map<String, Object>> returnChildrenListInfo = new ArrayList<>();
			for(int j=0; j<=list.size(); j++) {
				Map<String, Object> returnChildrenMapInfo = new HashMap<>();
				if(list.get(0).get("ship_id").equals(detailShip_id)) {
					int detailUser_id = (int) list.get(0).get("user_id");
					String detailUser_name = (String) list.get(0).get("user_name");
					String detailPhone = (String) list.get(0).get("phone");
					returnChildrenMapInfo.put("user_id",detailUser_name);
					returnChildrenMapInfo.put("user_name",detailUser_name);
					returnChildrenMapInfo.put("phone",detailPhone);
					returnChildrenMapInfo.put("text", "用户名:"+detailUser_name+", 手机号:"+detailPhone);
					returnChildrenMapInfo.put("IC", detailCard_no1);
					list.remove(0);
				}else {
					break;
				}
				returnChildrenListInfo.add(returnChildrenMapInfo);
			}
			returnMapInfo.put("children", returnChildrenListInfo);
			returnListInfo.add(returnMapInfo);
		}
		return returnListInfo;
	}

	@Override
	public int toInsertMsg(List<BdMsgChat> list) {
		
		return this.bdMsgChatMapper.toInsertMsg(list);
	}
}
