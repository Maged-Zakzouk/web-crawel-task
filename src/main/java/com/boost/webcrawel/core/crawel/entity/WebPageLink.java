package com.boost.webcrawel.core.crawel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "web_page_link")
@EqualsAndHashCode(callSuper = false)
public class WebPageLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(targetEntity = WebPage.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "web_page_link_source_id_fk"))
    private WebPage source;

    @ManyToOne(targetEntity = WebPage.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "web_page_link_destination_id_fk"))
    private WebPage destination;

    public WebPageLink(WebPage source, WebPage destination) {
        this.source = source;
        this.destination = destination;
    }
}
