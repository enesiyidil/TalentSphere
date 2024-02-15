package org.group3.rabbit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ManagerSaveModel {

    Long authId;
    String name;
    String surname;
    String email;
    String phone;
    String title;
}
