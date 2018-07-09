package com.ctbt.beidou.base.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationContext;

import com.ctbt.beidou.base.CommValue;
import com.ctbt.beidou.base.bo.ReturnVo;
import com.ctbt.beidou.base.bo.ValidityRuleBo;

/**
 * 验证框架，对bo进行验证，每一个需要验证的bo，必须在同目录下有一个***-hpxvalidity.xml的配置文件 在项目启动时加载这些配置文件
 * 
 * @author zhangheng
 * 
 */
public class ValidityUtil {

	private Logger logger = Logger.getLogger(ValidityUtil.class);

	private String regxNumeric = "[0-9]+[\\.]?[0-9]*";

	private String regxLetter = "^[A-Za-z]+$";

	private String regxChinese = "^[\u4e00-\u9fa5],{0,}$";

	public static enum RuleType {
		required, length, numeric, letter, date, chinese, email, url, mobile, tel, postcode, regx
	};

	private static ValidityUtil instance = new ValidityUtil();

	private static Map<String, TreeMap> allRules = new HashMap<String, TreeMap>();

	private ValidityUtil() {
	}

	/**
     * 加载所有的bo验证配置文件
     * 
     * @param ac
     * @throws Exception
     */
	public static void init(ApplicationContext ac) throws Exception {
		String appRoot = "";//WebAppContextUtil.getInstance().getRealPath();
		List<File> ruleFiles = findBoRuleXml(appRoot);
		SAXReader reader = new SAXReader();
		Document document = null;
		Element root = null;
		String className = "";

		for(File sourceFile : ruleFiles){
			// 创建新文件的目录成功
			document = reader.read(sourceFile);
			root = document.getRootElement();
			className = root.attributeValue("class");

			TreeMap<String, List> fieldRulesMap = new TreeMap<String, List>();
			for(Iterator i = root.elementIterator(); i.hasNext();){
				Element fieldElement = (Element) i.next();
				String fieldName = fieldElement.attributeValue("name");
				String fieldText = fieldElement.attributeValue("text");
				List<ValidityRuleBo> rulesList = new ArrayList<ValidityRuleBo>();

				for(Iterator f = fieldElement.elementIterator(); f.hasNext();){
					Element ruleElement = (Element) f.next();
					String ruleType = ruleElement.attributeValue("type");
					ValidityRuleBo ruleBo = null;

					if("required".equalsIgnoreCase(ruleType)){
						ruleBo = new ValidityRuleBo(RuleType.required, className, fieldName, fieldText);
					}else if("length".equalsIgnoreCase(ruleType)){
						ruleBo = new ValidityRuleBo(RuleType.length, className, fieldName, fieldText);
						String min = ruleElement.attributeValue("min");
						String max = ruleElement.attributeValue("max");

						ruleBo.setMin(Integer.valueOf(min));
						ruleBo.setMax(Integer.valueOf(max));
					}else if("numeric".equalsIgnoreCase(ruleType)){
						ruleBo = new ValidityRuleBo(RuleType.numeric, className, fieldName, fieldText);
					}else if("letter".equalsIgnoreCase(ruleType)){
						ruleBo = new ValidityRuleBo(RuleType.letter, className, fieldName, fieldText);
					}else if("date".equalsIgnoreCase(ruleType)){
						ruleBo = new ValidityRuleBo(RuleType.date, className, fieldName, fieldText);
					}else if("chinese".equalsIgnoreCase(ruleType)){
						ruleBo = new ValidityRuleBo(RuleType.chinese, className, fieldName, fieldText);
					}else if("email".equalsIgnoreCase(ruleType)){
						ruleBo = new ValidityRuleBo(RuleType.email, className, fieldName, fieldText);
					}else if("url".equalsIgnoreCase(ruleType)){
						ruleBo = new ValidityRuleBo(RuleType.url, className, fieldName, fieldText);
					}else if("mobile".equalsIgnoreCase(ruleType)){
						ruleBo = new ValidityRuleBo(RuleType.mobile, className, fieldName, fieldText);
					}else if("tel".equalsIgnoreCase(ruleType)){
						ruleBo = new ValidityRuleBo(RuleType.tel, className, fieldName, fieldText);
					}else if("postcode".equalsIgnoreCase(ruleType)){
						ruleBo = new ValidityRuleBo(RuleType.postcode, className, fieldName, fieldText);
					}else if("regx".equalsIgnoreCase(ruleType)){
						ruleBo = new ValidityRuleBo(RuleType.regx, className, fieldName, fieldText);

						String regx = ruleElement.attributeValue("regx");
						ruleBo.setRegx(regx);
					}

					rulesList.add(ruleBo);
				}

				if(rulesList.size() > 0){
					fieldRulesMap.put(fieldName, rulesList);
				}
			}

			if(fieldRulesMap.size() > 0){
				allRules.put(className, fieldRulesMap);
			}
		}
	}

