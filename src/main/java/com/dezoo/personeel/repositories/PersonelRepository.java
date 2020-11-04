package com.dezoo.personeel.repositories;

import com.dezoo.personeel.models.PersonnelMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonelRepository extends JpaRepository<PersonnelMember, Integer> {
    PersonnelMember findPersonelMemberByPersonelId(String personelId);
}
