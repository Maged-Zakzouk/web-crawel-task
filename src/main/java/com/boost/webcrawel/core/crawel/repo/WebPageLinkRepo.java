package com.boost.webcrawel.core.crawel.repo;

import com.boost.webcrawel.core.crawel.dto.SourceDestinationLinkingDto;
import com.boost.webcrawel.core.crawel.entity.WebPageLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebPageLinkRepo extends JpaRepository<WebPageLink, Long> {

    @Query("SELECT new com.boost.webcrawel.core.crawel.dto.SourceDestinationLinkingDto(wpl.source.id, wpl.destination.url) " +
            "FROM WebPageLink wpl " +
            "WHERE wpl.source.id IN ?1")
    List<SourceDestinationLinkingDto> findInSourceIds(List<Long> pagesIds);
}
