#!/bin/bash

#sudo gem update deliver supply sigh

nomedir="$1"
diretorio_imagens="$2"
diretorio_dados="$3"
nomecoop="$4"
nomeapp="$5"
nomeapppassageiro="$6"
nomeapptaxista="$7"
keyword="$8"
tipo="$9"
cidade="${10}"
idioma="${11}"
bundleid="${12}"
newtxm="${13}"
target="${14}" # Indica se rodará o meta para android, ios ou ambos
typeTarget="${15}" # Indica se rodará o meta para passageiro, taxista ou ambos
pasta_original="${16}" # Pasta de execução do script
secret_dir="${17}"  # Pasta onde estão os arquivos txm_secret.json e drm_secret.json

mkdir -p "$nomedir"/"$diretorio_dados"/Metadados/android/metadata/"$idioma"/images/phoneScreenshots
mkdir -p "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"/images/phoneScreenshots
mkdir -p "$nomedir"/"$diretorio_dados"/Metadados/ios/screenshots/"$idioma" "$nomedir"/"$diretorio_dados"/Metadados/ios/metadata/"$idioma"

#cp Modules/metadata/base/ios/"$tipo"/Deliverfile "$nomedir"/"$diretorio_dados"/Metadados/ios
#cp Modules/metadata/base/ios/"$tipo"/metadata/*.txt "$nomedir"/"$diretorio_dados"/Metadados/ios/metadata
#cp Modules/metadata/base/ios/"$tipo"/metadata/"$idioma"/*.txt "$nomedir"/"$diretorio_dados"/Metadados/ios/metadata/"$idioma"
cp $pasta_original/metadata/base/ios/"$tipo"/Deliverfile "$nomedir"/"$diretorio_dados"/Metadados/ios
cp $pasta_original/metadata/base/ios/"$tipo"/metadata/*.txt "$nomedir"/"$diretorio_dados"/Metadados/ios/metadata
cp $pasta_original/metadata/base/ios/"$tipo"/metadata/"$idioma"/*.txt "$nomedir"/"$diretorio_dados"/Metadados/ios/metadata/"$idioma"

#cp Modules/metadata/base/android/"$tipo"/"$idioma"/*.txt "$nomedir"/"$diretorio_dados"/Metadados/android/metadata/"$idioma"
cp $pasta_original/metadata/base/android/"$tipo"/"$idioma"/*.txt "$nomedir"/"$diretorio_dados"/Metadados/android/metadata/"$idioma"

#cp Modules/metadata/base/taxista/"$tipo"/"$idioma"/*.txt "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"
cp $pasta_original/metadata/base/taxista/"$tipo"/"$idioma"/*.txt "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"

echo "Alterando Deliverfile..."

cp "$nomedir"/"$diretorio_imagens"/Imagens-base/logo.png "$nomedir"/"$diretorio_dados"/Metadados/ios
echo "app_icon './logo.png'" >> "$nomedir"/"$diretorio_dados"/Metadados/ios/Deliverfile

echo "Alterando Supply..."

#logo
cp "$nomedir"/"$diretorio_imagens"/imagensGeradas/logo_512.jpg "$nomedir"/"$diretorio_dados"/Metadados/android/metadata/"$idioma"/images/icon.jpg
if [ -f "$nomedir"/"$diretorio_imagens"/imagensGeradas/logo-taxi_512.jpg ]; then
	cp "$nomedir"/"$diretorio_imagens"/imagensGeradas/logo-taxi_512.jpg "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"/images/icon.jpg;
else
	cp "$nomedir"/"$diretorio_imagens"/imagensGeradas/logo_512.jpg "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"/images/icon.jpg;
fi

#featureGraphic
cp "$nomedir"/"$diretorio_imagens"/imagensGeradas/googleplay.png "$nomedir"/"$diretorio_dados"/Metadados/android/metadata/"$idioma"/images/featureGraphic.png
if [ -f "$nomedir"/"$diretorio_imagens"/imagensGeradas/googleplay-taxi.png ]; then
	cp "$nomedir"/"$diretorio_imagens"/imagensGeradas/googleplay-taxi.png "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"/images/featureGraphic.png;
else
	cp "$nomedir"/"$diretorio_imagens"/imagensGeradas/googleplay.png "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"/images/featureGraphic.png;
fi

echo "Copiando screenshots iOS..."

cp -v "$nomedir"/"$diretorio_imagens"/Telas/iOS/*.png "$nomedir"/"$diretorio_dados"/Metadados/ios/screenshots/"$idioma"/

echo "Copiando screenshots Android..."

cp -v "$nomedir"/"$diretorio_imagens"/Telas/cliente-Android/*.png "$nomedir"/"$diretorio_dados"/Metadados/android/metadata/"$idioma"/images/phoneScreenshots/

cp -v "$nomedir"/"$diretorio_imagens"/Telas/taxista/*.png "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"/images/phoneScreenshots/

echo "Adicionando metadados iOS restantes..."

echo "$(date "+%Y") $nomeapp" > "$nomedir"/"$diretorio_dados"/Metadados/ios/metadata/copyright.txt

echo "$nomeapppassageiro" > "$nomedir"/"$diretorio_dados"/Metadados/ios/metadata/"$idioma"/name.txt

if [ -s "$nomedir"/"$diretorio_dados"/description.txt ]; then
	cat "$nomedir"/"$diretorio_dados"/description.txt > "$nomedir"/"$diretorio_dados"/Metadados/ios/metadata/"$idioma"/description.txt;
fi

keywords_gerais=$(cat "$nomedir"/"$diretorio_dados"/Metadados/ios/metadata/"$idioma"/keywords.txt)
palavraschave="$nomeapp, $keyword, $cidade, $keywords_gerais"
if [ ${#palavraschave} > 100 ]; then
	echo "$nomeapp, $keyword, $cidade" > "$nomedir"/"$diretorio_dados"/Metadados/ios/metadata/"$idioma"/keywords.txt;
else
	echo "$palavraschave" > "$nomedir"/"$diretorio_dados"/Metadados/ios/metadata/"$idioma"/keywords.txt;
fi

echo "Adicionando metadados Android restantes..."

echo "$nomeapppassageiro" > "$nomedir"/"$diretorio_dados"/Metadados/android/metadata/"$idioma"/title.txt
echo "$nomeapptaxista" > "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"/title.txt

if [ -s "$nomedir"/"$diretorio_dados"/description.txt ]; then
	cat "$nomedir"/"$diretorio_dados"/description.txt > "$nomedir"/"$diretorio_dados"/Metadados/android/metadata/"$idioma"/full_description.txt;
fi
echo "" >> "$nomedir"/"$diretorio_dados"/Metadados/android/metadata/"$idioma"/full_description.txt
echo "" >> "$nomedir"/"$diretorio_dados"/Metadados/android/metadata/"$idioma"/full_description.txt
echo "Palavras-chave: $palavraschave" >> "$nomedir"/"$diretorio_dados"/Metadados/android/metadata/"$idioma"/full_description.txt

if [ -s "$nomedir"/"$diretorio_dados"/short_description.txt ]; then
	cat "$nomedir"/"$diretorio_dados"/short_description.txt > "$nomedir"/"$diretorio_dados"/Metadados/android/metadata/"$idioma"/short_description.txt;
fi

if [ -s "$nomedir"/"$diretorio_dados"/description-taxi.txt ]; then
	cat "$nomedir"/"$diretorio_dados"/description-taxi.txt > "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"/full_description.txt;
fi
echo "" >> "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"/full_description.txt
echo "" >> "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"/full_description.txt
echo "Palavras-chave: $palavraschave" >> "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"/full_description.txt

if [ -s "$nomedir"/"$diretorio_dados"/short_description-taxi.txt ]; then
	cat "$nomedir"/"$diretorio_dados"/short_description-taxi.txt > "$nomedir"/"$diretorio_dados"/Metadados/taxista/metadata/"$idioma"/short_description.txt;
fi

bundletipo="taxi"
prefixo="txm"
if [[ "$tipo" == Motorista ]] && [[ "$newtxm" == "YES" ]]; then
	bundletipo="driver";
	prefixo="drm"
fi

if ( [ $target = "ios" ] || [ $target = "both" ] ) && ( [ $typeTarget = "both" ] || [ $typeTarget = "psg" ] ); then
	echo "Executando Deliver iOS..."

	cd "$nomedir"/"$diretorio_dados"/Metadados/ios

	fastlane deliver -a "$bundleid" --force

	#if [ "$(fastlane deliver -a "$bundleid" --force | tee /dev/tty | grep 'update')" != '' ]; then
	#	sudo gem update deliver supply sigh
	#	deliver -a "$bundleid" --force
	#fi

	cd -
fi

if ( [ $target = "android" ] || [ $target = "both" ] ) && ( [ $typeTarget = "both" ] || [ $typeTarget = "psg" ] ); then
	echo "Executando Supply Android Passageiro..."

	cd "$nomedir"/"$diretorio_dados"/Metadados/android

# Inicio - Horácio - 16/09/2018
#	supply --skip_upload_apk -j /Users/integrador/ProjetosAndamento/Proj076-TaxiMachine/Miscelâneas/"$prefixo"_secret.json -p br.com."$nomecoop".passenger."$bundletipo"machine
	fastlane supply --skip_upload_apk -j $secret_dir/"$prefixo"_secret.json -p br.com."$nomecoop".passenger."$bundletipo"machine
# Fim - Horácio - 16/09/2018

	cd -
fi

if ( [ $target = "android" ] || [ $target = "both" ] ) && ( [ $typeTarget = "both" ] || [ $typeTarget = "tx" ] ); then
	echo "Executando Supply Android Taxista..."

	cd "$nomedir"/"$diretorio_dados"/Metadados/taxista

# Inicio - Horácio - 16/09/2018
#	supply --skip_upload_apk -j /Users/integrador/ProjetosAndamento/Proj076-TaxiMachine/Miscelâneas/"$prefixo"_secret.json -p br.com."$nomecoop".taxi."$bundletipo"machine
	fastlane supply --skip_upload_apk -j $secret_dir/"$prefixo"_secret.json -p br.com."$nomecoop".taxi."$bundletipo"machine
# Fim - Horácio - 16/09/2018

	cd -
fi

