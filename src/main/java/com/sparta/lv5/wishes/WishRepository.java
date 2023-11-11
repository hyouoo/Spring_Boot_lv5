package com.sparta.lv5.wishes;

import com.sparta.lv5.wishes.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Integer> {
    List<Wish> findAllByAccountId(Integer id);
}
