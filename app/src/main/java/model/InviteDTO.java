package model;

public record InviteDTO (
  UserProfileDTO sender,
  String receiver,
  GroupDTO groupDTO
 ) implements NotificationDTO{}
