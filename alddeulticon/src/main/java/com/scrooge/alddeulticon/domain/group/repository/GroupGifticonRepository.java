package com.scrooge.alddeulticon.domain.group.repository;

import com.scrooge.alddeulticon.domain.group.entity.GroupGifticon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.scrooge.alddeulticon.domain.group.entity.GroupRoom;

import java.util.List;

public interface GroupGifticonRepository extends JpaRepository<GroupGifticon, Long> {

    @Query("SELECT gg FROM GroupGifticon gg WHERE gg.group.id IN :groupIds")
    List<GroupGifticon> findByGroupIds(@Param("groupIds") List<Long> groupIds);

    List<GroupGifticon> findByGroup(GroupRoom group);
}