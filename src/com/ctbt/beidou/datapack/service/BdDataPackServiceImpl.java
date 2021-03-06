package com.ctbt.beidou.datapack.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctbt.beidou.base.CommValue;
import com.ctbt.beidou.base.LoginSessionManager;
import com.ctbt.beidou.base.dao.BdDataPackRecvMapper;
import com.ctbt.beidou.base.dao.BdDataPackSendMapper;
import com.ctbt.beidou.base.dao.BdMsgAlarmMapper;
import com.ctbt.beidou.base.dao.BdMsgChatMapper;
import com.ctbt.beidou.base.dao.BdMsgNoticeMapper;
import com.ctbt.beidou.base.dao.BdMsgWeatherMapper;
import com.ctbt.beidou.base.model.BdDataPackRecv;
import com.ctbt.beidou.base.model.BdMsgAlarm;
import com.ctbt.beidou.base.model.BdSendPackage;
import com.ctbt.beidou.base.model.BdUserLoginSession;
import com.ctbt.beidou.user.service.IUserService;
import com.sun.swing.internal.plaf.basic.resources.basic;
import com.ctbt.beidou.base.model.BdMsgChat;
import com.ctbt.beidou.base.model.BdMsgNotice;
import com.ctbt.beidou.base.model.BdMsgWeather;
import com.ctbt.beidou.base.model.BdRecvPackage;

@Transactional
@Service("datapackService")
public class BdDataPackServiceImpl implements IBdDataPackService{

	@Resource
	private BdDataPackSendMapper bdDataPackSendMapper;
	
	@Resource
	private BdDataPackRecvMapper bdDataPackRecvMapper;

	@Resource
	private BdMsgChatMapper bdMsgChatMapper;
	
	@Resource
	private BdMsgAlarmMapper bdMsgAlarmMapper;
	
	@Resource
	private BdMsgWeatherMapper bdMsgWeatherMapper;
	
	@Resource 
	private BdMsgNoticeMapper bdMsgNoticeMapper;

	@Resource 
	private IUserService userService;
	
	@Override
	public List<BdSendPackage> queryDataPackSendList(Map<String, Object> record) {
		
		return bdDataPackSendMapper.selectByCondition(record);
	}

	@Override
	public List<BdDataPackRecv> queryDataPackRecvList(Map<String, Object> record) {
		
		return bdDataPackRecvMapper.selectByCondition(record);
	}

	/**
	 * 从数据站接收到的数据包，保存至中心平台的数据包接收表
	 */
	@Override
	public int saveBdRecvPackage(BdRecvPackage pkg) {

		pkg.setFromPkgId(pkg.getPkgId());//数据站中的 pkgId 放置 FromPkgId
		pkg.setPkgId(null);//到了中心平台重新生成pkgId
		
		String pkgType = pkg.getPkgType();//报文类型
		Date now = new Date();
		switch(pkgType){
			case "d":
				//d普通消息（文本）
				
				BdMsgChat chat = new BdMsgChat();
				chat.setRecvTime(pkg.getRecvTime());
				chat.setMsgTxt(pkg.getPkgMsg());
				chat.setMsgType(CommValue.BdMsgChat_MsgType_TEXT);
				
				chat.setMsgFrom(pkg.getPkgFrom());
				chat.setFromPhone(pkg.getPhoneFrom());
				chat.setMsgTo(pkg.getPkgTo());
				chat.setToPhone(pkg.getPhoneTo());
				
				chat.setIsRecv(CommValue.NO);
				bdMsgChatMapper.insert(chat);
				
				pkg.setMsgId(Long.valueOf(chat.getMsgId().intValue()));
				
				//同时转发
				String phoneTo = pkg.getPhoneTo();
				BdUserLoginSession loginSession = LoginSessionManager.sessionPhoneMap.get(phoneTo);
				if(loginSession != null && "1".equals(loginSession.getNetType()) && loginSession.getDeviceId() != null) {
					String deviceId = loginSession.getDeviceId();
					int pkgTo = Integer.valueOf(deviceId);
					BdSendPackage sendPkg = new BdSendPackage();
					sendPkg.setCenterNo(CommValue.CenterNo);
					sendPkg.setPkgType(pkgType);
					sendPkg.setMsgId(chat.getMsgId());
					sendPkg.setPkgMsg(pkg.getPkgMsg());

					sendPkg.setMasterFrom(pkg.getMasterFrom());
					sendPkg.setPkgFrom(pkg.getPkgFrom());
					sendPkg.setPhoneFrom(pkg.getPhoneFrom());

					sendPkg.setMasterTo(459270);
					sendPkg.setPkgTo(pkgTo);
					sendPkg.setPhoneTo(phoneTo);
					
					sendPkg.setCreateTime(now);
				}
				break;
				
			case "e":
				//e登录
				userService.loginByPhoneAndICcard(pkg.getPhoneFrom(), pkg.getPkgFrom());
				
				break;
				
			case "f":
				//f退出登录
				
				break;
				
			case "a":
				//a报警
				BdMsgAlarm alarm = new BdMsgAlarm();
				alarm.setRecvTime(pkg.getRecvTime());
				alarm.setMsgTxt(pkg.getPkgMsg());
				alarm.setMsgType("99");
				
				alarm.setMsgFrom(pkg.getPkgFrom());
				alarm.setMsgTo(pkg.getPkgTo());
								
				//是否已接收已排除
				alarm.setIsRecv(CommValue.NO);
				alarm.setIsExclude(CommValue.NO);
				
				bdMsgAlarmMapper.insert(alarm);
				
				pkg.setMsgId(Long.valueOf(alarm.getMsgId().intValue()));
				break;
				
			case "b":
				//b解除报警
				BdMsgAlarm alarmRelease = new BdMsgAlarm();
				alarmRelease.setMsgFrom(pkg.getPkgFrom());
				alarmRelease.setExcludeConfirmTime(now);
				alarmRelease.setIsExclude(CommValue.YES);
				bdMsgAlarmMapper.updateAlarmRelease(alarmRelease);			
				break;
						
			default:
				//非正常报文类型
				pkgType = "";
				break;
		}
		
		return bdDataPackRecvMapper.insert(pkg);
	}

