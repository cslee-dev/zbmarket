package com.example.zbmarket.repository;


import com.example.zbmarket.repository.entity.MemberOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<MemberOrderEntity, Long> {
}
