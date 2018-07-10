package com.ctbt.beidou.chat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;

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

	//查询消息，构造树形结构
	@Override
	public List<Map<String, Object>> findAll(HttpServletRequest request) {
		
		String text = request.getParameter("text");
		System.out.println("text is -------------------------------------------------"+text);
		List<Map<String, Object>> list = null;
		if(text == "") {
			list = bdMsgChatMapper.findAll();
		}else {
			list = bdMsgChatMapper.searchInfo(text);
		}
				
		//6
		System.out.println("------------------------"+list.size());
		//返回的List数据
		List<Map<String, Object>> returnListInfo = new ArrayList<>();	
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
			returnMapInfo.put("ischecked",false);
			//用户具体信息
			List<Map<String, Object>> returnChildrenListInfo = new ArrayList<>();
			while(list.size() > 0){
				Map<String, Object> returnChildrenMapInfo = new HashMap<>();
				if(list.get(0).get("ship_id").equals(detailShip_id)) {
					int detailUser_id = (int) list.get(0).get("user_id");
					String detailUser_name = (String) list.get(0).get("user_name");
					String detailPhone = (String) list.get(0).get("phone");
					returnChildrenMapInfo.put("user_id",detailUser_name);
					returnChildrenMapInfo.put("user_name",detailUser_name);
					returnChildrenMapInfo.put("phone",detailPhone);
					returnChildrenMapInfo.put("text", detailUser_name+"("+detailPhone+")");
					returnChildrenMapInfo.put("IC", detailCard_no1);
					returnChildrenMapInfo.put("icon", request.getScheme() +"://" + request.getServerName()  + 
							":" +request.getServerPort() +request.getContextPath()+"/images/icons/user22.png");
					returnChildrenMapInfo.put("ischecked", false);
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
