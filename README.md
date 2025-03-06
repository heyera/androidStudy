# Android Study - 계산기 및 설정 저장 프로젝트

## 개요
이 프로젝트는 **안드로이드 개발 방법을 학습하며 적용하는 토이 프로젝트**입니다.  
Jetpack Compose, DataStore, Hilt, ViewModel 등의 최신 안드로이드 아키텍처를 활용하여 기능을 구현하고 있으며,  
새로운 기능을 추가하고 있습니다.

---

</br>

## 📌 목차
- [계산기](#-계산기)
- [설정 데이터 저장 기능](#-설정-데이터-저장-기능)
- [기술 스택](#-기술-스택)
- [프로젝트 실행 방법](#-프로젝트-실행-방법)

</br>

---

</br>

## 📱 계산기
계산기 기능에서는 사용자의 입력을 받아 연산을 수행하고, 계산 기록을 저장 및 관리합니다.

### 🔹 사용된 기술 및 패턴
- **MVVM (Model-View-ViewModel)** 아키텍처
- **DataStore**를 이용한 계산 기록 저장
- **Hilt (Dagger Hilt)** 를 이용한 DataStore 의존성 주입
- **Jetpack Compose**를 활용한 UI 구성
- **Exp4j** 라이브러리를 활용한 수식 연산

</br>

---

</br>


## ⚙️ 설정 데이터 저장 기능
설정 화면에서는 사용자의 기본 설정(스위치, 알람 설정 등)을 저장하고 관리할 수 있습니다.

### 🔹 사용된 기술 및 패턴
- **MVVM (Model-View-ViewModel)** 아키텍처
- **DataStore**를 이용한 설정 데이터 저장
- **Hilt (Dagger Hilt)** 를 이용한 의존성 주입
- **Jetpack Compose**를 활용한 UI 구성

</br>

---

</br>

</br>

## 🛠️ 기술 스택
- **언어:** Kotlin
- **UI:** Jetpack Compose, Material 3
- **아키텍처:** MVVM (Model-View-ViewModel)
- **데이터 저장:** DataStore Preferences
- **의존성 주입:** Hilt (Dagger Hilt)
- **수식 계산:** Exp4j 라이브러리

</br>

---

</br>

</br>

## 🚀 프로젝트 실행 방법
1. 프로젝트를 클론합니다.
   ```sh
   git clone https://github.com/your-repository/android-study.git
