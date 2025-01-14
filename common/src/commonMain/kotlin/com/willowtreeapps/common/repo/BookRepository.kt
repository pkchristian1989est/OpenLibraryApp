package com.willowtreeapps.common.repo


interface BookRepository {
    suspend fun search(query: String): GatewayResponse<List<Book>, GenericError>
}
