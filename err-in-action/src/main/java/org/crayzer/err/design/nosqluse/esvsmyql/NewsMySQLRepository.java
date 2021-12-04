package org.crayzer.err.design.nosqluse.esvsmyql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsMySQLRepository extends JpaRepository<org.crayzer.err.design.nosqluse.esvsmyql.News, Long> {
    //JPA：搜索分类等于cateid参数，且内容同时包含关键字keyword1和keyword2，计算符合条件的新闻总数量
    long countByCateidAndContentContainingAndContentContaining(int cateid, String keyword1, String keyword2);
}
