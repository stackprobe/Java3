CD /D C:\pleiades\workspace\Test03\src-03\_bat
IF NOT EXIST _Test03_bat GOTO END

COPY C:\Dev\Annex\Junk\BusyDlg_old\BusyDlg\bin\Release\BusyDlg.exe ..\evergarden\busydlg\res\BusyDlg.exe_

:END
PAUSE
