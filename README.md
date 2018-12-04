# Android-PullToRefresh

下拉刷新/上拉加载控件

# Gradle

在项目的build.gradle中,添加:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

在使用库module中添加，为避免重复引用，推荐使用exclude:
```groovy
dependencies {
    implementation 'com.android.support:recyclerview-v7:latest.version'
    implementation ('com.github.shucc:Android-PullToRefresh:v3.0.2') {
        exclude group: 'com.android.support', module: 'recyclerview-v7'
    }
}
```
