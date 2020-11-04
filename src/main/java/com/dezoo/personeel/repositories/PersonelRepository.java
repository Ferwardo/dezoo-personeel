package com.dezoo.personeel.repositories;

import com.dezoo.personeel.models.PersonelMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonelRepository extends JpaRepository<PersonelMember, Integer> {
}
