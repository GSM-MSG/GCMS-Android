<h1 align="center"><b>GCMS</b></h1>

<p align="center">
    <h3 align="center">
        <b>광주소프트웨어마이스터고 교내 동아리 관리 서비스<br>
        GSM(GwangjuSoftwareMeisterhighschool) Club Management Service</b>
    </h3>
    <br>
    <p>
        <b>GCMS</b>는 광주 소프트웨어 마이스터고의 전공, 자율, 사설 동아리를 편리하게 관리하게 위해 <br>
        <a href="https://matsogeum.notion.site/MSG-7ac3204e54e0484c9b1a63f472aa3e95">MSG</a>
        가 개발한 교내 동아리 관리 플랫폼입니다.<br> 기존에 동아리를 관리할 때에는 동아리를 홍보하는 데에도 불편함이 있고, <br>동아리의 부장이 수기로 동아리 인원을 작성하여 담당 선생님께 제출해야 하는 불편함이 있었어요.<br>
        이러한 불편함을 줄일 수 있도록 편리하게 동아리를 관리해 주는 서비스입니다.
    </p>
    <img src = "https://user-images.githubusercontent.com/82383983/220404231-7a84e3b9-e44c-48b5-95a6-5983d8084614.png" />
    <img src = "https://user-images.githubusercontent.com/82383983/220406552-be535ea6-47ff-410a-87d3-2ab90196d8d0.png" />

</p>
<br>
<br>


<h2>
    Installation 🎁 
</h2>

- PlayStore: [GCMS](https://play.google.com/store/apps/details?id=com.msg.gcms)

<br>
<h2>
Tech Stack
</h2>
<img src = "https://user-images.githubusercontent.com/82383983/220412681-daafd612-8375-4496-86ea-286b4b05e169.png"/>

GCMS는 Android 공식문서에 서술된 [Android App Architecture](https://developer.android.com/topic/architecture?hl=ko#recommended-app-arch)를 기반으로 작성되었습니다.
<br>

* Minumun SDK 26
* Language: ```Kotlin```
* Async: ```Coroutine```
* DI: ```Dagger-Hilt```
* Network: ```Retrofit2```, ```OKhttp3```
* Image: ```Glide```, ```Coil```
* AndroidX Jetpack
    * Navigation
    * LiveData
* Animation: ```Lottie```
* CI: ```Github action```
* Cooperation: ```Git```, ```Github```, ```GitFlow```
* Architecture: ```Clean Architecture```, ```MVVM```


<h2>
Team 👯‍♂️
</h2>
<div align = "center">
    <table>
    <th>👑<a href="https://github.com/leehyeonbin">이현빈</a></th>
        <th><a href="https://github.com/100Seung-Min">백승민</a></th>
        <th><a href="https://github.com/khs3994">김현승</a></th>
        <tr>
             <td align="center">
                <img src="https://avatars.githubusercontent.com/u/82383983?v=4" width='120' />
            </td>
            <td align="center">
                <img src="https://avatars.githubusercontent.com/u/81063667?v=4" width='120' />
            </td>
            <td align="center">
                <img src="https://avatars.githubusercontent.com/u/80810303?v=4" width='120' />
            </td>
        </tr>
        <tr>
            <td align="center">
            동아리 생성,<br>
            동아리 정보 수정,<br>
            동아리 메뉴 사이드 바,<br>
            학생 검색 파트 담당
            </td>
            <td align="center">
            마이 프로필,<br>
            동아리 멤버 관리 파트 담당
            </td>
            <td align="center">
            동아리 세부 페이지,<br>
            로그인 파트 담당
            </td>
        </tr>
    </table>
</div>
<br>

<h2>Packages 📁</h2>

```
GCMS Android
 ┣ 📂data
 ┃ ┣ 📂local
 ┃ ┃ ┣ 📂dao
 ┃ ┃ ┣ 📂datasource
 ┃ ┃ ┗ 📂datastorage
 ┃ ┣ 📂remote
 ┃ ┃ ┣ 📂datasource
 ┃ ┃ ┣ 📂dto
 ┃ ┃ ┣ 📂network
 ┃ ┃ ┗ 📂util
 ┃ ┣ 📂mapper
 ┃ ┃ ┣ 📂request
 ┃ ┃ ┗ 📂response
 ┃ ┗ 📂repository
 ┣ 📂di
 ┣ 📂domain
 ┃ ┣ 📂data
 ┃ ┣ 📂exception
 ┃ ┣ 📂repository
 ┃ ┗ 📂usecase
 ┣ 📂presentaion
 ┃ ┣ 📂adapter
 ┃ ┣ 📂base
 ┃ ┣ 📂extension
 ┃ ┣ 📂utils
 ┃ ┣ 📂view
 ┃ ┗ 📂viewmodel
 ┗ 📂util
```

> GCMS는 2.0.0 버전으로 업데이트하며 6천줄 이상을 수정하기도 했을 만큼 <br>
> 더욱 좋은 코드를 위해, 그리고 사용성을 개선하기 위해 노력하고 있습니다.