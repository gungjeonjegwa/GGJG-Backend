package com.example.gungjeonjegwa.global.configuration.security.filter

import com.example.gungjeonjegwa.global.exception.ErrorCode
import com.example.gungjeonjegwa.global.exception.ErrorResponse
import com.example.gungjeonjegwa.global.exception.exceptions.BasicException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: BasicException) {
            sendError(response, e.errorCode)
        } catch (e: Exception) {
            sendError(response, ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }

    private fun sendError(res: HttpServletResponse, errorCode: ErrorCode) {
        val errorResponse = ErrorResponse(errorCode)
        val responseString = objectMapper!!.writeValueAsString(errorResponse)
        res.characterEncoding = "UTF-8"
        res.status = errorCode.code
        res.contentType = MediaType.APPLICATION_JSON_VALUE
        res.writer.write(responseString)
    }
}