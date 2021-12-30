#!/bin/bash

pasta_mkt="$1/$2"

# Inicio - Horácio - 23/06/2018
# Diretório temporário para gravação das imagens, será removido ao final do script.
#pasta_origem=$(pwd | sed -e s/' '/'\ '/g -e s/'('/'\('/g -e s/')'/'\)'/g)"/Modules/flyer"
pasta_origem="${11}"/flyer
# Fim - Horácio - 23/06/2018
cd "$pasta_origem"

palavrachave="$4"
nomedoapp="$5"
nomecoop="$6"
bairro="$7"
cidade="$8"
uf="$9"
nomeappstaxi="${10}"
# Inicio - Horácio - 23/06/2018
imagensdir="$1/${12}"/imagensGeradas
# Fim - Horácio - 23/06/2018

case $3 in
	"Taxista" )
	tipoveiculo="táxi"
	site_mch="apptaxi.mobi"
	;;
	"Motorista" )
	tipoveiculo="carro executivo"
	site_mch="carro.mobi"
	;;
	"Mototaxista" )
	tipoveiculo="mototáxi"
	site_mch="mototaxi.mobi"
	;;
esac

#tipo='táxi'
#palavrachave='FONETAXIFEIRA'
#nomedoapp='Fone Taxi'
#nomecoop='fonetaxifeira'
#cidade='Feira de Santana'
#uf='BA'

# pwd=$(pwd)

# echo "TXMDBG $pwd"

echo "Gerando textos do flyer..."

convert -background '#f6ba17ff' -fill white -font verdana  -pointsize 120 label:"Chame $tipoveiculo\ncom praticidade!" imagens/title-$nomecoop.png
composite -geometry +1100+515 imagens/title-$nomecoop.png imagens/FlyerNovo.png imagens/flyer2-$nomecoop.png
rm imagens/title-$nomecoop.png

convert -background '#1c2020ff' -fill white -font verdana  -pointsize 60 label:"Abra a Play Store \(aparelhos Android\)\nou a App Store \(iPhones\) e clique no\nícone Buscar." imagens/step1-$nomecoop.png
composite -geometry +267+2310 imagens/step1-$nomecoop.png imagens/flyer2-$nomecoop.png imagens/flyer2-$nomecoop.png
rm imagens/step1-$nomecoop.png

convert -background '#1c2020ff' -fill white -font verdana  -pointsize 60 label:"Digite $palavrachave.\nApós a busca, encontre o nosso\naplicativo e clique em \"Instalar\"." imagens/step2-$nomecoop.png
composite -geometry +267+2592 imagens/step2-$nomecoop.png imagens/flyer2-$nomecoop.png imagens/flyer2-$nomecoop.png
rm imagens/step2-$nomecoop.png

convert -background '#1c2020ff' -fill white -font verdana  -pointsize 60 label:"Clique no ícone \"$nomedoapp\". Em\nseguida, clique em Cadastrar. Após o\ncadastro, já poderá utilizar o aplicativo!" imagens/step3-$nomecoop.png
composite -geometry +267+2878 imagens/step3-$nomecoop.png imagens/flyer2-$nomecoop.png imagens/flyer2-$nomecoop.png
rm imagens/step3-$nomecoop.png  

convert -background '#f6ba17ff' -fill white -font verdana -gravity center -pointsize 72 -size 1221 caption:"$site_mch/$nomeappstaxi" imagens/contact-$nomecoop.png
composite -geometry +1152+1194 imagens/contact-$nomecoop.png imagens/flyer2-$nomecoop.png imagens/flyer2-$nomecoop.png
rm imagens/contact-$nomecoop.png

echo "Gerando logos do flyer..."

# Inicio - Horácio - 23/06/2018
#cp "$1"/Design/imagensGeradas/648x228fa.png imagens/432x152fa.png
#cp "$1"/Design/imagensGeradas/648x228fp.png imagens/432x152fp.png
cp "$imagensdir"/648x228fa.png imagens/432x152fa-$nomecoop.png
cp "$imagensdir"/648x228fp.png imagens/432x152fp-$nomecoop.png
# Fim - Horácio - 23/06/2018
sips -Z 432 imagens/432x152fa-$nomecoop.png imagens/432x152fp-$nomecoop.png

##topo
# Inicio - Horácio - 23/06/2018
#composite -geometry +1438+59 "$1"/Design/imagensGeradas/648x228fa.png imagens/flyer2.png imagens/flyer2.png
composite -geometry +1438+59 "$imagensdir"/648x228fa.png imagens/flyer2-$nomecoop.png imagens/flyer2-$nomecoop.png
# Fim - Horácio - 23/06/2018

##navbar
cp imagens/432x152fa-$nomecoop.png imagens/238x85fa-$nomecoop.png
sips -Z 238 imagens/238x85fa-$nomecoop.png
composite -geometry +463+322 imagens/238x85fa-$nomecoop.png imagens/flyer2-$nomecoop.png imagens/flyer2-$nomecoop.png
rm imagens/238x85fa-$nomecoop.png

##logos abaixo dos aparelhos/Users/colaborador006/Dropbox (Gaudium)/Operacao/Projetos andamento/Proj076-TaxiMachine/Implantação/Novas bandeiras/modules/flyer/flyer.sh
composite -geometry +1750+3301 imagens/432x152fp-$nomecoop.png imagens/flyer2-$nomecoop.png imagens/flyer2-$nomecoop.png

rm imagens/432x152fp-$nomecoop.png
rm imagens/432x152fa-$nomecoop.png

echo "Inserindo qrcodes..."
composite -geometry +165+3261 "$pasta_mkt"/QRCodes/qrcode-android-passageiro.png imagens/flyer2-$nomecoop.png imagens/flyer2-$nomecoop.png
composite -geometry +819+3261 "$pasta_mkt"/QRCodes/qrcode-iOS-passageiro.png imagens/flyer2-$nomecoop.png imagens/flyer2-$nomecoop.png

chavegoogle="AIzaSyCmUYZGEKHm7XIt_Ku1uFQbn9Ujc5PBR8k"

#echo "Gerando mapas..."

#python ../mapa/mapa.py $3 $bairro,$cidade,$uf

echo "Colocando mapa no flyer..."
# Inicio - Horácio - 23/06/2018
#convert -crop 782x909+0+0 +repage ../mapa/imagens/mapa.png ../mapa/imagens/mapa.png
#composite -geometry +193+416 ../mapa/imagens/mapa.png imagens/flyer2.png imagens/flyer2.png
cp "$imagensdir"/mapa.png imagens/mapa-$nomecoop.png
convert -crop 782x909+0+0 +repage imagens/mapa-$nomecoop.png imagens/mapa-$nomecoop.png
composite -geometry +193+416 imagens/mapa-$nomecoop.png imagens/flyer2-$nomecoop.png imagens/flyer2-$nomecoop.png
rm imagens/mapa-$nomecoop.png
# Fim - Horácio - 23/06/2018

#cp ../mapa/imagens/mapa*.png "$1"/Design/imagensGeradas/

mkdir -p "$pasta_mkt"/Flyer
cp imagens/flyer2-$nomecoop.png "$pasta_mkt"/Flyer/flyer.png
rm imagens/flyer2-$nomecoop.png
cd -
