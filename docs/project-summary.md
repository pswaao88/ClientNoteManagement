# 프로젝트 요약 및 현황

## 프로젝트 개요

- 플랫폼: Android 전용
- 동작 방식: 오프라인 로컬 앱
- 저장 방식: Room DB
- 주요 흐름: 거래처 > 과 > 기기 / PC

## 구현 상태

현재 1차 동작 버전까지 구현되어 있습니다.

완료된 항목:

- Android 프로젝트 구성
- Room DB 연결
- Entity / DAO / Repository 구성
- Navigation 연결
- 거래처, 과, 기기, PC CRUD
- 검색 기능
- 삭제 확인 다이얼로그
- 상세 화면 연결
- 카드형 목록 UI

## 현재 UI/UX 반영 사항

- 앱 이름 정리
- 안전 영역 기준 상단 레이아웃 정리
- 카드 클릭과 수정 버튼 역할 분리
- 기기 / PC 상세 화면 추가
- 목록 카드에 `필드명: 값` 형식 적용
- 메모 한 줄 말줄임 처리
- PC의 전화번호 라벨 반영
- 비밀번호 기본 표시

## 빌드 상태

디버그 빌드는 확인 완료했습니다.

검증 명령:

```powershell
.\gradlew.bat assembleDebug
```

샌드박스 환경에서 사용한 명령:

```powershell
$env:GRADLE_USER_HOME='D:\project_univ\Daddy\ipadress\.gradle-home'
$env:ANDROID_USER_HOME='D:\project_univ\Daddy\ipadress\.android-home'
.\gradlew.bat assembleDebug
```

APK 위치:

- `app/build/outputs/apk/debug/app-debug.apk`

## 주의 사항

- DB 스키마가 여러 번 바뀌어 `fallbackToDestructiveMigration()`을 사용 중입니다.
- 따라서 앱 업데이트 시 기존 로컬 데이터가 초기화될 수 있습니다.
- 현재는 실사용 초기 단계이므로 기능 안정화가 우선입니다.

## 다음 작업 후보

- 실기기 설치 및 최종 확인
- 입력 폼 UX 추가 개선
- 검색창 clear 버튼
- 저장 후 피드백 메시지
- release 빌드 정리
