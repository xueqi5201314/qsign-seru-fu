@rem ������к����룬���Ҽ��༭�ű� ���Ϊ ����ѡANSI ����
@echo off
title API����ر�
echo API����������־���log.txt
rem �ٳֿ���̨������ļ���1Ϊ������Ϣ��2Ϊ������Ϣ��34Ϊ������
rem ʹ��findstr����ɸѡ��Ҫ����־��Ϣ
>>log.txt 2>&1 3>&1 4>&1 echo %date%-%time%

bin\unidbg-fetch-qsign --basePath=txlib\8.9.85|findstr "����:"
rem �������޸���Ҫ��¼��QQ�汾

exit
