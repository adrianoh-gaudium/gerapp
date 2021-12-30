#!/bin/bash

#QRCODES



echo "Gerando QRCodes...";

old_IFS=$IFS;

mkdir -p "$1/$2/QRCodes";

IFS=$'\n';
TAB=$'\t' 
urles="android-passageiro: https://play.google.com/store/apps/details?id=br.com.$3.passenger.$6machine\nandroid-taxista: https://play.google.com/store/apps/details?id=br.com.$3.taxi.$6machine\niOS-passageiro: $4\napps-taxi-br: http://apps.taxi.br/$5";
echo $urles | sed s/" "/"${TAB}&"/g > "$1/$2/URLs.txt"
for linha in $(printf $urles); do
	nome=$(echo $linha | cut -d':' -f1);
	appurl=$(echo $linha | cut -d' ' -f2);
	#echo "URL de $nome: $appurl";
	short=$(curl "http://api.bit.ly/v3/shorten?login=earaujogaudium&apiKey=R_280aafce653e4fce96b442a4a306629b&longURL=$appurl&format=txt");
	#echo "URL de $nome encurtada: $short";
	if [ $nome == 'apps-taxi-br' ]; then
		sizeqr="193x193";
	else
		sizeqr="232x232";
	fi
	wget -O "$1/$2/QRCodes/qrcode-$nome.png" "http://api.qrserver.com/v1/create-qr-code/?data=$short&size=$sizeqr";
done;
IFS=$old_IFS;