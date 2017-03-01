/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bana.common.util.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liuwenjie
 */
public class PageResult<T> implements Serializable{
    /** 
	* @Fields serialVersionUID :
	*/ 
	private static final long serialVersionUID = -4114045990410261418L;
	private List<T> resultList;
    private PageCond pageCond;

    public PageResult(List<T> resultList, PageCond pageCond) {
        this.resultList = resultList;
        this.pageCond = pageCond;
        if(this.pageCond != null){
        	this.pageCond.calculate();
        }
    }

    public PageCond getPageCond() {
        return pageCond;
    }

    public void setPageCond(PageCond pageCond) {
        this.pageCond = pageCond;
    }

    public List<T> getResultList() {
    	if(resultList == null){
    		return new ArrayList<T>();
    	}
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
    
}