	/**
	 * 遍历项目路径，查找所有的bo验证配置文件
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static List<File> findBoRuleXml(String path) throws Exception {
		List<File> findFiles = new ArrayList<File>();
		File tempFile = null;
		File folder = new File(path);
		if(folder.exists() && folder.isDirectory()){
			File[] fileList = folder.listFiles();
			for(int k = 0; k < fileList.length; k++){
				tempFile = fileList[k];
				if(tempFile.isFile() && tempFile.getName().toLowerCase().endsWith("-hpxvalidity.xml")){
					findFiles.add(tempFile);
				}else if(tempFile.isDirectory()){
					// 如果是目录，往下一级找；
					findFiles.addAll(findBoRuleXml(tempFile.getPath()));
				}
			}
		}

		return findFiles;
	}

	/**
	 * 验证该对象中所有的属性
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public static ReturnVo validity(Object bo) throws Exception {
		Class boclass = bo.getClass();
		String className = boclass.getName();
		if(allRules.containsKey(className)){
			TreeMap<String, Map> boRulesMap = allRules.get(className);

			String fieldName;
			Field field;
			Object fieldValue;
			for(Iterator i = boRulesMap.keySet().iterator(); i.hasNext();){
				fieldName = (String) i.next();
				field = boclass.getDeclaredField(fieldName);
				field.setAccessible(true);
				fieldValue = field.get(bo);

				List<ValidityRuleBo> fieldRulesList = (List<ValidityRuleBo>) boRulesMap.get(fieldName);
				if(fieldRulesList != null && fieldRulesList.size() > 0){
					for(Iterator m = fieldRulesList.iterator(); m.hasNext();){
						ValidityRuleBo ruleBo = (ValidityRuleBo) m.next();

						ReturnVo rv = instance.validityField(fieldValue, ruleBo);
						if(CommValue.NO.equals(rv.getFlag())){
							// 只要有一个字段验证没有通过，就返回
							return rv;
						}
					}
				}
			}
		}

		return new ReturnVo(CommValue.YES, "验证通过！");
	}

	/**
	 * 验证指定的类classObj中，指定的属性field 的值
	 * @param classObj
	 * @param field
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static ReturnVo validity(Class boclass, String fieldName, Object fieldValue) throws Exception {

		String className = boclass.getName();
		if(allRules.containsKey(className)){
			TreeMap<String, Map> boRulesMap = allRules.get(className);
			List<ValidityRuleBo> fieldRulesList = (List<ValidityRuleBo>) boRulesMap.get(fieldName);

			if(fieldRulesList != null && fieldRulesList.size() > 0){
				for(Iterator m = fieldRulesList.iterator(); m.hasNext();){
					ValidityRuleBo ruleBo = (ValidityRuleBo) m.next();

					ReturnVo rv = instance.validityField(fieldValue, ruleBo);
					if(CommValue.NO.equals(rv.getFlag())){
						// 只要有一个字段的一种规则验证没有通过，就返回
						return rv;
					}
				}
			}
		}

		return new ReturnVo(CommValue.YES, "验证通过！");
	}

	/**
	     * 对一个属性验证一条规则
	     * 
	     * @param fieldName
	     * @param fieldVale
	     * @param ruleBo
	     * @return
	     * @throws Exception
	     */
	public ReturnVo validityField(Object fieldVale, ValidityRuleBo ruleBo) throws Exception {

		if(RuleType.required.equals(ruleBo.getRuleType())){
			return validityRequired(fieldVale, ruleBo);
		}else if(RuleType.length.equals(ruleBo.getRuleType())){
			return validityLength(fieldVale, ruleBo);
		}else if(RuleType.numeric.equals(ruleBo.getRuleType())){
			return validityNumeric(fieldVale, ruleBo);
		}else if(RuleType.letter.equals(ruleBo.getRuleType())){
			return validityLength(fieldVale, ruleBo);
		}else if(RuleType.date.equals(ruleBo.getRuleType())){
			return validityDate(fieldVale, ruleBo);
		}else if(RuleType.chinese.equals(ruleBo.getRuleType())){
			return validityChinese(fieldVale, ruleBo);
		}else if(RuleType.email.equals(ruleBo.getRuleType())){
			return validityEmail(fieldVale, ruleBo);
		}else if(RuleType.url.equals(ruleBo.getRuleType())){
			return validityUrl(fieldVale, ruleBo);
		}else if(RuleType.mobile.equals(ruleBo.getRuleType())){
			return validityMobile(fieldVale, ruleBo);
		}else if(RuleType.tel.equals(ruleBo.getRuleType())){
			return validityTel(fieldVale, ruleBo);
		}else if(RuleType.postcode.equals(ruleBo.getRuleType())){
			return validityPostcode(fieldVale, ruleBo);
		}else if(RuleType.regx.equals(ruleBo.getRuleType())){
			return validityRegx(fieldVale, ruleBo);
		}else{
			return new ReturnVo(CommValue.NO, "验证类型（" + ruleBo.getRuleType() + "）未知的验证类型，验证失败！");
		}
	}

