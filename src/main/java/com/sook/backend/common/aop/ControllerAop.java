package com.sook.backend.common.aop;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class ControllerAop {
	@Around("execution(* com.sook.backend..*Controller.*(..))")
	public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		List<Object> args = List.of(joinPoint.getArgs());

		String controller = joinPoint.getTarget().getClass().getSimpleName();

		String method = request.getMethod();
		String path = request.getRequestURI();
		String addr = request.getRemoteAddr();

		log.info("##########################################################################");
		log.info("# REQUEST | CONTROLLER = {} | METHOD = {} | PATH = {} | REMOTE_ADDR = {} | IN_PARAMS = {}",
				controller, method, path, addr, args);
		log.info("##########################################################################");

		return joinPoint.proceed();
	}
}
