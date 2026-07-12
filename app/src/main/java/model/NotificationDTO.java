package model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = MatchDTO.class, name = "match"),
    @JsonSubTypes.Type(value = InviteDTO.class, name = "invite")
})
public sealed interface NotificationDTO permits MatchDTO, InviteDTO {}