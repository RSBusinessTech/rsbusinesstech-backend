package com.rsbusinesstech.rsbusinesstech_backend.springSecurity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
   * DTO used for Roles.
   * A role represents a group of permissions for the actions a user is having to perform in the system.
     Examples:
              - ADMIN  → USER_READ, USER_CREATE, USER_UPDATE, USER_DELETE
              - AGENT  → USER_READ, USER_CREATE, USER_UPDATE
   * Roles are assigned to users, and permissions are derived from roles.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role
{
    private String name;               //name of a role like ADMIN,AGENT etc.
    private Set<String> permissions;   //permission a role contains like USER_READ, USER_CREATE, USER_UPDATE, USER_DELETE etc.
}
