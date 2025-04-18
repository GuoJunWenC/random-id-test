package org.example.api.chain.responsibility;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component("ChainPatternDemo")
public class ChainPatternDemo {

    //自动注入各个责任链的对象
    @Autowired
    private List<AbstractHandler> abstractHandleList;

    private AbstractHandler abstractHandler;

    //spring注入后自动执行，责任链的对象连接起来
    @PostConstruct
    public void initializeChainFilter() {
        for (int i = 0; i < abstractHandleList.size(); i++) {
            if (i == 0) {
                abstractHandler = abstractHandleList.get(0);
            } else {
                AbstractHandler currentHandler = abstractHandleList.get(i - 1);
                AbstractHandler nextHandler = abstractHandleList.get(i);
                currentHandler.setNextHandler(nextHandler);
            }
        }
    }

    //直接调用这个方法使用
    public Response exec(Request request, Response response) {
        abstractHandler.filter(request, response);
        return response;
    }

    public AbstractHandler getAbstractHandler() {
        return abstractHandler;
    }

    public void setAbstractHandler(AbstractHandler abstractHandler) {
        this.abstractHandler = abstractHandler;
    }
}