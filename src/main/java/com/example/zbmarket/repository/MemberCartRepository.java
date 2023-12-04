package com.example.zbmarket.repository;

import com.example.zbmarket.repository.entity.MemberCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCartRepository extends JpaRepository<MemberCartEntity, Long> {

}
