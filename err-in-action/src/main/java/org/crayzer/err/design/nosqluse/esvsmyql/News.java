package org.crayzer.err.design.nosqluse.esvsmyql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;


@Entity
@Document(indexName = "news", replicas = 0) //@Document注解定义了这是一个ES的索引，索引名称news，数据不需要冗余
@Table(name = "news", indexes = {@Index(columnList = "cateid")}) //@Table注解定义了这是一个MySQL表，表名news，对cateid列做索引
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class News {
    @Id
    private long id;
    @Field(type = FieldType.Keyword)
    private String category;//新闻分类名称
    private int cateid;//新闻分类ID
    @Column(columnDefinition = "varchar(500)")//@Column注解定义了在MySQL中字段，比如这里定义title列的类型是varchar(500)
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")//@Field注解定义了ES字段的格式，使用ik分词器进行分词
    private String title;//新闻标题
    @Column(columnDefinition = "text")
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;//新闻内容
}
