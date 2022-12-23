package com.example.gungjeonjegwa.global.exception.handler

import com.example.gungjeonjegwa.global.exception.ErrorResponse
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BasicException::class)
    fun basicExceptionHandler(request: HttpServletRequest, ex: BasicException): ResponseEntity<ErrorResponse> {
        println(request.requestURI)
        println(ex.message)
        val errorResponse = ErrorResponse(ex.errorCode)
        return ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(ex.errorCode.code))
    }
