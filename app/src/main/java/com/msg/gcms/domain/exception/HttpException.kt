package com.msg.gcms.domain.exception

/**
 * 요청이 올바르지 않은 경우
 * Http Status 가 400번일 때 사용
 * */
class BadRequestException(
    override val message: String?
) : RuntimeException()


/**
 * 비인증 되었을 경우 -> 인증을 해야함
 * Http Status 가 401번일 때 사용
 */
class UnauthorizedException(
    override val message: String?
) : RuntimeException()


/**
 * 권한이 없는 경우
 * Http Status 가 403번일 때 사용
 */
class ForBiddenException(
    override val message: String?
) : RuntimeException()


/**
 * 요청 받은 리소스를 찾을 수 없는 경우
 * Http Status 가 404번일 때 사용
 */
class NotFoundException(
    override val message: String?
) : RuntimeException()


/**
 * 요청이 너무 오래 걸리는 경우
 * Http Status가 408번 일 때 사용
 */
class TimeOutException(
    override val message: String?,
) : RuntimeException()


/**
 * 권한이 없을 경우
 * Http Status 가 409일 때 사용
 * */
class ConflictException(
    override val message: String?,
) : RuntimeException()


/**
 * 너무 많은 요청을 보내는 경우
 * HttpStatus 가 429일 때 사용
 */
class TooManyRequestsException(
    override val message: String?,
) : RuntimeException()


/**
 * 서버에러가 발생하는 경우
 * Http Status 가 50X일 때 사용
 */
class ServerException(
    override val message: String?,
) : RuntimeException()





