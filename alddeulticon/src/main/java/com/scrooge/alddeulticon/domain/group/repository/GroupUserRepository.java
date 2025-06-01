package com.scrooge.alddeulticon.domain.group.repository;

import com.scrooge.alddeulticon.domain.group.entity.GroupRoom;
import com.scrooge.alddeulticon.domain.group.entity.GroupUser;
import com.scrooge.alddeulticon.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {

    boolean existsByGroupAndUser(GroupRoom group, User user);

    @Query("SELECT gu.group.id FROM GroupUser gu WHERE gu.user.userId = :userId")
    List<Long> findGroupIdsByUserId(@Param("userId") String userId);


}