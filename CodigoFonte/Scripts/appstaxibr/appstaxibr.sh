#!/bin/bash

# Inicio - Horácio - 01/07/2018
imagensdir="$1/$4"/imagensGeradas
# Fim - Horácio - 01/07/2018

#convert "$1"/Design/imagensGeradas/mapa.png -resize 318x369 "$1"/Design/imagensGeradas/mapa2.png
convert "$imagensdir"/mapa.png -resize 318x369 "$imagensdir"/mapa2.png

#echo "-----------------===========-------------------"
#pwd
#echo "-----------------===========-------------------"

#composite -geometry +601+192 "$1"/Design/imagensGeradas/mapa2.png modules/appstaxibr/imagens/appstaxi.png "$1"/Design/imagensGeradas/appstaxi.png

# Inicio - Horácio - 01/07/2018
# Modificação das pastas
#composite -geometry +646+192 "$1"/Design/imagensGeradas/mapa2.png modules/appstaxibr/imagens/appstaxi.png "$1"/Design/imagensGeradas/appstaxi.png
#composite -geometry +706+151 "$1"/Design/imagensGeradas/108x38fa.png "$1"/Design/imagensGeradas/appstaxi.png "$1"/Design/imagensGeradas/appstaxi.png
#composite -geometry +0+0 modules/appstaxibr/imagens/appstaxi-over-"$2".png "$1"/Design/imagensGeradas/appstaxi.png "$1"/Design/imagensGeradas/appstaxi.png
#composite -geometry +353+96 "$1"/Design/imagensGeradas/318x343.png "$1"/Design/imagensGeradas/appstaxi.png "$1"/Design/imagensGeradas/appstaxi.png
composite -geometry +646+192 "$imagensdir"/mapa2.png "$3"/appstaxibr/imagens/appstaxi.png "$imagensdir"/appstaxi.png
composite -geometry +706+151 "$imagensdir"/108x38fa.png "$imagensdir"/appstaxi.png "$imagensdir"/appstaxi.png
composite -geometry +0+0 "$3"/appstaxibr/imagens/appstaxi-over-"$2".png "$imagensdir"/appstaxi.png "$imagensdir"/appstaxi.png
composite -geometry +353+96 "$imagensdir"/318x343.png "$imagensdir"/appstaxi.png "$imagensdir"/appstaxi.png
# Fim - Horácio - 01/07/2018