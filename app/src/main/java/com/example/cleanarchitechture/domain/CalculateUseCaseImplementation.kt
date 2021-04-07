package com.example.cleanarchitechture.domain

class CalculateUseCaseImplementation(
    val calculateRepository: CalculateRepository,
    val operationsRepository: OperationsRepository
) : CalculateUseCase {

    override fun calculate(first: Int, second: Int): Int {
        val rezult = calculateRepository.calculate(Operation(first, second))
        val operation = Operation(first, second, rezult)
        operationsRepository.addOperation(operation)
        return rezult
    }



}