	@Override
	public int saveBdSendPackage(BdSendPackage record) {
		return bdDataPackSendMapper.insert(record);
	}

	@Override
	public int saveBdSendPackageForChat(BdMsgChat msg) {
		if("1".equals(msg.getMsgType())) {
			// 文本消息
			String pkgType = "d";
			
			BdSendPackage record = new BdSendPackage();
			
			record.setCenterNo(CommValue.CenterNo);
			
			// 根据session找到要发往的手机号的IC卡号
			BdUserLoginSession object = LoginSessionManager.getInstance().sessionPhoneMap.get(msg.getToPhone());			
			int card_no;
			// 如果没有该手机号相关的IC卡号，则直接返回
			if (object.getDeviceId().length() != 6) {
				return 0;
			}else {
				card_no = Integer.valueOf(object.getDeviceId());
			}
			
			record.setMasterFrom(msg.getMsgFrom());
			record.setPkgFrom(msg.getMsgFrom());			
			record.setMasterTo(card_no);
			record.setPkgTo(card_no);
			
			record.setPhoneFrom(msg.getFromPhone());		
			record.setPhoneTo(msg.getToPhone());
			
			record.setPkgMsg(msg.getMsgTxt());
			record.setMsgId(msg.getMsgId());
			record.setCreateTime(new Date());
			record.setPkgType(pkgType);
			
			return bdDataPackSendMapper.insert(record);
		}
		
		return 0;
	}

	@Override
	public int saveBdSendPackageForAlarm(BdMsgAlarm msg) {
		// 报警信息		
		String pkgType = "a";
		
		BdSendPackage record = new BdSendPackage();
		
			record.setCenterNo(CommValue.CenterNo);
			record.setMasterFrom(msg.getMsgFrom());
			record.setPkgFrom(msg.getMsgFrom());
			
			record.setMasterTo(msg.getMsgTo());
			record.setPkgTo(msg.getMsgTo());
			
			record.setPkgMsg(msg.getMsgTxt());
			record.setMsgId(msg.getMsgId());
			record.setCreateTime(new Date());
			record.setPkgType(pkgType);
			
			return bdDataPackSendMapper.insert(record);
	}	

	@Override
	public int saveBdSendPackageForNotice(BdMsgNotice msg) {
		// 通知
		String pkgType = "o";
		
		BdSendPackage record = new BdSendPackage();
		
		record.setCenterNo(CommValue.CenterNo);
		record.setMasterFrom(msg.getMsgFrom());
		record.setPkgFrom(msg.getMsgFrom());
		
		record.setMasterTo(msg.getMsgTo());
		record.setPkgTo(msg.getMsgTo());
		
		record.setPkgMsg(msg.getMsgTxt());
		record.setMsgId(msg.getMsgId());
		record.setCreateTime(new Date());
		record.setPkgType(pkgType);
		
		return bdDataPackSendMapper.insert(record);
	}

	@Override
	public int saveBdSendPackageForWeather(BdMsgWeather msg) {
		// 天气
		String pkgType = "c";
		
		BdSendPackage record = new BdSendPackage();
		
		record.setCenterNo(CommValue.CenterNo);
		record.setMasterFrom(msg.getMsgFrom());
		record.setPkgFrom(msg.getMsgFrom());
		
		record.setMasterTo(msg.getMsgTo());
		record.setPkgTo(msg.getMsgTo());
		
		record.setPkgMsg(msg.getMsgTxt());
		record.setMsgId(msg.getMsgId());
		record.setCreateTime(new Date());
		record.setPkgType(pkgType);
		
		return bdDataPackSendMapper.insert(record);
	}

	@Override
	public List<BdSendPackage> queryBdSendPackageForSend() {
		return bdDataPackSendMapper.selectForSend();
	}

	@Override
	public int updateBdSendPackageForSend(BdSendPackage record) {
		return bdDataPackSendMapper.updateForSend(record);
	}

	
}
