#!/bin/bash

pasta_mkt="$1/$2"
rm -rf "$pasta_mkt"/Adesivo
mkdir -p "$pasta_mkt"/Adesivo
#pasta_origem=$(pwd | sed -e s/' '/'\ '/g -e s/'('/'\('/g -e s/')'/'\)'/g)"/Modules/adesivo"
#cd "$pasta_origem"

tipo="$3"
nomeappstaxi="$4"
telefone=$(echo "$5" | cut -d" " -f2)

case $3 in
	"Taxista" )
	tipoveiculo="táxi"
	site_mch="meuapp.mobi"
	;;
	"Motorista" )
	tipoveiculo="carro executivo"
	site_mch="meuapp.mobi"
	;;
	"Mototaxista" )
	tipoveiculo="mototáxi"
	site_mch="meuapp.mobi"
	;;
esac

pasta_origem="$6"/adesivo/imagens
imagensdir="$1/$7"/imagensGeradas


echo "Gerando textos do adesivo do app..."

#cp adesivo.png "$pasta_mkt"/Adesivo/adesivo.png

if [ "$telefone" != "none" ]; then
# INICIO - Horácio - 01/07/2018
#	cp adesivo.png "$pasta_mkt"/Adesivo/adesivo.png
#	convert -background '#f7ca40' -fill black -font VerdanaB -gravity center -pointsize 300 -size 2449 caption:"$telefone" telefone.png;
#	composite -geometry +1093+818 telefone.png "$pasta_mkt"/Adesivo/adesivo.png "$pasta_mkt"/Adesivo/adesivo.png;
	cp "$pasta_origem"/adesivo.png "$pasta_mkt"/Adesivo/adesivo.png
	convert -background '#f7ca40' -fill black -font VerdanaB -gravity center -pointsize 300 -size 2449 caption:"$telefone" "$pasta_origem"/telefone-$nomeappstaxi.png;
	composite -geometry +1093+818 "$pasta_origem"/telefone-$nomeappstaxi.png "$pasta_mkt"/Adesivo/adesivo.png "$pasta_mkt"/Adesivo/adesivo.png;
	rm "$pasta_origem"/telefone-$nomeappstaxi.png  #arquivo não mais necessário
# FIM - Horácio - 01/07/2018
else
	cp "$pasta_origem"/adesivo-st.png "$pasta_mkt"/Adesivo/adesivo.png;
fi

# INICIO - Horácio - 01/07/2018
#convert -background '#ffffffff' -fill black -font VerdanaB -gravity center -pointsize 167 -size 3542 caption:"apps.taxi.br/$nomeappstaxi" appstaxibr.png
#composite -geometry +0+1276 appstaxibr.png "$pasta_mkt"/Adesivo/adesivo.png "$pasta_mkt"/Adesivo/adesivo.png
convert -background '#ffffffff' -fill black -font VerdanaB -gravity center -pointsize 167 -size 3542 caption:"$site_mch/$nomeappstaxi" "$pasta_origem"/appstaxibr-$nomeappstaxi.png
composite -geometry +0+1276 "$pasta_origem"/appstaxibr-$nomeappstaxi.png "$pasta_mkt"/Adesivo/adesivo.png "$pasta_mkt"/Adesivo/adesivo.png
rm "$pasta_origem"/appstaxibr-$nomeappstaxi.png  #arquivo não mais necessário
# FIM - Horácio - 01/07/2018

#imagemcoop
# INICIO - Horácio - 01/07/2018
#composite -geometry +334+124 "$pasta_mkt"/../Design/imagensGeradas/720x1080.png "$pasta_mkt"/Adesivo/adesivo.png "$pasta_mkt"/Adesivo/adesivo.png
composite -geometry +334+124 "$imagensdir"/720x1080.png "$pasta_mkt"/Adesivo/adesivo.png "$pasta_mkt"/Adesivo/adesivo.png
# FIM - Horácio - 01/07/2018
