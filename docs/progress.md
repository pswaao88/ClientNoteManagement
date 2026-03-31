# 진행상황 정리

## 현재 상태

프로젝트는 안드로이드 오프라인 앱으로 1차 실사용 가능한 수준까지 구현되었습니다.

기본 목적:

- 거래처 관리
- 과 관리
- 기기 관리
- PC 관리
- 접속 정보 조회

현재 구조:

- 거래처
- 과
- 기기
- PC

## 완료된 개발 항목

- Android 프로젝트 생성 및 기본 구조 구성
- Kotlin + Jetpack Compose + Room + ViewModel + Navigation Compose 적용
- 거래처 CRUD 구현
- 과 CRUD 구현
- 기기 CRUD 구현
- PC CRUD 구현
- 검색 기능 연결
- 삭제 확인 다이얼로그 추가
- 상세 화면 추가
- 카드 클릭은 상세, 연필 버튼은 수정 흐름으로 정리

## 데이터 구조 변경 반영

### 거래처

- 거래처명
- 메모

### 과

- 과명
- 담당자명
- 연락처
- 메모

### 기기

- 모델명
- IP 주소
- 아이디
- 비밀번호
- 메모

### PC

- 전화번호
- IP 주소
- 아이디
- 비밀번호
- 메모

PC 항목은 빈값 저장이 가능하도록 nullable 기준으로 정리했습니다.

## UI/UX 반영 사항

- 앱 이름을 `거래처 기기관리`로 정리
- 상단 잘림 이슈 대응
- 주요 화면 안전 영역 기준 정리
- 카드 정보를 `필드명: 값` 형식으로 통일
- 메모는 카드에서 한 줄 말줄임 처리
- 기기와 PC 목록에서 상세 화면 진입 가능
- 비밀번호는 현재 기본 표시
- PC 라벨을 `전화번호` 기준으로 통일

## 빌드 및 설치 상태

### debug APK

- 경로: `app/build/outputs/apk/debug/app-debug.apk`

### release APK

- 경로: `app/build/outputs/apk/release/app-release.apk`

release 빌드는 실제 사용 가능한 서명 APK 기준으로 생성 완료했습니다.

## 서명키 및 배포 준비

생성 완료:

- keystore 파일
- release signing 설정
- release APK

중요 파일:

- `keystore/client-note-release.jks`
- `release-signing.properties`

이 두 파일은 반드시 별도 백업이 필요합니다.

## Git 상태

- 로컬 Git 저장소 초기화 완료
- GitHub 원격 저장소 연결 완료
- `main` 브랜치 사용 중

## 다음 작업 후보

- 실기기 최종 설치 확인
- 실사용 기준 폼 입력 UX 보강
- 저장 후 피드백 메시지 보강
- release 설치 후 최종 수동 QA
