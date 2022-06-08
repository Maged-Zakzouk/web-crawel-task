package com.boost.webcrawel.core.crawel.repo;


import com.boost.webcrawel.core.crawel.entity.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebsiteRepo extends JpaRepository<Website, Long> {

    Boolean existsByUrl(String link);

    @Query("SELECT w " +
            "FROM Website w " +
            "WHERE w.url = url")
    Optional<Website> findByUrl(String url);
}
