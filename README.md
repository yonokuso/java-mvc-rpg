# Turn-Based RPG Swing MVC

Java Swing과 MVC 패턴을 활용한 턴제 RPG 게임 프로젝트입니다.

## 실행 방법

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out rpg.Main
```

## 구조

- `model`: 몬스터, 스킬, 플레이어, 전투 규칙 등 핵심 게임 데이터와 로직
- `view`: Swing 화면 클래스
- `controller`: 사용자 입력을 받아 모델을 변경하고 화면을 갱신
- `service`: 저장 및 불러오기 담당

## 현재 구현된 기능

- 시작 메뉴
- 몬스터 선택
- 턴제 전투
- 스킬 선택과 데미지 처리
- 간단한 적 AI 턴
- 승패 판정
- 파일 저장 및 불러오기
