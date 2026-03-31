# 배포 및 실기기 테스트

## 설치 방법

### Android Studio로 설치

1. USB 디버깅을 켭니다.
2. 휴대폰을 PC에 연결합니다.
3. Android Studio에서 기기를 선택합니다.
4. `Run app`으로 실행합니다.

### APK 직접 설치

1. `app/build/outputs/apk/debug/app-debug.apk` 파일을 휴대폰으로 옮깁니다.
2. 휴대폰에서 APK를 눌러 설치합니다.
3. 필요하면 `알 수 없는 앱 설치 허용`을 켭니다.

### adb 설치

```powershell
C:\Users\pswaa\AppData\Local\Android\Sdk\platform-tools\adb.exe devices
C:\Users\pswaa\AppData\Local\Android\Sdk\platform-tools\adb.exe install -r "D:\project_univ\Daddy\ipadress\app\build\outputs\apk\debug\app-debug.apk"
```

## 실기기 체크리스트

- 앱이 정상 실행된다
- 거래처 추가 / 수정 / 삭제가 된다
- 과 추가 / 수정 / 삭제가 된다
- 기기 추가 / 수정 / 삭제가 된다
- PC 추가 / 수정 / 삭제가 된다
- 검색이 동작한다
- 상세 화면 진입이 된다
- 앱 재실행 후 데이터가 유지된다

## 배포 전 확인

- 주요 흐름 수동 테스트 완료
- APK 설치 확인
- UI 잘림 여부 확인
- 저장/삭제 오류 여부 확인

## 참고

- 현재 APK는 `debug` 빌드입니다.
- 외부 배포용으로는 나중에 `release` 서명 APK가 필요합니다.
