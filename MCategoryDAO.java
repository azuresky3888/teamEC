package com.internousdev.jaguar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.jaguar.dto.MCategoryDTO;
import com.internousdev.jaguar.util.DBConnector;

public class MCategoryDAO {
	public List<MCategoryDTO> getMCategoryList(){
		DBConnector dbConnector = new DBConnector();
		Connection con = dbConnector.getConnection();
		List<MCategoryDTO> mCategoryDTOList= new ArrayList<MCategoryDTO>();
		String sql="select * from m_category";
		try {
			PreparedStatement ps= con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				MCategoryDTO mCategoryDTO=new MCategoryDTO();
				mCategoryDTO.setId(rs.getInt("Id"));
				mCategoryDTO.setCategoryId(rs.getInt("category_Id"));
				mCategoryDTO.setCategoryName(rs.getString("category_Name"));
				mCategoryDTO.setCategoryDescription(rs.getString("category_description"));
				mCategoryDTOList.add(mCategoryDTO);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return mCategoryDTOList;
	}
}
