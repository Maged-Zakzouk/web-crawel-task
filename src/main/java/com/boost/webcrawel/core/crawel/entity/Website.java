package com.boost.webcrawel.core.crawel.entity;

import com.boost.webcrawel.core.commons.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "website")
@EqualsAndHashCode(callSuper = false)
public class Website extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "status", nullable = false, length = 50)
    @Convert(converter = DBStatusConverter.class)
    private Status status = Status.PENDING;

    @OneToMany(targetEntity = WebPage.class, mappedBy = "website", fetch = FetchType.LAZY)
    private Set<WebPage> pages = new HashSet<>();

}