	/**
	 * 验证是不是为空
	 * @param fieldVale
	 * @param ruleBo
	 * @return
	 */
	public ReturnVo validityRequired(Object fieldVale, ValidityRuleBo ruleBo) {
		if(fieldVale != null){
			if(fieldVale instanceof String){
				if(!"".equals((String) fieldVale)){
					return new ReturnVo(CommValue.YES, "");
				}else{
					return new ReturnVo(CommValue.NO, getText("hpxvalidity.rule.required", new String[] { ruleBo.getFieldText() }));
				}
			}

			return new ReturnVo(CommValue.YES, "");
		}

		return new ReturnVo(CommValue.NO, getText("hpxvalidity.rule.required", new String[] { ruleBo.getFieldText() }));
	}

	/**
	 * 验证长度
	 * @param fieldVale
	 * @param ruleBo
	 * @return
	 */
	public ReturnVo validityLength(Object fieldVale, ValidityRuleBo ruleBo) {
		String value = fieldVale.toString();
		if(value.length() >= ruleBo.getMin() && value.length() <= ruleBo.getMax()){
			return new ReturnVo(CommValue.YES, "验证通过！");
		}

		return new ReturnVo(CommValue.NO, getText("hpxvalidity.rule.length", new String[] { ruleBo.getFieldText(), ruleBo.getMin() + "", ruleBo.getMax() + "" }));
	}

	/**
	 * 验证是不是一个数字，允许带小数点
	 * @param fieldVale
	 * @param ruleBo
	 * @return
	 */
	public ReturnVo validityNumeric(Object fieldVale, ValidityRuleBo ruleBo) {
		boolean boo = Pattern.matches(regxNumeric, fieldVale.toString());
		if(boo){
			return new ReturnVo(CommValue.YES);
		}else{
			return new ReturnVo(CommValue.NO, getText("hpxvalidity.rule.numeric", new String[] { ruleBo.getFieldText() }));
		}
	}

	/**
	 * 验证是不是字母
	 * @param fieldVale
	 * @param ruleBo
	 * @return
	 */
	public ReturnVo validityLetter(Object fieldVale, ValidityRuleBo ruleBo) {
		boolean boo = Pattern.matches(regxLetter, fieldVale.toString());
		if(boo){
			return new ReturnVo(CommValue.YES);
		}else{
			return new ReturnVo(CommValue.NO, getText("hpxvalidity.rule.letter", new String[] { ruleBo.getFieldText() }));
		}
	}

	/**
	 * 验证是不是日期类型
	 * @param fieldVale
	 * @param ruleBo
	 * @return
	 */
	public ReturnVo validityDate(Object fieldVale, ValidityRuleBo ruleBo) {
		if(fieldVale != null && fieldVale instanceof Date){
			return new ReturnVo(CommValue.YES);
		}else{
			return new ReturnVo(CommValue.NO, getText("hpxvalidity.rule.date", new String[] { ruleBo.getFieldText() }));
		}
	}

	/**
	 * 验证是不是中文
	 * @param fieldVale
	 * @param ruleBo
	 * @return
	 */
	public ReturnVo validityChinese(Object fieldVale, ValidityRuleBo ruleBo) {
		boolean boo = Pattern.matches(regxChinese, fieldVale.toString());
		if(boo){
			return new ReturnVo(CommValue.YES);
		}else{
			return new ReturnVo(CommValue.NO, getText("hpxvalidity.rule.chinese", new String[] { ruleBo.getFieldText() }));
		}
	}

	public ReturnVo validityEmail(Object fieldVale, ValidityRuleBo ruleBo) {
		return null;
	}

	public ReturnVo validityUrl(Object fieldVale, ValidityRuleBo ruleBo) {
		return null;
	}

	public ReturnVo validityMobile(Object fieldVale, ValidityRuleBo ruleBo) {
		return null;
	}

	public ReturnVo validityTel(Object fieldVale, ValidityRuleBo ruleBo) {
		return null;
	}

	public ReturnVo validityPostcode(Object fieldVale, ValidityRuleBo ruleBo) {
		return null;
	}

	/**
	 * 验证是不是符合指定的正则表达式
	 * @param fieldVale
	 * @param ruleBo
	 * @return
	 */
	public ReturnVo validityRegx(Object fieldVale, ValidityRuleBo ruleBo) {
		boolean boo = Pattern.matches(ruleBo.getRegx(), fieldVale.toString());
		if(boo){
			return new ReturnVo(CommValue.YES);
		}else{
			return new ReturnVo(CommValue.NO, getText("hpxvalidity.rule.regx", new String[] { ruleBo.getFieldText() }));
		}
	}
	
	private String getText(String s, String[] ary) {
		return "";
	}
}
