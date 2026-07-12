package model;

public record InviteDTO (
  UserProfileDTO receiver,
  String sender,
  GroupDTO groupDTO
 ) implements NotificationDTO{}
