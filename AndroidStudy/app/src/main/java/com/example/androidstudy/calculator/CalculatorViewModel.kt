package com.example.androidstudy.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {

    private val _inputText = MutableStateFlow("")
    val inputText = _inputText.asStateFlow()

    private val _resultText = MutableStateFlow("")
    val resultText = _resultText.asStateFlow()

    private var lastOperator = false // 마지막 입력이 연산자인지 확인

    // 입력 처리
    fun handleInput(value: String) {
        when (value) {
            "지우기" -> deleteLastChar()  // 마지막 문자 삭제
            "C" -> clearAll()            // 전체 초기화
            "=" -> calculateResult()
            in listOf("+", "-", "*", "/") -> handleOperator(value)
            else -> handleNumber(value)
        }
    }

    // 숫자 입력 처리
    private fun handleNumber(number: String) {
        if (lastOperator) {
            _inputText.value = "" // 연산자 입력 후 새로운 숫자가 오면 초기화
            lastOperator = false
        }
        _inputText.value += number
    }

    // 연산자 처리 (즉시 계산 후 연산 이어가기)
    private fun handleOperator(operator: String) {
        if (_inputText.value.isNotEmpty()) {
            calculateResult() // 기존 입력값 먼저 계산
            _inputText.value += operator
            lastOperator = true
        }
    }

    // 수식 계산
    private fun calculateResult() {
        try {
            val expression = ExpressionBuilder(_inputText.value).build()
            val result = expression.evaluate()
            _resultText.value = result.toString()
            _inputText.value = result.toString() // 결과를 다음 입력값으로 설정
            lastOperator = false
        } catch (e: Exception) {
            _resultText.value = "오류"
        }
    }

    // 마지막 입력 문자 삭제 (백스페이스)
    private fun deleteLastChar() {
        if (_inputText.value.isNotEmpty()) {
            _inputText.value = _inputText.value.dropLast(1)
        }
    }

    // 전체 초기화
    private fun clearAll() {
        _inputText.value = ""
        _resultText.value = ""
        lastOperator = false
    }
}
