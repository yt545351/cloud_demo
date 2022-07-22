package com.example.system.utils;

import com.example.system.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

/**
 * ES工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class EsUtil {
    @Value("${spring.elasticsearch.uris}")
    private static String url;

    @SneakyThrows
    public static void main(String[] args) {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        createRequest(client);
//        getRequest(client);
//        deleteRequest(client);
//        createIndex(client);
//        updateIndex(client);
//        getIndex(client);
//        deleteIndex(client);
//        bulkCreateIndex(client);
//        bulkDeleteIndex(client);
//        searchIndex(client);
//        searchHighLight(client);
//        searchAggregation(client);
//        searchGroup(client);
        client.close();
    }

    /**
     * 创建索引
     *
     * @param client 客户端
     */
    @SneakyThrows
    public static void createRequest(RestHighLevelClient client) {
        CreateIndexRequest request = new CreateIndexRequest("user");
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        log.info("操作状态:{}", acknowledged);

    }

    /**
     * 查看索引
     *
     * @param client 客户端
     */
    @SneakyThrows
    public static void getRequest(RestHighLevelClient client) {
        GetIndexRequest request = new GetIndexRequest("user");
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        log.info("aliases:{}", response.getAliases());
        log.info("mappings:{}", response.getMappings());
        log.info("settings:{}", response.getSettings());
    }

    /**
     * 删除索引
     *
     * @param client 客户端
     */
    @SneakyThrows
    public static void deleteRequest(RestHighLevelClient client) {
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        log.info("操作结果:{}", response.isAcknowledged());
    }

    /**
     * 新增文档
     *
     * @param client 客户端
     */
    @SneakyThrows
    public static void createIndex(RestHighLevelClient client) {
        //请求对象
        IndexRequest request = new IndexRequest();
        //设置索引及唯一性标识
        request.index("user").id("1001");
        User user = new User();
        user.setName("张三");
        user.setAge(18);
        user.setSex("男");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        //添加文档数据，为json格式
        request.source(json, XContentType.JSON);
        //客户端发送请求，获取响应对象
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        log.info("_index:{}", response.getIndex());
        log.info("_id:{}", response.getId());
        log.info("_result:{}", response.getResult());
    }

    /**
     * 修改文档
     *
     * @param client 客户端
     */
    @SneakyThrows
    public static void updateIndex(RestHighLevelClient client) {
        //请求对象
        UpdateRequest request = new UpdateRequest();
        //配置修改参数
        request.index("user").id("1001");
        //设置请求体，对数据进行修改
        request.doc(XContentType.JSON, "sex", "女");
        //客户端发送请求，获取响应对象
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        log.info("_index:{}", response.getIndex());
        log.info("_id:{}", response.getId());
        log.info("_result:{}", response.getResult());
    }

    /**
     * 查询文档
     *
     * @param client 客户端
     */
    @SneakyThrows
    public static void getIndex(RestHighLevelClient client) {
        //请求对象
        GetRequest request = new GetRequest().index("user").id("1001");
        //客户端发送请求，获取响应对象
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        log.info("_index:{}", response.getIndex());
        log.info("_id:{}", response.getId());
        log.info("_type:{}", response.getType());
        log.info("source:{}", response.getSourceAsString());
    }

    /**
     * 删除文档
     *
     * @param client 客户端
     */
    @SneakyThrows
    public static void deleteIndex(RestHighLevelClient client) {
        //请求对象
        DeleteRequest request = new DeleteRequest().index("user").id("1001");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        log.info("{}", response.toString());
    }

    /**
     * 批量新增
     *
     * @param client 客户端
     */
    @SneakyThrows
    public static void bulkCreateIndex(RestHighLevelClient client) {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("user").id("1001").source(XContentType.JSON, "name", "张三", "age", 20, "sex", "男"));
        request.add(new IndexRequest().index("user").id("1002").source(XContentType.JSON, "name", "李四", "age", 30, "sex", "女"));
        request.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON, "name", "王五", "age", 40, "sex", "男"));
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        log.info("took:{}", response.getTook());
        log.info("items:{}", response.getItems());

    }

    /**
     * 批量删除
     *
     * @param client 客户端
     */
    @SneakyThrows
    public static void bulkDeleteIndex(RestHighLevelClient client) {
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest().index("user").id("1001"));
        request.add(new DeleteRequest().index("user").id("1002"));
        request.add(new DeleteRequest().index("user").id("1003"));
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        log.info("took:{}", response.getTook());
        log.info("items:{}", response.getItems());
    }

    /**
     * 请求体查询
     *
     * @param client 客户端
     */
    @SneakyThrows
    public static void searchIndex(RestHighLevelClient client) {
        //创建搜索请求对象
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder;

        //查询所有数据
        sourceBuilder = searchAll(new SearchSourceBuilder());
        //关键字查询
//        sourceBuilder = searchKeyword(new SearchSourceBuilder());
        //分页查询
//        sourceBuilder = searchPag(new SearchSourceBuilder());
        //数据排序
//        sourceBuilder = searchSort(new SearchSourceBuilder());
        //过滤字段
//        sourceBuilder = searchFilter(new SearchSourceBuilder());
        //Bool查询
//        sourceBuilder = searchBool(new SearchSourceBuilder());
        //范围查询
//        sourceBuilder = searchRange(new SearchSourceBuilder());
        //模糊查询
//        sourceBuilder = searchFuzzy(new SearchSourceBuilder());

        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = response.getHits();
        log.info("took:{}", response.getTook());
        log.info("timeout:{}", response.isTimedOut());
        log.info("total:{}", hits.getTotalHits());
        log.info("MaxSource:{}", hits.getMaxScore());
        log.info("hits-------->");

        for (SearchHit hit : hits) {
            log.info("{}", hit.getSourceAsString());
        }
        log.info("<--------");

    }

    /**
     * 高亮查询
     *
     * @param client 客户端
     */
    @SneakyThrows
    public static void searchHighLight(RestHighLevelClient client) {
        SearchRequest request = new SearchRequest().indices("user");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termsQuery("sex", "女"));
        //构建高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("sex");

        sourceBuilder.highlighter(highlightBuilder);
        request.source(sourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        log.info("took:{}", response.getTook());
        log.info("timeout:{}", response.isTimedOut());
        log.info("total:{}", hits.getTotalHits());
        log.info("MaxSource:{}", hits.getMaxScore());
        log.info("hits-------->");
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            log.info("{}", sourceAsString);
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            log.info("{}", highlightFields);
        }
        log.info("<--------");


    }

    /**
     * 聚合查询
     *
     * @param client 客户端
     */
    @SneakyThrows
    public static void searchAggregation(RestHighLevelClient client) {
        SearchRequest request = new SearchRequest().indices("user");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(AggregationBuilders.max("maxAge").field("age"));
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        log.info("{}", response);
//        String responseStr = response.toString();
//        Map responseMap = JSON.parseObject(responseStr, Map.class);
//        String aggregationsStr = responseMap.get("aggregations").toString();
//        Map aggregationsMap = JSON.parseObject(aggregationsStr, Map.class);
//        String s1 = aggregationsMap.get("max#maxAge").toString();
//        Map map1 = JSON.parseObject(s1, Map.class);
//        Object value = map1.get("value");
//        System.out.println(value);

    }

    /**
     * 分组统计
     *
     * @param client 客户端
     */
    @SneakyThrows
    public static void searchGroup(RestHighLevelClient client) {
        SearchRequest request = new SearchRequest().indices("user");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.aggregation(AggregationBuilders.terms("age_groupby").field("age"));
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        log.info("{}", response);
    }

    /**
     * 查询所有数据
     *
     * @param sourceBuilder 源构建器
     * @return {@link SearchSourceBuilder}
     */
    public static SearchSourceBuilder searchAll(SearchSourceBuilder sourceBuilder) {
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        return sourceBuilder;
    }

    /**
     * 关键字查询
     *
     * @param sourceBuilder 源构建器
     * @return {@link SearchSourceBuilder}
     */
    public static SearchSourceBuilder searchKeyword(SearchSourceBuilder sourceBuilder) {
        sourceBuilder.query(QueryBuilders.termQuery("sex", "男"));
        return sourceBuilder;
    }

    /**
     * 分页查询
     *
     * @param sourceBuilder 源构建器
     * @return {@link SearchSourceBuilder}
     */
    public static SearchSourceBuilder searchPag(SearchSourceBuilder sourceBuilder) {
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        //当前页起始索引(第一条数据的顺序号)from
        sourceBuilder.from(0);
        //每页显示多少条size
        sourceBuilder.size(2);
        return sourceBuilder;
    }

    /**
     * 数据排序
     *
     * @param sourceBuilder 源构建器
     * @return {@link SearchSourceBuilder}
     */
    public static SearchSourceBuilder searchSort(SearchSourceBuilder sourceBuilder) {
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        sourceBuilder.sort("age", SortOrder.DESC);
        return sourceBuilder;
    }

    /**
     * 过滤字段
     *
     * @param sourceBuilder 源构建器
     * @return {@link SearchSourceBuilder}
     */
    public static SearchSourceBuilder searchFilter(SearchSourceBuilder sourceBuilder) {
        //查询所有数据
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        //排除
        String[] excludes = {};
        //包含
        String[] includes = {"name", "age"};
        sourceBuilder.fetchSource(includes, excludes);
        return sourceBuilder;
    }

    /**
     * Bool查询
     *
     * @param sourceBuilder 源构建器
     * @return {@link SearchSourceBuilder}
     */
    public static SearchSourceBuilder searchBool(SearchSourceBuilder sourceBuilder) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //必须包含
        boolQueryBuilder.must(QueryBuilders.matchQuery("age", "30"));
        //一定不包含
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("name", "张三"));
        //可能包含
        boolQueryBuilder.should(QueryBuilders.matchQuery("sex", "男"));
        sourceBuilder.query(boolQueryBuilder);
        return sourceBuilder;
    }

    /**
     * 范围查询
     *
     * @param sourceBuilder 源构建器
     * @return {@link SearchSourceBuilder}
     */
    public static SearchSourceBuilder searchRange(SearchSourceBuilder sourceBuilder) {
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
        //大于等于
        rangeQuery.gte("30");
        //小于等于
        rangeQuery.lte("40");
        sourceBuilder.query(rangeQuery);
        return sourceBuilder;
    }

    /**
     * 模糊查询
     *
     * @param sourceBuilder 源构建器
     * @return {@link SearchSourceBuilder}
     */
    public static SearchSourceBuilder searchFuzzy(SearchSourceBuilder sourceBuilder) {
        sourceBuilder.query(QueryBuilders.fuzzyQuery("name", "李四").fuzziness(Fuzziness.ONE));
        return sourceBuilder;
    }
}
