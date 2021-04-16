package com.example.cleanarchitechture.data

import com.example.cleanarchitechture.domain.Operation
import com.example.cleanarchitechture.domain.OperationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext

class OperationsLocalSource : OperationsRepository {

    private var operationsFlow: MutableSharedFlow<List<Operation>> = MutableSharedFlow(replay = 1)
    private var operations = mutableListOf(Operation(1, 2, 3), Operation(3, 6, 9))


    override suspend fun getOperations() = operationsFlow
            .apply {
                emit(operations)
            }
    override suspend fun addOperation(operation: Operation) {
        withContext(Dispatchers.IO) {
            delay(2000)
        }
        operations.add(operation)
        operationsFlow.emit(operations)
    }

    override suspend fun removeOperation(operation: Operation) {
        withContext(Dispatchers.IO) {
            delay(2000)
        }
        operations.remove(operation)
        operationsFlow.emit(operations)
    }


}