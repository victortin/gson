package tw.com.nanshanlife.gics.integration.db.dao.insur;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import tw.com.nanshanlife.casa.integration.db.dao.GenericDAOJPA;
import tw.com.nanshanlife.casa.integration.db.dao.JPAUtil;
import tw.com.nanshanlife.casa.integration.exception.DBException;
import tw.com.nanshanlife.gics.integration.to.insur.InsurBenyTO;
import tw.com.nanshanlife.gics.integration.to.insur.InsurCovgTO;
import tw.com.nanshanlife.gics.integration.to.insur.InsurInsTO;

/**
 * 
 * @author Bruce Yeh
 * 
 * Description: InsurInsDAO
 *
 * System     : GICS
 *
 * History
 * Date			Developer	    SR_NO.		Remarks
 * ------------------------------------------------------------
   2011/11/15	Bruce Yeh		AG11195		開發團險自費加保系統
 */

public class InsurInsDAO extends GenericDAOJPA<InsurInsTO> {
	
	public InsurInsDAO() {
		super();
		persistanceUnit = JPAUtil.PU_CASAUAM;
	}
	

	public List<InsurInsTO> getInsurIns(Date tranDate,String clntCode, String certNo, String polNo) throws DBException {

		Query q = super.getCurrentEntityManager().createQuery("select a from InsurInsTO a where a.tranDate=? and a.clntCode=? and a.certNo=? and a.polNo=?");		
		q.setParameter(1, tranDate);
		q.setParameter(2, clntCode);
		q.setParameter(3, certNo);
		q.setParameter(4, polNo);
		
		List<InsurInsTO> list = q.getResultList();
		
		if(list.size()==0){
			return list;
		}
		
		List<InsurInsTO> newList = new ArrayList<InsurInsTO>();
		
		for(InsurInsTO insTO : list){
			List<InsurCovgTO> covgList = insTO.getInsurCovgList();
			List<InsurBenyTO> benyList = insTO.getInsurBenyList();			
					
			if(covgList!=null && covgList.size() > 0){
				insTO.setInsurCovgList(covgList);
			}else{
				insTO.setInsurCovgList(new ArrayList<InsurCovgTO>());
			}
			System.out.println("benyList.size()==>"+benyList.size());
			if(benyList!=null && benyList.size() > 0){
				insTO.setInsurBenyList(benyList);
			}else{
				insTO.setInsurBenyList(new ArrayList<InsurBenyTO>());
			}
			newList.add(insTO);
		}
			
		return newList;
		 		
	}
	
	
}
