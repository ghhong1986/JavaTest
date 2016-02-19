package test;
/*
import com.chanjet.ai.client.AIClientException;
import com.chanjet.ai.client.search.SearchException;
import com.chanjet.ai.client.search.SearchParam;
import com.chanjet.ai.client.search.SearchResult;
import com.chanjet.ai.client.search.SearchService;
import com.chanjet.ai.client.search.ServiceFactory;
import com.google.common.collect.Lists;

public class SearchServiceImplTest {
    public static void main(String[] args) throws AIClientException, SearchException {
        SearchService  search = null;
        search =  ServiceFactory.getService("172.18.22.50", 9090, "wt", "hong", "guan"); 
        
        SearchParam param = search.createSearchParam();
        param.setKeyword("合作");
        param.setQuery("startTime:[0 TO 4000]");
        param.setStart(0).setRows(10).setDocType("BsvcPartnerCustomers")
                .setFields(Lists.newArrayList("id", "orgName","partnerName"))
                .setQueryFields(Lists.newArrayList("partnerName", "orgName"));
        param.getSort().addAsc("startTime").addDesc("endTime");

        SearchResult res = search.search(param);
        System.out.println(res.getNumFound());
    }
}*/
