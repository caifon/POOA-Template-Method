@echo off
echo ========================================
echo  Compilando e executando VERSAO 1
echo  (Sem padrao GoF - codigo duplicado)
echo ========================================
cd versao1_sem_gof\src
javac *.java
if errorlevel 1 goto erro1
echo.
java Main
cd ..\..
echo.

echo ========================================
echo  Compilando e executando VERSAO 2
echo  (Com Template Method GoF)
echo ========================================
cd versao2_com_template\src
javac *.java
if errorlevel 1 goto erro2
echo.
java Main
cd ..\..
echo.

echo ========================================
echo  Compilando e executando VERSAO 3
echo  (Com Reflexao e Anotacoes)
echo ========================================
cd versao3_com_anotacoes\src
javac -d . anotacoes\PassoRelatorio.java anotacoes\RelatorioConfig.java
if errorlevel 1 goto erro3
javac -d . RelatorioGerador.java RelatorioPDF.java RelatorioHTML.java RelatorioCSV.java ExecutorRelatorio.java Main.java
if errorlevel 1 goto erro3
echo.
java Main
cd ..\..
goto fim

:erro1
echo ERRO na compilacao da Versao 1
cd ..\..
goto fim

:erro2
echo ERRO na compilacao da Versao 2
cd ..\..
goto fim

:erro3
echo ERRO na compilacao da Versao 3
cd ..\..
goto fim

:fim
echo.
echo === Execucao concluida ===
pause
