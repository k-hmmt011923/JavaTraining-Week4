# JavaTraining-Final 

## プロダクト概要 

Spring Boot を用いたシンプルなタスク管理Webアプリです。 ユーザーはログイン後にタスクの一覧表示・登録・編集・削除・完了切替を行うことができます。 
画面操作とREST APIの両方からタスクCRUDを実行できます。 

## 開発環境 
- JDK / Java 21 
- フレームワーク / Spring Boot 
- ビルドツール / Gradle 
- IDE / VS Code 
- DB / H2 Database 
- テンプレートエンジン / Thymeleaf 
- テスト / MockMvc 

## セットアップ手順 

- リポジトリを取得し、プロジェクト直下でアプリ起動

1. リポジトリの取得 
```powershell
git clone https://github.com/k-hmmt011923/JavaTraining-Final.git 
```

2. プロジェクト直下へ移動 cd JavaTraining-Final 3. アプリ起動 本プロジェクトではGradle Wrapperを使用しています。 

- Mac / Linuxの場合以下をコマンドラインで実行してください。 

```powershell
./gradlew bootRun 
```

- Windowsの場合 
```powershell
gradlew.bat bootRun　もしくは　.\gradlew.bat bootRun 
```

- Maven（参考） 
```powershell
./mvnw spring-boot:run 
```

## 動作確認手順 
1. ブラウザで上で以下URLへアクセス 　

    http://localhost:8080/login 


2. ログイン情報の入力 　

   username : testuser
   password : password 

3. ログインが成功すると http://localhost:8080/tasks 
   にリダイレクトされます 

## 画面操作 

### タスク一覧(/tasks) 

表示内容 
タスク一覧・新規登録・ID・タイトル・完了状態・編集・完了切替・削除 

### タスク登録(/tasks/new) 

タスク一覧から　「新規作成」リンクをクリック タイトルを入力し、登録する 

### タスク編集(/tasks/{id}/edit) 

追加したタスクの右部項目の「編集」リンクをクリック タイトルを入力し、更新する 

### タスク削除 

タスク一覧画面から、削除したいタスクの右部項目の「削除」ボタンをクリック 

### 完了状態切替 

タスク一覧画面から、完了状態を切り替えたいタスクの右部項目の「完了切替」ボタンをクリック 

## REST　API 

このREADMEのセットアップ手順・動作確認手順にてプロジェクトの起動、ログイン認証を済ませ、タスク一覧画面(/tasks)をブラウザ上で開いた状態で行ってください。

ベースのURLは /api/tasksです 

コマンドラインにて例のような記述で実行してください 

### タスク一覧取得 GET /api/tasks 

例 
```powershell
curl http://localhost:8080/api/tasks
```

### タスク登録 POST /api/tasks 

例 Linux/Mac

```powershell
curl -X POST http://localhost:8080/api/tasks \
-H "Content-Type: application/json" \
-d '{"title":"sample task","completed":false}'
```

Windows

```powershell
$json = '{"title":"sample task","completed":false}'
curl.exe -X POST http://localhost:8080/api/tasks -H "Content-Type: application/json" -d $json
```


### タスク更新 PUT /api/tasks/{id} 

例 
```powershell
curl -X PUT http://localhost:8080/api/tasks/1 \ -H "Content-Type: application/json" \ -d '{"title":"updated task","completed":true}' 
```

### タスク削除 DELETE /api/tasks/{id} 

例
```powershell
curl -X DELETE http://localhost:8080/api/tasks/1
```

## アーキテクチャ

ブラウザ
↓
Controller
LoginController
TaskViewController
TaskRestController
↓
DTO / Entity
TaskForm
Task
↓
Service
TaskService
↓
Repository
TaskRepository
↓
Database
H2 Database

## パッケージ構成


```powershell
JavaTraining-Final/
├─ src/
│  ├─ main/
│  │  ├─ java/com/example/taskapp/
│  │  │  ├─ config/
│  │  │  │   └─ SecurityConfig.java
│  │  │  ├─ controller/
│  │  │  │   ├─ LoginController.java
│  │  │  │   ├─ TaskViewController.java
│  │  │  │   └─ TaskRestController.java
│  │  │  ├─ dto/
│  │  │  │   └─ TaskForm.java
│  │  │  ├─ entity/
│  │  │  │   └─ Task.java
│  │  │  ├─ exception/
│  │  │  │   ├─ GlobalExceptionHandler.java
│  │  │  │   └─ TaskNotFoundException.java
│  │  │  ├─ repository/
│  │  │  │   └─ TaskRepository.java
│  │  │  ├─ service/
│  │  │  │   └─ TaskService.java
│  │  │  └─ TaskappApplication.java
│  │  │
│  │  └─ resources/
│  │      ├─ templates/
│  │      │  ├─ layout.html
│  │      │  ├─ login.html
│  │      │  ├─ error/
│  │      │  │   └─ 404.html
│  │      │  └─ tasks/
│  │      │      ├─ index.html
│  │      │      └─ form.html
│  │      │
│  │      └─ application.properties
│  │
│  └─ test/java/com/example/taskapp/controller/
│     └─ TaskControllerTest.java
│
├─ screenshots/
│  ├─ login.png
│  ├─ list.png
│  └─ tests.png
│
├─ build.gradle
├─ gradlew
├─ gradlew.bat
├─ settings.gradle
└─ README.md
```

## 既知の制約

- ユーザー管理機能は未実装（単一ユーザーのみ）

- UIデザインは最小構成

- タスク検索機能は未実装

## 今後の改善

- タスク検索・ページング機能

- PostgreSQL + Docker対応

- OpenAPI(Swagger)によるAPI仕様公開

- GitHub ActionsによるCI導入

- UIデザイン改善