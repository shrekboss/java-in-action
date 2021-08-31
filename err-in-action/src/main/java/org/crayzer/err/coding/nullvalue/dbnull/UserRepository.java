package org.crayzer.err.coding.nullvalue.dbnull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "SELECT SUM(score) FROM `user`")
    Long wrong1();

    @Query(nativeQuery = true, value = "SELECT COUNT(score) FROM `user`")
    Long wrong2();

    @Query(nativeQuery = true, value = "SELECT * FROM `user` WHERE score=null")
    List<User> wrong3();

    // MySQL 中 sum 函数没统计到任何记录时，会返回 null 而不是 0，可以使用 IFNULL 函数把 null 转换为 0
    @Query(nativeQuery = true, value = "SELECT IFNULL(SUM(score),0) FROM `user`")
    Long right1();

    // MySQL 中 count 字段不统计 null 值，COUNT(*) 才是统计所有记录数量的正确方式。
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM `user`")
    Long right2();

    // MySQL 中使用诸如 =、<、> 这样的算数比较操作符比较 NULL 的结果总是 NULL
    @Query(nativeQuery = true, value = "SELECT * FROM `user` WHERE score IS NULL")
    List<User> right3();
}
