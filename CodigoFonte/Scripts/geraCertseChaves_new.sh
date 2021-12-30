#!/bin/bash

#colors
RED='\033[0;31m'
SRED='\033[0;91m'
NC='\033[0m' # No Color
YELLOW0='\033[0;33m'
YELLOW='\033[1;33m'

#pasta="../../Seguranca_nao_sensivel/Certificados/Android/TaxiMachine/"
bandSmall=$(echo $2 | tr '[:upper:]' '[:lower:]')
bandBig="$(tr '[:lower:]' '[:upper:]' <<< ${bandSmall:0:1})${bandSmall:1}"
app_key="$8"
target="$9"	#A variável deverá vir com: 'android', se for apenas android. 'ios', se for apenas iOS. 'both', se for ambos. 

#variável de ambiente para skipar 2fa apple
export SPACESHIP_SKIP_2FA_UPGRADE=1

mkdir -p "$1"/"$4"/Segurança/Backup
pasta_chaves="$1"/"$4"/Segurança/
pasta_backup="$1"/"$4"/Segurança/Backup/

if [ "$target" != "ios" ]; then		#Se não for exclusivo do ios, pode gerar android.
	if [ ! -f "$pasta_chaves$bandBig".keystore ]; then
		echo "Gerando keystore..."
		echo y | keytool -genkeypair -dname "cn=$bandBig, ou=$bandBig, o=$bandBig, c=BR" -keysize 1024 -alias $bandSmall -keypass "$bandBig"@txm123 -keystore "$pasta_chaves$bandBig".keystore -storepass "$bandBig"@txm123 -validity 9131
		
		if [ ! -f "$pasta_chaves$bandBig".keystore ]; then
			cp "$pasta_chaves$bandBig".keystore "$pasta_backup$bandBig".keystore
		fi
	fi
fi

if [ "$target" != "android" ]; then		#Se não for exclusivo do android, pode gerar iOS.
	echo "Gerando Certificado de push iOS..."

	#Exemplo
	#fastlane pem -a "br.com.taximachine.cooperativajacarei" -u "gaudiumtecnicos@gmail.com" -p "CooperativaJacarei123" -o "/Users/<usuario>/ProjetosAndamento/Proj451-TXM-CooperativaJacarei/Miscelâneas/Segurança/CertJacareiAPNSDistribution.pem"

	# Inicio - Horácio - 30/05/2018 - parametrização da conta do FastLane
	#fastlane pem -a "$5" -u gaudiumtecnicos@gmail.com -p "$bandSmall"123 -o "$pasta_chaves"Cert"$2"APNSDistribution.pem						
	pemName=Cert"$2"APNSDistribution.pem
	pemFolder="$pasta_chaves"
	pathPem="$pemFolder$pemName"

	#gerar app novo na loja
	echo fastlane produce -a "$5" -u "$7" -q "$2" --skip_itc
	fastlane produce -a "$5" -u "$7" -q $2 --skip_itc

	echo fastlane pem -a "$5" -u "$7" -p "$bandSmall"123 -o "$pemName" --output_path "$pemFolder" --generate_p12 false --save_private_key false --force true
	fastlane pem -a "$5" -u "$7" -p "$bandSmall"123 -o "$pemName" --output_path "$pemFolder" --generate_p12 false --save_private_key false --force true
	# output=$( (fastlane pem -a "$5" -u "$7" -p "$bandSmall"123 -o "$pemName" -e \""$pemFolder"\" --generate_p12 false --save_private_key false) 2>&1)

	# if [[ $output = *"is valid for"* ]]; then
	# 	if [ "$condition" != "--force true" ]; then
	# 		dias=$(echo $output | grep "is valid for "| sed 's/.*is valid for \([^ more days.]*\).*/\1/')
	# 		echo -en "\r❕  ${WHITE}Chave ${GRAY}$arquivo (Proj$arg)${WHITE} ainda é válido por mais ${GRAY}$dias ${WHITE}dias.${NC}"
	# 		continue
	# 	fi
	# fi
	# echo "$output" &> "$DIR_PEM_PENDENTES"log_"$arg"_renovaPems.txt

	# cp Cert"$2"APNSDistribution.pem "$6"/PEMs_pendentes

	echo -e ""
	# mv Cert"$2"APNSDistribution.pem "$1"/"$4"/Segurança
	# mv Cert"$2"APNSDistribution.p12 "$1"/"$4"/Segurança
	# mv Cert"$2"APNSDistribution.pkey "$1"/"$4"/Segurança

	if [ -f "$pathPem" ]; then
		#Enviar PEM gerado p/ o backend
		echo -en "\r✅  Chave ${YELLOW}$pemName${NC} foi gerado!"
		echo curl -s -H "App-Key: $app_key" -F "certFile=@$pathPem" https://api.taximachine.com.br/api/marcaAplicativo/uploadApnsCert
		response=$(curl -s -H "App-Key: $app_key" -F "certFile=@$pathPem" https://api.taximachine.com.br/api/marcaAplicativo/uploadApnsCert)
		if [[ $response = '{"success":true}' ]]; then
		 	echo -en "\r✅  Chave ${YELLOW}$pemName${NC} foi enviada com sucesso! 🎉"
		 	rm -f "$pathPem"
		else
			echo -en "\r❌  ${RED}Erro ao atualizar chave ${SRED}$pemName${RED}${NC}"
			echo -e "${RED}$response${NC}"
		fi
	fi

	echo "Gerando Provisioning Profile iOS..."

	# Inicio - Horácio - 30/05/2018 - parametrização da conta do FastLane
	#fastlane sigh -a "$5" -u gaudiumtecnicos@gmail.com -n "$bandSmall"APPSTORE -q "$bandSmall"APPSTORE.mobileprovision
	fastlane sigh -a "$5" -u "$7" -n "$bandSmall"APPSTORE -q "$bandSmall"APPSTORE.mobileprovision
	# Fim

	mv "$bandSmall"APPSTORE.mobileprovision "$1"/"$4"/Segurança
fi

echo "Gerando bundle-sha1.txt ..."

echo "br.com.$bandSmall.passenger.$3machine" >> "$1"/Miscelâneas/bundle-sha1.txt
echo "br.com.$bandSmall.taxi.$3machine" >> "$1"/Miscelâneas/bundle-sha1.txt
keytool -v -list -alias $bandSmall -keystore "$pasta_chaves$bandBig".keystore -storepass "$bandBig"@txm123 | grep "SHA1:" | cut -d' ' -f3 >> "$1"/Miscelâneas/bundle-sha1.txt
echo "" >> "$1"/Miscelâneas/bundle-sha1.txt
echo "$5" >> "$1"/Miscelâneas/bundle-sha1.txt

echo "" >> "$1"/Miscelâneas/bundle-sha1.txt

# Extrai FACEBOOK HASH
echo "Facebook Hash" >> "$1"/Miscelâneas/bundle-sha1.txt
keytool -exportcert -alias $bandSmall -storepass "$bandBig"@txm123 -keystore "$pasta_chaves$bandBig".keystore | openssl sha1 -binary | openssl base64 >> "$1"/Miscelâneas/bundle-sha1.txt

#echo "" >> "$1"/Miscelâneas/bundle-sha1.txt
#echo "$bandBig=$bandBig@txm123 $bandSmall $bandBig@txm123" >> "$1"/Miscelâneas/bundle-sha1.txt

echo "Transferindo keystore..."
cp "$pasta_chaves$bandBig".keystore "$1"/Android/CoopAndroid/
