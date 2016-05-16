/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bana.common.util.page;

/**
 *
 * @author liuwenjie
 */
public class PageCondUtil {

    public static int calculateX(PageCond pageCond) {
        pageCond = check(pageCond);
        int firstResult = pageCond.getPageSize() * (pageCond.getCurrentPage() -1);
        pageCond.setFirstResult(firstResult);
        return firstResult;
    }

    public static int calculatePageCount(PageCond pageCond) {
        pageCond = check(pageCond);
        if(pageCond.getTotalCount() == 0){
            return 0;
        }
        if(pageCond.getTotalCount()%pageCond.getPageSize() == 0){
            return pageCond.getTotalCount()/pageCond.getPageSize();
        }
        return pageCond.getTotalCount()/pageCond.getPageSize() + 1;
    }
    
    public static PageCond check(PageCond pageCond){
        if (pageCond == null) {
            pageCond = new PageCond();
            pageCond.setPageSize(10);
            pageCond.setCurrentPage(1);
        }
        if(pageCond.getCurrentPage() == 0){
            pageCond.setCurrentPage(1);
        }
        if(pageCond.getPageSize() == 0){
            pageCond.setPageSize(10);
        }
        int firstResult = pageCond.getPageSize() * (pageCond.getCurrentPage() -1);
        pageCond.setFirstResult(firstResult);
        return pageCond;
    }
        
}
