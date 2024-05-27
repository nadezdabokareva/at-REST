Как поднять Allure

Подключить зависимости:
 testImplementation platform("io.qameta.allure:allure-bom:$allureVersion")
    testImplementation "io.qameta.allure:allure-junit5"

1) C:\allure-2.25.0\allure-2.25.0\bin\allure.bat generate -c -o C:\projects\at-REST\allure-result
2) gradlew clean test --tests UserAuthTest
3) C:\allure-2.25.0\allure-2.25.0\bin\allure.bat generate -c -o C:\projects\at-REST\allure-result
4) C:\allure-2.25.0\allure-2.25.0\bin\allure.bat serve
