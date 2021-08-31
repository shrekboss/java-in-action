## 实验一：
    
**client: port 45678**
[02:00:43.060] [INFO ] [RibbonRetryIssueClientController:22  ] - client is called
[02:00:44.083] [INFO ] [RibbonRetryIssueServerController:17  ] - http://localhost:45678/ribbonretryissueserver/wrong is called, 13600000000=>c71df0f0-040b-4195-a77f-fa6aecac7ef5
[02:00:45.077] [ERROR] [RibbonRetryIssueClientController:26  ] - send sms failed : Read timed out executing GET http://SmsClient/ribbonretryissueserver/wrong?mobile=13600000000&message=c71df0f0-040b-4195-a77f-fa6aecac7ef5
**server: port 45679**
[02:00:43.074] [http-nio-45679-exec-2] [INFO ] [o.b.c.c.h.r.RibbonRetryIssueServerController:17  ] - http://localhost:45679/ribbonretryissueserver/wrong is called, 13600000000=>c71df0f0-040b-4195-a77f-fa6aecac7ef5

这说明客户端自作主张进行了一次重试，导致短信重复发送。
解决办法
- 第一种：接口从 Get 改为 Post
- 第二种：将 MaxAutoRetriesNextServer 参数配置为 0

```
// MaxAutoRetriesNextServer 参数默认为 1，也就是 Get 请求在某个服务端节点出现问题（比如读取超时）
// 时，Ribbon 会自动重试一次

// DefaultClientConfigImpl
public static final int DEFAULT_MAX_AUTO_RETRIES_NEXT_SERVER = 1;
public static final int DEFAULT_MAX_AUTO_RETRIES = 0;

// RibbonLoadBalancedRetryPolicy
public boolean canRetry(LoadBalancedRetryContext context) {
   HttpMethod method = context.getRequest().getMethod();
   return HttpMethod.GET == method || lbContext.isOkToRetryOnAllOperations();
}

@Override
public boolean canRetrySameServer(LoadBalancedRetryContext context) {
   return sameServerCount < lbContext.getRetryHandler().getMaxRetriesOnSameServer()
         && canRetry(context);
}

@Override
public boolean canRetryNextServer(LoadBalancedRetryContext context) {
   // this will be called after a failure occurs and we increment the counter
   // so we check that the count is less than or equals to too make sure
   // we try the next server the right number of times
   return nextServerCount <= lbContext.getRetryHandler().getMaxRetriesOnNextServer()
         && canRetry(context);
}
```