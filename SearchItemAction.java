package com.internousdev.jaguar.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.jaguar.dao.MCategoryDAO;
import com.internousdev.jaguar.dao.ProductInfoDAO;
import com.internousdev.jaguar.dto.MCategoryDTO;
import com.internousdev.jaguar.dto.ProductInfoDTO;
import com.internousdev.jaguar.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class SearchItemAction extends ActionSupport implements SessionAware{
	private String categoryId;
	private String keywords;
	private List<String> keywordsErrorMessageList;
	private List<ProductInfoDTO> productInfoDTOList;
	private Map<String , Object> session;

	public String execute() {

		//カテゴリーの選択肢が存在しない場合は、すべてのカテゴリーを設定する
		if (categoryId == null) {
			categoryId = "1";
		}

		InputChecker inputChecker = new InputChecker();

		//処理用の変数に値を入れる
		if (StringUtils.isBlank(keywords)) {
			//キーワードがnull,""," ","　"の時に空文字に設定する
			keywords = "";
		}else {
			//キーワードの"　"を" "に変換して,空白2個以上を" "に変換
			keywords = keywords.replaceAll("　", " ").replaceAll("\\s{2,}"," ").trim();
		}
		//空文字じゃなかったら
		if(!(keywords.equals(""))) {
			//ListにCheckをかけた結果を入れる
			keywordsErrorMessageList = inputChecker.doCheck("検索ワード", keywords,0,50, true, true, true, true, true, true);
			//該当要素数が１つでもあれば
			if(keywordsErrorMessageList.size() > 0 ){
				return SUCCESS;
			}
		}

		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		switch (categoryId) {
		case "1":
			//カテゴリーIDが1の場合は半角スペースで区切った検索ワードをListに入れる
			productInfoDTOList = productInfoDAO.getProductInfoListByKeyword(keywords.split(" "));
			break;

		default:
			//カテゴリーIDが1以外の場合は半角スペースで区切った検索ワードとカテゴリーIDをListに入れる
			productInfoDTOList = productInfoDAO.getProductInfoListByCategoryIdAndKeyword(keywords.split(" "), categoryId);
			break;
		}

		// カテゴリーのリストが表示されていないのは良くないので、作成する
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
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public List<String> getKeywordsErrorMessageList(){
		return keywordsErrorMessageList;
	}
	public void setKeyowordsErrorMessageList(List<String> keywordsErrorMessageList) {
		this.keywordsErrorMessageList = keywordsErrorMessageList;
	}
	public List<ProductInfoDTO> getProductInfoDTOList(){
		return productInfoDTOList;
	}
	public void setProductInfoDTOList(List<ProductInfoDTO> productInfoDTOList) {
		this.productInfoDTOList = productInfoDTOList;
	}
	public Map<String, Object> getSession(){
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
