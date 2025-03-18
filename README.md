# 🌦️ WeatherFit

## 📌 프로젝트 소개
**WeatherFit**은 대한민국 기상청 API를 활용하여 날씨에 맞는 코디를 추천하는 웹 애플리케이션입니다.  
Spring Boot 백엔드 & React 프론트엔드를 기반으로 개발 중입니다.

---

## 🚀 프로젝트 구조
```plaintext
WeatherFit/
├── WeatherFitBackend/ # Spring Boot 백엔드
├── WeatherFitFrontend/ # React 프론트엔드
├── .gitignore # 공통 .gitignore 파일
└── README.md # 프로젝트 소개
```

---

## 🚨 Git 규칙 (⚠️ `main` 브랜치에서 직접 `push` 금지!)
1. **개발은 `dev` 브랜치에서 진행!**
2. `main` 브랜치는 **배포용 브랜치**로, **Pull Request(PR)를 통해서만 병합 가능**
3. `feature-작업이름` 브랜치에서 개발 후 `dev`으로 PR, `dev` 브랜치에서 코드 리뷰를 거쳐 `main`으로 병합
4. PR 승인 후 **관리자(팀 리더)만 `main` 브랜치에 병합 가능**

```sh
# ❌ main에서 직접 push 금지!
git checkout main
git push origin main  # ❌ 금지!
```

---

## 🛠️ 기술 스택  
- **Backend:** Spring Boot, MySQL, AWS (예정)  
- **Frontend:** React (Vite), Tailwind CSS  
- **Deployment:** AWS S3 + CloudFront (예정)  

---

## 📜 라이선스  
이 프로젝트는 `MIT License`를 따릅니다.  

