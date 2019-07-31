package com.internousdev.jaguar.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.jaguar.dao.MCategoryDAO;
import com.internousdev.jaguar.dto.MCategoryDTO;
import com.internousdev.jaguar.util.CommonUtility;
import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session;

	public String execute() {
		//tempUserIdとuserIdがなければ
		if (!(session.containsKey("tempUserId")) && !(session.containsKey("userId"))) {
			CommonUtility commonUtility = new CommonUtility();
			//ランダム１６桁の仮ユーザーIDが作られる
			session.put("tempUserId", commonUtility.getRamdomValue());
		}

		if (!session.containsKey("logined")) {
			session.put("logined", 0);
		}

		if(!session.containsKey("mCategoryDTOList")) {
			List<MCategoryDTO> mCategoryDTOList = new ArrayList<MCategoryDTO>();
			MCategoryDAO mCategoryDAO = new MCategoryDAO();
			try {
				mCategoryDTOList = mCategoryDAO.getMCategoryList();
			}catch (NullPointerException e) {
				mCategoryDTOList = null;
			}

			session.put("mCategoryDTOList", mCategoryDTOList);
		}
		return SUCCESS;
	}

	public Map<String, Object>getSession(){
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
