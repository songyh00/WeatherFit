# WeatherFit
날씨와 기온을 기반으로 오늘 입기 좋은 코디를 추천해주는 웹 서비스.
사용자는 지역을 검색해 오늘/내일 날씨를 확인하고,
날씨·기온·성별·스타일에 맞는 옷 추천을 받을 수 있습니다.

---

## 🧭 프로젝트 개요
- **기능:** 지역 기반 실시간 날씨 정보 제공 + 날씨에 맞는 코디 추천
- **목적:** 날씨에 맞는 옷 선택의 고민을 줄이고, 직관적인 코디 제안 제공
- **특징:** 공공 API(기상청)와 지도 API(카카오)를 활용한 실시간 날씨 기반 추천

---

## ⚙️ 기술 스택

### 🖥 Frontend (WeatherFitFrontend)
- **Framework:** React + Vite
- **Routing:** React Router DOM
- **Styling:** styled-components
- **State Management:** React Hooks
- **API Communication:** Axios
- **IDE:** VSCode
- **OS:** Windows / macOS

### ☕ Backend (WeatherFitBackend)
- **Framework:** Spring Boot
- **Language:** Java
- **Build Tool:** Gradle
- **Dependencies:** Spring Web, Spring Data JPA,  Validation, Lombok
- **Database:** MySQL
- **External APIs:** 기상청 날씨 API, Kakao Map / Local API
- **IDE:** IntelliJ IDEA / Eclipse
- **OS:** Windows / macOS

---

## 📂 프로젝트 구조
```
WeatherFit/
├── WeatherFitFrontend/   # 프론트엔드 (React + Vite)
├── WeatherFitBackend/    # 백엔드 (Spring Boot)
├── .gitignore
├── package.json
└── README.md

```

