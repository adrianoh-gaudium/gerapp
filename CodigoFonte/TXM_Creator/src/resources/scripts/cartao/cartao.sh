#!/bin/bash

pasta_mkt="$1/$2"
rm -rf "$pasta_mkt"/Cartao
mkdir "$pasta_mkt"/Cartao
#pasta_origem=$(pwd | sed -e s/' '/'\ '/g -e s/'('/'\('/g -e s/')'/'\)'/g)"/Modules/cartao"
#cd "$pasta_origem"

tipo="$3"
nomeappstaxi="$4"
telefone="$5"
pasta_origem="$6"/cartao/imagens
imagensdir="$1/$7"/imagensGeradas

echo "Gerando textos do cartão do app..."

# Inicio - Horácio - 01/07/2018
#cp cartao-"$tipo".png "$pasta_mkt"/Cartao/cartao.png
cp "$pasta_origem"/cartao-"$tipo".png "$pasta_mkt"/Cartao/cartao.png
# Fim - Horácio - 01/07/2018

if [ "$telefone" != "none" ]; then
# Inicio - Horácio - 01/07/2018
#	convert -background '#ffffffff' -fill black -font VerdanaB -gravity center -pointsize 46 -size 662 caption:"$telefone" telefone.png;
#	composite -geometry +402+28 telefone.png "$pasta_mkt"/Cartao/cartao.png "$pasta_mkt"/Cartao/cartao.png;
	convert -background '#ffffffff' -fill black -font VerdanaB -gravity center -pointsize 46 -size 662 caption:"$telefone" "$pasta_origem"/telefone-{$nomeappstaxi}.png;
	composite -geometry +402+28 "$pasta_origem"/telefone-{$nomeappstaxi}.png "$pasta_mkt"/Cartao/cartao.png "$pasta_mkt"/Cartao/cartao.png;
	rm "$pasta_origem"/telefone-{$nomeappstaxi}.png   #arquivo não é mais necessário
# Fim - Horácio - 01/07/2018
fi

# Inicio - Horácio - 01/07/2018
#convert -background '#ffffffff' -fill black -font VerdanaB -gravity center -pointsize 46 -size 1063 caption:"apps.taxi.br/$nomeappstaxi" appstaxibr.png
#composite -geometry +0+510 appstaxibr.png "$pasta_mkt"/Cartao/cartao.png "$pasta_mkt"/Cartao/cartao.png
convert -background '#ffffffff' -fill black -font VerdanaB -gravity center -pointsize 46 -size 1063 caption:"apps.taxi.br/$nomeappstaxi" "$pasta_origem"/appstaxibr-{$nomeappstaxi}.png
composite -geometry +0+510 "$pasta_origem"/appstaxibr-{$nomeappstaxi}.png "$pasta_mkt"/Cartao/cartao.png "$pasta_mkt"/Cartao/cartao.png
rm "$pasta_origem"/appstaxibr-{$nomeappstaxi}.png   #arquivo não é mais necessário
# Fim - Horácio - 01/07/2018

#imagemcoop
# Inicio - Horácio - 01/07/2018
#composite -geometry +85+114 "$pasta_mkt"/../Design/imagensGeradas/300x367.png "$pasta_mkt"/Cartao/cartao.png "$pasta_mkt"/Cartao/cartao.png
composite -geometry +85+114 "$imagensdir"/300x367.png "$pasta_mkt"/Cartao/cartao.png "$pasta_mkt"/Cartao/cartao.png
# Fim - Horácio - 01/07/2018

composite -geometry +815+201 "$pasta_mkt"/QRCodes/qrcode-apps-taxi-br.png "$pasta_mkt"/Cartao/cartao.png "$pasta_mkt"/Cartao/cartao.png