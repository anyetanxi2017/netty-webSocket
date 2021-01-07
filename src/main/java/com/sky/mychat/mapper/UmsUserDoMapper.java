package com.sky.mychat.mapper;

import com.sky.mychat.dto.UserInfo;
import org.apache.ibatis.annotations.Param;

import com.sky.mychat.entiry.UmsUserDo;

import java.util.List;

/**
 * @author tiankong
 * @date 2019/11/17 16:05
 */
public interface UmsUserDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UmsUserDo record);

    int insertSelective(UmsUserDo record);

    UmsUserDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UmsUserDo record);

    int updateByPrimaryKey(UmsUserDo record);

    UmsUserDo selectOneByUsername(@Param("username") String username);

    /**
     * 批量获取用户
     */
    List<UserInfo> getUserByIdList(List<Integer> userIdList);
}
