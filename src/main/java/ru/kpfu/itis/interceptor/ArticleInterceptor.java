package ru.kpfu.itis.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ArticleInterceptor implements HandlerInterceptor {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    private final String SLUG_PATH = "/{slug}";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Map<RequestMappingInfo, HandlerMethod> handlerMap = this.getHandlers(request);

            // only one handler for this request
            if (handlerMap.size() < 2) {
                return true;
            }

            // at least two handlers for this request
            // need check that this handler is not ArticleController's handler for SLUG_PATH

            for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMap.entrySet()) {
                if (entry.getKey().getPatternsCondition().getPatterns().contains(SLUG_PATH)) {
                    // found the handler for '/{slug}', check that it is the same handler that interceptor took
                    // as an argument
                    if (entry.getValue().equals(handlerMethod)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    protected Map<RequestMappingInfo, HandlerMethod> getHandlers(HttpServletRequest request) {

        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getMatchingCondition(request) != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return map;
    }

}
