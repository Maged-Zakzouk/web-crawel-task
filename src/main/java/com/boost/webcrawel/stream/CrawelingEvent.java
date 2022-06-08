package com.boost.webcrawel.stream;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CrawelingEvent {

    private String link;

    private Integer crawelingDepth;
}
