package com.warmer.web;

import com.warmer.base.util.Neo4jUtil;
import com.warmer.web.entity.KgDomain;
import com.warmer.web.service.KgGraphService;
import com.warmer.web.service.KnowledgeGraphService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private KgGraphService kgGraphService;
    @Autowired
    private KnowledgeGraphService kgService;
    @Test
    void contextLoads() {
        List<KgDomain> domains = kgService.getDomains();
        if(domains!=null&&domains.size()>0){
            for (KgDomain domainItem : domains) {
                String cypher=String.format("match(n:`%s`) return count(n)",domainItem.getName());
                long nodeCount = Neo4jUtil.getGraphValue(cypher);
                if(nodeCount<10){
                    kgService.deleteDomain(domainItem.getId());
                    kgGraphService.deleteKGDomain(domainItem.getName());
                }
            }
        }
    }

}
