package com.handpay.arch.stat.bean.page;

import org.springframework.data.domain.PageRequest;

/**
 * spring-data-commons PageRequest使用hessian序列化异常处理
 * http://stackoverflow.com/questions/34984166/spring-data-page-pageable-cant-deserialization-how-to-use
 * https://github.com/obiteaaron/fix-spring-data-common-deserialize
 * Created by fczheng on 2016/11/29.
 */
public class PageableRequest extends PageRequest {
    private static final long serialVersionUID = -499964834309543186L;

    public PageableRequest() {
        super(0, 10);
    }

    public PageableRequest(int page) {
        super(page, 10);
    }
}
