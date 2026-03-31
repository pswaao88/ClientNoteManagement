# 거래처 기기관리

안드로이드 전용 오프라인 앱입니다.  
거래처별 과, 기기, PC 접속 정보를 빠르게 조회하고 수정하는 용도로 만들었습니다.

## 핵심 기능

- 거래처 관리
- 과 관리
- 기기 관리
- PC 관리
- 검색
- 로컬 저장(Room)

## 기술 스택

- Kotlin
- Jetpack Compose
- Room
- ViewModel
- Navigation Compose

## 프로젝트 구조

- `app/src/main/java/com/example/ipadress/feature`
  화면별 UI
- `app/src/main/java/com/example/ipadress/data`
  Room, DAO, Repository
- `app/src/main/java/com/example/ipadress/navigation`
  내비게이션
- `app/src/main/java/com/example/ipadress/presentation`
  ViewModel

## 실행 방법

### Android Studio

1. 프로젝트 폴더를 엽니다.
2. Gradle Sync를 완료합니다.
3. 기기 또는 에뮬레이터를 연결합니다.
4. `Run app`으로 실행합니다.

### APK 직접 설치

- 디버그 APK:
  `app/build/outputs/apk/debug/app-debug.apk`

## 빌드 명령

```powershell
.\gradlew.bat assembleDebug
```

샌드박스 환경에서는 아래처럼 홈 경로를 지정해 빌드했습니다.

```powershell
$env:GRADLE_USER_HOME='D:\project_univ\Daddy\ipadress\.gradle-home'
$env:ANDROID_USER_HOME='D:\project_univ\Daddy\ipadress\.android-home'
.\gradlew.bat assembleDebug
```

## 문서

- [요구사항](docs/requirements.md)
- [프로젝트 요약 및 현황](docs/project-summary.md)
- [배포 및 실기기 테스트](docs/deployment-checklist.md)

## Git 사용

현재 저장소는 Git으로 초기화되어 있고 `main` 브랜치가 GitHub와 연결되어 있습니다.

```powershell
git -c safe.directory=D:/project_univ/Daddy/ipadress status
git -c safe.directory=D:/project_univ/Daddy/ipadress add .
git -c safe.directory=D:/project_univ/Daddy/ipadress commit -m "설명"
git -c safe.directory=D:/project_univ/Daddy/ipadress push
```
