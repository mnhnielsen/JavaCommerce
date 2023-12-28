package javaproject.dapr.sub.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DaprSubscription {
    private String pubSubName;
    private String topic;
    private String route;
}
