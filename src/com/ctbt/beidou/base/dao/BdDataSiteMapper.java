package com.ctbt.beidou.base.dao;

import java.util.List;

import com.ctbt.beidou.base.model.BdDataSite;

public interface BdDataSiteMapper {
    int deleteByPrimaryKey(String siteNo);

    int insert(BdDataSite record);

    int insertSelective(BdDataSite record);

    BdDataSite selectByPrimaryKey(String siteNo);

    int updateByPrimaryKeySelective(BdDataSite record);

    int updateByPrimaryKey(BdDataSite record);
    
    List<BdDataSite> selectAll();
}