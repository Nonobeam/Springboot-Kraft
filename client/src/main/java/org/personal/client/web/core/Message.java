package org.personal.client.web.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Message {
    private String from;
    private String to;
    private String body;
}
