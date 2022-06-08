package com.boost.webcrawel.core.crawel.repo;

import com.boost.webcrawel.core.crawel.entity.WebPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebPageRepo extends JpaRepository<WebPage, Long> {

    @Query("SELECT wp FROM WebPage wp WHERE wp.website.id = ?1")
    List<WebPage> findAllByWebsiteId(Long websiteId);

    Boolean existsByUrl(String url);

    WebPage findByUrl(String url);

    @Query("SELECT count(wp.id) > 0 FROM WebPage wp " +
            "WHERE wp.website.id = ?1 " +
            "AND wp.status = 'PENDING'")
    Boolean hasWebPageWithPendingStatusByWebsiteId(Long websiteId);

    @Query("SELECT wp " +
            "FROM WebPage wp " +
            "where wp.website.id = ?1")
    Page<WebPage> getWebPagesByWebsiteId(Long websiteId, Pageable pageable);
}
