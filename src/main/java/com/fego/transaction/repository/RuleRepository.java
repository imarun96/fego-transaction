package com.fego.transaction.repository;

import com.fego.transaction.common.base.BaseRepository;
import com.fego.transaction.entity.Rule;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

@Repository
public interface RuleRepository extends BaseRepository<Rule> {

    /**
     * Delete all the rules associated with a Goal.
     */

    @Modifying
    @Query("delete from Rule u where u.id in ?1")
    void deleteRuleByIds(List<Long> ruleIds);
}