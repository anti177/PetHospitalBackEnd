package com.example.pethospitalbackend.dao;

import com.example.pethospitalbackend.dto.InspectionItemDetailDTO;
import com.example.pethospitalbackend.entity.InspectionItem;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface InspectionItemDao extends Mapper<InspectionItem> {

  @Select("select * from inspection_item")
  @Results(
      id = "inspection_item_list",
      value = {
        @Result(id = true, column = "inspection_item_id", property = "inspectionItemId"),
        @Result(
            column = "department_id",
            property = "departmentName",
            javaType = String.class,
            one =
                @One(
                    select =
                        "com.example.pethospitalbackend.dao.DepartmentDao.selectNameByPrimaryKey",
                    fetchType = FetchType.EAGER)),
        @Result(column = "department_id", property = "departmentId"),
        @Result(column = "fee", property = "fee"),
        @Result(column = "intro", property = "intro"),
        @Result(column = "item_name", property = "itemName")
      })
  List<InspectionItemDetailDTO> selectAllDetails();
}
