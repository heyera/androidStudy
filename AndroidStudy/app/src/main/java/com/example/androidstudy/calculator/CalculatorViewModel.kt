package com.example.androidstudy.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val calculationHistoryDataStore: CalculationHistoryDataStore
) : ViewModel() {

    private val _inputText = MutableStateFlow("")
    val inputText = _inputText.asStateFlow()


    private var lastOperator = false // 마지막 입력이 연산자인지 확인

    private val _history = MutableStateFlow<List<String>>(emptyList())
    val history = _history.asStateFlow()

    init {
        viewModelScope.launch {
            calculationHistoryDataStore.historyFlow.collect { historyList ->
                _history.value = historyList
            }
        }
    }

    // 입력 처리
    fun handleInput(value: String) {
        when (value) {
            "<-" -> deleteLastChar()  // 마지막 문자 삭제
            "C" -> clearAll()         // 전체 초기화
            "=" -> calculateResult()
            in listOf("+", "-", "*", "/") -> handleOperator(value)
            else -> handleNumber(value)
        }
    }

    // 숫자 입력 처리
    private fun handleNumber(number: String) {
        if (lastOperator) {
            lastOperator = false
        }
        _inputText.value += number
    }

    // 연산자 입력 처리
    private fun handleOperator(operator: String) {
        if (_inputText.value.isNotEmpty()) {
            if (lastOperator) {
                // 마지막 입력이 연산자라면, 현재 연산자로 교체
                _inputText.value = _inputText.value.dropLast(1) + operator

            } else {
                _inputText.value += operator
            }

            lastOperator = true
        }
    }

    // 수식 계산
    private fun calculateResult() {
        try {

            val expression = _inputText.value   //  수식 저장
            val result = net.objecthunter.exp4j.ExpressionBuilder(expression).build().evaluate().toInt()
            _inputText.value = result.toString()
            lastOperator = false

            viewModelScope.launch {
                saveCalculation(expression, result.toString()) //수식과 결과 함께 저장
            }

        } catch (e: Exception) {
            _inputText.value = "오류"
        }
    }


    // 마지막 입력 문자 삭제 (백스페이스)
    private fun deleteLastChar() {

        if (_inputText.value.isNotEmpty()) {
            _inputText.value = _inputText.value.dropLast(1)
            lastOperator = _inputText.value.isNotEmpty() && _inputText.value.last() in listOf(
                '+',
                '-',
                '*',
                '/'
            )
        }
    }

    // 전체 초기화
    private fun clearAll() {
        _inputText.value = ""
        lastOperator = false
    }

    private suspend fun saveCalculation(expression: String, result: String) {
        val fullCalculation = "$expression\n = $result"
        calculationHistoryDataStore.addCalculation(fullCalculation)
    }

    suspend fun clearHistory() {
        calculationHistoryDataStore.clearHistory()
    }
}
