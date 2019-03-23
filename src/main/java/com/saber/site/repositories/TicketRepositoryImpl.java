package com.saber.site.repositories;

import com.saber.site.model.MyTicket;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.EntityManagerProxy;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class TicketRepositoryImpl implements SearchableRepository<MyTicket> {
    private EntityManager entityManager;

    private EntityManagerProxy entityManagerProxy;

    @PostConstruct
    public void initialize() {
        if (!(this.entityManager instanceof EntityManagerProxy)) {
           throw new FatalBeanException("Unable Determine EntityManager Convert to EntityManager Proxy");

        }
        this.entityManagerProxy = (EntityManagerProxy) entityManager;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private FullTextEntityManager getFullTextEntityManager() {
        return Search.getFullTextEntityManager(this.entityManagerProxy.getTargetEntityManager());
    }


    @Override
    public Page<SearchResult<MyTicket>> search(String query, Pageable pageable) {
        FullTextEntityManager fullTextEntityManager = this.getFullTextEntityManager();
        QueryBuilder builder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
                .forEntity(MyTicket.class).get();

        Query lucene = builder.keyword().onFields("body", "subject").matching(query).createQuery();
        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(lucene, MyTicket.class);

        fullTextQuery.setProjection(FullTextQuery.THIS);

        long total = fullTextQuery.getResultSize();

        fullTextQuery.setFirstResult((int)pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        List<Object> results = fullTextQuery.getResultList();
        List<SearchResult<MyTicket>> list = new ArrayList<>();
        results.forEach(result ->list.add(new SearchResult<>((MyTicket) results) ));

        return new PageImpl<SearchResult<MyTicket>>(list,pageable,total);
    }
}
