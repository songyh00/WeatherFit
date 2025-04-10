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
1. **개발은 `feature-작업이름` 브랜치에서 진행!**
2. `main` 브랜치는 **배포용 브랜치**로, **Pull Request(PR)를 통해서만 병합 가능**
3. `feature-작업이름` 브랜치에서 개발 후 코드 리뷰를 거쳐 `dev`으로 PR, `dev`가 충분히 안정화되면 `main`으로 병합
4. PR 승인 후 **관리자(팀 리더)만 `main` 브랜치에 병합 가능**

---

## 🛠️ 기술 스택  
- **Backend:** Spring Boot, MySQL, AWS 
- **Frontend:** React (Vite), Tailwind CSS  
- **Deployment:** AWS S3 + CloudFront (예정)  

---

## 📜 라이선스  
이 프로젝트는 `MIT License`를 따릅니다.  

---

# 🚧 WeatherFit 개발 가이드 (`dev` 브랜치)

## 🛠️ 개발 브랜치 규칙
- 이 브랜치는 **개발 브랜치**입니다.
- **절대 `main` 브랜치에 직접 푸시하지 마세요!**
- 기능 개발 시 반드시 **새로운 브랜치(feature-작업이름)에서 작업 후 PR**을 통해 `dev`로 병합하세요.
- `dev`로 병합 완료하여 기능 개발 완료한 새로운 브랜치(feature-작업이름)는 pr후 **삭제**하세요.

---

## 📌 브랜치 설명
- main: 완전히 안정화된 코드를 포함하는 브랜치. 최종 배포용.
- dev: 모든 기능이 모여서 통합되고, 코드 리뷰가 진행되는 브랜치.
- feature-작업이름: 각자 새로운 기능을 개발하는 브랜치

---

## 📌 Git 작업 흐름

### ✅ 1. 깃허브 브랜치 최신화
```sh
git fetch origin --prune
```

### ✅ 2. `dev` 브랜치 최신화
```sh
git checkout dev
git pull origin dev
```

### ✅ 3. 새 기능 브랜치 생성
```sh
git checkout -b feature-작업이름
```

### ✅ 4. 내 브랜치에 dev내용 최신화
```sh
git fetch origin
git merge origin/dev
```

### ✅ 5. 작업 후 커밋 & 푸시
```sh
git status
git add .
git commit -m "새로운 기능 추가"
git push origin feature-작업이름
```

### ✅ 6. PR 생성 후 리뷰 요청
- GitHub에서 feature-작업이름 → dev로 Pull Request(PR) 생성
- PR 제목은 feat: 기능 추가 / fix: 버그 수정 식으로 작성
- Reviewer 지정해서 코드 리뷰 요청 or 완벽할경우 스스로 머지 승인

### ✅ 7. 기타 github 명령어
```sh
# 수정한 파일 복구 (수정 취소)
git restore 파일명

# 스테이징(=git add한거) 취소
git restore --staged 파일명

# 로컬 브랜치 삭제
git branch -d 브랜치명

# 리모트(깃허브) 브랜치 삭제
git push origin --delete 브랜치명

# 커밋 수정 (최근 커밋 메시지 고치기)
git commit --amend

# 커밋 되돌리기 (취소하고 다시 커밋)
git reset --soft HEAD~1

# 브랜치 목록 보기 (로컬/리모트)
git branch -a

# 리모트 목록 보기
git remote -v
```

---

## 🔥 개발 시 주의할 점
1. dev 브랜치는 실행 가능한 코드만 유지 (작업 중인 코드는 feature-작업이름 에서 진행)
2. .env, application.properties 등 민감한 파일을 Git에 올리지 않도록 주의
3. 커밋 메시지는 명확하게 작성

    - feat: 새로운 기능 추가 (예: 사용자 프로필 페이지 추가)
    - fix: 버그 수정 (예: 로그인 오류 수정)
    - chore: 개발과 무관한 단순 작업 (예: 패키지 업데이트, 설정 파일 수정)
    - refactor: 코드 리팩토링 (예: 불필요한 코드 정리)
    - docs: 문서 수정 (예: README 수정)

---

## 📌 협업을 위한 체크리스트
- dev 브랜치에서 git pull origin dev로 최신 코드 받아오기
- 새로운 기능 브랜치에서 작업 진행 (feature-작업이름)
- git push origin feature-작업이름 후, GitHub에서 PR 생성
- 코드 리뷰 후 병합 (feature-작업이름 → dev)
- feature-작업이름 삭제
- dev 브랜치가 안정화되면 main 브랜치로 병합

---

## 💾 DB 초기화 방법

1. MySQL에서 `weatherfit` 데이터베이스 생성

2. 아래 명령어로 테이블과 초기 데이터를 설정하세요:

```bash
# 스키마
mysql -u root -p weatherfit < sql/schema.sql

# DATA
mysql -u root -p weatherfit < sql/clothes_data.sql
```
※ SQL 파일 경로가 다를 경우 해당 경로를 맞춰서 입력하세요.

