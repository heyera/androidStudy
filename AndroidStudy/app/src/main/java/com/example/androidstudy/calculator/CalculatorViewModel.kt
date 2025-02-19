package com.example.androidstudy.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {

    private val _inputText = MutableStateFlow("")
    val inputText = _inputText.asStateFlow()


    private var lastOperator = false // 마지막 입력이 연산자인지 확인

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
            var expressionStr = _inputText.value

            if (expressionStr.isNotEmpty() && expressionStr.last() in listOf('+', '-', '*', '/')) {
                expressionStr = expressionStr.dropLast(1)
            }

            val expression = ExpressionBuilder(expressionStr).build()
            val result = expression.evaluate().toInt()

            _inputText.value = result.toString()
            lastOperator = false

        } catch (e: Exception) {
            _inputText.value = "오류"
        }
    }


    // 마지막 입력 문자 삭제 (백스페이스)
    private fun deleteLastChar() {

        if (_inputText.value.isNotEmpty()) {
            _inputText.value = _inputText.value.dropLast(1)
            lastOperator = _inputText.value.isNotEmpty() && _inputText.value.last() in listOf('+', '-', '*', '/')
        }
    }

    // 전체 초기화
    private fun clearAll() {
        _inputText.value = ""
        lastOperator = false
    }
}
