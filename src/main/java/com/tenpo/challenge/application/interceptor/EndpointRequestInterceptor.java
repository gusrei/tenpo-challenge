package com.tenpo.challenge.application.interceptor;

import com.tenpo.challenge.application.dto.SecretsFields;
import com.tenpo.challenge.domain.RequestEntity;
import com.tenpo.challenge.services.EndpointService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Component
public class EndpointRequestInterceptor extends HandlerInterceptorAdapter {

    private static final String SENSIBLE_MASK = "***";

    private final EndpointService endpointService;

    public EndpointRequestInterceptor(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        persistEndpointRequest(request);
        return true;
    }

    private void persistEndpointRequest(HttpServletRequest request) {
        RequestEntity endpoint = new RequestEntity();
        endpoint.setMethod(request.getMethod());
        endpoint.setUri(request.getRequestURI());
        endpoint.setUsername(getUserDetails(request.getSession()));
        endpoint.setParameters(convert(request.getParameterMap()));
        endpointService.save(endpoint);
    }

    private String convert(final Map<String, String[]> parameterMap) {
        var map = new HashMap<String, String[]>(parameterMap);
        maskSensibleData(map);
        JSONObject jObject = new JSONObject(map);
        return jObject.toJSONString();
    }

    private void maskSensibleData(HashMap<String, String[]> map) {
        SecretsFields.stream().forEach(f ->
             map.replace(f.getFieldName(), new String[]{SENSIBLE_MASK})
        );
    }

    public static boolean isUserLogged() {
        try {
            return !SecurityContextHolder.getContext().getAuthentication()
                    .getName().equals("anonymousUser");
        } catch (Exception e) {
            return false;
        }
    }

    private String getUserDetails(HttpSession session) {
        if (isUserLogged()) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }else{
            return null;
        }

    }

}