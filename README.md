## 2022 UMC PROJECT | 모아도
진행 기간 : 7/1 ~ 7/24 

### [TEAM INFO]
#### Name
- 지라이프랩
   
>Member
- 임재완 [@jaewan0114](https://github.com/jaewan0114)
- 윤진난 [@Jinnan-Yun](https://github.com/Jinnan-Yun)
- 서위영 [@HBSPS](https://github.com/HBSPS)
> Designer 
- 정민희 [Linkedin](https://www.linkedin.com/in/%EB%AF%BC%ED%9D%AC-%EC%A0%95-b96197204/)

------------- 
### [아이디어 소개]   

#### 1. 모아도(Moments of Ability Map)
편의시설과 위험지역에 대한 정보 제공으로  **교통약자의 안전과 이동편의 증진**  
> _#편의증진_ _#교통약자_ _#사회적가치_ _#사회적문제해결_ _#지도_
   
     
#### 2. Information Architecture
<img src="./img/IA.png" height="600px" width="950px">   

#### 3. Flow Chart
<img src="./img/FlowChart.png"> 

------------- 
### [백엔드]

#### 1. 개발자
- 서위영 [@HBSPS](https://github.com/HBSPS)
- 윤진난 [@Jinnan-Yun](https://github.com/Jinnan-Yun)
- 곽정아 [@Jungahgo](https://github.com/Jungahgo)

#### 2. 개발 환경
- <img src="https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=Node.js&logoColor=white">
- <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
- <img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=for-the-badge&logo=Amazon AWS&logoColor=white">

#### 3. 사용 언어
- 데이터 크롤링 : Python
- 서버 구성 : JavaScript, SQL

#### 4. API 구성
```bash
├─ Content
│      ├─ 자전거 사고 다발지역 받아오기
│      ├─ 휠체어 급속 충전기 정보 받아오기
│      ├─ 급경사지 위치 받아오기
│      ├─ 학교 휠체어 경사로 위치 받아오기
│      ├─ 지하철 역 엘리베이터 위치 받아오기
│      ├─ 의료기관 정보 받아오기
│      ├─ 복지센터 정보 받아오기
│      └─ 휠체어 리프트 위치 받아오기
│
└─ Report
       ├─ 위험지역 제보하기
       ├─ 위험지역 정보 받아오기
       └─ 위험지역 정보 수정하기
```

------------
### [어플 화면] 
!<img src="https://user-images.githubusercontent.com/55147540/180637177-34474f19-54c2-49dc-a32c-27e8dc15e7a3.png" width=20%>
<img src ="https://user-images.githubusercontent.com/55147540/180637187-0ecf4bac-b0b2-43e6-bff1-af5b9bb5d432.png" width=20%>
<img src="https://user-images.githubusercontent.com/55147540/180637204-170b682f-8d9d-438a-b2f2-c977ae0a4627.png" width=20%>
<img src="https://user-images.githubusercontent.com/55147540/180637207-66933abb-1395-46a6-a69e-ee401013b015.png" width=20%>

!<img src="https://user-images.githubusercontent.com/55147540/180637541-1b14ded5-47c2-433d-89e5-76ccf4a90788.png" width=20%>
<img src="https://user-images.githubusercontent.com/55147540/180637544-92a1a49b-c111-42a2-83b2-ea6207de3257.png" width=20%>
<img src="https://user-images.githubusercontent.com/55147540/180637546-706bc013-f8ef-46cc-b95a-98c87c444ef7.png" width=20%>
<img src="https://user-images.githubusercontent.com/55147540/180637548-9db7ae76-2d8d-449f-9bb3-b76611058533.png" width=20%>

#### 이용 설명서
!<img src="https://user-images.githubusercontent.com/55147540/180637616-14fb27f7-6276-449a-9077-8d2b5dfefc93.png" width=20%>
<img src="https://user-images.githubusercontent.com/55147540/180637617-a3b56b0f-4958-441c-a83d-321efb8e36d2.png" width=20%>
<img src="https://user-images.githubusercontent.com/55147540/180637622-1900ece5-7889-4ae0-ae21-6914d03b7241.png" width=20%>
<img src="https://user-images.githubusercontent.com/55147540/180637624-5a63dacc-9e4f-4132-9b3c-3a172628a9b7.png" width=20%>

-------------
### [공공데이터 및 오픈소스 활용]  
#### 1. 공공데이터
- 자전거 사고다발 지역  
https://www.data.go.kr/data/15056681/openapi.do
- 전동휠체어 급속충전기  
https://www.data.go.kr/data/15034533/standard.do
- 성동구 급경사지  
https://www.data.go.kr/data/15084652/fileData.do
- 지하처 출입구 리프트   
https://data.seoul.go.kr/dataList/OA-21211/S/1/datasetView.do
- 지하철 엘리베이터  
https://www.data.go.kr/data/15041387/fileData.do#layer-api-guide
- 전국 약국 정보 조회   
https://www.data.go.kr/data/15000576/openapi.do
- 전국 병/의원 찾기   
https://www.data.go.kr/data/15000736/openapi.do

#### 2. 오픈소스  

- Android-Toggle : [Custom Switches for Android](https://github.com/singhangadin/android-toggle)
   
>Copyright (C) 2018 Angad Singh
>   
>Licensed under the Apache License, Version 2.0 (the "License");
>you may not use this file except in compliance with the License.
>You may obtain a copy of the License at
>    
   >http://www.apache.org/licenses/LICENSE-2.0
>  
>Unless required by applicable law or agreed to in writing, software
>distributed under the License is distributed on an "AS IS" BASIS,
>WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
>See the License for the specific language governing permissions and
>limitations under the License. 
