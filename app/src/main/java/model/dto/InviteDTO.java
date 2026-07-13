package model.dto;

public record InviteDTO (
  UserProfileDTO sender,
  String receiver,
  GroupDTO groupDTO
 ) implements NotificationDTO{}
