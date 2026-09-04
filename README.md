# Turn-Based RPG Swing MVC

Java Swing과 MVC 패턴을 활용한 턴제 RPG 게임 프로젝트입니다.

<img width="1920" height="1080" alt="32" src="https://github.com/user-attachments/assets/20805f8b-413d-4f8e-afbd-c1e9cbfb0705" />
<img width="1920" height="1080" alt="31" src="https://github.com/user-attachments/assets/237c7ff5-57d3-462f-9ea5-9b04e9b0ddb3" />
<img width="1920" height="1080" alt="30" src="https://github.com/user-attachments/assets/a07be7a3-0b6d-40dc-9a94-9949dd812ae1" />
<img width="1920" height="1080" alt="29" src="https://github.com/user-attachments/assets/5797348c-9475-48f1-9dd3-cddaa47e6487" />
<img width="1920" height="1080" alt="28" src="https://github.com/user-attachments/assets/3c071c9c-ee8e-4a76-8a37-653ea6e56afe" />
<img width="1920" height="1080" alt="27" src="https://github.com/user-attachments/assets/af9f0761-ade1-4ea9-bb7c-ca1fd7cfb636" />
<img width="1920" height="1080" alt="33" src="https://github.com/user-attachments/assets/9c0d299f-d722-4ad4-8491-d68f895b5036" />


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
- 전투 화면 몬스터 이미지 표시
- GIF 몬스터 이미지 표시

## 몬스터 이미지 추가

이미지는 `assets/images` 폴더에 넣습니다. GIF와 PNG를 모두 지원합니다.

- `assets/images/spr_sword.gif` 또는 `assets/images/spr_sword.png`
- `assets/images/spr_axe.gif` 또는 `assets/images/spr_axe.png`
- `assets/images/skeleton_attack.gif` 또는 `assets/images/skeleton_attack.png`

GIF는 애니메이션을 유지하기 위해 원본 크기로 표시합니다. PNG, JPG, JPEG는 전투 화면에서 `150x150` 크기로 조정됩니다.

이미지가 없으면 전투 화면에는 필요한 파일명이 대신 표시됩니다.
