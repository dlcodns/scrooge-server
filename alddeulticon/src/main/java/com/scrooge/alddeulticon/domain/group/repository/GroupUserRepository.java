package com.scrooge.alddeulticon.domain.group.repository;
import com.scrooge.alddeulticon.domain.group.entity.GroupRoom;
import com.scrooge.alddeulticon.domain.user.entity.User;
import com.scrooge.alddeulticon.domain.group.entity.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {

    boolean existsByGroupAndUser(GroupRoom group, User user);

}