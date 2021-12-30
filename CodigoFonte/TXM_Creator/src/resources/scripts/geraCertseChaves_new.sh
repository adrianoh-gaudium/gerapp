#!/bin/bash

#pasta="../../Seguranca_nao_sensivel/Certificados/Android/TaxiMachine/"

bandSmall=$(echo $2 | tr '[:upper:]' '[:lower:]')
bandBig="$(tr '[:lower:]' '[:upper:]' <<< ${bandSmall:0:1})${bandSmall:1}"

mkdir -p "$1"/"$4"/Segurança

if [ ! -f "$1"/"$4"/Segurança/"$bandBig".keystore ]; then
	echo "Gerando keystore..."
	echo y | keytool -genkeypair -dname "cn=$bandBig, ou=$bandBig, o=$bandBig, c=BR" -alias $bandSmall -keypass "$bandBig"@txm123 -keystore "$1"/"$4"/Segurança/"$bandBig".keystore -storepass "$bandBig"@txm123 -validity 9131
fi

echo "Gerando Certificado de push iOS..."

#Exemplo
#fastlane pem -a "br.com.taximachine.cooperativajacarei" -u "viniciusoh@gaudium.com.br" -p "CooperativaJacarei123" -o "/Users/<usuario>/ProjetosAndamento/Proj451-TXM-CooperativaJacarei/Miscelâneas/Segurança/CertJacareiAPNSDistribution.pem"

# Inicio - Horácio - 30/05/2018 - parametrização da conta do FastLane
#fastlane pem -a "$5" -u viniciusoh@gaudium.com.br -p "$bandSmall"123 -o "$1"/"$4"/Segurança/Cert"$2"APNSDistribution.pem						
fastlane pem -a "$5" -u "$7" -p "$bandSmall"123 -o "$1"/"$4"/Segurança/Cert"$2"APNSDistribution.pem						
# Fim

cp Cert"$2"APNSDistribution.pem "$6"/PEMs_pendentes

mv Cert"$2"APNSDistribution.pem "$1"/"$4"/Segurança
mv Cert"$2"APNSDistribution.p12 "$1"/"$4"/Segurança
mv Cert"$2"APNSDistribution.pkey "$1"/"$4"/Segurança

echo "Gerando Provisioning Profile iOS..."

# Inicio - Horácio - 30/05/2018 - parametrização da conta do FastLane
#fastlane sigh -a "$5" -u viniciusoh@gaudium.com.br -n "$bandSmall"APPSTORE -q "$bandSmall"APPSTORE.mobileprovision
fastlane sigh -a "$5" -u "$7" -n "$bandSmall"APPSTORE -q "$bandSmall"APPSTORE.mobileprovision
# Fim

mv "$bandSmall"APPSTORE.mobileprovision "$1"/"$4"/Segurança

echo "Gerando bundle-sha1.txt ..."

echo "br.com.$bandSmall.passenger.$3machine" >> "$1"/Miscelâneas/bundle-sha1.txt
echo "br.com.$bandSmall.taxi.$3machine" >> "$1"/Miscelâneas/bundle-sha1.txt
keytool -v -list -alias $bandSmall -keystore "$1"/"$4"/Segurança/"$bandBig".keystore -storepass "$bandBig"@txm123 | grep "SHA1:" | cut -d' ' -f3 >> "$1"/Miscelâneas/bundle-sha1.txt
echo "" >> "$1"/Miscelâneas/bundle-sha1.txt
echo "$5" >> "$1"/Miscelâneas/bundle-sha1.txt

echo "" >> "$1"/Miscelâneas/bundle-sha1.txt

# Extrai FACEBOOK HASH
echo "Facebook Hash" >> "$1"/Miscelâneas/bundle-sha1.txt
keytool -exportcert -alias $bandSmall -storepass "$bandBig"@txm123 -keystore "$1"/"$4"/Segurança/"$bandBig".keystore | openssl sha1 -binary | openssl base64 >> "$1"/Miscelâneas/bundle-sha1.txt

#echo "" >> "$1"/Miscelâneas/bundle-sha1.txt
#echo "$bandBig=$bandBig@txm123 $bandSmall $bandBig@txm123" >> "$1"/Miscelâneas/bundle-sha1.txt

echo "Transferindo keystore..."
cp "$1"/"$4"/Segurança/"$bandBig".keystore "$1"/Android/CoopAndroid/
