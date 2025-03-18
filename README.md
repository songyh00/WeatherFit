# 🚧 WeatherFit 개발 가이드 (`dev` 브랜치)

## 🛠️ 개발 브랜치 규칙
- 이 브랜치는 **개발 브랜치**입니다.
- **절대 `main` 브랜치에 직접 푸시하지 마세요!**
- 기능 개발 시 반드시 **새로운 브랜치(feature-작업이름)에서 작업 후 PR**을 통해 `dev`로 병합하세요.
- `dev`로 병합 완료하여 기능 개발 완료한 새로운 브랜치(feature-작업이름)는 pr후 **삭제**하세요.

---

## 📌 Git 작업 흐름

### ✅ 1. `dev` 브랜치 최신화
```sh
git checkout dev
git pull origin dev
```

### ✅ 2. 새 기능 브랜치 생성
```sh
git checkout -b feature-작업이름
```

### ✅ 3. 작업 후 커밋 & 푸시
```sh
git add .
git commit -m "새로운 기능 추가"
git push origin feature-작업이름
```

### ✅ 4. PR 생성 후 리뷰 요청
- GitHub에서 feature-작업이름 → dev로 Pull Request(PR) 생성
- 팀원들의 코드 리뷰를 거쳐 병합

---

## 🔥 개발 시 주의할 점
1. dev 브랜치는 실행 가능한 코드만 유지 (작업 중인 코드는 feature-작업이름 에서 진행)
2. .env, application.properties 등 민감한 파일을 Git에 올리지 않도록 주의
3. 커밋 메시지는 명확하게 작성
    - fix: 로그인 버그 수정
    - feat: 사용자 프로필 페이지 추가
    - chore: 코드 리팩토링

---

## 📌 협업을 위한 체크리스트
- dev 브랜치에서 git pull origin dev로 최신 코드 받아오기
- 새로운 기능 브랜치에서 작업 진행 (feature-작업이름)
- git push origin feature-작업이름 후, GitHub에서 PR 생성
- 코드 리뷰 후 병합 (feature-작업이름 → dev)
- feature-작업이름 삭제
- dev 브랜치가 안정화되면 main 브랜치로 병합
