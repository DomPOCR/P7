package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.bidList;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BidListRepository extends JpaRepository<bidList, Integer> {

}
